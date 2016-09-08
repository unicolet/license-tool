package it.lic.keypair;

import java.io.File;

/**
 * TODO: Add Javadoc.
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
    public byte[] read(final String key) {
        return new byte[0];
    }

    @Override
    public boolean exists(final String key) {
        return false;
    }

    @Override
    public void write(final String key, final byte[] data) {

    }
}
