package it.lic.keypair;

import java.io.File;
import java.io.FileOutputStream;
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

    public FileStorage() throws Exception {
        this(
            String.format(
                "%s%s%s",
                System.getProperty("user.home"),
                System.getProperty("file.separator"),
                ".lictool"
            )
        );
    }

    public FileStorage(String path) throws Exception {
        this(new File(path));
    }

    public FileStorage(File path) throws Exception {
        this.root = path;
        if(!this.root.exists()) {
            if(!this.root.mkdirs()) {
                throw new Exception(
                    String.format(
                        "Can't operate (mkdirs) on %s",
                        this.root.getPath()
                    )
                );
            }
        }
    }

    @Override
    public final byte[] read(final String key) throws Exception {
        return FileUtils.readFileToByteArray(new File(this.root, key));
    }

    @Override
    public final boolean exists(final String key) {
        return new File(this.root, key).exists();
    }

    @Override
    public final void write(final String key, final byte[] data) throws Exception {
        try (
            final FileOutputStream fos = new FileOutputStream(
                new File(this.root, key)
            )
        ) {
            fos.write(data);
        }
    }
}
