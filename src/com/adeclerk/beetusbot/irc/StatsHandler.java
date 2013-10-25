/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import com.adeclerk.beetusbot.dao.UserDao;
import com.adeclerk.beetusbot.model.BloodGlucose;
import com.adeclerk.beetusbot.model.User;
import java.util.Set;
import java.util.StringTokenizer;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class StatsHandler extends GenericHandler{
    private UserDao userDao;
    
    public StatsHandler(SSLIRCConnection conn, String channel) {
        super(conn,channel);
        userDao = new UserDao();
    }

    @Override
    public void onPrivmsg(String target, IRCUser user, String msg) {
        if(target.equals(channel)) {
            StringTokenizer tokenizer = new StringTokenizer(msg);
            String command = tokenizer.nextToken();
            if(command.equals(".bg-stats")) {
                User usr = userDao.getUserByNick(user.getNick());
                Set<BloodGlucose> bgs = usr.getBloodGlucose();
                int bgCount = bgs.size();
                
                double bgAvg = 0;
                for(BloodGlucose bg : bgs) 
                    bgAvg += Double.parseDouble(bg.getValue());
                bgAvg /= bgCount;
                
                conn.doPrivmsg(channel, user.getNick() + " : bg count = " + Integer.toString(bgCount) + " | bg avg = " + Double.toString(bgAvg));
            }
        }
    }
}
