/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class ReconnectHandler extends GenericHandler {
    
    public ReconnectHandler(SSLIRCConnection conn, String channel) {
        super(conn,channel);
    }

    @Override
    public void onDisconnected() {
        try {
            Thread.sleep(60000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReconnectHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.connect();
            conn.doJoin(channel);
        } catch (IOException ex) {
            Logger.getLogger(ReconnectHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
