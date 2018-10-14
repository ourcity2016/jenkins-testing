package loan.ppcat.talk.service.implement;

import loan.ppcat.talk.dao.api.IGroupUserDao;
import loan.ppcat.talk.dao.api.IUserDao;
import loan.ppcat.talk.model.GroupUser;
import loan.ppcat.talk.model.User;
import loan.ppcat.talk.service.api.IGroupUserService;
import loan.ppcat.talk.service.api.IUserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("groupUserService")
public class GroupUserService extends BaseService<GroupUser> implements IGroupUserService {

    private IGroupUserDao groupUserDao;

    @Inject
    public void setSuperBaseDao(IGroupUserDao groupUserDao) {
        this.groupUserDao = groupUserDao;
        super.setBaseDao(groupUserDao);
    }


}
