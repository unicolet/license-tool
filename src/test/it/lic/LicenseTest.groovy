package it.lic

import it.lic.keypair.InMemoryStorage
import it.lic.keypair.LicenseKeyPair
/**
 * Test suite for KeyPair functionality.
 */
public class LicenseTest extends spock.lang.Specification {
  def "can build license"() {
    setup:
    def storage = new InMemoryStorage();
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

  def "can store and retrieve license"() {
    setup:
    def storage = new InMemoryStorage();
    def keypair = new LicenseKeyPair.Default(storage, "abc")
    def license = new License.Default(
      "server1.example.org",
      keypair,
      "Umberto Nicoletti",
      new Date(),
      Collections.emptyMap()
    )
    storage.write(license.path(), license.encode().bytes)

    expect:
    storage.read(license.path()) == license.encode().bytes
  }
}
