package it.lic;

import it.lic.storage.System;

/**
 * A Fake system implementation for unit tests.
 *
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class FakeSystem implements System {
    @Override
    public String home() {
        return "/tmp";
    }

    @Override
    public String fileSeparator() {
        return java.lang.System.getProperty("file.separator");
    }
}
