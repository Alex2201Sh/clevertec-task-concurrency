package by.shumilov.service;

import by.shumilov.bean.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class ServerIntegrationTest {

    @ParameterizedTest
    @MethodSource("getArguments")
    void callCheck(Server server, int expectedResponsesSize) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<Response>> future = executorService.submit(server);
        executorService.shutdown();
        List<Response> responses = future.get();
        Assertions.assertThat(responses.size()).isEqualTo(expectedResponsesSize);
    }

    static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(new Server(new Client(10)), 10),
                Arguments.of(new Server(new Client(20)), 20),
                Arguments.of(new Server(new Client(100)), 100)
        );
    }

}
