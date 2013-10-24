/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.dao;

import com.adeclerk.beetusbot.db.DatabaseUtil;
import com.adeclerk.beetusbot.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author adeclerk
 */
public class UserDao extends HibernateDao<User>{
    
    public User getUserByNick(String nick) {
        Session dbSess = DatabaseUtil.getSession();
        Criteria crit = dbSess.createCriteria(User.class);
        crit.add(Restrictions.eq("nick", nick));
        User ret = null;
        try {
            ret = (User) crit.uniqueResult();
        } catch(Exception e) {
            System.out.println("ECEPTION");
            return null;
            
        } finally {
            dbSess.close();
            return ret;
        }
    }
}
