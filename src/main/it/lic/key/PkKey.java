package it.lic.key;

/**
 * A Key representing a private key fullpath.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class PkKey implements Key {
    private final String name;

    public PkKey(String name) {
        this.name=name;
    }

    @Override
    public String fullpath() {
        return String.format("%s.priv", this.name);
    }

    @Override
    public String path() {
        return this.name;
    }

    @Override
    public boolean nested() {
        return false;
    }

    @Override
    public Key parentKey() {
        return new Key.Root();
    }
}
