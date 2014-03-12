package jp.k_ui.beanconverter.json.command;

import java.io.InputStream;
import java.io.OutputStream;

import jp.k_ui.beanconverter.json.JsonIOProcess;

public class OneshotCommandProcess implements JsonIOProcess {

  private Process process;

  public OneshotCommandProcess(Process process) {
    this.process = process;
  }

  @Override
  public OutputStream getInput() {
    return process.getOutputStream();
  }

  @Override
  public InputStream getOutput() {
    return process.getInputStream();
  }
}
