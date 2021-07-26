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
  
  public void put(K key, V value) {
    entries.addFirst(new Entry<>(key, value));
  }
  
  public V get(K key) {
    for(Entry<K, V> entry: entries)
      if(entry.key.equals(key)) return entry.value;
    return null;
  }
}
