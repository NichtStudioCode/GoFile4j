package de.studiocode.gofile4j;

import java.io.File;
import java.net.URLConnection;

/**
 * A class for a file and the corresponding content type
 * The right content type is not necessary for the upload to work. However, images for example won't have a preview on the download page if they don't have the right content type.
 */
public class ContentFile {

    private final File file;
    private final String contentType;

    /**
     * Create an instance of ContentFile without specifying the content type. The content type will be guessed using URLConnection.guessContentTypeFromName
     *
     * @param file The file
     */
    public ContentFile(File file) {
        this.file = file;
        this.contentType = URLConnection.guessContentTypeFromName(file.getName());
    }

    /**
     * Create an instance of ContentFile
     *
     * @param file        The file
     * @param contentType The content type
     */
    public ContentFile(File file, String contentType) {
        this.file = file;
        this.contentType = contentType;
    }

    public File getFile() {
        return file;
    }

    public String getContentType() {
        return contentType;
    }
}
