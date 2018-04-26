package ru.foxit.grayfox;

public class MyThread extends Thread {
    MyThread(String name) {
        super(name);
        start();
    }

    @Override
    public void run() {
        try {
            sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // release resources
        // close DB
    }
}