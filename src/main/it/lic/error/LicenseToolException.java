package it.lic.error;

/**
 * TODO: Add Javadoc.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class LicenseToolException extends Exception {
    private final Exception cause;
    private final String msg;

    public LicenseToolException(String msg, Exception cause) {
        this.cause = cause;
        this.msg = msg;
    }
}
