package loan.ppcat.talk.service.api;

public interface IRabbitMQSender {
    void sendDataToQueue(String queueKey, Object object);
}
