package loan.ppcat.talk.dao.implement;

import loan.ppcat.talk.dao.api.IUserRelationsDao;
import loan.ppcat.talk.model.User;
import loan.ppcat.talk.model.UserRelations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRelationsDao")
public class UserRelationsDao extends BaseDao<UserRelations> implements IUserRelationsDao {
    @Override
    public List<UserRelations> listUserRelationsByUser(User user) throws Exception {
        String hql = "from " + getClaz().getName() + " users where users.self.name = ?";
        return getHibernateSession().createQuery(hql).setString(0, user.getName()).list();
    }
}
