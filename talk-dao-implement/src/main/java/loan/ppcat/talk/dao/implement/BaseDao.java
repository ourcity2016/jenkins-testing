package loan.ppcat.talk.dao.implement;

import loan.ppcat.talk.dao.api.IBaseDao;
import loan.ppcat.talk.model.BucketPage;
import loan.ppcat.talk.utils.SystemContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseDao<T> implements IBaseDao<T> {
    private Class<T> claz;

    public Class<T> getClaz() {

        if (claz == null) {
            claz = (Class<T>) (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments())[0];

        }
        return claz;
    }

    @Inject
    private SessionFactory sessionFactory;

    protected Session getHibernateSession() {
        //return sessionFactory.openSession();
        return sessionFactory.getCurrentSession();
    }

    public void save(T t) throws Exception {
        getHibernateSession().save(t);

    }

    public void delete(T t) throws Exception {
        getHibernateSession().delete(t);
    }

    public void delete(int id) throws Exception {
        getHibernateSession().delete(this.load(id));

    }

    public void update(T t) throws Exception {
        getHibernateSession().update(t);
    }

    public void saveOrUpdate(T t) throws Exception {
        getHibernateSession().saveOrUpdate(t);
    }

    public T load(int id) throws Exception {
        return (T) getHibernateSession().load(getClaz(), id);
    }

    public List<T> list() throws Exception {
        String hql = "from " + getClaz().getName();
        return getHibernateSession().createQuery(hql).list();
    }

    public BucketPage<T> find() throws Exception {
        String hql = "from " + getClaz().getName();
        int pageOffset = SystemContext.getPageOffset();
        int pageSize = SystemContext.getPageSize();
        Query query = getHibernateSession().createQuery(hql);
        query.setFirstResult(pageOffset);
        query.setMaxResults(pageSize);
        BucketPage<T> bucketPage = new BucketPage<T>();
        List<T> list = query.list();
        bucketPage.setPageSize(pageSize);
        bucketPage.setList(list);
        bucketPage.setPageOffset(pageOffset);
        bucketPage.setTotal(getTotalSizeFromObject(hql));
        return bucketPage;
    }

    private long getTotalSizeFromObject(String hql) {
        hql = "select count(*) " + hql;
        return (Long) getHibernateSession().createQuery(hql).uniqueResult();
    }

    private long getTotalSizeFromObject() {
        String hql = "select count(*) from " + getClaz().getName();
        return (Long) getHibernateSession().createQuery(hql).uniqueResult();
    }

    public BucketPage<T> find(int pageOffset, int pageSize) throws Exception {
        String hql = "from " + getClaz().getName();
        Query query = getHibernateSession().createQuery(hql);
        query.setFirstResult(pageOffset);
        query.setMaxResults(pageSize);
        BucketPage<T> bucketPage = new BucketPage<T>();
        List<T> list = query.list();
        bucketPage.setPageSize(pageSize);
        bucketPage.setPageOffset(pageOffset);
        bucketPage.setList(list);
        bucketPage.setTotal(getTotalSizeFromObject(hql));
        return bucketPage;
    }
}
