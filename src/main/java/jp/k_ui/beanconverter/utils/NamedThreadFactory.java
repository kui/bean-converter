package jp.k_ui.beanconverter.utils;

import java.util.IllegalFormatException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
  private final AtomicInteger threadNumber = new AtomicInteger(1);
  private String format;
  private boolean daemon;

  public NamedThreadFactory(String threadNameFormat, boolean daemon) {
    this.format = threadNameFormat;
    assertFormat();

    this.daemon = daemon;
  }

  private void assertFormat() {
    try {
      String.format(format, 1);
    } catch (IllegalFormatException e) {
      throw new IllegalArgumentException("invalid format: " + format, e);
    }
  }

  @Override
  public Thread newThread(Runnable r) {
    Thread t = new Thread(r, generateThreadName());
    t.setDaemon(daemon);
    if (t.getPriority() != Thread.NORM_PRIORITY)
      t.setPriority(Thread.NORM_PRIORITY);
    return t;
  }

  private String generateThreadName() {
    return String.format(format, threadNumber.getAndIncrement());
  }
}
