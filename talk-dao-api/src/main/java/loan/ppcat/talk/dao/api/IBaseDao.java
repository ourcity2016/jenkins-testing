package loan.ppcat.talk.dao.api;


import loan.ppcat.talk.model.BucketPage;

import java.util.List;

public interface IBaseDao<T> {
    void save(T t) throws Exception;

    void delete(T t) throws Exception;

    void delete(int id) throws Exception;

    void update(T t) throws Exception;

    void saveOrUpdate(T t) throws Exception;

    T load(int id) throws Exception;

    List<T> list() throws Exception;

    BucketPage<T> find() throws Exception;

    BucketPage<T> find(int pageOffset, int pageSize) throws Exception;
}
