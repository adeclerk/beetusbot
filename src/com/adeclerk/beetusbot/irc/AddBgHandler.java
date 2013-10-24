/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class AddBgHandler extends GenericHandler{
    private BloodGlucoseDao bgDao;
    private UserDao userDao;
    public AddBgHandler(SSLIRCConnection conn, String channel) {
        super(conn,channel);
        bgDao = new BloodGlucoseDao();
        userDao = new UserDao();
    }
    @Override
    public void onPrivmsg(String target, IRCUser user, String msg) {
        if(target.equals(channel)) {
            StringTokenizer tokenizer = new StringTokenizer(msg);
            String command = tokenizer.nextToken();
            if(command.equals(".bg")) {
                if(tokenizer.hasMoreTokens()) {
                    String bg = tokenizer.nextToken();
                    String comment = null;
                    if(tokenizer.hasMoreElements()) {
                        comment = tokenizer.nextToken();
                    }
                    User usr = userDao.getUserByNick(user.getNick());
                    if(usr != null && isNumeric(bg)) {
                        bgDao.create(new BloodGlucose(usr,bg,comment));
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
