package it.lic.keypair;

import it.lic.License;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * TODO: Add Javadoc.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class LicenseIterable implements Iterable<License> {
    @Override
    public Iterator<License> iterator() {
        return new LicenseIterator();
    }

    @Override
    public void forEach(final Consumer<? super License> action) {
        throw new UnsupportedOperationException("Unsupported operation.");
    }

    @Override
    public Spliterator<License> spliterator() {
        throw new UnsupportedOperationException("Unsupported operation.");
    }

    private class LicenseIterator implements Iterator<License> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public License next() {
            return null;
        }
    }
}
