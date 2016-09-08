package it.lic

import it.lic.keypair.FileStorage
/**
 * Test suite for FileStorage functionality.
 */
public class FileStorageTest extends spock.lang.Specification {
    def "can work from a tmp directory"() {
      expect:
      def tmp = File.createTempDir()
      tmp.deleteOnExit()
      def storage = new FileStorage(tmp)
      storage.exists(key) == exists

      where:
      key          | exists
      "idontexist"  | false
    }

  def "can store and retrieve key"() {
    expect:
    def tmp = File.createTempDir()
    tmp.deleteOnExit()
    def storage = new FileStorage(tmp)
    storage.write(key, value.bytes)
    storage.read(key) == value.bytes
    storage.exists(key) == exists

    where:
    key  | value | exists
    "k"  | "val" | true
  }
}
