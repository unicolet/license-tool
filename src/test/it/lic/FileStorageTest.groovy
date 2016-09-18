package it.lic

import it.lic.error.LicenseToolException
import it.lic.key.NestedKey
import it.lic.key.StorageKey
import it.lic.storage.FileStorage

/**
 * Test suite for FileStorage functionality.
 */
public class FileStorageTest extends spock.lang.Specification {
  def "can work from a tmp directory"() {
    setup:
    def tmp = File.createTempDir()
    tmp.deleteOnExit()
    def storage = new FileStorage(tmp)

    expect:
    storage.exists(new StorageKey(key)) == exists

    where:
    key          | exists
    "idontexist" | false
  }

  def "can create a FileStorage from a Key"() {
    setup:
    def key = new StorageKey("/tmp/abc")

    when:
    def storage = new FileStorage(key)

    then:
    noExceptionThrown()

    cleanup:
    new File(key.fullpath()).deleteOnExit()
  }

  def "can store and retrieve key"() {
    setup:
    def tmp = File.createTempDir()
    tmp.deleteOnExit()
    def storage = new FileStorage(tmp)

    expect:
    storage.write(new StorageKey(key), value.bytes)
    storage.read(new StorageKey(key)) == value.bytes
    storage.exists(new StorageKey(key)) == exists

    where:
    key | value | exists
    "k" | "val" | true
  }

  def "can list keys from an empty storage"() {
    setup:
    def tmp = File.createTempDir()
    tmp.deleteOnExit()
    def storage = new FileStorage(tmp)

    expect:
    storage.keys().size() == 0
  }

  def "can list keys from flat storage"() {
    setup:
    def tmp = File.createTempDir()
    tmp.deleteOnExit()
    def storage = new FileStorage(tmp)
    storage.write(new StorageKey("a"), "".bytes)
    storage.write(new StorageKey("b"), "".bytes)

    expect:
    storage.keys().size() == 2
  }

  def "can list keys from nested storage"() {
    setup:
    def tmp = File.createTempDir()
    tmp.deleteOnExit()
    def storage = new FileStorage(tmp)
    storage.write(new StorageKey("a"), "".bytes)
    storage.write(new StorageKey("b"), "".bytes)
    def c = new StorageKey("c");
    storage.write(new NestedKey(c, "d"), "".bytes)
    storage.write(new NestedKey(c, "e"), "".bytes)

    expect:
    storage.keys().size() == 4
  }

  def "throws exception on directory w/o access"() {
    setup:
    def tmp = new File("/directory/wo/permissions")

    when:
    def storage = new FileStorage(tmp)

    then:
    thrown LicenseToolException
  }

  def "throws exception when writing to directory w/o access"() {
    setup:
    def storage = new FailingTempFileStorage()

    when:
    storage.write(new StorageKey("abc"), new byte[0])

    then:
    thrown LicenseToolException
  }

  def "throws exception when writing a nested key to directory w/o access"() {
    setup:
    def storage = new FailingTempFileStorage()

    when:
    storage.write(new NestedKey(new StorageKey("abc"), "def"), new byte[0])

    then:
    thrown LicenseToolException
  }

  def "throws exception when reading a non existent key"() {
    setup:
    def storage = new TempFileStorage()

    when:
    storage.read(new StorageKey("abc"))

    then:
    thrown LicenseToolException
  }
}
