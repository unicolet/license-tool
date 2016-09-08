package it.lic.keypair;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
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
    void create() throws Exception;

    /**
     * Reads and returns  public key.
     */
    PublicKey readPublicKey() throws Exception;

    class Default implements LicenseKeyPair {
        /**
         * The storage implementation.
         */
        private final Storage storage;
        private final String name;

        /**
         * Ctor.
         */
        public Default(final Storage storage, final String name) {
            this.storage=storage;
            this.name=name;
        }

        @Override
        public final boolean exists() {
            return this.storage.exists(this.name);
        }

        @Override
        public final void create() throws Exception {
            final KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            final KeyPair keypair = kpg.generateKeyPair();
            this.storage.write(
                this.name,
                keypair.getPublic().getEncoded()
            );
            this.storage.write(
                String.format("%s/pk", this.name),
                keypair.getPrivate().getEncoded()
            );
        }

        @Override
        public final PublicKey readPublicKey()
            throws Exception {
            final KeyFactory keyfactory = KeyFactory.getInstance("RSA");
            final KeySpec keyspec = new X509EncodedKeySpec(
                this.storage.read(this.name)
            );
            return keyfactory.generatePublic(keyspec);
        }
    }
}
