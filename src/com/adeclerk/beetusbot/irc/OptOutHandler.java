/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import com.adeclerk.beetusbot.dao.UserDao;
import com.adeclerk.beetusbot.log.Logger;
import com.adeclerk.beetusbot.model.User;
import java.util.StringTokenizer;
import org.schwering.irc.lib.IRCEventAdapter;
import org.schwering.irc.lib.IRCEventListener;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class OptOutHandler extends GenericHandler{

    private UserDao userDao;
    public OptOutHandler(SSLIRCConnection conn, String channel) {
        super(conn,channel);
        this.userDao = new UserDao();
    }
    @Override
    public void onPrivmsg(String target, IRCUser user, String msg) {
        if(target.equals(channel)) {
            StringTokenizer tokenizer = new StringTokenizer(msg);
            String command = tokenizer.nextToken();
            
            if(command.equals(".beetusbot-optout")) {
                User usr = userDao.getUserByNick(user.getNick());
                if(user == null) {
                    conn.doPrivmsg(channel, "User " + user.getNick() + " not opted in!");
                } else {
                    userDao.delete(usr);
                    logger.log("user_deleted", user.getNick());
                }
            }
        }
    }
    
}
