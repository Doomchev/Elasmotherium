package base;

import base.SimpleMap.Entry;
import java.util.Iterator;
import java.util.LinkedList;

public class SimpleMap<K, V> implements Iterable<Entry<K, V>> {
  private final LinkedList<Entry<K, V>> entries = new LinkedList<>();
  
  public static class Entry<K, V> {
    public K key;
    public V value;

    public Entry(K key, V value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public String toString() {
      return key + " = " + value;
    }
  }
  
  @Override
  public Iterator<Entry<K, V>> iterator() {
    return entries.iterator();
  }
  
  public void put(K key, V value) {
    entries.add(new Entry<>(key, value));
  }
  
  public V get(K key) {
    Iterator<Entry<K, V>> it = entries.descendingIterator();
    while(it.hasNext()) {
      Entry<K, V> entry = it.next();
      if(entry.key.equals(key)) return entry.value;
    }
    return null;
  }
}
