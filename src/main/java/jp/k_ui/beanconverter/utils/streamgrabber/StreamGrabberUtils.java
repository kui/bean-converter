package jp.k_ui.beanconverter.utils.streamgrabber;

public class StreamGrabberUtils {
  public static InputStreamGrabberFactory julInputStreamGrabberFactory() {
    return new JulInputStreamGrabberFactory();
  }

  public static InputStreamGrabberFactory log4jInputStreamGrabberFactory() {
    return new Log4jInputStreamGrabberFactory();
  }

  public static InputStreamGrabberFactory fixedInputStreamGrabberFactory(InputStreamGrabber grabber) {
    return new FixedInputStreamGrabberFactory(grabber);
  }
}
