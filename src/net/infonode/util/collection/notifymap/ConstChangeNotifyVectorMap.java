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


// $Id: ConstChangeNotifyVectorMap.java,v 1.11 2005/12/04 13:46:04 jesper Exp $
package net.infonode.util.collection.notifymap;

import net.infonode.util.ValueChange;
import net.infonode.util.collection.map.ConstVectorMap;
import net.infonode.util.collection.map.MapAdapter;
import net.infonode.util.collection.map.base.ConstMap;
import net.infonode.util.collection.map.base.ConstMapIterator;
import net.infonode.util.signal.Signal;
import net.infonode.util.signal.SignalListener;

import java.util.ArrayList;

/**
 * <p>ConstChangeNotifyVectorMap class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class ConstChangeNotifyVectorMap extends AbstractConstChangeNotifyMap {
  private ConstVectorMap vectorMap = new ConstVectorMap();
  private ArrayList mapListeners;

  /**
   * <p>firstListenerAdded.</p>
   */
  protected void firstListenerAdded() {
    mapListeners = new ArrayList(vectorMap.getMapCount() + 2);

    for (int i = 0; i < vectorMap.getMapCount(); i++) {
      addMapListener(i);
    }
  }

  /**
   * <p>lastListenerRemoved.</p>
   */
  protected void lastListenerRemoved() {
    for (int i = vectorMap.getMapCount() - 1; i >= 0; i--) {
      removeMapListener(i);
    }

    mapListeners = null;
  }

  private Object getValue(Object key, int fromIndex, int toIndex) {
    for (int i = fromIndex; i < toIndex; i++) {
      Object value = getMap(i).get(key);

      if (value != null)
        return value;
    }

    return null;
  }

  /**
   * <p>getMapIndex.</p>
   *
   * @param map a {@link net.infonode.util.collection.map.base.ConstMap} object.
   * @return a int.
   */
  public int getMapIndex(ConstMap map) {
    return vectorMap.getMapIndex(map);
  }

  /**
   * <p>addMap.</p>
   *
   * @param map a {@link net.infonode.util.collection.notifymap.ConstChangeNotifyMap} object.
   */
  public void addMap(ConstChangeNotifyMap map) {
    addMap(vectorMap.getMapCount(), map);
  }

  /**
   * <p>addMap.</p>
   *
   * @param index a int.
   * @param map a {@link net.infonode.util.collection.notifymap.ConstChangeNotifyMap} object.
   */
  public void addMap(int index, ConstChangeNotifyMap map) {
    vectorMap.addMap(index, map);

    if (getChangeSignalInternal().hasListeners()) {
      addMapListener(index);
      MapAdapter changes = new MapAdapter();

      for (ConstMapIterator iterator = map.constIterator(); iterator.atEntry(); iterator.next()) {
        Object value = getValue(iterator.getKey(), 0, index);

        if (value == null) {
          Object mapValue = iterator.getValue();
          changes.put(iterator.getKey(),
                      new ValueChange(getValue(iterator.getKey(), index + 1, getMapCount()), mapValue));
        }
      }

      fireEntriesChanged(changes);
    }
  }

  private void addMapListener(int index) {
    if (mapListeners == null)
      mapListeners = new ArrayList(index + 2);

    final ConstChangeNotifyMap map = getMap(index);

    SignalListener mapListener = new SignalListener() {
      public void signalEmitted(Signal signal, Object object) {
        ConstMap changes = (ConstMap) object;
        MapAdapter changes2 = new MapAdapter();
        int index = getMapIndex(map);

        for (ConstMapIterator iterator = changes.constIterator(); iterator.atEntry(); iterator.next()) {
          Object value = getValue(iterator.getKey(), 0, index);

          if (value == null) {
            ValueChange vc = (ValueChange) iterator.getValue();
            changes2.put(iterator.getKey(), vc.getOldValue() == null ? new ValueChange(
                getValue(iterator.getKey(), index + 1, getMapCount()), vc.getNewValue()) :
                                            vc.getNewValue() == null ? new ValueChange(vc.getOldValue(),
                                                                                       getValue(iterator.getKey(),
                                                                                                index + 1,
                                                                                                getMapCount())) :
                                            vc);
          }
        }

        fireEntriesChanged(changes2);
      }
    };

    mapListeners.add(index, mapListener);
    map.getChangeSignal().add(mapListener);
  }

  private void removeMapListener(int index) {
    ConstChangeNotifyMap map = getMap(index);
    map.getChangeSignal().remove((SignalListener) mapListeners.get(index));
    mapListeners.remove(index);
  }

  /**
   * <p>getMapCount.</p>
   *
   * @return a int.
   */
  public int getMapCount() {
    return vectorMap.getMapCount();
  }

  /**
   * <p>removeMap.</p>
   *
   * @param index a int.
   */
  public void removeMap(int index) {
    if (getChangeSignalInternal().hasListeners())
      removeMapListener(index);

    ConstMap map = vectorMap.removeMap(index);

    if (getChangeSignalInternal().hasListeners()) {
      MapAdapter changes = new MapAdapter();

      for (ConstMapIterator iterator = map.constIterator(); iterator.atEntry(); iterator.next()) {
        Object value = getValue(iterator.getKey(), 0, index);

        if (value == null) {
          Object mapValue = iterator.getValue();
          changes.put(iterator.getKey(), new ValueChange(mapValue, getValue(iterator.getKey(), index, getMapCount())));
        }
      }

      fireEntriesChanged(changes);
    }
  }

  /** {@inheritDoc} */
  public Object get(Object key) {
    return vectorMap.get(key);
  }

  /** {@inheritDoc} */
  public boolean containsKey(Object key) {
    return vectorMap.containsKey(key);
  }

  /** {@inheritDoc} */
  public boolean containsValue(Object value) {
    return vectorMap.containsValue(value);
  }

  /**
   * <p>isEmpty.</p>
   *
   * @return a boolean.
   */
  public boolean isEmpty() {
    return vectorMap.isEmpty();
  }

  /**
   * <p>getMap.</p>
   *
   * @param index a int.
   * @return a {@link net.infonode.util.collection.notifymap.ConstChangeNotifyMap} object.
   */
  public ConstChangeNotifyMap getMap(int index) {
    return (ConstChangeNotifyMap) vectorMap.getMap(index);
  }

  /**
   * <p>constIterator.</p>
   *
   * @return a {@link net.infonode.util.collection.map.base.ConstMapIterator} object.
   */
  public ConstMapIterator constIterator() {
    return vectorMap.constIterator();
  }
}
