package jp.k_ui.beanconverter.json.command;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * A wrapper class for immutable {@link ProcessBuilder}
 * 
 * @author kui
 */
public class Command {
  private ProcessBuilder processBuilder;

  public Command(String... command) {
    this(new ProcessBuilder(command), null, null);
  }

  public Command(List<String> command) {
    this(new ProcessBuilder(command), null, null);
  }

  public Command(List<String> command, File currentDirectory){
    this(new ProcessBuilder(command), currentDirectory, null);
  }

  public Command(List<String> command, Map<String, String> additionalEnvironments) {
    this(new ProcessBuilder(command), null, additionalEnvironments);
  }

  public Command(List<String> command, File currentDirectory,
      Map<String, String> additionalEnvironments) {
    this(new ProcessBuilder(command), currentDirectory, additionalEnvironments);
  }

  private Command(ProcessBuilder processBuilder, File currentDirectory,
      Map<String, String> additionalEnvironments) {
    processBuilder
        .redirectError(Redirect.PIPE)
        .redirectInput(Redirect.PIPE)
        .redirectOutput(Redirect.PIPE);
    if (currentDirectory != null)
      processBuilder.directory(currentDirectory);
    if (additionalEnvironments != null)
      processBuilder.environment().putAll(additionalEnvironments);

    this.processBuilder = processBuilder;
  }

  public Process execute() throws IOException {
    return processBuilder.start();
  }

  public Redirect getErrorRedirect() {
    return processBuilder.redirectError();
  }

  public void setErrorRedirect(Redirect destination) {
    processBuilder.redirectError(destination);
  }
}
