/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.log;

import com.adeclerk.beetusbot.dao.LogDao;
import com.adeclerk.beetusbot.model.Log;

/**
 *
 * @author adeclerk
 */
public class Logger {
    private static Logger self = null;
    private LogDao dao = null;
    private Logger() { 
        dao = new LogDao();
    }
    
    public static Logger getInstance() {
        if(self == null)
            self = new Logger();
        return self;
    }
    
    public void log(String event, String message) {
        dao.create(new Log(event,message));
    }
}
