package by.shumilov.controller;

import by.shumilov.bean.Response;
import by.shumilov.service.Client;
import by.shumilov.service.Server;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Runner {

    @SneakyThrows
    public static void main(String[] args) {
        int numberOfClientRequests = 10;
        Client client = new Client(numberOfClientRequests);
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<List<Response>> future = executor.submit(new Server(client));
        executor.shutdown();
        List<Response> responses = future.get();
        System.out.println("Request list finished. Response list was filled in: " + !responses.isEmpty() + ".");
    }
}
