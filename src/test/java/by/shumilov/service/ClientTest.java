package by.shumilov.service;

import by.shumilov.bean.Request;
import by.shumilov.bean.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

class ClientTest {


    @ParameterizedTest
    @MethodSource("getArgumentsForSendingRequests")
    void sendRequestCheck(int requestListSize, int numberOfRequests, boolean expected) {
        Client client = new Client(requestListSize);
        IntStream.range(0, numberOfRequests - 1).forEach(i -> client.sendRequest());
        Request request = client.sendRequest();
        Assertions.assertThat(request != null).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForSavingResponses")
    void saveResponseCheck(int numberOfResponseSavings, int expectedSize) {
        Client client = new Client(1);
        IntStream.range(0, numberOfResponseSavings).forEach(i -> client.saveResponse(new Response()));
        int actualSize = client.getResponseList().size();
        Assertions.assertThat(actualSize).isEqualTo(expectedSize);
    }

    static Stream<Arguments> getArgumentsForSavingResponses() {
        return Stream.of(
                Arguments.of(10, 10),
                Arguments.of(0, 0),
                Arguments.of(100, 100)
        );
    }

    static Stream<Arguments> getArgumentsForSendingRequests() {
        return Stream.of(
                Arguments.of(10, 10, true),
                Arguments.of(10, 5, true),
                Arguments.of(100, 101, false),
                Arguments.of(10, 20, false)
        );
    }
}
