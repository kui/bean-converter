package jp.k_ui.beanconverter.utils.streamgrabber;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class Log4jInputStreamGrabberFactory extends FixedInputStreamGrabberFactory {

  public static final String DEFAULT_NAME = "ErrOut";
  public static final Priority DEFAULT_LEVEL = Level.INFO;

  public Log4jInputStreamGrabberFactory() {
    this(DEFAULT_NAME, DEFAULT_LEVEL);
  }

  public Log4jInputStreamGrabberFactory(String name, Priority priority) {
    this(Logger.getLogger(name), priority);
  }

  public Log4jInputStreamGrabberFactory(final Logger logger, final Priority priority) {
    super(new ByLineInputStreamGrabber() {
      @Override
      protected void grabLine(String line) {
        logger.log(priority, line);
      }
    });
  }
}
