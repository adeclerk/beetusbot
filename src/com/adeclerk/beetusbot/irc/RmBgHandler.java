/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import com.adeclerk.beetusbot.dao.BloodGlucoseDao;
import com.adeclerk.beetusbot.dao.UserDao;
import com.adeclerk.beetusbot.model.BloodGlucose;
import com.adeclerk.beetusbot.model.User;
import java.util.Collections;
import java.util.Set;
import java.util.StringTokenizer;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class RmBgHandler extends GenericHandler {

    private BloodGlucoseDao bgDao;
    private UserDao userDao;

    public RmBgHandler(SSLIRCConnection conn, String channel) {
        super(conn, channel);
        bgDao = new BloodGlucoseDao();
        userDao = new UserDao();
    }

    @Override
    public void onPrivmsg(String target, IRCUser user, String msg) {
        if (target.equals(channel)) {
            StringTokenizer tokenizer = new StringTokenizer(msg);
            String command = tokenizer.nextToken();
            if (command.equals(".bgoops")) {

                User usr = userDao.getUserByNick(user.getNick());
                if (usr != null) {
                    Set<BloodGlucose> bgs = usr.getBloodGlucose();
                    BloodGlucose rm = Collections.max(bgs);
                    BloodGlucoseDao bgDao = new BloodGlucoseDao();
                    bgDao.delete(rm);

                }
            }
        }
    }
}
