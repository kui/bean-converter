package jp.k_ui.beanconverter;

import java.util.ArrayList;
import java.util.List;

public class ListFileter<E> extends CollectionFilter<E, List<E>> {

  public ListFileter(BeanConverter<E, Boolean> filter) {
    super(filter);
  }

  @Override
  protected List<E> initCollection() {
    return new ArrayList<>();
  }
}
