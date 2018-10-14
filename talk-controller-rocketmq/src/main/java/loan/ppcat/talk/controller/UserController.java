package loan.ppcat.talk.controller;

import loan.ppcat.talk.model.User;
import loan.ppcat.talk.model.UserRelations;
import loan.ppcat.talk.service.api.IConversationService;
import loan.ppcat.talk.service.api.IUserRelationsService;
import loan.ppcat.talk.service.api.IUserService;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Inject
    private IUserService userService;
    @Inject
    private IUserRelationsService userRelationsService;

    @RequestMapping(value = "/saveAndCreateConversation", method = RequestMethod.POST)
    @ResponseBody
    public void addUserAndCreataCoversation(@ModelAttribute User user, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("username", user.getName());
        userService.saveUserAndCreateConversation(user);
    }

    @RequestMapping(value = "/relations/list", method = RequestMethod.GET)
    @ResponseBody
    public List<UserRelations> listRelations(@ModelAttribute User user) throws Exception {
        return userRelationsService.listUserRelationsByUser(user);
    }

    @RequestMapping(value = "/relations/bind", method = RequestMethod.POST)
    @ResponseBody
    public void bindRelations(int selfUser, int targetUser) throws Exception {
        User self = userService.load(selfUser);
        User target = userService.load(targetUser);
        UserRelations relations = new UserRelations();
        relations.setSelf(self);
        relations.setFriend(target);
        userRelationsService.save(relations);
    }
}
