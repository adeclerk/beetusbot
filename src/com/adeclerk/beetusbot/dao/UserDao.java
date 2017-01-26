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
