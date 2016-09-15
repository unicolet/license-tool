package it.lic.error;

/**
 * Exception for our app.
 *
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class LicenseToolException extends Exception {
    public LicenseToolException(String msg, Exception cause) {
        super(msg, cause);
    }
}
