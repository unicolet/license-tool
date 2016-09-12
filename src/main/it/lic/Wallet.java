package it.lic;

import it.lic.key.Key;
import it.lic.keypair.LicenseKeyPair;
import it.lic.keypair.StorableLicenseKeyPair;
import it.lic.storage.Storage;
import java.security.KeyPairGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * TODO: Add Javadoc.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Wallet {
    Iterator<License> licenses(LicenseKeyPair lkp, String filter) throws Exception;

    LicenseKeyPair newLicenseKeyPair(String name) throws Exception ;

    final class Default implements Wallet {
        final Storage storage;

        public Default(Storage storage) {
            this.storage = storage;
        }

        @Override
        public Iterator<License> licenses(final LicenseKeyPair lkp, final String filter) throws Exception {
            final Collection<License> licenses = new ArrayList<>(1);
            for( final Key key : this.storage.keys() ) {
                if(key.nested()) {
                    if( key.parentKey().path().equals(lkp.name())
                        && key.path().toLowerCase().contains(filter.toLowerCase())
                        ) {
                        licenses.add(new License.FromByte(
                            this.storage.read(key),
                            lkp
                        ));
                    }
                }
            }
            return licenses.iterator();
        }

        @Override
        public LicenseKeyPair newLicenseKeyPair(final String name) throws Exception {
            final KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            final StorableLicenseKeyPair lkp =
                new StorableLicenseKeyPair(
                    new LicenseKeyPair.Default(kpg.generateKeyPair(), name)
                );
            lkp.save(this.storage);

            return lkp;
        }
    }
}
