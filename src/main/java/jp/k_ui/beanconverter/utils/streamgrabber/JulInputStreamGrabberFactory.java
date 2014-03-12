package jp.k_ui.beanconverter.utils.streamgrabber;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JulInputStreamGrabberFactory extends FixedInputStreamGrabberFactory {
  public static final String DEFAULT_NAME = "ErrOut";
  public static final Level DEFAULT_LEVEL = Level.INFO;

  public JulInputStreamGrabberFactory() {
    this(DEFAULT_NAME);
  }

  public JulInputStreamGrabberFactory(String logName) {
    this(Logger.getLogger(logName), DEFAULT_LEVEL);
  }

  public JulInputStreamGrabberFactory(final Logger logger, final Level level) {
    super(new ByLineInputStreamGrabber() {
      @Override
      protected void grabLine(String line) {
        logger.log(level, line);
      }
    });
  }
}
