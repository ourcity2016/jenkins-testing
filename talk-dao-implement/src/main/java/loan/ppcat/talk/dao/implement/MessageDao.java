package loan.ppcat.talk.dao.implement;

import loan.ppcat.talk.dao.api.IMessageDao;
import loan.ppcat.talk.dao.api.IUserDao;
import loan.ppcat.talk.model.Message;
import loan.ppcat.talk.model.User;
import org.springframework.stereotype.Repository;

@Repository("messageDao")
public class MessageDao extends BaseDao<Message> implements IMessageDao {
}
