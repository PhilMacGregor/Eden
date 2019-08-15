package cz.macgregor.eden.util;

import java.util.Collection;

/**
 * class used to apply a filter to selection from a list of a T type.
 * 
 * @param <T>
 *          object to filter
 * 
 * @author MacGregor
 *
 */
public class Selector<T extends Object> {

  /**
   * apply the filter, select the filtered items from source to dest.
   * 
   * @param source
   *          source collection
   * @param dest
   *          destination
   * @param filter
   *          filter to apply
   */
  public void select(Collection<T> source, Collection<T> dest, Filter<T> filter) {
    for (T t : source) {
      if (filter.select(t)) {
        dest.add(t);
      }
    }
  }

  /**
   * interface used by this class.
   * 
   * @author MacGregor
   *
   * @param <T>
   *          object to filter
   */
  public interface Filter<T extends Object> {

    /**
     * method to perform object selection and filtering.
     * 
     * @param t
     *          object to filter
     * @return true if the object should be the part of the selection.
     */
    boolean select(T t);

  }
}
