/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.dao;

import com.adeclerk.beetusbot.model.PendingUser;

/**
 *
 * @author adeclerk
 */
public class PendingUserDao extends HibernateDao<PendingUser>{
    
    public PendingUser getPendingUser(String name) {
        return super.getItemByField("nick", name);
    }
}
