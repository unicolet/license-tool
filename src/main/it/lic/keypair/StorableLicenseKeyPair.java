package it.lic.keypair;

import it.lic.error.LicenseToolException;
import it.lic.key.PkKey;
import it.lic.key.PubKey;
import it.lic.storage.Storage;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * TODO: Add Javadoc.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class StorableLicenseKeyPair implements LicenseKeyPair {
    private final LicenseKeyPair lkp;

    public StorableLicenseKeyPair(LicenseKeyPair lkp) {
        this.lkp = lkp;
    }

    @Override
    public PublicKey publicKey() throws LicenseToolException {
        return this.lkp.publicKey();
    }

    @Override
    public PrivateKey privateKey() throws LicenseToolException {
        return this.lkp.privateKey();
    }

    @Override
    public String name() {
        return this.lkp.name();
    }

    public void save(final Storage storage) throws Exception {
        storage.write(
            new PubKey(this.lkp.name()),
            this.lkp.publicKey().getEncoded()
        );
        storage.write(
            new PkKey(this.lkp.name()),
            this.lkp.privateKey().getEncoded()
        );
    }
}
