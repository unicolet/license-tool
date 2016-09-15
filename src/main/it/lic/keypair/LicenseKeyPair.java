package it.lic.keypair;

import it.lic.error.LicenseToolException;
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
    PublicKey publicKey() throws LicenseToolException;

    /**
     * Returns the private key.
     */
    PrivateKey privateKey() throws LicenseToolException;

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
        public PublicKey publicKey() throws LicenseToolException {
            return this.keypair.getPublic();
        }

        @Override
        public PrivateKey privateKey() throws LicenseToolException {
            return this.keypair.getPrivate();
        }

        @Override
        public String name() {
            return this.name;
        }
    }

    final class Comparable implements LicenseKeyPair {
        final LicenseKeyPair origin;

        public Comparable(LicenseKeyPair origin) {
            this.origin = origin;
        }

        @Override
        public PublicKey publicKey() throws LicenseToolException {
            return this.origin.publicKey();
        }

        @Override
        public PrivateKey privateKey() throws LicenseToolException {
            return this.origin.privateKey();
        }

        @Override
        public String name() {
            return this.origin.name();
        }

        public boolean equals(final LicenseKeyPair other) {
            boolean result = false;
            try {
                result = this.origin.privateKey().equals(other.privateKey())
                    && this.origin.publicKey().equals(other.publicKey());
            } catch (LicenseToolException e) {}
            return result;
        }
    }
}
