/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import com.adeclerk.beetusbot.dao.PendingUserDao;
import com.adeclerk.beetusbot.dao.UserDao;
import com.adeclerk.beetusbot.log.Logger;
import com.adeclerk.beetusbot.model.PendingUser;
import com.adeclerk.beetusbot.model.User;
import java.util.StringTokenizer;
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
