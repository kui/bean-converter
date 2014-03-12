package jp.k_ui.beanconverter.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import jp.k_ui.beanconverter.BeanConverter;
import jp.k_ui.beanconverter.ConverterException;
import jp.k_ui.beanconverter.utils.ThreadUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonIOConverter<A, B> implements BeanConverter<A, B> {

  private JavaType returnType;
  private JsonIOProcessor processor;
  private ObjectMapper objectMapper;
  private ExecutorService writerService;

  public JsonIOConverter(Class<B> returnType, JsonIOProcessor processor) {
    this(returnType, processor, JsonUtils.prettyprintObjectMapper());
  }

  public JsonIOConverter(TypeReference<B> returnType, JsonIOProcessor processor) {
    this(returnType, processor, JsonUtils.prettyprintObjectMapper());
  }

  public JsonIOConverter(JavaType returnType, JsonIOProcessor processor) {
    this(returnType, processor, JsonUtils.prettyprintObjectMapper(), buildReaderService());
  }

  JsonIOConverter(Class<B> returnType, JsonIOProcessor processor, ObjectMapper objectMapper) {
    this(objectMapper.getTypeFactory().constructType(returnType), processor, objectMapper,
        buildReaderService());
  }

  JsonIOConverter(TypeReference<B> returnType, JsonIOProcessor processor, ObjectMapper objectMapper) {
    this(objectMapper.getTypeFactory().constructType(returnType), processor, objectMapper,
        buildReaderService());
  }

  private static ExecutorService buildReaderService() {
    return ThreadUtils.buildNamedExecutorService(1, "JsonWriter-%03d", true);
  }

  JsonIOConverter(JavaType returnType, JsonIOProcessor processor, ObjectMapper objectMapper,
      ExecutorService writerService) {
    this.returnType = returnType;
    this.processor = processor;
    this.objectMapper = objectMapper;
    this.writerService = writerService;
  }

  @Override
  public B convert(A a) throws ConverterException {
    try {
      JsonIOProcess process = processor.process();

      writerService.submit(new WriteJson(a, objectMapper, process));
      try (InputStream in = process.getOutput()) {
        return objectMapper.readValue(in, returnType);
      }
    } catch (Exception e) {
      throw new ConverterException("convertion error for " + a, e);
    }
  }

  static class WriteJson implements Callable<Object> {

    private Object input;
    private ObjectMapper objectMapper;
    private JsonIOProcess process;

    public WriteJson(Object input, ObjectMapper objectMapper, JsonIOProcess process) {
      this.input = input;
      this.objectMapper = objectMapper;
      this.process = process;
    }

    @Override
    public Object call() throws IOException {
      try (OutputStream out = process.getInput()) {
        objectMapper.writeValue(out, input);
      }
      return null;
    }
  }
}
