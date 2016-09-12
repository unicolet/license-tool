package it.lic;

import it.lic.key.Key;
import it.lic.storage.FileStorage;
import it.lic.storage.Storage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;

/**
 * TODO: Add Javadoc.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class TempFileStorage implements Storage {
    final Storage storage;

    public TempFileStorage() throws Exception {
        final File tmp = Files.createTempDirectory(
            "test",
            PosixFilePermissions.asFileAttribute(
                PosixFilePermissions.fromString("rwxrwx---")
            )
        ).toFile();
        tmp.deleteOnExit();
        this.storage = new FileStorage(tmp);
    }

    @Override
    public byte[] read(final Key key) throws Exception {
        return this.storage.read(key);
    }

    @Override
    public boolean exists(final Key key) {
        return this.storage.exists(key);
    }

    @Override
    public void write(final Key key, final byte[] data) throws Exception {
        this.storage.write(key, data);
    }

    @Override
    public List<Key> keys() {
        return this.storage.keys();
    }
}
