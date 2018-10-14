package loan.ppcat.talk.service.api;

public interface IRabbitService {
    void createExchange(String name) throws Exception;

    void createQuee(String name) throws Exception;
}
