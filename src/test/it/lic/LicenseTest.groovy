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

  def "can list all licenses belonging to a new keypair"() {
    setup:
    def tmp = File.createTempDir()
    tmp.deleteOnExit()
    def storage = new FileStorage(tmp);
    def keypair = new LicenseKeyPair.Default(storage, "abc")

    expect:
    !keypair.licenses("").hasNext()
  }

  def "can list all licenses belonging to a keypair"() {
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
    keypair.licenses("").hasNext()
    keypair.licenses("").next().encode() == license.encode()
    keypair.licenses("").next().name() == license.name()
  }

  def "can filter licenses belonging to a keypair"() {
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
    keypair.licenses("Server1").hasNext()
    keypair.licenses("server1").hasNext()
    keypair.licenses("server1").next().encode() == license.encode()
    keypair.licenses("server1").next().name() == license.name()

    !keypair.licenses("server2").hasNext()
  }

  def "can filter licenses belonging to a specific keypair"() {
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

    def keypair2 = new LicenseKeyPair.Default(storage, "abc2")
    def license2 = new License.Default(
      "server1.example.org",
      keypair2,
      "Umberto Nicoletti",
      new Date(),
      Collections.emptyMap()
    )
    storage.write(new StorableLicense(license2).path(), license2.encode().bytes)
    Iterator licenses = keypair.licenses("Server1")

    expect:
    licenses.hasNext()
    licenses.next().encode() == license.encode()
    !licenses.hasNext()
    keypair.licenses("server1").hasNext()

    !keypair.licenses("server2").hasNext()
  }
}
