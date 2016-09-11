package it.lic.keypair;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import it.lic.Key;
import it.lic.License;
import it.lic.PkKey;
import it.lic.PubKey;
import it.lic.Storage;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A Private/Public key pair.
 *
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface LicenseKeyPair {
    /**
     * Sign a JWT.
     * @param jwt
     * @throws Exception
     */
    void sign(JwtBuilder jwt) throws Exception;

    /**
     * Reads and returns  public key.
     */
    PublicKey publicKey() throws Exception;

    /**
     * The name of this kp.
     * @return
     */
    String name();

    /**
     * A list of licenses optionally filtered by name (contains).
     */
    Iterator<License> licenses(String namefilter) throws Exception;

    final class Default implements LicenseKeyPair {
        /**
         * The storage implementation.
         */
        private final Storage storage;
        private final String name;

        /**
         * Ctor.
         */
        public Default(final Storage storage, final String name) throws Exception {
            this.storage=storage;
            this.name=name;

            final KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            final KeyPair keypair = kpg.generateKeyPair();
            this.storage.write(
                new PubKey(this.name),
                keypair.getPublic().getEncoded()
            );
            this.storage.write(
                new PkKey(this.name),
                keypair.getPrivate().getEncoded()
            );
        }

        @Override
        public void sign(final JwtBuilder jwt) throws Exception {
            final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(
                this.storage.read(
                    new PkKey(this.name)
                )
            );
            final KeyFactory kf = KeyFactory.getInstance("RSA");
            jwt.signWith(SignatureAlgorithm.RS512, kf.generatePrivate(spec));
        }

        @Override
        public PublicKey publicKey()
            throws Exception {
            final KeyFactory keyfactory = KeyFactory.getInstance("RSA");
            final KeySpec keyspec = new X509EncodedKeySpec(
                this.storage.read(
                    new PubKey(this.name)
                )
            );
            return keyfactory.generatePublic(keyspec);
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public Iterator<License> licenses(final String namefilter) throws Exception {
            final Collection<License> licenses = new ArrayList<>(1);
            for( final Key key : this.storage.keys() ) {
                if(key.nested()) {
                    if(key.path().toLowerCase().contains(namefilter.toLowerCase())) {
                        licenses.add(new License.FromByte(
                            this.storage.read(key),
                            this
                        ));
                    }
                }
            }
            return licenses.iterator();
        }
    }
}
