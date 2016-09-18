package it.lic;

import it.lic.storage.System;

/**
 * TODO: Add Javadoc.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SystemTest extends spock.lang.Specification {
    def "default settings"() {
        setup:
        def sys = new System.Default();

        expect:
        sys.home() == java.lang.System.getProperty("user.home")
        sys.fileSeparator() == java.lang.System.getProperty("file.separator")
    }
}
