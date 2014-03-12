package jp.k_ui.beanconverter.utils.streamgrabber;

public class FixedInputStreamGrabberFactory implements InputStreamGrabberFactory {

  private InputStreamGrabber grabber;

  public FixedInputStreamGrabberFactory(InputStreamGrabber grabber) {
    this.grabber = grabber;
  }

  @Override
  public InputStreamGrabber getGrabber() {
    return grabber;
  }
}
