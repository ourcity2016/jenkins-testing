package loan.ppcat.talk.service.implement;


import loan.ppcat.talk.dao.api.IBaseDao;
import loan.ppcat.talk.model.BucketPage;
import loan.ppcat.talk.service.api.IBaseService;

import java.util.List;

public class BaseService<T> implements IBaseService<T> {
    private IBaseDao<T> baseDao;

    public void setBaseDao(IBaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }


    public void save(T t) throws Exception {
        baseDao.save(t);

    }

    public void delete(T t) throws Exception {
        baseDao.delete(t);
    }

    public void delete(int id) throws Exception {
        baseDao.delete(id);
    }

    public void update(T t) throws Exception {
        baseDao.update(t);
    }

    public void saveOrUpdate(T t) throws Exception {
        baseDao.saveOrUpdate(t);
    }

    public T load(int id) throws Exception {
        return baseDao.load(id);
    }

    public List<T> list() throws Exception {
        return baseDao.list();
    }

    public BucketPage<T> find() throws Exception {
        return baseDao.find();
    }

    public BucketPage<T> find(int pageOffset, int pageSize) throws Exception {
        return baseDao.find(pageOffset, pageSize);
    }
}
