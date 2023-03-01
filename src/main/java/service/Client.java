package service;

import bean.Request;
import bean.Response;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;


@Data
public class Client {

    private AtomicInteger counter = new AtomicInteger(0);
    private List<Request> requestList;
    private List<Response> responseList;

    private Lock lock = new ReentrantLock();

    public Client() {
        this.requestList = Stream.generate(() -> new Request(new Random().nextInt(100))).limit(100).toList();
        this.responseList = new ArrayList<>();
    }

    @SneakyThrows
    public Request sendRequest(){
        Thread.sleep(new Random().nextInt(200));
        return counter.get()<requestList.size()? requestList.get(counter.getAndIncrement()) : null;
    }

    public void saveResponse(Response response){
//        lock.lock();
        responseList.add(response);
//        lock.unlock();
    }



}
