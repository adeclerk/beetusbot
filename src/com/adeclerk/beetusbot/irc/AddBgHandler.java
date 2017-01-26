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

import com.adeclerk.beetusbot.dao.BloodGlucoseDao;
import com.adeclerk.beetusbot.dao.UserDao;
import com.adeclerk.beetusbot.model.BloodGlucose;
import com.adeclerk.beetusbot.model.User;
import java.util.StringTokenizer;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class AddBgHandler extends GenericHandler {

    private BloodGlucoseDao bgDao;
    private UserDao userDao;

    public AddBgHandler(SSLIRCConnection conn, String channel) {
        super(conn, channel);
        bgDao = new BloodGlucoseDao();
        userDao = new UserDao();
    }

    @Override
    public void onPrivmsg(String target, IRCUser user, String msg) {
        if (target.equals(channel)) {
            StringTokenizer tokenizer = new StringTokenizer(msg);
            String command = tokenizer.nextToken();
            if (command.equals(".bg")) {
                if (tokenizer.hasMoreTokens()) {
                    String bg = tokenizer.nextToken();
                    String comment = null;
                    if (tokenizer.hasMoreElements()) {
                        comment = tokenizer.nextToken();
                    }
                    User usr = userDao.getUserByNick(user.getNick());
                    if (usr != null && isNumeric(bg)) {
                        bgDao.create(new BloodGlucose(usr, bg, comment));
                        logger.log("bg_added", user.getNick() + " " + bg + " " + usr.getUnit());
                    }
                }
            }
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
