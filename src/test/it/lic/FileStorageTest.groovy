package it.lic
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
      storage.exists(key) == exists

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
    storage.write(key, value.bytes)
    storage.read(key) == value.bytes
    storage.exists(key) == exists

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
    storage.write("a","".bytes)
    storage.write("b","".bytes)

    expect:
    storage.keys().size() == 2
  }

  def "can list keys from nested storage"() {
    setup:
    def tmp = File.createTempDir()
    tmp.deleteOnExit()
    def storage = new FileStorage(tmp)
    storage.write("a","".bytes)
    storage.write("b","".bytes)
    storage.write("c/d","".bytes)
    storage.write("c/e","".bytes)

    expect:
    storage.keys().size() == 4
  }
}
