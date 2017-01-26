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
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author adeclerk
 */
public class HibernateDao<T> implements GenericDao<T> {

    private Class<T> persistentClass;
    private Session session = null;
    private boolean open;

    public HibernateDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.open = false;
    }

    public HibernateDao(Session session) {
        this();
        this.session = session;
        this.open = true;
    }

    public Session getSession() {
        return DatabaseUtil.getSession();
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        getSession();
    }

    public void close() {
        getSession().close();
    }

    @Override
    public void create(T object) {
        Session dbSess;
        if (isOpen()) {
            dbSess = getSession();
        } else {
            dbSess = DatabaseUtil.getSession();
        }
        Transaction txn = dbSess.getTransaction();

        try {
            txn.begin();
            dbSess.persist(object);
            txn.commit();
        } catch (RuntimeException e) {
            txn.rollback();
        } finally {
            if (!isOpen()) {
                dbSess.close();
            }
        }
    }

    @Override
    public void createAll(ArrayList<T> list) {
        Session dbSess;
        if (isOpen()) {
            dbSess = getSession();
        } else {
            dbSess = DatabaseUtil.getSession();
        }
        Transaction txn = dbSess.getTransaction();

        try {
            txn.begin();
            for (T object : list) {
                dbSess.persist(object);
            }
            txn.commit();
        } catch (RuntimeException e) {
            txn.rollback();
        } finally {
            if (!isOpen()) {
                dbSess.close();
            }
        }
    }

    @Override
    public T read(long id) {
        Session dbSess;
        if (isOpen()) {
            dbSess = getSession();
        } else {
            dbSess = DatabaseUtil.getSession();
        }
        T entity = (T) dbSess.get(persistentClass, id);
        if (!isOpen()) {
            dbSess.close();
        }
        return entity;
    }

    @Override
    public ArrayList<T> readAll() {
        Session dbSess;
        if (isOpen()) {
            dbSess = getSession();
        } else {
            dbSess = DatabaseUtil.getSession();
        }
        Criteria crit = dbSess.createCriteria(persistentClass);

        ArrayList<T> entities = (ArrayList<T>) crit.list();
        if (!isOpen()) {
            dbSess.close();
        }
        return entities;
    }

    @Override
    public T findByCriteria(ArrayList<Criterion> criteria) {
        Session dbSess;
        if (isOpen()) {
            dbSess = getSession();
        } else {
            dbSess = DatabaseUtil.getSession();
        }
        Criteria crit = dbSess.createCriteria(persistentClass);
        for (Criterion c : criteria) {
            crit.add(c);
        }
        T entity = (T) crit.uniqueResult();
        if (!isOpen()) {
            dbSess.close();
        }
        return entity;
    }

    @Override
    public ArrayList<T> findAllByCriteria(ArrayList<Criterion> criteria) {
        Session dbSess;
        if (isOpen()) {
            dbSess = getSession();
        } else {
            dbSess = DatabaseUtil.getSession();
        }
        Criteria crit = dbSess.createCriteria(persistentClass);
        for (Criterion c : criteria) {
            crit.add(c);
        }
        ArrayList<T> entity = (ArrayList<T>) crit.list();
        if (!isOpen()) {
            dbSess.close();
        }
        return entity;
    }

    @Override
    public void update(T object) {
        Session dbSess;
        if (isOpen()) {
            dbSess = getSession();
        } else {
            dbSess = DatabaseUtil.getSession();
        }
        Transaction txn = dbSess.getTransaction();
        try {
            txn.begin();
            dbSess.saveOrUpdate(object);
            txn.commit();
        } catch (RuntimeException e) {
            txn.rollback();
        } finally {
            if (!isOpen()) {
                dbSess.close();
            }
        }
    }

    @Override
    public void updateAll(ArrayList<T> objects) {
        Session dbSess;
        if (isOpen()) {
            dbSess = getSession();
        } else {
            dbSess = DatabaseUtil.getSession();
        }
        Transaction txn = dbSess.getTransaction();
        try {
            txn.begin();
            for (T object : objects) {
                dbSess.update(object);
            }
            txn.commit();
        } catch (RuntimeException e) {
            txn.rollback();
        } finally {
            if (!isOpen()) {
                dbSess.close();
            }
        }
    }

    @Override
    public void delete(T object) {
        Session dbSess;
        if (isOpen()) {
            dbSess = getSession();
        } else {
            dbSess = DatabaseUtil.getSession();
        }
        Transaction txn = dbSess.getTransaction();
        try {
            txn.begin();
            dbSess.delete(object);
            txn.commit();
        } catch (RuntimeException e) {
            txn.rollback();
        } finally {
            if (!isOpen()) {
                dbSess.close();
            }
        }
    }

    @Override
    public T getItemByField(String field, String value) {
        Session dbSess;
        if (isOpen()) {
            dbSess = getSession();
        } else {
            dbSess = DatabaseUtil.getSession();
        }

        Criteria crit = dbSess.createCriteria(persistentClass);
        crit.add(Restrictions.eq(field, value));
        T entity = (T) crit.uniqueResult();

        if (!isOpen()) {
            dbSess.close();
        }
        return entity;
    }

}
