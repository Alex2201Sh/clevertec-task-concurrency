package by.shumilov.service;

import by.shumilov.bean.Request;
import by.shumilov.bean.Response;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server implements Callable<List<Response>> {

    private final Client client;
    private final Lock lock = new ReentrantLock();
    private final Random random = new Random();

    public Server(Client client) {
        this.client = client;
    }
    @Override
    public List<Response> call() throws Exception {
        evaluate();
        return client.getResponseList();
    }

    private void evaluate() throws InterruptedException {
        Request request = getRequestFromClient(client);
        while (request != null) {
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
                lock.lock();
                Response response = new Response();
                response.setValue(100 - request.getValue());
                saveResponse(client, response);
                request = getRequestFromClient(client);
            } finally {
                lock.unlock();
            }

        }
    }

    private Request getRequestFromClient(Client client) {
        return client.sendRequest();
    }

    @SneakyThrows
    private void saveResponse(Client client, Response response) {
        client.saveResponse(response);
    }

}
