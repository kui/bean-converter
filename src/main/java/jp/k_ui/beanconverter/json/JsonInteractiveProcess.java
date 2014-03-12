package jp.k_ui.beanconverter.json;

import java.io.InputStream;
import java.io.OutputStream;

public interface JsonInteractiveProcess {

  public OutputStream getInput();

  public InputStream getOutput();

  public void destory();

}
