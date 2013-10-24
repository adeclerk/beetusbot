/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
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

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date added;
    
    private String unit;
    
    private String nick;
    @OneToMany(fetch=FetchType.EAGER, mappedBy="user")
    private Set<BloodGlucose> bloodGlucose = new HashSet<BloodGlucose>();
    public User() { }
    public User(String nick, String unit) {
        this.nick = nick;
        this.unit = unit;
        this.added = new Date();
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

    /**
     * @return the added
     */
    public Date getAdded() {
        return added;
    }

    /**
     * @param added the added to set
     */
    public void setAdded(Date added) {
        this.added = added;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
