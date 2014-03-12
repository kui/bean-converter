package jp.k_ui.beanconverter.utils.streamgrabber;

public interface InputStreamGrabberFactory {

  /**
   * you should implement to return non-null
   */
  InputStreamGrabber getGrabber();

}
