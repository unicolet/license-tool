package it.lic

import it.lic.keypair.InMemoryStorage
import it.lic.keypair.LicenseKeyPair;

/**
 * Test suite for KeyPair functionality.
 */
public class LicenseKeyPairTest extends spock.lang.Specification {
  def "can retrieve a publickey"() {
    setup:
    def license = new LicenseKeyPair.Default(new InMemoryStorage(), name)

    expect:
    license.readPublicKey() != null

    where:
    name      | exists
    "iexist"  | true
  }
}
