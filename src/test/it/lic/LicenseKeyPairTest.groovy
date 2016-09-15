package it.lic

import it.lic.keypair.LicenseKeyPair

/**
 * Test suite for KeyPair functionality.
 */
public class LicenseKeyPairTest extends spock.lang.Specification {
  def "can create a keypair and get public key"() {
    setup:
    def wallet = new Wallet.Default(new TempFileStorage())
    def license = wallet.newLicenseKeyPair(name)

    expect:
    license.publicKey() != null

    where:
    name      | exists
    "iexist"  | true
  }

  def "can create and read keypair back"() {
    setup:
    def name = "abc"
    def wallet = new Wallet.Default(new TempFileStorage())
    def license = wallet.newLicenseKeyPair(name)

    expect:
    new LicenseKeyPair.Comparable(wallet.licenseKeyPair(name)).equals(license)
  }
}
