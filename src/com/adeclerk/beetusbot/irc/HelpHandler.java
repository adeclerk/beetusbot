/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import java.util.StringTokenizer;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class HelpHandler extends GenericHandler {

    public HelpHandler(SSLIRCConnection conn, String channel) {
        super(conn, channel);
    }

    @Override
    public void onPrivmsg(String target, IRCUser user, String msg) {
        if (target.equals(channel)) {
            StringTokenizer tokenizer = new StringTokenizer(msg);
            String command = tokenizer.nextToken();
            if (command.equals(".beetusbot-help")) {
                conn.doPrivmsg(user.getNick(), "beetusbot help ===============");
                conn.doPrivmsg(user.getNick(), ".beetusbot-optin -> opt-in");
                conn.doPrivmsg(user.getNick(), ".beetusbot-optout -> opt-out");
                conn.doPrivmsg(user.getNick(), ".bg -> add bg if opted in");
                conn.doPrivmsg(user.getNick(), ".bgoops -> remove last bg");
                conn.doPrivmsg(user.getNick(), ".bg-log -> show link to public log");
                conn.doPrivmsg(user.getNick(), ".bg-stats -> show bg count and avg");
            }
        }
    }
}
