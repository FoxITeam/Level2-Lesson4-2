package ru.foxit.grayfox;

public class Threading implements Runnable {

    Threading() {
//        incAllVars();
    }

    private static int a;
    private static int b;
    private static int c;

    synchronized static void incAllVars2() {
        String vars;
        for (int i = 0; i < 1000000; i++) {
            a = a + 1;
            b = b + 1;
            c = c + 1;
//            if (a != b || b != c || a != c) {
//                vars = "a = " + a + ", b = " + b + ", c = " + c;
//                throw new RuntimeException(vars);
//            }
        }
        vars = "a = " + a + ", b = " + b + ", c = " + c;
        System.out.println(vars);
    }

    synchronized static void incAllVars() {
        String vars;
        for (int i = 0; i < 1000000; i++) {
            a = a + 1;
            b = b + 1;
            c = c + 1;
//            if (a != b || b != c || a != c) {
//                vars = "a = " + a + ", b = " + b + ", c = " + c;
//                throw new RuntimeException(vars);
//            }
        }
        vars = "a = " + a + ", b = " + b + ", c = " + c;
        System.out.println(vars);
    }

    public static void main(String[] args) {
//        new Threading();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                incAllVars();
            }
        };
        new Thread(r, "Thread #1").start();
        new Thread(r, "Thread #2").start();
        new Thread(r, "Thread #3").start();


//        MyThread myThread = new MyThread("MyThread");
//
//        for (int i = 0; i < 1000000; i++) {
//            int a = i * 345;
//        }
//        System.out.println("finished hard work");
//
//        try {
//            myThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(myThread.isAlive());

//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("runnable thread");
//            }
//        };
//
//
//        Thread t = new Thread(r);
//        t.start();
//
        System.out.println(Thread.currentThread().getName());
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}