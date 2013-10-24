/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import com.adeclerk.beetusbot.log.Logger;
import org.schwering.irc.lib.IRCEventAdapter;
import org.schwering.irc.lib.IRCEventListener;
import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class GenericHandler extends IRCEventAdapter implements IRCEventListener{
    protected Logger logger;
    protected String channel;
    protected SSLIRCConnection conn;
    
    public GenericHandler(SSLIRCConnection conn, String channel) {
        this.logger = Logger.getInstance();
        this.channel = channel;
        this.conn = conn;
    }
}
