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
        return null;
    }

    @Override
    public void forEach(final Consumer<? super License> action) {

    }

    @Override
    public Spliterator<License> spliterator() {
        return null;
    }
}
