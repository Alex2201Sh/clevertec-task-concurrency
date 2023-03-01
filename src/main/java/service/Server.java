package service;

import bean.Request;
import bean.Response;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server implements Runnable {

    private Client client;
    private Lock lock = new ReentrantLock();


    public Server(Client client) {
        this.client = client;
    }

    public void evaluate() {
        while (client.sendRequest() != null) {
            Random random = new Random();
            lock.lock();
            Request request = getRequestFromClient(client);
            lock.unlock();
//            System.out.println("request.getValue() " + request.getValue());
            Response response = new Response();
            try {
                Thread.sleep(random.nextInt(1));
                response.setValue(100 - request.getValue());
                saveResponse(client, response);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print("   response.getValue() " + response.getValue() + "\n");
        }
    }

    private Request getRequestFromClient(Client client) {
        return client.sendRequest();
    }

    @SneakyThrows
    private void saveResponse(Client client, Response response) {
        client.saveResponse(response);
    }


    @Override
    public void run() {
        evaluate();
    }
}
