package ru.foxit.grayfox;

public class MyThread extends Thread {
    MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(getName());
    }
}