package loan.ppcat.talk.dao.implement;

import loan.ppcat.talk.dao.api.IGroupUserDao;
import loan.ppcat.talk.dao.api.IUserDao;
import loan.ppcat.talk.model.GroupUser;
import loan.ppcat.talk.model.User;
import org.springframework.stereotype.Repository;

@Repository("groupUserDao")
public class GroupUserDao extends BaseDao<GroupUser> implements IGroupUserDao {
}
