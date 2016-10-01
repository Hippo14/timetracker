package dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;


/**
 * Created by MSI on 2016-10-01.
 */
public class GenericDaoImpl<T> implements IGenericDao<T> {

    private Logger log;
    private SessionFactory sf;

    public GenericDaoImpl(Class<T> cl, SessionFactory sf) {
        this.log = Logger.getLogger(cl.getName() + "GenericDAO");
        this.sf = sf;
        if (sf == null)
            throw new RuntimeException("Session factory is null!");
    }

    public T get(Class<T> cl, Integer id) {
        log.info("STARTED - get");
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        T element = (T) session.get(cl, id);
        session.getTransaction().commit();
        log.info("FINISHED - get");

        return element;
    }

    public T save(T object) {
        log.info("STARTED - save");
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        session.save(object);
        session.getTransaction().commit();
        log.info("FINISHED - save");

        return object;
    }

    public void update(T object) {
        log.info("STARTED - update");
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        session.update(object);
        session.getTransaction().commit();
        log.info("FINISHED - update");
    }

    public void delete(T object) {
        log.info("STARTED - delete");
        Session session = sf.getCurrentSession();
        session.beginTransaction();

        session.delete(object);
        session.getTransaction().commit();
        log.info("FINISHED - delete");
    }

    @SuppressWarnings("unchecked")
    public List<T> query(String hsql, Map<String, Object> params) {
        log.info("STARTED - query");
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery(hsql);
        if (params != null)
            for (String i : params.keySet())
                query.setParameter(i, params.get(i));

        List<T> result = null;
        if ((hsql.toUpperCase().indexOf("DELETE") == -1)
                && (hsql.toUpperCase().indexOf("UPDATE") == -1)
                && (hsql.toUpperCase().indexOf("INSERT") == -1)) {
            result = query.list();
            log.info("FINISHED - query. Result size=" + result.size());
        } else
            log.info("FINISHED - query. ");

        session.getTransaction().commit();
        return result;
    }

}
