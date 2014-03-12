package jp.k_ui.beanconverter;

import java.util.Collection;

public abstract class CollectionFilter<E, C extends Collection<E>> implements BeanConverter<C, C> {

  private BeanConverter<E, Boolean> filter;

  public CollectionFilter(BeanConverter<E, Boolean> filter) {
    this.filter = filter;
  }

  @Override
  public C convert(C collection) throws ConverterException {
    C newCollection = initCollection();
    for (E e : collection) {
      boolean filterOut;
      try {
        filterOut = filter.convert(e);
      } catch (Exception ex) {
        throw new ConverterException("filtering error for " + e, ex);
      }

      if (!filterOut)
        newCollection.add(e);
    }
    return newCollection;
  }

  abstract protected C initCollection();
}
