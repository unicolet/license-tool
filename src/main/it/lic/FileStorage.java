package it.lic;

import java.io.File;
import java.io.FileOutputStream;
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

    public FileStorage() throws Exception {
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

    public FileStorage(Key key) throws Exception {
        this(new File(key.path()));
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
    public final byte[] read(final Key key) throws Exception {
        final File source = new File(this.root, key.path());
        return FileUtils.readFileToByteArray(source);
    }

    @Override
    public final boolean exists(final Key key) {
        return new File(this.root, key.path()).exists();
    }

    @Override
    public final void write(final Key key, final byte[] data) throws Exception {
        final File destination = new File(this.root, key.path());
        if(
            !destination.getParentFile().exists()
            && !destination.getParentFile().mkdirs()) {
            throw new Exception(
                String.format(
                    "Cannot create path to store: %s",
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
