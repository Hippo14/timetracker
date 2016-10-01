package service;

import dao.GenericDaoImpl;
import dao.IGenericDao;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by MSI on 2016-10-01.
 */
public class GenericServiceImpl<T> implements IGenericService<T> {

    private IGenericDao<T> dao;
    private Class<T> cl;

    public GenericServiceImpl(Class<T> cl, SessionFactory sf) {
        this.cl = cl;
        dao = new GenericDaoImpl<T>(cl, sf);
    }

    public T get(Class<T> cl, Integer id) {
        return (T) dao.get(cl, id);
    }

    public T save(T object) {
        return (T)dao.save(object);
    }

    public void update(T object) {
        dao.update(object);
    }

    public void delete(T object) {
        dao.delete(object);
    }

    public List<T> query(String hsql, Map<String, Object> params) {
        return (List<T>)dao.query(hsql, params);
    }

    public List<T> getAll() {
        return query("from " + cl.getName(), null);
    }

    public void deleteAll() {
        query("delete from " + cl.getName(),null);
    }

}
