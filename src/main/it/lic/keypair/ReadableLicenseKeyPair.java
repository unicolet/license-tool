package it.lic.keypair;

import it.lic.error.LicenseToolException;
import it.lic.key.PkKey;
import it.lic.key.PubKey;
import it.lic.storage.Storage;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * TODO: Add Javadoc.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class ReadableLicenseKeyPair implements LicenseKeyPair {
    private final Storage storage;
    private final String name;

    public ReadableLicenseKeyPair(String name, Storage storage) {
        this.storage = storage;
        this.name = name;
    }

    @Override
    public PublicKey publicKey() throws LicenseToolException {
        try {
            return this.readPublicKey();
        } catch (Exception e) {
            throw new LicenseToolException("Error loading public key", e);
        }
    }

    @Override
    public PrivateKey privateKey() throws LicenseToolException {
        try {
            return this.readPrivateKey();
        } catch (Exception e) {
            throw new LicenseToolException("Error loading private key", e);
        }
    }

    @Override
    public String name() {
        return this.name;
    }

    private PublicKey readPublicKey() throws Exception {
        final KeyFactory keyfactory = KeyFactory.getInstance("RSA");
        final KeySpec keyspec = new X509EncodedKeySpec(
            this.storage.read(
                new PubKey(this.name)
            )
        );
        return keyfactory.generatePublic(keyspec);
    }

    private PrivateKey readPrivateKey() throws Exception {
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(
            this.storage.read(
                new PkKey(this.name)
            )
        );
        final KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
