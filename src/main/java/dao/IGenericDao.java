package dao;

import java.util.List;
import java.util.Map;

/**
 * Created by MSI on 2016-10-01.
 */
public interface IGenericDao<T> {

    T get(Class<T> cl, Integer id);
    T save(T object);
    void update(T object);
    void delete(T object);
    List<T> query(String hsql, Map<String, Object> params);

}
