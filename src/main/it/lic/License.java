package it.lic;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import it.lic.keypair.LicenseKeyPair;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

/**
 * A SW License.
 *
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface License {
    /**
     * A representation of this license.
     * @return
     */
    String encode() throws Exception;

    /**
     * The license name.
     * @return
     */
    String name();

    /**
     *
     */

    /**
     * The keypair that signed this license.
     * @return the license key pair.
     */
    LicenseKeyPair signer();

    final class Default implements License {
        private final String name;
        private final String issuer;
        private final LicenseKeyPair keypair;
        private final Date until;
        private final Map<String,String> headers;

        /**
         * Ctor.
         */
        public Default(
            final String name,
            final LicenseKeyPair keypair,
            final String issuer,
            final Date until,
            final Map<String,String> headers) throws Exception {
            this.name = name;
            this.issuer = issuer;
            this.until = until;
            this.headers = headers;
            this.keypair = keypair;
        }

        @Override
        public String encode() throws Exception {
            final JwtBuilder builder = Jwts.builder()
                .setIssuer(this.issuer)
                .setHeaderParam("until", this.until);
            for(final Map.Entry<String, String> header : this.headers.entrySet()) {
                builder.setHeaderParam(header.getKey(), header.getValue());
            }
            builder.setHeaderParam("name", this.name);
            this.keypair.sign(builder);
            return builder.compact();        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public LicenseKeyPair signer() {
            return this.keypair;
        }
    }

    final class FromByte implements License {
        private final byte[] bytes;
        private final LicenseKeyPair keypair;
        private final PublicKey pubkey;

        public FromByte(byte[] bytes, LicenseKeyPair keypair) throws Exception {
            this.bytes = bytes;
            this.keypair = keypair;
            this.pubkey = keypair.publicKey();
        }

        @Override
        public String encode() throws Exception {
            return new String(bytes);
        }

        @Override
        public String name() {
            final Jwt token = Jwts.parser()
                .setSigningKey(this.pubkey)
                .parse(new String(this.bytes));
            return (String) token.getHeader().get("name");
        }

        @Override
        public LicenseKeyPair signer() {
            return this.keypair;
        }
    }
}
