package loan.ppcat.talk.service.implement;

import loan.ppcat.talk.dao.api.IMessageDao;
import loan.ppcat.talk.dao.api.IUserDao;
import loan.ppcat.talk.model.Message;
import loan.ppcat.talk.model.User;
import loan.ppcat.talk.service.api.IMessageService;
import loan.ppcat.talk.service.api.IUserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("messageService")
public class MessageService extends BaseService<Message> implements IMessageService {

    private IMessageDao messageDao;

    @Inject
    public void setSuperBaseDao(IMessageDao messageDao) {
        this.messageDao = messageDao;
        super.setBaseDao(messageDao);
    }


}
