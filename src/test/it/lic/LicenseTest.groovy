package it.lic
/**
 * Test suite for KeyPair functionality.
 */
public class LicenseTest extends spock.lang.Specification {

  def "can build a license"() {
    setup:
    def wallet = new Wallet.Default(new TempFileStorage())
    def keypair = wallet.newLicenseKeyPair("abc")
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
    def storage = new TempFileStorage()
    def wallet = new Wallet.Default(storage)
    def keypair = wallet.newLicenseKeyPair("abc")
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
    def storage = new TempFileStorage()
    def wallet = new Wallet.Default(storage)
    def keypair = wallet.newLicenseKeyPair("abc")

    expect:
    !wallet.licenses(keypair, "").hasNext()
  }

  def "can list all licenses belonging to a keypair"() {
    setup:
    def storage = new TempFileStorage()
    def wallet = new Wallet.Default(storage)
    def keypair = wallet.newLicenseKeyPair("abc")
    def license = new License.Default(
      "server1.example.org",
      keypair,
      "Umberto Nicoletti",
      new Date(),
      Collections.emptyMap()
    )
    storage.write(new StorableLicense(license).path(), license.encode().bytes)

    expect:
    wallet.licenses(keypair, "").hasNext()
    wallet.licenses(keypair, "").next().encode() == license.encode()
    wallet.licenses(keypair, "").next().name() == license.name()
  }

  def "can filter licenses belonging to a keypair"() {
    setup:
    def storage = new TempFileStorage()
    def wallet = new Wallet.Default(storage)
    def keypair = wallet.newLicenseKeyPair("abc")
    def license = new License.Default(
      "server1.example.org",
      keypair,
      "Umberto Nicoletti",
      new Date(),
      Collections.emptyMap()
    )
    storage.write(new StorableLicense(license).path(), license.encode().bytes)

    expect:
    wallet.licenses(keypair, "Server1").hasNext()
    wallet.licenses(keypair, "server1").hasNext()
    wallet.licenses(keypair, "server1").next().encode() == license.encode()
    wallet.licenses(keypair, "server1").next().name() == license.name()

    !wallet.licenses(keypair, "server2").hasNext()
  }

  def "can filter licenses belonging to a specific keypair"() {
    setup:
    def storage = new TempFileStorage()
    def wallet = new Wallet.Default(storage)
    def keypair = wallet.newLicenseKeyPair("abc")
    def license = new License.Default(
      "server1.example.org",
      keypair,
      "Umberto Nicoletti",
      new Date(),
      Collections.emptyMap()
    )
    storage.write(new StorableLicense(license).path(), license.encode().bytes)

    def keypair2 = wallet.newLicenseKeyPair("def")
    def license2 = new License.Default(
      "server1.example.org",
      keypair2,
      "Umberto Nicoletti",
      new Date(),
      Collections.emptyMap()
    )
    storage.write(new StorableLicense(license2).path(), license2.encode().bytes)
    Iterator licenses = wallet.licenses(keypair, "Server1")

    expect:
    licenses.hasNext()
    licenses.next().encode() == license.encode()
    !licenses.hasNext()
    wallet.licenses(keypair, "server1").hasNext()

    !wallet.licenses(keypair, "server2").hasNext()
  }
}
