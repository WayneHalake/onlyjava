package threaddemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadPoolExecutor
 newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
 newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 *
 * ForkJoinPool
 newWorkStealingPool 不是ThreadPoolExecutor的扩展，它是新的线程池类ForkJoinPool的扩展，但是都是在统一的一个Executors类中实现，由于能够合理的使用CPU进行对任务操作（并行操作），所以适合使用在很耗时的任务中
 */
public class ThreadDemo {

    public static void main(String[] args){

        //创建固定线程池 -- 包含两个线程对象
        //ExecutorService executorService = Executors.newFixedThreadPool(2);

        //缓存线程池
        //ExecutorService executorService = Executors.newCachedThreadPool();

        //单线程 线程池中只有一个线程
        //ExecutorService executorService = Executors.newSingleThreadExecutor();

        //定长支持周期性任务执行的线程池
        //ExecutorService executorService = Executors.newScheduledThreadPool(2);

        // 抢占式操作的线程池 传入参数为并行数量
        ExecutorService executorService = Executors.newWorkStealingPool(5);

        //创建Runnable对象
        Runnable runnable = new Runnable() {

            //重写runnable方法
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前线程id："+Thread.currentThread().getId());
                System.out.println("线程执行Runnable的run方法。。。");
            }
        };

        //创建callable对象
        Callable<String> callable = new Callable<String>() {

            //具备返回值
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                System.out.println("当前线程id：" + Thread.currentThread().getId());
                System.out.println("当前线程name：" + Thread.currentThread().getName());
                System.out.println("线程执行Callabel的call方法。。。");
                return "线程执行Callabel的call方法。。。";
            }
        };

        while (true){
            //从线程池中获取线程对象，然后调用runnable中的run方法
            //submit()方法调用完毕之后，线程池将控制线程关闭，将使用完的线程又归还到线程池当中
           // executorService.submit(runnable);  //从执行的结果中看出，获取的线程对象是随机的

            try {
                //返回值为callable中的泛型对象，即call方法中的返回值
                String futureString = executorService.submit(callable).get();
                System.out.println("futureString:"+futureString);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            //关闭线程池
           //executorService.shutdown();
        }
    }
}
