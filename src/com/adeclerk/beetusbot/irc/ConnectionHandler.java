/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class ConnectionHandler extends GenericHandler {
    
    public ConnectionHandler(SSLIRCConnection conn, String channel) {
        super(conn,channel);
    }

    @Override
    public void onRegistered() {
        logger.log("irc_connected", conn.getHost());
    }

    @Override
    public void onDisconnected() {
        logger.log("irc_disconnected", conn.getHost());
    }

    @Override
    public void onError(String msg) {
        logger.log("error",msg);
    }

    @Override
    public void onError(int num, String msg) {
        logger.log("erro", Integer.toString(num) + " : " + msg);
    }
    
}
