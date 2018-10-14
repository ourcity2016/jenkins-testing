package loan.ppcat.talk.mq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class RabbitMQListener1 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        String str = "";
        try {
            str = new String(message.getBody(), "UTF-8");
            System.out.println("RabbitMQListener1 GET MESSAGE " + message);
            System.out.println("RabbitMQListener1 GET MESSAGE " + str);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
