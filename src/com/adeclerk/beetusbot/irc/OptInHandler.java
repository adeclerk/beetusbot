/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import com.adeclerk.beetusbot.dao.PendingUserDao;
import com.adeclerk.beetusbot.dao.UserDao;
import com.adeclerk.beetusbot.model.PendingUser;
import java.util.StringTokenizer;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.ssl.SSLIRCConnection;
/**
 *
 * @author adeclerk
 */
public class OptInHandler extends GenericHandler{
   private PendingUserDao pendingDao;
   private UserDao userDao;
   
   public OptInHandler(SSLIRCConnection conn, String channel) {
       super(conn,channel);
       this.pendingDao = new PendingUserDao();
       this.userDao = new UserDao();
   }
   
      public void onPrivmsg(String target, IRCUser user, String msg) {
        if (target.equals(channel)) {
            StringTokenizer tokenizer = new StringTokenizer(msg);
            String command = tokenizer.nextToken();
            if (command.equals(".beetusbot-optin")) {
                register(user);
            } 
        }
    }
    
    public void register(IRCUser user) {
        pendingDao.create(new PendingUser(user.getNick()));
        conn.doPrivmsg(user.getNick(), "beetusbot opt-in~");
        conn.doPrivmsg(user.getNick(), "select unit: 1 (mg/dL) 2 (mmol/L)");
    }
}
