package it.lic;

import it.lic.error.LicenseToolException;
import it.lic.key.Key;
import it.lic.keypair.LicenseKeyPair;
import it.lic.keypair.ReadableLicenseKeyPair;
import it.lic.keypair.StorableLicenseKeyPair;
import it.lic.storage.Storage;
import java.security.KeyPairGenerator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * TODO: Add Javadoc.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Wallet {
    Iterator<License> licenses(LicenseKeyPair lkp, String filter) throws Exception;

    LicenseKeyPair newLicenseKeyPair(String name) throws Exception ;

    LicenseKeyPair licenseKeyPair(String name) throws Exception ;

    License newLicense(
        String name,
        LicenseKeyPair keypair,
        String issuer,
        Date until,
        Map<String,String> headers) throws LicenseToolException;

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

        @Override
        public LicenseKeyPair licenseKeyPair(String name) throws Exception {
            return new ReadableLicenseKeyPair(name, this.storage);
        }

        @Override
        public License newLicense(
            final String name, final LicenseKeyPair keypair,
            final String issuer, final Date until,
            final Map<String, String> headers) throws LicenseToolException {
            final License newlicense;
            try {
                newlicense = new License.Default(
                    name, keypair, issuer, until, headers
                );
                this.storage.write(new StorableLicense(newlicense).path(), newlicense.encode().getBytes());
            } catch (Exception e) {
                throw new LicenseToolException("Can't generate new License", e);
            }
            return newlicense;
        }
    }
}
