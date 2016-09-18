package it.lic

import it.lic.key.Key

/**
 * Test suite for Key classes.
 */
public class KeyTest extends spock.lang.Specification {

  def "a root key has an empty path"() {
    setup:
    def root = new Key.Root();

    expect:
    root.path() == ""
    root.fullpath() == ""
    root.parentKey() == root
    !root.nested()
  }
}
