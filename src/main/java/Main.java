public class Main {

    static Object mon = new Object();
    static volatile int currentCount = 1;
    static final int count = 5;

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    synchronized (mon) {
                        while (currentCount != 1) {
                            mon.wait();
                        }
                        currentCount = 2;
                        System.out.print("A");
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    synchronized (mon) {
                        while (currentCount != 2) {
                            mon.wait();
                        }
                        currentCount = 3;
                        System.out.print("B");
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    synchronized (mon) {
                        while (currentCount != 3) {
                            mon.wait();
                        }
                        currentCount = 1;
                        System.out.println("C");
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
