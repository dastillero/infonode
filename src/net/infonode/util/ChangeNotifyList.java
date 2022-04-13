/*
 * Copyright (C) 2004 NNL Technology AB
 * Visit www.infonode.net for information about InfoNode(R) 
 * products and how to contact NNL Technology AB.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, 
 * MA 02111-1307, USA.
 */


// $Id: ChangeNotifyList.java,v 1.4 2005/02/16 11:28:14 jesper Exp $
package net.infonode.util;

import java.util.*;

/**
 * <p>Abstract ChangeNotifyList class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.4 $
 */
abstract public class ChangeNotifyList implements List {
  private List list;

  /**
   * <p>changed.</p>
   */
  abstract protected void changed();

  /**
   * <p>Constructor for ChangeNotifyList.</p>
   */
  protected ChangeNotifyList() {
    this(new ArrayList(2));
  }

  /**
   * <p>Constructor for ChangeNotifyList.</p>
   *
   * @param list a {@link java.util.List} object.
   */
  protected ChangeNotifyList(List list) {
    this.list = list;
  }

  /**
   * <p>Getter for the field <code>list</code>.</p>
   *
   * @return a {@link java.util.List} object.
   */
  protected List getList() {
    return list;
  }

  /**
   * <p>size.</p>
   *
   * @return a int.
   */
  public int size() {
    return list.size();
  }

  /**
   * <p>isEmpty.</p>
   *
   * @return a boolean.
   */
  public boolean isEmpty() {
    return list.isEmpty();
  }

  /**
   * <p>toArray.</p>
   *
   * @return an array of {@link java.lang.Object} objects.
   */
  public Object[] toArray() {
    return list.toArray();
  }

  /** {@inheritDoc} */
  public Object get(int index) {
    return list.get(index);
  }

  /** {@inheritDoc} */
  public int indexOf(Object o) {
    return list.indexOf(o);
  }

  /** {@inheritDoc} */
  public int lastIndexOf(Object o) {
    return list.lastIndexOf(o);
  }

  /** {@inheritDoc} */
  public boolean contains(Object o) {
    return list.contains(o);
  }

  /** {@inheritDoc} */
  public boolean containsAll(Collection c) {
    return list.containsAll(c);
  }

  /**
   * <p>iterator.</p>
   *
   * @return a {@link java.util.Iterator} object.
   */
  public Iterator iterator() {
    return listIterator();
  }

  /**
   * <p>listIterator.</p>
   *
   * @return a {@link java.util.ListIterator} object.
   */
  public ListIterator listIterator() {
    return listIterator(0);
  }

  /** {@inheritDoc} */
  public ListIterator listIterator(int index) {
    return new ChangeIterator(list.listIterator(index));
  }

  /**
   * <p>toArray.</p>
   *
   * @param a an array of {@link java.lang.Object} objects.
   * @return an array of {@link java.lang.Object} objects.
   */
  public Object[] toArray(Object[] a) {
    return list.toArray(a);
  }

  /**
   * <p>clear.</p>
   */
  public void clear() {
    list.clear();
    changed();
  }

  /**
   * <p>remove.</p>
   *
   * @param index a int.
   * @return a {@link java.lang.Object} object.
   */
  public Object remove(int index) {
    Object result = list.remove(index);
    changed();
    return result;
  }

  /** {@inheritDoc} */
  public void add(int index, Object element) {
    list.add(index, element);
    changed();
  }

  /** {@inheritDoc} */
  public boolean add(Object o) {
    if (list.add(o)) {
      changed();
      return true;
    }
    else
      return false;
  }

  /** {@inheritDoc} */
  public boolean remove(Object o) {
    if (list.remove(o)) {
      changed();
      return true;
    }
    else
      return false;
  }

  /** {@inheritDoc} */
  public boolean addAll(int index, Collection c) {
    if (list.addAll(index, c)) {
      changed();
      return true;
    }
    else
      return false;
  }

  /** {@inheritDoc} */
  public boolean addAll(Collection c) {
    if (list.addAll(c)) {
      changed();
      return true;
    }
    else
      return false;
  }

  /** {@inheritDoc} */
  public boolean removeAll(Collection c) {
    if (list.removeAll(c)) {
      changed();
      return true;
    }
    else
      return false;
  }

  /** {@inheritDoc} */
  public boolean retainAll(Collection c) {
    if (list.retainAll(c)) {
      changed();
      return true;
    }
    else
      return false;
  }

  /** {@inheritDoc} */
  public List subList(int fromIndex, int toIndex) {
    return new ChangeNotifyList(list.subList(fromIndex, toIndex)) {
      protected void changed() {
        ChangeNotifyList.this.changed();
      }
    };
  }

  /** {@inheritDoc} */
  public Object set(int index, Object element) {
    Object result = list.set(index, element);
    changed();
    return result;
  }

  private class ChangeIterator implements ListIterator {
    private ListIterator iterator;

    ChangeIterator(ListIterator iterator) {
      this.iterator = iterator;
    }

    public int nextIndex() {
      return iterator.nextIndex();
    }

    public int previousIndex() {
      return iterator.previousIndex();
    }

    public boolean hasPrevious() {
      return iterator.hasPrevious();
    }

    public Object previous() {
      return iterator.previous();
    }

    public void add(Object o) {
      iterator.add(o);
      changed();
    }

    public void set(Object o) {
      iterator.set(o);
      changed();
    }

    public void remove() {
      iterator.remove();
      changed();
    }

    public boolean hasNext() {
      return iterator.hasNext();
    }

    public Object next() {
      return iterator.next();
    }
  }
}
