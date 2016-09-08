package it.lic.keypair;

import java.util.HashMap;
import java.util.Map;

/**
 * A storage backed by a Map.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class InMemoryStorage implements Storage {
    private final Map<String, byte[]> map;

    public InMemoryStorage() {
        this.map = new HashMap(2);
    }

    @Override
    public byte[] read(final String key) {
        return this.map.get(key);
    }

    @Override
    public boolean exists(final String key) {
        return this.map.containsKey(key);
    }

    @Override
    public void write(final String key, final byte[] data) {
        this.map.put(key, data);
    }
}
