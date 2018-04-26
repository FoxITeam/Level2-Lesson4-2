package ru.foxit.grayfox;

public class Threading implements Runnable {
    public static void main(String[] args) {
        MyThread myThread = new MyThread("MyThread");
        myThread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        System.out.println(Thread.currentThread().getName());
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}