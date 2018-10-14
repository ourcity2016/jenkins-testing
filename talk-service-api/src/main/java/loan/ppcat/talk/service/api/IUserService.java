package loan.ppcat.talk.service.api;

import loan.ppcat.talk.model.User;

public interface IUserService extends IBaseService<User> {
    void saveUserAndCreateConversation(User user) throws Exception;
}
