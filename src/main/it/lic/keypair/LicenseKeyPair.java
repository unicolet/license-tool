package it.lic.keypair;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * A Private/Public key pair.
 *
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface LicenseKeyPair {
    /**
     * Returns the public key.
     */
    PublicKey publicKey() ;

    /**
     * Returns the private key.
     */
    PrivateKey privateKey() ;

    /**
     * The name of this kp.
     * @return
     */
    String name();

    final class Default implements LicenseKeyPair {
        private final String name;
        private KeyPair keypair;

        /**
         * Ctor.
         */
        public Default(final KeyPair kp, final String name) {
            this.keypair = kp;
            this.name = name;
        }

        @Override
        public PublicKey publicKey() {
            return this.keypair.getPublic();
        }

        @Override
        public PrivateKey privateKey() {
            return this.keypair.getPrivate();
        }

        @Override
        public String name() {
            return this.name;
        }
    }
}
