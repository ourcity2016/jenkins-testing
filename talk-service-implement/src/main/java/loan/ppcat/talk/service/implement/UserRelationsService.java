package loan.ppcat.talk.service.implement;

import loan.ppcat.talk.dao.api.IUserRelationsDao;
import loan.ppcat.talk.model.User;
import loan.ppcat.talk.model.UserRelations;
import loan.ppcat.talk.service.api.IUserRelationsService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("userRelationsService")
public class UserRelationsService extends BaseService<UserRelations> implements IUserRelationsService {

    private IUserRelationsDao userRelationsDao;

    @Inject
    public void setSuperBaseDao(IUserRelationsDao userRelationsDao) {
        this.userRelationsDao = userRelationsDao;
        super.setBaseDao(userRelationsDao);
    }


    @Override
    public List<UserRelations> listUserRelationsByUser(User user) throws Exception {
        return userRelationsDao.listUserRelationsByUser(user);
    }
}
