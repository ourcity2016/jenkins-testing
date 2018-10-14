package loan.ppcat.talk.dao.implement;

import loan.ppcat.talk.dao.api.IUserDao;
import loan.ppcat.talk.model.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {
}
