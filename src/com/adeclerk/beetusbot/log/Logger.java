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
