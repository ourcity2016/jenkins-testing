package loan.ppcat.talk.dao.implement;

import loan.ppcat.talk.dao.api.IConversationDao;
import loan.ppcat.talk.dao.api.IUserDao;
import loan.ppcat.talk.model.Conversation;
import loan.ppcat.talk.model.User;
import org.springframework.stereotype.Repository;

@Repository("conversationDao")
public class ConversationDao extends BaseDao<Conversation> implements IConversationDao {
}
