package it.lic;

import it.lic.key.Key;
import it.lic.key.NestedKey;
import it.lic.key.StorageKey;
import it.lic.keypair.LicenseKeyPair;
import java.util.Date;

/**
 * A license that can be stored to a storage.
 *
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StorableLicense implements License {
    private final License origin;

    public StorableLicense(License license) {
        this.origin = license;
    }

    @Override
    public String encode() throws Exception {
        return origin.encode();
    }

    @Override
    public String name() {
        return this.origin.name();
    }

    @Override
    public String issuer() {
        return this.origin.issuer();
    }

    @Override
    public Date until() {
        return this.origin.until();
    }

    @Override
    public LicenseKeyPair signer() {
        return this.origin.signer();
    }

    public Key path() {
        return new NestedKey(
            new StorageKey(this.origin.signer().name()),
            this.name()
        );
    }
}
