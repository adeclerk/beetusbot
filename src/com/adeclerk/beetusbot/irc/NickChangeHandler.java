/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import com.adeclerk.beetusbot.dao.UserDao;
import com.adeclerk.beetusbot.model.User;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class NickChangeHandler extends GenericHandler{
    private UserDao userDao;
    public NickChangeHandler(SSLIRCConnection conn, String channel) {
        super(conn, channel);
        userDao = new UserDao();
    }

    @Override
    public void onNick(IRCUser user, String newNick) {
        User usr = userDao.getUserByNick(user.getNick());
        if(usr != null) {
            usr.setNick(newNick);
            userDao.update(usr);
        }
    }
    
    
}
