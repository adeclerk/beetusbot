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
package com.adeclerk.beetusbot.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
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
    @OneToMany(fetch=FetchType.EAGER, mappedBy="user", cascade=CascadeType.ALL)
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
