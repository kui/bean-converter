package jp.k_ui.beanconverter.json.command;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import jp.k_ui.beanconverter.json.JsonIOProcess;
import jp.k_ui.beanconverter.json.JsonIOProcessor;
import jp.k_ui.beanconverter.utils.ThreadUtils;
import jp.k_ui.beanconverter.utils.streamgrabber.InputStreamGrabber;
import jp.k_ui.beanconverter.utils.streamgrabber.InputStreamGrabberFactory;

public class OneShotCommandProcessor implements JsonIOProcessor {
  private Command command;
  private InputStreamGrabberFactory errorOutputGrabberFactory;
  private ExecutorService executorService;

  public OneShotCommandProcessor(String... command) {
    init(new Command(command), null, buildDefaultExecutorService());
  }

  public OneShotCommandProcessor(List<String> command) {
    init(new Command(command), null, buildDefaultExecutorService());
  }

  public OneShotCommandProcessor(List<String> command, InputStreamGrabberFactory errorOutputGrabberFactory) {
    init(new Command(command), errorOutputGrabberFactory, buildDefaultExecutorService());
  }

  public OneShotCommandProcessor(Command command, InputStreamGrabberFactory errorOutputCallback) {
    init(command, errorOutputCallback, buildDefaultExecutorService());
  }

  private static ExecutorService buildDefaultExecutorService() {
    return ThreadUtils.buildNamedExecutorService(0, "ErrorOutputGrabber-%d", true);
  }

  public OneShotCommandProcessor(Command command,
      InputStreamGrabberFactory errorOutputCallback, ExecutorService executorService) {
    init(command, errorOutputCallback, executorService);
  }

  private void init(Command command, InputStreamGrabberFactory errorOutputGrabberFactory,
      ExecutorService executorService) {
    if (command == null || executorService == null)
      throw new NullPointerException();
    this.command = command;
    this.errorOutputGrabberFactory = errorOutputGrabberFactory;
    this.executorService = executorService;

    if (errorOutputGrabberFactory == null) {
      command.setErrorRedirect(Redirect.INHERIT);
    } else {
      command.setErrorRedirect(Redirect.PIPE);
    }
  }

  @Override
  public JsonIOProcess process() throws Exception {
    Process process = executeProcess();
    return new OneshotCommandProcess(process);
  }

  private Process executeProcess() throws IOException {
    Process process = command.execute();

    InputStream errOut = process.getErrorStream();
    if (errorOutputGrabberFactory != null && errOut != null) {
      InputStreamGrabber grabber = errorOutputGrabberFactory.getGrabber();
      executorService.submit(new InputStreamGrabberCallable(errOut, grabber));
    }

    return process;
  }

  static class InputStreamGrabberCallable implements Callable<Object> {
    private InputStream in;
    private InputStreamGrabber callback;

    public InputStreamGrabberCallable(InputStream in, InputStreamGrabber grabber) {
      this.in = in;
      this.callback = grabber;
    }

    @Override
    public Object call() throws Exception {
      if (callback != null)
        callback.grab(in);
      return null;
    }
  }
}
