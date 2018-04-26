package ru.foxit.grayfox;

public class ChatServer {
    // Метод старт, принимающих на вход порт
    public void start(int port) {
        // Логируем
        System.out.println("Server started at port: " + port);
    }
    // Метод стоп
    public void stop() {
        // Логируем
        System.out.println("Server stoped");
    }
}