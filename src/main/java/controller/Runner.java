package controller;

import service.Client;
import service.Server;

public class Runner {

    public static void main(String[] args) {
        Client client = new Client();
        Thread thread1 = new Thread(new Server(client));
        Thread thread2 = new Thread(new Server(client));
        Thread thread3 = new Thread(new Server(client));
        Thread thread4 = new Thread(new Server(client));
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
