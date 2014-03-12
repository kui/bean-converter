package jp.k_ui.beanconverter;

public interface BeanConverter<A, B> {
  B convert(A a) throws ConverterException;
}
