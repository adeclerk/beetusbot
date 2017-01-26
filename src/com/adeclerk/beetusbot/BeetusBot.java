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
package com.adeclerk.beetusbot;

import com.adeclerk.beetusbot.irc.AddBgHandler;
import com.adeclerk.beetusbot.irc.BgLogHandler;
import com.adeclerk.beetusbot.irc.ConnectionHandler;
import com.adeclerk.beetusbot.irc.HelpHandler;
import com.adeclerk.beetusbot.irc.NickChangeHandler;
import com.adeclerk.beetusbot.irc.OptInHandler;
import com.adeclerk.beetusbot.irc.OptOutHandler;
import com.adeclerk.beetusbot.irc.ReconnectHandler;
import com.adeclerk.beetusbot.irc.RmBgHandler;
import com.adeclerk.beetusbot.irc.StatsHandler;
import com.adeclerk.beetusbot.irc.UserRegisterHandler;
import java.io.IOException;
import org.schwering.irc.lib.ssl.SSLDefaultTrustManager;
import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class BeetusBot {

    /**
     * @param args the command line arguments
     */
    public static String CHANNEL;

    public static void main(String[] args) {
        CHANNEL = args[0];
        SSLIRCConnection conn = new SSLIRCConnection(
                "irc.freenode.net",
                new int[]{6697, 7000, 7070},
                null,
                "beetusbot",
                "beetus",
                "bg loggin bot");

        conn.addIRCEventListener(new AddBgHandler(conn, CHANNEL));
        conn.addIRCEventListener(new BgLogHandler(conn, CHANNEL));
        conn.addIRCEventListener(new ConnectionHandler(conn, CHANNEL));
        conn.addIRCEventListener(new HelpHandler(conn, CHANNEL));
        conn.addIRCEventListener(new NickChangeHandler(conn, CHANNEL));
        conn.addIRCEventListener(new OptInHandler(conn, CHANNEL));
        conn.addIRCEventListener(new OptOutHandler(conn, CHANNEL));
        conn.addIRCEventListener(new ReconnectHandler(conn, CHANNEL));
        conn.addIRCEventListener(new RmBgHandler(conn, CHANNEL));
        conn.addIRCEventListener(new UserRegisterHandler(conn));
        conn.addIRCEventListener(new StatsHandler(conn, CHANNEL));

        conn.addTrustManager(new SSLDefaultTrustManager());
        conn.setDaemon(false);
        conn.setColors(true);
        conn.setPong(true);

        try {
            conn.connect(); // Try to connect!!! Don't forget this!!!
            conn.doJoin(CHANNEL);
            conn.join();
        } catch (IOException ioexc) {
            ioexc.printStackTrace();
        } catch (InterruptedException ex) {
            conn.doPart(CHANNEL, "Later haters!");
        }
    }
}
