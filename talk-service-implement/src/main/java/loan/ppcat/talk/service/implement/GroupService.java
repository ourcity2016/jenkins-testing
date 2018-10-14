package loan.ppcat.talk.service.implement;

import loan.ppcat.talk.dao.api.IGroupDao;
import loan.ppcat.talk.dao.api.IUserDao;
import loan.ppcat.talk.model.Group;
import loan.ppcat.talk.model.User;
import loan.ppcat.talk.service.api.IGroupService;
import loan.ppcat.talk.service.api.IUserService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("groupService")
public class GroupService extends BaseService<Group> implements IGroupService {

    private IGroupDao groupDao;

    @Inject
    public void setSuperBaseDao(IGroupDao groupDao) {
        this.groupDao = groupDao;
        super.setBaseDao(groupDao);
    }


}
