package ru.foxit.grayfox;

public class ServerSocketThread extends Thread {
    private final int port;

    public ServerSocketThread(String name, int port) {
        super(name);
        this.port = port;
        start();
    }
    @Override
    public void run() {
        System.out.println("Server Socket Thread started");
        while (!isInterrupted()) {
            System.out.println("SST is working");
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                break;
            }
        }
        // release resources;
        System.out.println("Server Socket Thread stopped");
    }
}