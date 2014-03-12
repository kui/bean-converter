package jp.k_ui.beanconverter.json.command;

import java.io.IOException;
import java.util.List;

import jp.k_ui.beanconverter.json.JsonInteractionConverter;
import jp.k_ui.beanconverter.utils.streamgrabber.InputStreamGrabber;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;

/**
 * TODO
 *
 * @author kui
 *
 * @param <A>
 * @param <B>
 */
public class InteractiveCommandConverter<A, B> extends JsonInteractionConverter<A, B> {
  public InteractiveCommandConverter(Class<B> returnType, String... command) throws IOException {
    this(returnType, new Command(command), null);
  }

  public InteractiveCommandConverter(TypeReference<B> returnType, String... command)
      throws IOException {
    this(returnType, new Command(command), null);
  }

  public InteractiveCommandConverter(JavaType returnType, String... command) throws IOException {
    this(returnType, new Command(command), null);
  }

  //

  public InteractiveCommandConverter(Class<B> returnType, List<String> command) throws IOException {
    this(returnType, new Command(command), null);
  }

  public InteractiveCommandConverter(TypeReference<B> returnType, List<String> command)
      throws IOException {
    this(returnType, new Command(command), null);
  }

  public InteractiveCommandConverter(JavaType returnType, List<String> command) throws IOException {
    this(returnType, new Command(command), null);
  }

  //

  public InteractiveCommandConverter(Class<B> returnType, List<String> command,
      InputStreamGrabber errorOutputGrabber) throws IOException {
    this(returnType, new Command(command), errorOutputGrabber);
  }

  public InteractiveCommandConverter(TypeReference<B> returnType, List<String> command,
      InputStreamGrabber errorOutputGrabber) throws IOException {
    this(returnType, new Command(command), errorOutputGrabber);
  }

  public InteractiveCommandConverter(JavaType returnType, List<String> command,
      InputStreamGrabber errorOutputGrabber) throws IOException {
    this(returnType, new Command(command), errorOutputGrabber);
  }

  //

  public InteractiveCommandConverter(Class<B> returnType, Command command)
      throws IOException {
    super(returnType, new InteractiveCommandProcess(command, null));
  }

  public InteractiveCommandConverter(TypeReference<B> returnType, Command command)
      throws IOException {
    super(returnType, new InteractiveCommandProcess(command, null));
  }

  public InteractiveCommandConverter(JavaType returnType, Command command) throws IOException {
    super(returnType, new InteractiveCommandProcess(command, null));
  }

  //

  public InteractiveCommandConverter(Class<B> returnType, Command command,
      InputStreamGrabber errorOutputGrabber) throws IOException {
    super(returnType, new InteractiveCommandProcess(command, errorOutputGrabber));
  }

  public InteractiveCommandConverter(TypeReference<B> returnType, Command command,
      InputStreamGrabber errorOutputGrabber) throws IOException {
    super(returnType, new InteractiveCommandProcess(command, errorOutputGrabber));
  }

  public InteractiveCommandConverter(JavaType returnType, Command command,
      InputStreamGrabber errorOutputGrabber) throws IOException {
    super(returnType, new InteractiveCommandProcess(command, errorOutputGrabber));
  }

  //

  public InteractiveCommandConverter(JavaType returnType, InteractiveCommandProcess process) {
    super(returnType, process);
  }
}
