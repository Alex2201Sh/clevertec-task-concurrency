package by.shumilov.controller;

import by.shumilov.bean.Response;
import lombok.SneakyThrows;
import by.shumilov.service.Client;
import by.shumilov.service.Server;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Runner {

    @SneakyThrows
    public static void main(String[] args) {
        Client client = new Client();
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<List<Response>> future = executor.submit(new Server(client));
        executor.shutdown();
        List<Response> responses = future.get();
        System.out.println(client.getRequestList().stream().count());
        System.out.println(responses.stream().count());

    }
}
