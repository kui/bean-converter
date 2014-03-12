package jp.k_ui.beanconverter.json;

import java.io.InputStream;
import java.io.OutputStream;

public interface JsonIOProcess {

  OutputStream getInput();

  InputStream getOutput();
}
