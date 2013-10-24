/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.irc;

import com.adeclerk.beetusbot.dao.BloodGlucoseDao;
import com.adeclerk.beetusbot.dao.UserDao;
import com.adeclerk.beetusbot.log.Logger;
import com.adeclerk.beetusbot.model.BloodGlucose;
import com.adeclerk.beetusbot.model.User;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.StringTokenizer;
import org.schwering.irc.lib.IRCEventAdapter;
import org.schwering.irc.lib.IRCEventListener;
import org.schwering.irc.lib.IRCModeParser;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.ssl.SSLIRCConnection;

/**
 *
 * @author adeclerk
 */
public class EventListener extends IRCEventAdapter implements IRCEventListener {

    private Logger logger = null;
    SSLIRCConnection conn = null;
    private String channel = "#distsms-devel";

    public EventListener(SSLIRCConnection conn, String channel) {
        logger = Logger.getInstance();
        this.conn = conn;
        this.channel = channel;
    }

    public void onConnect() {
        System.out.println("Connected successfully.");
    }

    public void onDisconnected() {
        try {
            logger.log("disconnect", "disconnected");
            conn.connect();
        } catch (IOException ex) {
        }
    }

    public void onError(String msg) {
        logger.log("error", msg);
    }

    public void onError(int num, String msg) {
        logger.log("error", msg);
    }

    public void onInvite(String chan, IRCUser user, String nickPass) {
        System.out.println("INVITE: " + user.getNick()
                + " invites " + nickPass + " to " + chan);
    }

    public void onJoin(String chan, IRCUser user) {
        System.out.println("JOIN: " + user.getNick()
                + " joins " + chan);
        // add the nickname to the nickname-table
    }

    public void onKick(String chan, IRCUser user, String nickPass, String msg) {
        System.out.println("KICK: " + user.getNick()
                + " kicks " + nickPass + "(" + msg + ")");
        // remove the nickname from the nickname-table
    }

    public void onMode(String chan, IRCUser user, IRCModeParser modeParser) {
        System.out.println("MODE: " + user.getNick()
                + " changes modes in " + chan + ": " + modeParser.getLine());
        // some operations with the modes
    }

    public void onNick(IRCUser user, String nickNew) {
        UserDao userDao = new UserDao();
        User usr = userDao.getUserByNick(user.getNick());
        if (usr != null) {
            usr.setNick(nickNew);
            userDao.update(usr);
        }
    }

    public void onPart(String chan, IRCUser user, String msg) {
        System.out.println("PART: " + user.getNick()
                + " parts from " + chan + "(" + msg + ")");
        // remove the nickname from the nickname-table
    }

    public void onSignup(IRCUser user) {
        UserDao userDao = new UserDao();
        User tmp = userDao.getUserByNick(user.getNick());
        if (tmp != null) {
            conn.doPrivmsg(channel, "User " + user.getNick() + " already opted in!");
        } else {
            userDao.create(new User(user.getNick()));
            conn.doPrivmsg(channel, "User " + user.getNick() + " opted in");
            logger.log("user_added", user.getNick());
        }
    }

    public void onSignout(IRCUser user) {
        UserDao userDao = new UserDao();
        User tmp = userDao.getUserByNick(user.getNick());

        if (tmp != null) {
            userDao.delete(tmp);
            conn.doPrivmsg(channel, "User " + user.getNick() + " opted out.");
            logger.log("user_removed", user.getNick());
        } else {
            conn.doPrivmsg(channel, "User " + user.getNick() + " not opted in!");
        }
    }

    public void onBg(IRCUser user, String bg, String comment) {
        UserDao userDao = new UserDao();
        User tmp = userDao.getUserByNick(user.getNick());
        if (tmp != null) {
            if (isNumeric(bg)) {
                BloodGlucoseDao bgDao = new BloodGlucoseDao();
                bgDao.create(new BloodGlucose(tmp, bg, comment));
                conn.doPrivmsg(channel, user.getNick() + ": bg " + bg + " added ");
            } else {
                conn.doPrivmsg(channel, user.getNick() + ": that isn't a bg, silly goose!");
            }
        }
    }

    public void onHelp(IRCUser user) {
        conn.doPrivmsg(user.getNick(), ".beetusbot-signup -> opt-in");
        conn.doPrivmsg(user.getNick(), ".beetusbot-signout -> opt-out");
        conn.doPrivmsg(user.getNick(), ".bg -> add bg if opted in");
        conn.doPrivmsg(user.getNick(), ".bgoops -> remove last bg");
        conn.doPrivmsg(user.getNick(), ".bg-log -> show link to public log");
    }

    public void onBgOops(IRCUser user) {
        UserDao userDao = new UserDao();
        User usr = userDao.getUserByNick(user.getNick());
        if (usr != null) {
            Set<BloodGlucose> bgs = usr.getBloodGlucose();
            BloodGlucose rm = Collections.max(bgs);
            BloodGlucoseDao bgDao = new BloodGlucoseDao();
            bgDao.delete(rm);

        }
    }

    public void onLogLink(IRCUser user) {
        UserDao userDao = new UserDao();
        User tmp = userDao.getUserByNick(user.getNick());
        if (tmp != null) {
            conn.doPrivmsg(channel, "https://highasdick.biz:8080/bg/log?user=" + tmp.getNick());
        }
    }

    public void onPrivmsg(String target, IRCUser user, String msg) {
        if (target.equals(channel)) {
            StringTokenizer tokenizer = new StringTokenizer(msg);
            String command = tokenizer.nextToken();
            if (command.equals(".beetusbot-signup")) {
                onSignup(user);
            } else if (command.equals(".beetusbot-signout")) {
                onSignout(user);
            } else if (command.equals(".bg")) {
                String bg = tokenizer.nextToken();
                String comment = null;
                if (tokenizer.hasMoreTokens()) {
                    comment = tokenizer.nextToken();
                }
                onBg(user, bg, comment);
            } else if (command.equals(".bgoops")) {
                onBgOops(user);
            } else if (command.equals(".beetusbot-help")) {
                onHelp(user);
            } else if (command.equals(".bg-log")) {
                onLogLink(user);
            } else {
                return;
            }
        }
    }

    public void onQuit(IRCUser user, String msg) {
        System.out.println("QUIT: " + user.getNick() + " ("
                + user.getUsername() + "@" + user.getHost() + ") (" + msg + ")");
        // remove the nickname from the nickname-table
    }

    public void onReply(int num, String value, String msg) {
        System.out.println("Reply #" + num + ": Message: "
                + msg + " | Value: " + value);
    }

    public void onTopic(String chan, IRCUser user, String topic) {
        System.out.println("TOPIC: " + user.getNick()
                + " changes topic of " + chan + " into: " + topic);
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
