/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 *
 * @author adeclerk
 */
@Entity
@Table(name="pending_users")
@GenericGenerator(name="incr",strategy="increment")
public class PendingUser {
    @Id
    @GeneratedValue(generator="incr")
    private int id;
    private String nick;
    
    public PendingUser() { } 
    public PendingUser(String nick) {
        this.nick = nick;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * @param nick the nick to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }
}
