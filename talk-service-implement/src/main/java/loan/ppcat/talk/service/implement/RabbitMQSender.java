package loan.ppcat.talk.service.implement;

import loan.ppcat.talk.service.api.IRabbitMQSender;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("rabbitMQSender")
public class RabbitMQSender implements IRabbitMQSender {
    @Inject
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendDataToQueue(String queueKey, Object object) {
        try {
            System.out.println("--------------------Send MSG --------------" + object.toString());
            amqpTemplate.convertAndSend(queueKey, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
