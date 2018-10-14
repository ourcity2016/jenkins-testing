package loan.ppcat.talk.service.implement;

import loan.ppcat.talk.dao.api.IConversationDao;
import loan.ppcat.talk.dao.api.IUserDao;
import loan.ppcat.talk.model.Conversation;
import loan.ppcat.talk.model.User;
import loan.ppcat.talk.service.api.IUserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

@Service("userService")
public class UserService extends BaseService<User> implements IUserService {
    @Inject
    private IConversationDao conversationDao;
    private IUserDao userDao;

    @Inject
    public void setSuperBaseDao(IUserDao userDao) {
        this.userDao = userDao;
        super.setBaseDao(userDao);
    }

    @Override
    public void saveUserAndCreateConversation(User user) throws Exception {
        Conversation conversation = new Conversation();
        conversation.setType((byte) 0);
        conversation.setQueueKey(UUID.randomUUID().toString());
        user.setConversation(conversation);
        userDao.save(user);
        conversationDao.save(conversation);
    }
}
