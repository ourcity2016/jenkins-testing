package loan.ppcat.talk.service.api;

import loan.ppcat.talk.model.User;
import loan.ppcat.talk.model.UserRelations;

import java.util.List;

public interface IUserRelationsService extends IBaseService<UserRelations> {
    List<UserRelations> listUserRelationsByUser(User user) throws Exception;
}
