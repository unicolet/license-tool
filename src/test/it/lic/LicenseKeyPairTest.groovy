package it.lic
/**
 * Test suite for KeyPair functionality.
 */
public class LicenseKeyPairTest extends spock.lang.Specification {
  def "can retrieve a publickey"() {
    setup:
    def wallet = new Wallet.Default(new TempFileStorage())
    def license = wallet.newLicenseKeyPair(name)

    expect:
    license.publicKey() != null

    where:
    name      | exists
    "iexist"  | true
  }
}
