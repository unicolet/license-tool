package it.lic;

import it.lic.error.LicenseToolException;
import it.lic.key.Key;
import it.lic.storage.FileStorage;
import it.lic.storage.Storage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;

/**
 * A file storage implementation that will fail to work because of
 * missing permissions.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class FailingTempFileStorage implements Storage {
    final Storage storage;

    public FailingTempFileStorage() throws Exception {
        final File tmp = Files.createTempDirectory(
            "test",
            PosixFilePermissions.asFileAttribute(
                PosixFilePermissions.fromString("r-xr-x---")
            )
        ).toFile();
        tmp.deleteOnExit();
        this.storage = new FileStorage(tmp);
    }

    @Override
    public byte[] read(final Key key) throws LicenseToolException {
        return this.storage.read(key);
    }

    @Override
    public boolean exists(final Key key) {
        return this.storage.exists(key);
    }

    @Override
    public void write(final Key key, final byte[] data) throws LicenseToolException {
        this.storage.write(key, data);
    }

    @Override
    public List<Key> keys() {
        return this.storage.keys();
    }
}
