package sbu.cs;

/*
    In this exercise, you must analyse the following code and use interrupts
    in the main function to terminate threads that run for longer than 3 seconds.

    A thread may run for longer than 3 seconds due the many different reasons,
    including lengthy process times or getting stuck in an infinite loop.

    Take note that you are NOT ALLOWED to change or delete any existing line of code.
 */

public class UseInterrupts
{

    public static class SleepThread extends Thread {
        int sleepCounter;

        public SleepThread(int sleepCounter) {
            super();
            this.sleepCounter = sleepCounter;
        }

        @Override
        public void run() {
            System.out.println(this.getName() + " is Active.");

            while (this.sleepCounter > 0)
            {
                try {
                    Thread.sleep(1000);
                    if (Thread.interrupted()) {
                        break;
                    }
                } catch (InterruptedException e) {

                }
                finally {
                    this.sleepCounter--;
                    System.out.println("Number of sleeps remaining: " + this.sleepCounter);
                }
            }

        }
    }


    public static class LoopThread extends Thread {
        int value;
        public LoopThread(int value) {
            super();
            this.value = value;
        }

        @Override
        public void run() {
            System.out.println(this.getName() + " is Active.");

            for (int i = 0; i < 10; i += 3)
            {
                if (Thread.interrupted()) {
                    break;
                }
                i -= this.value;

            }
        }
    }
    public static class StopperThread extends Thread {
        long sleepTime;
        public StopperThread(long sleepTime) {
            super();
            this.sleepTime = sleepTime;
        }
        @Override
        public void run(){
            try {
                Thread.sleep(this.sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

/*
    You can add new code to the main function. This is where you must utilize interrupts.
    No existing line of code should be changed or deleted.
 */
    public static void main(String[] args) {
        SleepThread sleepThread = new SleepThread(5);
        sleepThread.start();
        StopperThread stopperThread_1=new StopperThread(3000);
        stopperThread_1.start();
        try {
            stopperThread_1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (sleepThread.isAlive()) {
                sleepThread.interrupt();
                System.out.println(sleepThread.getName() + " has been interrupted");
            }
        }




        LoopThread loopThread = new LoopThread(3);
        loopThread.start();
        StopperThread stopperThread_2 =new StopperThread(3000);
        stopperThread_2.start();
        try {
            stopperThread_2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (loopThread.isAlive()) {
                loopThread.interrupt();
                System.out.println(loopThread.getName() + " has been interrupted");
            }
        }

    }
}
