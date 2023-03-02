package by.shumilov.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ServerTest {
    @Test
    void evaluateCheck() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Client client = new Client(10);
        Server server = new Server(client);
        Method evaluate = server.getClass().getDeclaredMethod("evaluate");
        evaluate.setAccessible(true);
        evaluate.invoke(server);
        Assertions.assertThat(client.getResponseList()).isNotEmpty();
    }
}
