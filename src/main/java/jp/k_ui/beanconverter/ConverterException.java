package jp.k_ui.beanconverter;


public class ConverterException extends Exception {
  private static final long serialVersionUID = 4592588071819031302L;

  public ConverterException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConverterException(Throwable cause) {
    super(cause);
  }
}
