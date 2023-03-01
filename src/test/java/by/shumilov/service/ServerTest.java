package by.shumilov.service;

import by.shumilov.bean.Request;
import by.shumilov.bean.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class ServerTest {

    private Server server;

    @BeforeEach
    void init() {
        Client client = new Client();
        client.setRequestList(Arrays.asList(new Request(1), new Request(2)));
        server = new Server(client);
    }

    @Test
    void callCheck() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<Response>> future = executorService.submit(server);
        executorService.shutdown();
        List<Response> responses = future.get();
        int expectedResponsesSize = 2;
        Assertions.assertThat(responses.size()).isEqualTo(expectedResponsesSize);
    }

}
