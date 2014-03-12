package jp.k_ui.beanconverter.utils.streamgrabber;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class ByLineInputStreamGrabber implements InputStreamGrabber {
  @Override
  public void grab(InputStream errorStream) throws Exception {
    try (BufferedReader err = new BufferedReader(new InputStreamReader(errorStream))) {
      String line;
      while ((line = err.readLine()) != null) {
        grabLine(line);
      }
    }
  }

  protected abstract void grabLine(String line);
}
