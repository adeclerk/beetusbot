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
            Thread.sleep(6000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ReconnectHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        conn.close();
        conn = new SSLIRCConnection(
            "irc.freenode.net",
            new int[]{6697, 7000, 7070},
            null,
            "beetusbot",
            "beetus",
            "bg loggin bot");
        conn.doJoin(channel);
 
    }
}
