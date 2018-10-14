package loan.ppcat.talk.mq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class RabbitMQListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        String str = "";
        try {
            str = new String(message.getBody(), "UTF-8");
            System.out.println("RabbitMQListener GET MESSAGE " + message);
            System.out.println("RabbitMQListener GET MESSAGE " + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
