/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeclerk.beetusbot.dao;

import java.util.ArrayList;
import org.hibernate.Criteria;
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
