/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import com.adeclerk.beetusbot.irc.UserRegisterHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        conn.addIRCEventListener(new AddBgHandler(conn,CHANNEL));
        conn.addIRCEventListener(new BgLogHandler(conn,CHANNEL));
        conn.addIRCEventListener(new ConnectionHandler(conn,CHANNEL));
        conn.addIRCEventListener(new HelpHandler(conn,CHANNEL));
        conn.addIRCEventListener(new NickChangeHandler(conn,CHANNEL));
        conn.addIRCEventListener(new OptInHandler(conn, CHANNEL));
        conn.addIRCEventListener(new OptOutHandler(conn, CHANNEL));
        conn.addIRCEventListener(new ReconnectHandler(conn,CHANNEL));
        conn.addIRCEventListener(new RmBgHandler(conn,CHANNEL));
        conn.addIRCEventListener(new UserRegisterHandler(conn));
        
        conn.addTrustManager(new SSLDefaultTrustManager());
        conn.setDaemon(false);
        conn.setColors(true);
        conn.setPong(true);

        try {
            System.out.println("DO CONNECT");
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
