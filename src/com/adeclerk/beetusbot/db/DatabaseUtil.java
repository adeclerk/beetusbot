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
package com.adeclerk.beetusbot.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author adeclerk
 */
public class DatabaseUtil {
    
    private static SessionFactory sessionFactory = null;
    
    public static SessionFactory getSessionFactory() {
        if(DatabaseUtil.sessionFactory == null)
            sessionFactory = new AnnotationConfiguration().configure("/com/adeclerk/beetusbot/configuration/hibernate.cfg.xml").buildSessionFactory();
        return sessionFactory;
    }
    
    public static Session getSession() { 
        return getSessionFactory().openSession();
    }
    
    public static Transaction getTransaction(Session sess) {
        return sess.beginTransaction();
    }
}
