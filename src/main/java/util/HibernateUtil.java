package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by MSI on 2016-10-01.
 */
public class HibernateUtil {

    private static ServiceRegistry sr;
    private static SessionFactory sf;

    static {
        try {
            Configuration conf = new Configuration();
            conf.configure();
            sr = new ServiceRegistryBuilder().applySettings(conf.getProperties()).buildServiceRegistry();
            sf = conf.buildSessionFactory(sr);
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sf;
    }

    public static Session getSession() {
        return sf.openSession();
    }

}
