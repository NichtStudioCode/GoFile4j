package de.studiocode.gofile4j;

import org.apache.http.entity.ContentType;

import java.io.File;

/**
 * A class for a file and the corresponding content type (example: image.png would be ContentType.IMAGE_PNG)
 * The right content type is not necessary for the upload to work. However, images for example won't have a preview on the download page if they don't have the right content type.
 */
public class ContentFile {
    
    private ContentType contentType;
    private File file;

    /**
     * Create an instance of ContentFile without specifying the content type. Default value: DEFAULT_BINARY (APPLICATION_OCTET_STREAM)
     * @param file The file
     */
    public ContentFile(File file) { 
        this.file = file;
        this.contentType = ContentType.DEFAULT_BINARY;
    }

    /**
     * Create an instance of ContentFile
     * @param file The file
     * @param contentType The content type
     */
    public ContentFile(File file, ContentType contentType) {
        this(file);
        this.contentType = contentType;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public File getFile() {
        return file;
    }
}
