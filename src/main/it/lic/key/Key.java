package it.lic.key;

/**
 * Storage Key.
 *
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Key {
    /**
     * The full (parents + this) path identifying this key.
     * @return
     */
    String fullpath();

    /**
     * The relative path identifying this key.
     * @return
     */
    String path();

    /**
     * If the key is nested.
     * @return
     */
    boolean nested();
}
