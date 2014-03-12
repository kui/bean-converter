package jp.k_ui.beanconverter.utils.streamgrabber;

import java.io.InputStream;

public interface InputStreamGrabber {

  void grab(InputStream errorStream) throws Exception;

}
