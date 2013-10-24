/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author adeclerk
 */
@Entity
@Table(name="user")
@GenericGenerator(name="incr",strategy="increment")
public class User {
    @Id
    @GeneratedValue(generator="incr")
    private int id;
    
    private String nick;

    @OneToMany(fetch=FetchType.EAGER, mappedBy="user")
    private Set<BloodGlucose> bloodGlucose = new HashSet<BloodGlucose>();
    public User() { }
    public User(String nick) {
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

    /**
     * @return the bloodGlucose
     */
    public Set<BloodGlucose> getBloodGlucose() {
        return bloodGlucose;
    }

    /**
     * @param bloodGlucose the bloodGlucose to set
     */
    public void setBloodGlucose(Set<BloodGlucose> bloodGlucose) {
        this.bloodGlucose = bloodGlucose;
    }
}
