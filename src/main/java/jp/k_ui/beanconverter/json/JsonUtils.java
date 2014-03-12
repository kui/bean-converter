package jp.k_ui.beanconverter.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {

  static private final ObjectMapper ONELINE_OBJECT_MAPPER = new ObjectMapper();
  static {
    ONELINE_OBJECT_MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
  }
  static private final ObjectMapper PRETTYPRINT_OBJECT_MAPPER = new ObjectMapper();
  static {
    PRETTYPRINT_OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
  }

  public static ObjectMapper onelineObjectMapper() {
    return ONELINE_OBJECT_MAPPER;
  }

  public static ObjectMapper prettyprintObjectMapper() {
    return PRETTYPRINT_OBJECT_MAPPER;
  }
}
