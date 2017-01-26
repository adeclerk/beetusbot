/*
 *  This file is part of BeetusBot.
 *
 *  BeetusBot is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BeetusBot is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with BeetusBot.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.adeclerk.beetusbot.irc;

import com.adeclerk.beetusbot.dao.PendingUserDao;
import com.adeclerk.beetusbot.dao.UserDao;
import com.adeclerk.beetusbot.log.Logger;
import com.adeclerk.beetusbot.model.PendingUser;
import com.adeclerk.beetusbot.model.User;
import org.schwering.irc.lib.IRCEventAdapter;
import org.schwering.irc.lib.IRCEventListener;
import org.schwering.irc.lib.ssl.SSLIRCConnection;
import org.schwering.irc.lib.IRCUser;

/**
 *
 * @author adeclerk
 */
public class UserRegisterHandler extends IRCEventAdapter implements IRCEventListener {

    private SSLIRCConnection conn = null;
    private Logger logger;
    private PendingUserDao pendingDao = new PendingUserDao();
    private UserDao userDao = new UserDao();

    public UserRegisterHandler(SSLIRCConnection conn) {
        this.conn = conn;
        this.logger = Logger.getInstance();
    }

    public UserRegisterHandler(SSLIRCConnection conn, String CHANNEL) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onPrivmsg(String target, IRCUser user, String msg) {
        if (target.equals("beetusbot")) {
            PendingUser pUser = pendingDao.getPendingUser(user.getNick());
            if (pUser != null) {
                if (msg.equals("1")) {
                    conn.doPrivmsg(user.getNick(), "mg/dL selected.");
                    userDao.create(new User(user.getNick(),"mg/dL"));
                    pendingDao.delete(pUser);
                    logger.log("user_added", user.getNick());
                } else if (msg.equals("2")) {
                    conn.doPrivmsg(user.getNick(), "mmol/L selected.");
                    userDao.create(new User(user.getNick(),"mmol/L"));
                    pendingDao.delete(pUser);
                    logger.log("user_added", user.getNick());
                } else {
                    return;
                }
            } else {
                conn.doPrivmsg(user.getNick(), "Whatchu talkin' 'bout, Willis?");
            }
        } 
        return;
    }
}
