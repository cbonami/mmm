/* $Id: SimpleFileAccess.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.base.feature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.mmm.ui.toolkit.api.feature.FileAccessIF;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.feature.FileAccessIF} interface. It gives
 * read-access to a given file in the filesystem.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleFileAccess implements FileAccessIF {

    /** the actuall file */
    private final File file;

    /**
     * The constructor.
     * 
     * @param dataFile
     *        is the file to access.
     */
    public SimpleFileAccess(File dataFile) {

        super();
        this.file = dataFile;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.feature.FileAccessIF#getFile()
     * {@inheritDoc}
     */
    public InputStream getFile() throws IOException {

        return new FileInputStream(this.file);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.feature.FileAccessIF#getFilename()
     * {@inheritDoc}
     */
    public String getFilename() {

        return this.file.getName();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.feature.FileAccessIF#getSize()
     * {@inheritDoc}
     */
    public long getSize() {

        return this.file.length();
    }

}
