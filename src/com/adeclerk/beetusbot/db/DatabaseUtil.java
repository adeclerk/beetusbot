/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
