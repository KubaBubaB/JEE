package ekstra.jest.JEE.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface Repository<K, V> {
    Optional<V> get(K key);
    HashMap<K, V> getAll();
    void save(K key, V value);
    void remove(K key);
    void update(K key, V value);
}
