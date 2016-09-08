package it.lic.keypair;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * TODO: Add Javadoc.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface LicenseKeyPair {
    /**
     * Checks if a KP exists.
     * @return true if exists.
     */
    boolean exists();

    /**
     * Creates a keypair.
     */
    void create() throws NoSuchAlgorithmException;

    /**
     * Reads and returns  public key.
     */
    PublicKey readPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException;

    public class Default implements LicenseKeyPair {
        /**
         * The storage implementation.
         */
        final private Storage storage;
        final private String name;

        /**
         * Ctor.
         */
        public Default(Storage storage, String name) {
            this.storage=storage;
            this.name=name;
        }

        @Override
        public boolean exists() {
            return this.storage.exists(this.name);
        }

        @Override
        public void create() throws NoSuchAlgorithmException {
            final KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            final KeyPair kp = kpg.generateKeyPair();
            this.storage.write(
                this.name,
                kp.getPublic().getEncoded()
            );
            this.storage.write(
                String.format("%s/pk", this.name),
                kp.getPrivate().getEncoded()
            );
        }

        @Override
        public PublicKey readPublicKey()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
            final KeyFactory keyfactory = KeyFactory.getInstance("RSA");
            final KeySpec keyspec = new X509EncodedKeySpec(
                this.storage.read(this.name)
            );
            return keyfactory.generatePublic(keyspec);
        }
    }
}
