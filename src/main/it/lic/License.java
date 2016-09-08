package it.lic;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import it.lic.keypair.LicenseKeyPair;
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
     * An absolute path for our storage.
     * @return
     */
    String path();

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
            this.keypair.sign(builder);
            return builder.compact();        }

        @Override
        public String path() {
            return String.format("%s:%s", this.keypair.name(), this.name);
        }
    }
}
