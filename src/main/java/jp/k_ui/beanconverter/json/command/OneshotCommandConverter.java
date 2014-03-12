package jp.k_ui.beanconverter.json.command;

import java.util.List;

import jp.k_ui.beanconverter.json.JsonIOConverter;
import jp.k_ui.beanconverter.utils.streamgrabber.InputStreamGrabberFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;

public class OneshotCommandConverter<A, B> extends JsonIOConverter<A, B> {

  public OneshotCommandConverter(Class<B> returnType, String... command) {
    super(returnType, new OneShotCommandProcessor(command));
  }

  public OneshotCommandConverter(TypeReference<B> returnType, String... command) {
    super(returnType, new OneShotCommandProcessor(command));
  }

  public OneshotCommandConverter(JavaType returnType, String... command) {
    super(returnType, new OneShotCommandProcessor(command));
  }

  //

  public OneshotCommandConverter(Class<B> returnType, List<String> command) {
    super(returnType, new OneShotCommandProcessor(command));
  }

  public OneshotCommandConverter(TypeReference<B> returnType, List<String> command) {
    super(returnType, new OneShotCommandProcessor(command));
  }

  public OneshotCommandConverter(JavaType returnType, List<String> command) {
    super(returnType, new OneShotCommandProcessor(command));
  }

  //

  public OneshotCommandConverter(Class<B> returnType, List<String> command,
      InputStreamGrabberFactory grabberFactory) {
    super(returnType, new OneShotCommandProcessor(command, grabberFactory));
  }

  public OneshotCommandConverter(TypeReference<B> returnType, List<String> command,
      InputStreamGrabberFactory grabberFactory) {
    super(returnType, new OneShotCommandProcessor(command, grabberFactory));
  }

  public OneshotCommandConverter(JavaType returnType, List<String> command,
      InputStreamGrabberFactory grabberFactory) {
    super(returnType, new OneShotCommandProcessor(command, grabberFactory));
  }

  //

  public OneshotCommandConverter(Class<B> returnType, Command command) {
    super(returnType, new OneShotCommandProcessor(command, null));
  }

  public OneshotCommandConverter(TypeReference<B> returnType, Command command) {
    super(returnType, new OneShotCommandProcessor(command, null));
  }

  public OneshotCommandConverter(JavaType returnType, Command command) {
    super(returnType, new OneShotCommandProcessor(command, null));
  }

  //

  public OneshotCommandConverter(Class<B> returnType, Command command,
      InputStreamGrabberFactory grabberFactory) {
    super(returnType, new OneShotCommandProcessor(command, grabberFactory));
  }

  public OneshotCommandConverter(TypeReference<B> returnType, Command command,
      InputStreamGrabberFactory grabberFactory) {
    super(returnType, new OneShotCommandProcessor(command, grabberFactory));
  }

  public OneshotCommandConverter(JavaType returnType, Command command,
      InputStreamGrabberFactory grabberFactory) {
    super(returnType, new OneShotCommandProcessor(command, grabberFactory));
  }

  //

  public OneshotCommandConverter(Class<B> returnType, OneShotCommandProcessor processor) {
    super(returnType, processor);
  }

  public OneshotCommandConverter(TypeReference<B> returnType, OneShotCommandProcessor processor) {
    super(returnType, processor);
  }

  public OneshotCommandConverter(JavaType returnType, OneShotCommandProcessor processor) {
    super(returnType, processor);
  }
}
