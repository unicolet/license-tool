package it.lic;

/**
 * TODO: Add Javadoc.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class NestedKey implements Key {
    private Key parent;
    private final String path;

    public NestedKey(Key parent, String path) {
        this.parent = parent;
        this.path = path;
    }

    @Override
    public String path() {
        return String.format(
            "%s%s%s",
            this.parent.path(),
            new Separator.Default(),
            this.path
        );
    }

    @Override
    public boolean nested() {
        return true;
    }
}
