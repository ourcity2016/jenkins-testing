package loan.ppcat.talk.dao.implement;

import loan.ppcat.talk.dao.api.IGroupDao;
import loan.ppcat.talk.dao.api.IUserDao;
import loan.ppcat.talk.model.Group;
import loan.ppcat.talk.model.User;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDao extends BaseDao<Group> implements IGroupDao {
}
