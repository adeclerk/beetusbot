/*
 *  This file is part of BeetusBot.
 *
 *  BeetusBot is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  BeetusBot is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with BeetusBot.  If not, see <http://www.gnu.org/licenses/>.
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
