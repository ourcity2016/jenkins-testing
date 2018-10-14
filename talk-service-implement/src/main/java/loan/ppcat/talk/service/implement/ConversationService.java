package loan.ppcat.talk.service.implement;

import loan.ppcat.talk.dao.api.IConversationDao;
import loan.ppcat.talk.dao.api.IGroupDao;
import loan.ppcat.talk.model.Conversation;
import loan.ppcat.talk.model.Group;
import loan.ppcat.talk.service.api.IConversationService;
import loan.ppcat.talk.service.api.IGroupService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("conversationService")
public class ConversationService extends BaseService<Conversation> implements IConversationService {

    private IConversationDao conversationDao;

    @Inject
    public void setSuperBaseDao(IConversationDao conversationDao) {
        this.conversationDao = conversationDao;
        super.setBaseDao(conversationDao);
    }


}
