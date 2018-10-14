package loan.ppcat.talk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import loan.ppcat.talk.model.Message;
import loan.ppcat.talk.model.User;
import loan.ppcat.talk.service.api.IRabbitMQSender;
import loan.ppcat.talk.service.api.IUserService;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/rabbit")
public class RabbitController {
    @Inject
    private IRabbitMQSender rabbitMQSender;
    @Inject
    private ConnectionFactory connectionFactory;
    @Inject
    private IUserService userService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public void sendMessage(int selfID, int targetID, String messageInfo) throws Exception {
        User self = userService.load(selfID);
        User target = userService.load(targetID);
        Message message = new Message();
        message.setCreateDate(new Date());
        message.setFromUser(self);
        message.setToUser(target);
        message.setMessage(messageInfo);
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.createConnection();
            channel = connection.createChannel(false);
            channel.exchangeDeclare("ChatWithExchange", "direct");
            channel.queueDeclare(target.getConversation().getQueueKey(), true, false, false, null);
            channel.queueBind(target.getConversation().getQueueKey(), "ChatWithExchange", "");
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                }
            }
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                }
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        rabbitMQSender.sendDataToQueue(target.getConversation().getQueueKey(), "hello");
    }
}
