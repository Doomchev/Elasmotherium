package base;

import java.util.LinkedList;

public class SimpleMap<K, V> {
  public final LinkedList<Entry<K, V>> entries = new LinkedList<>();
  
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
  
  public void add(K key, V value) {
    entries.add(new Entry<>(key, value));
  }
}
