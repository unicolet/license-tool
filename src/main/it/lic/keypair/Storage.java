package it.lic.keypair;

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
    byte[] read(String key);

    /**
     * Check if a key exists.
     * @param key
     * @return true if it exists.
     */
    boolean exists(String key);

    /**
     * Writes data in our storage implementation.
     * @param key
     * @param data
     */
    void write(String key, byte[] data);
}
