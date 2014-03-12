package jp.k_ui.beanconverter;

import java.util.HashSet;
import java.util.Set;

public class SetFilter<E> extends CollectionFilter<E, Set<E>> {

  public SetFilter(BeanConverter<E, Boolean> filter) {
    super(filter);
  }

  @Override
  protected Set<E> initCollection() {
    return new HashSet<>();
  }
}
