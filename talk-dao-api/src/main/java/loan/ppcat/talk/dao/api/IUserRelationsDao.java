package loan.ppcat.talk.dao.api;

import loan.ppcat.talk.model.User;
import loan.ppcat.talk.model.UserRelations;

import java.util.List;

public interface IUserRelationsDao extends IBaseDao<UserRelations> {
    List<UserRelations> listUserRelationsByUser(User user) throws Exception;
}
