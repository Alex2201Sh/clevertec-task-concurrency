package by.shumilov.service;

import by.shumilov.bean.Request;
import by.shumilov.bean.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Client {

    @Getter
    @Setter
    private List<Request> requestList;
    @Getter
    @Setter
    private List<Response> responseList;
    private final AtomicInteger counter = new AtomicInteger(0);
    private final Random random = new Random();

    public Client(int numberOfRequests) {
        this.requestList = Stream.generate(() ->
                        new Request(random.nextInt(100)))
                .limit(numberOfRequests)
                .toList();
        this.responseList = new ArrayList<>();
    }

    @SneakyThrows
    public Request sendRequest() {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(50));
        return counter.get() < requestList.size() ? requestList.get(counter.getAndIncrement()) : null;
    }

    public void saveResponse(Response response) {
        responseList.add(response);
    }


}
