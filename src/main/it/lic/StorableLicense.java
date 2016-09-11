package it.lic;

import it.lic.keypair.LicenseKeyPair;

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
