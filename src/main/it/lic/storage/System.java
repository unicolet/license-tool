package it.lic.storage;

/**
 * A System wrapper
 *
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface System {
    /**
     * Returns the string repr of the user home directory
     */
    String home();

    /**
     * Returns the system-dependent file separator.
     */
    String fileSeparator();

    final class Default implements System {

        @Override
        public String home() {
            return java.lang.System.getProperty("user.home");
        }

        @Override
        public String fileSeparator() {
            return java.lang.System.getProperty("file.separator");
        }
    }
}
