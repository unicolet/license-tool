package it.lic.storage;

import it.lic.error.LicenseToolException;
import it.lic.key.Key;
import java.util.List;

/**
 * A Storage interface for all our KP management.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Storage {
    /**
     * Reads some data from this storage.
     * @param key
     * @return data. Exception if not found.
     */
    byte[] read(Key key) throws LicenseToolException;

    /**
     * Check if a key exists.
     * @param key
     * @return true if it exists.
     */
    boolean exists(Key key);

    /**
     * Writes data in our storage implementation.
     * @param key
     * @param data
     */
    void write(Key key, byte[] data) throws LicenseToolException;

    /**
     * Returns all keys.
     */
    List<Key> keys();
}
