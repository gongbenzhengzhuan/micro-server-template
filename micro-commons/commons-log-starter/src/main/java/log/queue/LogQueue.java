package log.queue;

import log.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import utils.utils.thread.ThreadPoolUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-29 20:07 星期三
 * @ClassName com.zyc.commons.log.queue.LogQueue
 * @Description: 操作日志数据有界队列，当队列中无数据时，取数据会阻塞，队列中数据满载时，会阻塞
 */
@SuppressWarnings("all")
@Slf4j
public class LogQueue<T> {

    @Autowired
    private IService service;

    /**
     * 队列默认大小1024
     */
    private int queueSize = 1024;

    /**
     * 生产者线程池
     */
    private ThreadPoolUtils producerPool;

    /**
     * 消费者线程池
     */
    private ThreadPoolUtils consumerPool;

    /**
     * 数据存储队列
     */
    private LinkedBlockingQueue<T> queue;

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    private void put(T data) throws Exception {
        this.queue.put(data);
    }

    private int get(List<T> dataList, int maxElements) {
        return this.queue.drainTo(dataList, maxElements);
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-31 15:03:43
     * @Description 监听启动
     * @Return void
     */
    @PostConstruct
    public void listener() {
        try {
            service.init();
            this.consumer();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-30 11:02 星期四
     * @Description 生产者
     * @Param[0] T data
     * @Return void
     */
    public void producer(T data) throws Exception {
        producerPool.execute(() -> {
            // 队列满载时，线程阻塞
            try {
                this.put(data);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-30 11:02 星期四
     * @Description 消费者
     * @Param[0]
     * @Return void
     */
    private void consumer() throws Exception {
        // 启动监听时，创建队列和线程池
        this.initQueue();
        this.initThreadPool();
        for (int i = 1; i <= $6; i++) {
            // 调用线程池，开始消费消息
            this.consumerPool.execute(() -> {
                try {
                    this.pullQueue();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
        }
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-30 11:02 星期四
     * @Description 初始化队列
     * @Return void
     */
    private void initQueue() {
        this.queue = new LinkedBlockingQueue<T>(this.queueSize);
    }

    /**
     * 消费者与远程存储线程池大小
     */
    private static final int $6 = 6;

    /**
     * 生产者与HTTP请求线程池大小
     */
    private static final int $10 = 10;

    /**
     * 线程队列大小
     */
    private static final int THREAD_QUEUE = 4 * 1024;

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-30 11:02 星期四
     * @Description 初始化线程池
     * @Return void
     */
    private void initThreadPool() {
        this.producerPool = new ThreadPoolUtils($10, $10, THREAD_QUEUE, "生产者");
        this.consumerPool = new ThreadPoolUtils($6, $6, THREAD_QUEUE, "消费者");
    }

    /**
     * 监听者扫描时间，每5秒扫描一次
     */
    private static final int PER_SECONDS = 5;

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-30 10:59 星期四
     * @Description 监听程序虚体执行方法，每5秒钟扫描一次队列
     * @Return void
     */
    private void pullQueue() throws Exception {
        // 首次进来，开始进行队列数据监听，每5秒扫描一次
        do {
            List<T> tList = new ArrayList<>(queueSize);
            this.get(tList, queueSize);
            // 调用服务存储数据
            service.save(tList);
            // N秒扫描一次
            TimeUnit.SECONDS.sleep(PER_SECONDS);
        } while (true);
    }
}
