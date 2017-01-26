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
