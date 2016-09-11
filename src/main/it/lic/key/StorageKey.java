package it.lic.key;

/**
 * A Storage Key implementation.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class StorageKey implements Key {
    private final String path;

    public StorageKey(String path) {
        this.path=path;
    }

    @Override
    public String fullpath() {
        return this.path;
    }

    @Override
    public String path() {
        return this.path;
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
