package jp.k_ui.beanconverter.json;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import jp.k_ui.beanconverter.ConverterException;
import mockit.FullVerifications;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonIOConverterTest {

  @Test
  public void testContertWithNormal(
      @Mocked final JsonIOProcessor mockProcessor,
      @Mocked final ObjectMapper mockMapper,
      @Mocked final ExecutorService mockExecutorService,
      @Mocked final Object mockResult,
      @Mocked final JsonIOProcess process,
      @Mocked final InputStream mockInput
      ) throws Exception {

    final Object stubArg = new Object();
    final JavaType stubType = TypeFactory.defaultInstance().constructType(Object.class);

    new NonStrictExpectations() {
      {
        mockProcessor.process();
        result = process;
        times = 1;

        mockExecutorService.submit((Callable<?>) any);
        times = 1;

        process.getOutput();
        result = mockInput;

        mockMapper.readValue(mockInput, stubType);
        result = mockResult;
        times = 1;

        mockInput.close();
        times = 1;
      }
    };

    //

    try {

      JsonIOConverter<Object, Object> converter =
          new JsonIOConverter<>(stubType, mockProcessor, mockMapper, mockExecutorService);

      Object result = converter.convert(stubArg);
      assertTrue(mockResult == result);

      //

    } finally {
      new FullVerifications() {
        {
          process.getOutput();
          minTimes = 0;
        }
      };
    }
  }

  @Test(expected = ConverterException.class)
  public void testContertWithIOException(
      @Mocked final JsonIOProcessor mockProcessor,
      @Mocked final ObjectMapper mockMapper,
      @Mocked final ExecutorService mockExecutorService,
      @Mocked final JsonIOProcess process,
      @Mocked final InputStream mockInput
      ) throws Exception {

    final Object stubArg = new Object();
    final JavaType stubType = TypeFactory.defaultInstance().constructType(Object.class);

    new NonStrictExpectations() {
      {
        mockProcessor.process();
        result = process;
        times = 1;

        mockExecutorService.submit((Callable<?>) any);
        times = 1;

        process.getOutput();
        result = mockInput;

        mockMapper.readValue(mockInput, stubType);
        result = new IOException();
        times = 1;

        mockInput.close();
        times = 1;
      }
    };

    //

    try {

      JsonIOConverter<Object, Object> converter =
          new JsonIOConverter<>(stubType, mockProcessor, mockMapper, mockExecutorService);
      converter.convert(stubArg);

      //

    } finally {
      new FullVerifications() {
        {
          process.getOutput();
          minTimes = 0;
        }
      };
    }
  }
}
