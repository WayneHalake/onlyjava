package threaddemo;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport
 */
public class ThreadDemoLockSupport {
    //线程1
    static Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("thread1 begin park!");
            //当前线程等待许可证，当线程获取许可证之后会唤醒当前线程，反之则会挂起当前线程
            LockSupport.park(this);
            System.out.println("thread1 end park!");
        }
    });

    static Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("thread2 begin park!");
            //当前线程等待许可证关联，当线程获取许可证之后会唤醒当前线程，反之则会挂起当前线程
            LockSupport.park(this);
            System.out.println("thread2 end park!");
        }
    });


    static Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            System.out.println("runnable1 begin park!");
            //当前线程等待许可证关联，当线程获取许可证之后会唤醒当前线程，反之则会挂起当前线程
            LockSupport.park(this);
            System.out.println("runnable1 end park!");
        }
    };

    static Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            System.out.println("runnable2 begin park!");
            //当前线程等待许可证关联，当线程获取许可证之后会唤醒当前线程，反之则会挂起当前线程
            LockSupport.park(this);
            System.out.println("runnable2 end park!");
        }
    };

    //运行main方法 线程1将会运行完毕，而线程2会由于等待许可证关联而被挂起
    public static void main(String[] args) throws InterruptedException {
        //启动两个线程
        thread1.start();
        thread2.start();
        Thread.currentThread().sleep(1000);
        //将线程1与许可证关联起来
        LockSupport.unpark(thread1);

        //以下结果与上述结果一致
        Thread threadRunnable1 = new Thread(runnable1);
        Thread threadRunnable2 = new Thread(runnable2);
        threadRunnable1.start();
        threadRunnable2.start();
        Thread.currentThread().sleep(1000);
        LockSupport.unpark(threadRunnable1);

    }


}
