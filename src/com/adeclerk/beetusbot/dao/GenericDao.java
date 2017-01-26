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

import java.util.ArrayList;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author adeclerk
 */
public interface GenericDao<T> {

    public abstract void create(T object);

    public abstract void createAll(ArrayList<T> list);

    public abstract T read(long id);

    public abstract ArrayList<T> readAll();

    public abstract T findByCriteria(ArrayList<Criterion> criteria);

    public abstract ArrayList<T> findAllByCriteria(ArrayList<Criterion> criteria);

    public abstract void update(T object);

    public abstract void updateAll(ArrayList<T> objects);

    public abstract void delete(T object);

    public abstract T getItemByField(String field, String value);

}
