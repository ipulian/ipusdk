package com.ipusoft.context.manager;


import com.ipusoft.context.utils.ArrayUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author : GWFan
 * time   : 3/31/21 5:59 PM
 * desc   : 线程管理类
 */

public final class ThreadPoolManager {
    private static final String TAG = "ThreadPoolManager";
    /**
     * corePoolSize：核心池的大小，这个参数跟后面讲述的线程池的实现原理有非常大的关系。
     * 在创建了线程池后，默认情况下，线程池中并没有任何线程，而是等待有任务到来才创建线程去执行任务，
     * 除非调用了prestartAllCoreThreads()或者prestartCoreThread()方法，
     * 从这2个方法的名字就可以看出，是预创建线程的意思，即在没有任务到来之前就创建corePoolSize个线程或者一个线程。
     * 默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，
     * 当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
     */
    private static final int SIZE_CORE_POOL = 2;

    /**
     * maximumPoolSize：线程池最大线程数，这个参数也是一个非常重要的参数，它表示在线程池中最多能创建多少个线程；
     */
    private static final int SIZE_MAX_POOL = 3;

    /**
     * keepAliveTime：表示线程没有任务执行时最多保持多久时间会终止。默认情况下，只有当线程池中的线程数大于corePoolSize时，
     * keepAliveTime才会起作用，直到线程池中的线程数不大于corePoolSize，即当线程池中的线程数大于corePoolSize时，
     * 如果一个线程空闲的时间达到keepAliveTime，则会终止，直到线程池中的线程数不超过corePoolSize。
     * 但是如果调用了allowCoreThreadTimeOut(boolean)方法，在线程池中的线程数不大于corePoolSize时，
     * keepAliveTime参数也会起作用，直到线程池中的线程数为0；
     */
    private static final int TIME_KEEP_ALIVE = 5000;

    // 线程池所使用的缓冲队列大小
    private static final int SIZE_WORK_QUEUE = 500;

    // 任务调度周期
    private static final int PERIOD_TASK_QOS = 1000;

    private ThreadPoolManager() {
    }

    private static final ThreadPoolManager sThreadPoolManager = new ThreadPoolManager();

    public static ThreadPoolManager newInstance() {
        return sThreadPoolManager;
    }

    /**
     * 一个任务缓冲队列，用来存储等待执行的任务，这个参数的选择也很重要，会对线程池的运行过程产生重大影响，一般来说，这里的阻塞队列有以下几种选择：
     * ArrayBlockingQueue;
     * LinkedBlockingQueue;
     * SynchronousQueue;
     */
    private final Queue<Runnable> mTaskQueue = new LinkedList<>();

    /*
     * 线程池超出界线时将任务加入缓冲队列
     */
    private final RejectedExecutionHandler mHandler = (task, executor) -> mTaskQueue.offer(task);

    /*
     * 线程池
     * 1）ArrayBlockingQueue：基于数组的先进先出队列，此队列创建时必须指定大小；
     * 2）LinkedBlockingQueue：基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE；
     * 3）synchronousQueue：这个队列比较特殊，它不会保存提交的任务，而是将直接新建一个线程来执行新来的任务。
     */
    private final ThreadPoolExecutor mThreadPool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL,
            TIME_KEEP_ALIVE, TimeUnit.SECONDS, new ArrayBlockingQueue<>(SIZE_WORK_QUEUE), mHandler);

    /*
     * 将缓冲队列中的任务重新加载到线程池
     */
    private final Runnable mAccessBufferThread = () -> {
        if (hasMoreAcquire()) {
            mThreadPool.execute(mTaskQueue.poll());
        }
    };

    /*
     * 创建一个调度线程池
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /*
     * 通过调度线程周期性的执行缓冲队列中任务
     */
    protected final ScheduledFuture<?> mTaskHandler = scheduler.scheduleAtFixedRate(mAccessBufferThread, 0,
            PERIOD_TASK_QOS, TimeUnit.MILLISECONDS);


    /**
     * prestartCoreThread: 初始化一个核心线程
     * prestartAllCoreThreads: 初始化所有核心线程
     * shutdown()：不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务
     * shutdownNow()：立即终止线程池，并尝试打断正在执行的任务，并且清空任务缓存队列，返回尚未执行的任务
     */
    public void prepare() {
        if (mThreadPool.isShutdown() && !mThreadPool.prestartCoreThread()) {
            int startThread = mThreadPool.prestartAllCoreThreads();
        }
    }

    /**
     * 返回总线程数
     */
    public long getTaskCount() {
        return mThreadPool.getPoolSize();
        // return mThreadPool.getTaskCount();
    }

    /**
     * 返回排队任务数
     *
     * @return
     */
    public long getTaskQueue() {
        return mThreadPool.getQueue().size();
    }

    /**
     * 返回正在执行的任务数
     *
     * @return
     */
    public long getActiveCount() {
        return mThreadPool.getActiveCount();
    }

    /*
     * 消息队列检查方法
     */
    private boolean hasMoreAcquire() {
        return !mTaskQueue.isEmpty();
    }

    /*
     * 向线程池中添加任务方法
     */
    public void addExecuteTask(Runnable task) {
//        String token = LocalStorageUtils.getToken();
//        if (task != null && StringUtils.isNotEmpty(token)) {
        mThreadPool.execute(task);
        //    }
    }

    public void addExecuteTasks(List<Runnable> taskList) {
//        String token = LocalStorageUtils.getToken();
//        if (StringUtils.isNotEmpty(token)) {
        if (ArrayUtils.isNotEmpty(taskList)) {
            for (Runnable r : taskList) {
                mThreadPool.execute(r);
            }
        }
        //   }
    }

    protected boolean isTaskEnd() {
        return mThreadPool.getActiveCount() == 0;
    }

    public void shutdown() {
        mTaskQueue.clear();
        mThreadPool.shutdown();
    }
}