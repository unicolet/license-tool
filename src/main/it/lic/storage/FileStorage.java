package it.lic.storage;

import it.lic.error.LicenseToolException;
import it.lic.key.Key;
import it.lic.key.NestedKey;
import it.lic.key.Separator;
import it.lic.key.StorageKey;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 * A file-based Storage impl.
 *
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class FileStorage implements Storage {
    File root;

    public FileStorage() throws LicenseToolException {
        this(
            new File(
                String.format(
                    "%s%s%s",
                    System.getProperty("user.home"),
                    System.getProperty("file.separator"),
                    ".lictool"
                )
            )
        );
    }

    public FileStorage(Key key) throws LicenseToolException {
        this(new File(key.fullpath()));
    }

    public FileStorage(File path) throws LicenseToolException {
        this.root = path;
        if(!this.root.exists()) {
            if(!this.root.mkdirs()) {
                throw new LicenseToolException(
                    String.format(
                        "Can't operate (mkdirs) on %s",
                        this.root.getPath()
                    )
                );
            }
        }
    }

    @Override
    public final byte[] read(final Key key) throws LicenseToolException {
        final File source = new File(this.root, key.fullpath());
        try {
            return FileUtils.readFileToByteArray(source);
        } catch (final IOException e) {
            throw new LicenseToolException(
                String.format("Can't read key %s", key.fullpath()),
                e
            );
        }
    }

    @Override
    public final boolean exists(final Key key) {
        return new File(this.root, key.fullpath()).exists();
    }

    @Override
    public final void write(final Key key, final byte[] data) throws LicenseToolException {
        final File destination = new File(this.root, key.fullpath());
        if(
            !destination.getParentFile().exists()
            && !destination.getParentFile().mkdirs()) {
            throw new LicenseToolException(
                String.format(
                    "Cannot create fullpath to store: %s",
                    destination.getAbsolutePath()
                )
            );
        }
        try (
            final FileOutputStream fos = new FileOutputStream(
                destination
            )
        ) {
            fos.write(data);
        } catch (final Exception fne) {
            throw new LicenseToolException(
                String.format("Can't write key %s", key.fullpath()),
                fne
            );
        }
    }

    @Override
    public List<Key> keys() {
        final Separator separator = new Separator.Default();
        final File[] files=this.root.listFiles();
        final List<Key> result=new ArrayList<>(files.length*2);
        for(File file: files) {
            final StorageKey current = new StorageKey(file.getName());
            if(file.isDirectory()) {
                for (File subfile : file.listFiles()) {
                    result.add(
                        new NestedKey(current, subfile.getName())
                    );
                }
            } else {
                result.add(current);
            }
        }
        return result;
    }
}
