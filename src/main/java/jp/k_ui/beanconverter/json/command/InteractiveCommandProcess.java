package jp.k_ui.beanconverter.json.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;

import jp.k_ui.beanconverter.json.JsonInteractiveProcess;
import jp.k_ui.beanconverter.utils.streamgrabber.InputStreamGrabber;

public class InteractiveCommandProcess implements JsonInteractiveProcess {

  private Process process;
  private StreamGrabberExecutor grabberExecutor = null;

  public InteractiveCommandProcess(Command command, InputStreamGrabber errorOutputGrabber)
      throws IOException {
    if (errorOutputGrabber == null) {
      command.setErrorRedirect(Redirect.INHERIT);
    } else {
      command.setErrorRedirect(Redirect.PIPE);
    }

    Process p = command.execute();
    init(p, errorOutputGrabber);
  }

  InteractiveCommandProcess(Process process, StreamGrabberExecutor streamGrabberExecutor)
      throws IOException {
    init(process, streamGrabberExecutor);
  }

  private void init(Process process, InputStreamGrabber errorOutputGrabber) {
    StreamGrabberExecutor e = null;
    if (errorOutputGrabber != null)
      e = new StreamGrabberExecutor(errorOutputGrabber, process.getErrorStream());

    init(process, e);
  }
  private void init(Process process, StreamGrabberExecutor streamGrabberExecutor) {
    this.process = process;
    if (streamGrabberExecutor != null) {
      grabberExecutor = streamGrabberExecutor;
      grabberExecutor.setDaemon(true);
      grabberExecutor.start();
    }
  }

  @Override
  public OutputStream getInput() {
    return process.getOutputStream();
  }

  @Override
  public InputStream getOutput() {
    return process.getInputStream();
  }

  @Override
  public void destory() {
    process.destroy();
  }

  static class StreamGrabberExecutor extends Thread {
    private InputStreamGrabber grabber;
    private InputStream in;

    public StreamGrabberExecutor(InputStreamGrabber grabber, InputStream in) {
      this.grabber = grabber;
      this.in = in;
    }

    @Override
    public void run() {
      try {
        grabber.grab(in);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
