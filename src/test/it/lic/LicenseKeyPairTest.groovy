package it.lic

import it.lic.keypair.InMemoryStorage
import it.lic.keypair.LicenseKeyPair;

/**
 * Test suite for KeyPair functionality.
 */
public class LicenseKeyPairTest extends spock.lang.Specification {
    def "can check if a keypair exists"() {
      expect:
      new LicenseKeyPair.Default(new InMemoryStorage(), name).exists() == exists

      where:
      name          | exists
      "idontexist"  | false
    }

  def "can create a keypair"() {
    expect:
    def license = new LicenseKeyPair.Default(new InMemoryStorage(), name)
    license.create()
    license.exists() == exists

    where:
    name      | exists
    "iexist"  | true
  }

  def "can retrieve a publickey"() {
    expect:
    def license = new LicenseKeyPair.Default(new InMemoryStorage(), name)
    license.create()
    license.exists() == exists
    license.readPublicKey() != null

    where:
    name      | exists
    "iexist"  | true
  }
}
