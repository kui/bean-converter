package jp.k_ui.beanconverter.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {
  public static ExecutorService buildNamedExecutorService(int idleThreads, String threadNameFormat,
      boolean daemon) {
    NamedThreadFactory tf = new NamedThreadFactory(threadNameFormat, daemon);
    ThreadPoolExecutor es =
        new ThreadPoolExecutor(idleThreads, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), tf);
    return es;
  }
}
