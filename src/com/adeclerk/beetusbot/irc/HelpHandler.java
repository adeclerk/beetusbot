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
