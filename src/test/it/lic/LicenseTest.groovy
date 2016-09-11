package it.lic

import it.lic.keypair.LicenseKeyPair
/**
 * Test suite for KeyPair functionality.
 */
public class LicenseTest extends spock.lang.Specification {
  def "can build a license"() {
    setup:
    def tmp = File.createTempDir()
    tmp.deleteOnExit()
    def storage = new FileStorage(tmp);
    def keypair = new LicenseKeyPair.Default(storage, "abc")
    def license = new License.Default(
      "server1.example.org",
      keypair,
      "Umberto Nicoletti",
      new Date(),
      Collections.emptyMap()
    )

    expect:
    license.encode() != null
  }

  def "can store and retrieve a license"() {
    setup:
    def tmp = File.createTempDir()
    tmp.deleteOnExit()
    def storage = new FileStorage(tmp);
    def keypair = new LicenseKeyPair.Default(storage, "abc")
    def license = new License.Default(
      "server1.example.org",
      keypair,
      "Umberto Nicoletti",
      new Date(),
      Collections.emptyMap()
    )
    storage.write(new StorableLicense(license).path(), license.encode().bytes)

    expect:
    storage.read(new StorableLicense(license).path()) == license.encode().bytes
  }
}
