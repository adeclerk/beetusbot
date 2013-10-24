/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot;

import com.adeclerk.beetusbot.irc.EventListener;
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
                "the beetus");

        conn.addIRCEventListener(new EventListener(conn, CHANNEL));
        conn.addTrustManager(new SSLDefaultTrustManager());
        conn.setDaemon(true);
        conn.setColors(false);
        conn.setPong(true);

        try {
            System.out.println("DO CONNECT");
            conn.connect(); // Try to connect!!! Don't forget this!!!
            conn.doJoin(CHANNEL);
            while (true) {
                Thread.sleep(1);
            }
        } catch (IOException ioexc) {
            ioexc.printStackTrace();
        } catch (InterruptedException ex) {
            conn.doPart(CHANNEL, "Later haters!");
        }
    }
}
