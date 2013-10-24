/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import com.adeclerk.beetusbot.dao.UserDao;
import com.adeclerk.beetusbot.model.User;
import java.util.StringTokenizer;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class BgLogHandler extends GenericHandler{
    private UserDao userDao;
    public BgLogHandler(SSLIRCConnection conn, String channel) {
        super(conn,channel);
        userDao = new UserDao();
    }

    @Override
    public void onPrivmsg(String target, IRCUser user, String msg) {
        if(target.equals(channel)) {
                        StringTokenizer tokenizer = new StringTokenizer(msg);
            String command = tokenizer.nextToken();
            if(command.equals(".bg-log")) {
                User usr = userDao.getUserByNick(user.getNick());
                if(usr != null) {
                    conn.doPrivmsg(channel, "https://highasdick.biz:8080/beetusbot/log?user=" + user.getNick());
                }
            }
        }
    }
}
