package it.lic;

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
    public String path() {
        return this.path;
    }

    public class Nested implements Key {
        private Key parent;
        private final String path;

        public Nested(Key parent, String path) {
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
    }
}
