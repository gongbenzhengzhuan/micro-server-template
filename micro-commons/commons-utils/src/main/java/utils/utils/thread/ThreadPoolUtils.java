package utils.utils.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-28 14:43 星期二
 * @ClassName com.zyc.datasystem.log.thread.ThreadPoolUtils
 * @Description: 线程工具类
 */
@SuppressWarnings("all")
@Slf4j
public class ThreadPoolUtils {

    /**
     * ExecutorService 线程池
     * 使用方式一  ThreadPoolUtils.submit();
     */
    private static ExecutorService pool = null;

    /*初始化线程池*/
    public static void init() {
        if (null == pool) {
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
            pool = new ThreadPoolExecutor(5,
                    10,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1024),
                    namedThreadFactory,
                    new ThreadPoolExecutor.AbortPolicy());
        }
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 14:03:00
     * @Description 交任务执行
     * @Param[0] java.lang.Runnable r
     * @Return void
     */
    public static void submit(Runnable r) {
        init();
        pool.execute(r);
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 14:03:13
     * @Description 关闭线程池
     * @Return void
     */
    public static void unInit() {
        if (pool == null || pool.isShutdown()) {
            return;
        }
        pool.shutdownNow();
        pool = null;
    }

    /**
     * ThreadPoolExecutor-线程池 可记录线程执行详情
     * 使用方式二  ThreadPoolUtils pool = new ThreadPoolUtils(5,5,10, "A业务线程池");
     * pool.execute()
     */
    private ThreadPoolExecutor executor;

    /**
     * 线程工厂
     */
    private CustomThreadFactory threadFactory;

    /**
     * 异步执行结果
     */
    private List<CompletableFuture<Void>> completableFutures;

    /**
     * 拒绝策略
     */
    private CustomAbortPolicy abortPolicy;

    /**
     * 失败数量
     */
    private AtomicInteger failedCount;

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 14:03:02
     * @Description
     * @Param[0] int corePoolSize 核心线程数大小，当线程数< corePoolSize ，会创建线程执行runnable
     * @Param[1] int maximumPoolSize 最大线程数， 当线程数 >= corePoolSize的时候，会把runnable放入workQueue中
     * @Param[2] int queueSize 阻塞队列，存放来不及执行的线程
     * ArrayBlockingQueue：构造函数一定要传大小
     * LinkedBlockingQueue：构造函数不传大小会默认为（Integer.MAX_VALUE ），当大量请求任务时，容易造成 内存耗尽。
     * SynchronousQueue：同步队列，一个没有存储空间的阻塞队列 ，将任务同步交付给工作线程。
     * PriorityBlockingQueue : 优先队列
     * @Param[3] java.lang.String poolName 创建线程的名字
     */
    public ThreadPoolUtils(int corePoolSize,
                           int maximumPoolSize,
                           int queueSize,
                           String poolName) {
        this.failedCount = new AtomicInteger(0);
        this.abortPolicy = new CustomAbortPolicy();
        this.completableFutures = new ArrayList<>();
        this.threadFactory = new CustomThreadFactory(poolName);
        // keepAliveTime   保持存活时间，当线程数大于corePoolSize的空闲线程能保持的最大时间
        // TimeUnit.SECONDS 时间单位
        // threadFactory 创建线程的工厂
        // abortPolicy 拒绝策略
        this.executor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize),
                this.threadFactory,
                abortPolicy);
    }

    /**
     * 执行任务
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(runnable, executor);
        // 设置好异常情况
        future.exceptionally(e -> {
            failedCount.incrementAndGet();
            log.error("Task Failed..." + e);
            e.printStackTrace();
            return null;
        });
        // 任务结果列表
        completableFutures.add(future);
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 14:03:21
     * @Description 执行自定义runnable接口（可省略，只是加了个获取taskName）
     * @Param[0] com.zyc.datasystem.log.thread.ThreadPoolUtils.SimpleTask runnable
     * @Return void
     */
    public void execute(SimpleTask runnable) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(runnable, executor);
        // 设置好异常情况
        future.exceptionally(e -> {
            failedCount.incrementAndGet();
            log.error("Task [" + runnable.taskName + "] Failed..." + e);
            e.printStackTrace();
            return null;
        });
        // 任务结果列表
        completableFutures.add(future);
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 14:03:34
     * @Description 停止线程池
     * @Return void
     */
    public void shutdown() {
        executor.shutdown();
        log.debug("************************停止线程池************************");
        log.debug("** 活动线程数：" + executor.getActiveCount() + "\t\t\t\t\t\t\t\t\t\t**");
        log.debug("** 等待任务数：" + executor.getQueue().size() + "\t\t\t\t\t\t\t\t\t\t**");
        log.debug("** 完成任务数：" + executor.getCompletedTaskCount() + "\t\t\t\t\t\t\t\t\t\t**");
        log.debug("** 全部任务数：" + executor.getTaskCount() + "\t\t\t\t\t\t\t\t\t\t**");
        log.debug("** 拒绝任务数：" + abortPolicy.getRejectCount() + "\t\t\t\t\t\t\t\t\t\t**");
        log.debug("** 成功任务数：" + (executor.getCompletedTaskCount() - failedCount.get()) + "\t\t\t\t\t\t\t\t\t\t**");
        log.debug("** 异常任务数：" + failedCount.get() + "\t\t\t\t\t\t\t\t\t\t**");
        log.debug("**********************************************************");
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 14:03:39
     * @Description 获取任务执行情况之所以遍历taskCount数的CompletableFuture，是因为如果有拒绝的任务，相应的CompletableFuture也会放进列表，而这种CompletableFuture调用get方法，是会永远阻塞的
     * @Return boolean
     */
    public boolean getExecuteResult() {
        // 任务数，不包含拒绝的任务
        long taskCount = executor.getTaskCount();
        for (int i = 0; i < taskCount; i++) {
            CompletableFuture<Void> future = completableFutures.get(i);
            try {
                // 获取结果，这个是同步的，目的是获取真实的任务完成情况
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("java.util.concurrent.CompletableFuture.get() Failed ..." + e);
                return false;
            }
            // 出现异常，false
            if (future.isCompletedExceptionally()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 14:03:53
     * @Description 线程工厂
     */
    private static class CustomThreadFactory implements ThreadFactory {
        private String poolName;
        private AtomicInteger count;

        private CustomThreadFactory(String poolName) {
            this.poolName = poolName;
            this.count = new AtomicInteger(0);
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            // 线程名，利于排查
            thread.setName(poolName + "-线程" + count.incrementAndGet());
            return thread;
        }
    }

    /**
    * @Author       Eric·Wang[王承]
    * @Date         2023-03-28 14:03:53
    * @Description  自定义拒绝策略
    */
    private static class CustomAbortPolicy implements RejectedExecutionHandler {
        /**
         * 拒绝的任务数
         */
        private AtomicInteger rejectCount;

        private CustomAbortPolicy() {
            this.rejectCount = new AtomicInteger(0);
        }

        private AtomicInteger getRejectCount() {
            return rejectCount;
        }

        /**
         * @Author Eric·Wang[王承]
         * @Date 2023-03-28 14:03:13
         * @Description 如果不抛异常，则执行此任务的线程会一直阻塞
         * @Param[0] java.lang.Runnable r
         * @Param[1] java.util.concurrent.ThreadPoolExecutor e
         * @Return void
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            log.error("Task " + r.toString() +
                    " rejected from " +
                    e.toString() + " 累计：" + rejectCount.incrementAndGet());
        }
    }

    public abstract static class SimpleTask implements Runnable {
        /**
         * 任务名称
         */
        private String taskName;

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }
    }
}
