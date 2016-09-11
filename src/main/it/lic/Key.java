package it.lic;

/**
 * Storage Key.
 *
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Key {
    /**
     * The path identifying this key.
     * @return
     */
    String path();

    /**
     * If the key is nested.
     * @return
     */
    boolean nested();
}
