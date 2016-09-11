package it.lic.key;

/**
 * A Key fullpath components separator.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Separator {
    /**
     * The representation of a separator as String.
     * @return
     */
    String toString();

    /**
     * The default separator is the OS-dependent file separator.
     */
    final class Default implements Separator {
        @Override
        public String toString() {
            return System.getProperty("file.separator");
        }
    }
}
