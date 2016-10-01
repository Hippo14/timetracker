package service;

import dao.IGenericDao;

import java.util.List;

/**
 * Created by MSI on 2016-10-01.
 */
public interface IGenericService<T> extends IGenericDao<T> {

    List<T> getAll();
    void deleteAll();

}
