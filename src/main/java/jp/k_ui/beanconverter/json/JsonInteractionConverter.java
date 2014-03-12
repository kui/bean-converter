package jp.k_ui.beanconverter.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import jp.k_ui.beanconverter.BeanConverter;
import jp.k_ui.beanconverter.utils.ThreadUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonInteractionConverter<A, B> implements BeanConverter<A, B> {

  private JavaType returnType;
  private PrintStream out;
  private BufferedReader in;
  private JsonInteractiveProcess process;
  private ObjectMapper objectMapper;
  private ExecutorService writerService;

  public JsonInteractionConverter(Class<B> returnType, JsonInteractiveProcess process) {
    ObjectMapper om = JsonUtils.onelineObjectMapper();
    init(om.getTypeFactory().constructType(returnType), process, om);
  }

  public JsonInteractionConverter(TypeReference<B> returnType, JsonInteractiveProcess process) {
    ObjectMapper om = JsonUtils.onelineObjectMapper();
    init(om.getTypeFactory().constructType(returnType), process, om);
  }

  public JsonInteractionConverter(JavaType returnType, JsonInteractiveProcess process) {
    init(returnType, process, JsonUtils.onelineObjectMapper());
  }

  JsonInteractionConverter(JavaType returnType, PrintStream out, BufferedReader in,
      ObjectMapper objectMapper, ExecutorService writerService) {
    init(returnType, out, in, objectMapper, writerService);
  }

  private void init(JavaType returnType, JsonInteractiveProcess process,
      ObjectMapper objectMapper) {
    init(
        returnType,
        new PrintStream(process.getInput(), true),
        new BufferedReader(new InputStreamReader(process.getOutput())),
        objectMapper,
        ThreadUtils.buildNamedExecutorService(1, "JSONWriter-%03d", true));
  }

  private void init(JavaType returnType, PrintStream out, BufferedReader in,
      ObjectMapper objectMapper, ExecutorService writerService) {
    this.returnType = returnType;
    this.out = out;
    this.in = in;
    this.objectMapper = objectMapper;
    this.writerService = writerService;
  }

  @Override
  public B convert(A a) {
    try {
      return convertInternal(a);
    } catch (Exception e) {
      try {
        process.destory();
      } catch (Exception ex) {}
      try {
        out.close();
      } catch (Exception ex) {}
      try {
        in.close();
      } catch (Exception ex) {}

      // all exeption thrown by convertInternal treat as fatal errors,
      // because json interactions with the JsonInteractiveProcess is too difficult
      // to manage these status.
      throw new RuntimeException("fatal error", e);
    }
  }

  synchronized private B convertInternal(A a) throws IOException {
    String beforeJson = objectMapper.writeValueAsString(a);
    writerService.submit(new WriteJson(beforeJson, out));
    String afterJson = in.readLine();
    return objectMapper.readValue(afterJson, returnType);
  }

  static class WriteJson implements Callable<Object> {
    private String json;
    private PrintStream out;

    public WriteJson(String json, PrintStream out) {
      this.json = json;
      this.out = out;
    }

    @Override
    public Object call() throws IOException {
      out.println(json);
      return null;
    }
  }
}
