package it.lic

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
      "idontexist"  | false
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
    key  | value | exists
    "k"  | "val" | true
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
    storage.write(new StorageKey("a"),"".bytes)
    storage.write(new StorageKey("b"),"".bytes)

    expect:
    storage.keys().size() == 2
  }

  def "can list keys from nested storage"() {
    setup:
    def tmp = File.createTempDir()
    tmp.deleteOnExit()
    def storage = new FileStorage(tmp)
    storage.write(new StorageKey("a"),"".bytes)
    storage.write(new StorageKey("b"),"".bytes)
    def c = new StorageKey("c");
    storage.write(new NestedKey(c, "d"),"".bytes)
    storage.write(new NestedKey(c, "e"),"".bytes)

    expect:
    storage.keys().size() == 4
  }
}
