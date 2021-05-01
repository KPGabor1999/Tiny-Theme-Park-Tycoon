package Idlethemeparkworld.misc.utils;

import java.util.Objects;

/**
 * A key-value pair for use in maps
 * @param <K> Key type
 * @param <V> Value type
 */
public class Pair<K, V> {

    private final K key;
    private final V value;

    /**
     * Creates a new key-value pair 
     * @param key The key, usually unique
     * @param value The value to store
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return The key of the pair
     */
    public K getKey() {
        return key;
    }

    /**
     * @return The value of the pair
     */
    public V getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.key);
        hash = 47 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
