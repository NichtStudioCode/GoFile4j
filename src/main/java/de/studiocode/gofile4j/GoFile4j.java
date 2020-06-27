package de.studiocode.gofile4j;

import de.studiocode.gofile4j.response.FileUploadResponse;
import de.studiocode.gofile4j.response.FindServerResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * A java wrapper for the GoFile.io API
 */
public class GoFile4j {

    private static final String FIND_SERVER_URL = "https://apiv2.gofile.io/getServer";
    private static final StringMessageFormat UPLOAD_URL_FORMAT = new StringMessageFormat("https://{0}.gofile.io/upload");
    private static final StringMessageFormat DOWNLOAD_LINK_FORMAT = new StringMessageFormat("https://gofile.io/d/{0}");
    private static final StringMessageFormat DIRECT_DOWNLOAD_LINK_FORMAT = new StringMessageFormat("https://{0}.gofile.io/download/{1}/{2}");

    private String email;
    private String description;
    private String password;
    private String tags;
    private String expire;

    private final ContentFile[] files;
    private final String fileServer;
    private final HttpPost postReq;


    public GoFile4j(ContentFile... files) throws IOException {
        this.files = files;
        fileServer = getFileServer();
        this.postReq = new HttpPost(UPLOAD_URL_FORMAT.format(fileServer));
    }

    /**
     * A quick method to upload files
     *
     * @param files The files to upload
     * @return The file upload result
     */
    public static FileUploadResult uploadFiles(File... files) throws IOException {
        return new GoFile4j(Arrays.stream(files).map(ContentFile::new).toArray(ContentFile[]::new)).upload();
    }

    /**
     * Get the best server available to receive uploads
     *
     * @return The name of the best server available to receive uploads
     */
    private String getFileServer() throws IOException {
        HttpGet getReq = new HttpGet(FIND_SERVER_URL);
        InputStream in = HttpClients.createDefault().execute(getReq).getEntity().getContent();
        return new FindServerResponse(in).getServer();
    }

    /**
     * Upload the provided files. The program will wait until all files are uploaded.
     *
     * @return The file upload result
     */
    public FileUploadResult upload() throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        for (ContentFile file : files) {
            File f = file.getFile();
            builder.addBinaryBody("filesUploaded", f, file.getContentType(), f.getName());
        }
        if (email != null) builder.addTextBody("email", email);
        if (description != null) builder.addTextBody("description", description);
        if (password != null) builder.addTextBody("password", password);
        if (tags != null) builder.addTextBody("tags", tags);
        if (expire != null) builder.addTextBody("expire", expire);
        HttpEntity multipart = builder.build();
        postReq.setEntity(multipart);

        InputStream content = HttpClients.createDefault().execute(postReq).getEntity().getContent();
        FileUploadResponse response = new FileUploadResponse(content);
        return new FileUploadResult(response);
    }

    /**
     * Set the email of the upload.
     *
     * @param email The email of the account the upload should be associated with (= manage uploads)
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the description of the upload.
     *
     * @param description The description of the upload.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the password of the upload. The direct download link will not be affected by this.
     *
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the tags of the upload.
     *
     * @param tags The tags of the upload. If multiple tags, separate them with comma (example : tag1,tag2)
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * Set the expiration date.
     *
     * @param expire The expiration date of the upload in the form of timestamp.
     */
    public void setExpire(long expire) {
        this.expire = String.valueOf(expire);
    }

    public class FileUploadResult {

        private final String code;
        private final String removalCode;
        private String downloadLink;
        private String[] directDownloadLinks;

        public FileUploadResult(FileUploadResponse response) {
            this.code = response.getCode();
            this.removalCode = response.getRemovalCode();

            createDownloadLinks();
        }

        private void createDownloadLinks() {
            downloadLink = DOWNLOAD_LINK_FORMAT.format(code);

            directDownloadLinks = Arrays.stream(files)
                    .map(ContentFile::getFile)
                    .map(file -> DIRECT_DOWNLOAD_LINK_FORMAT.format(fileServer, code, file.getName()))
                    .toArray(String[]::new);
        }

        /**
         * Get the upload code.
         *
         * @return The code used in the upload link
         */
        public String getCode() {
            return code;
        }

        /**
         * Get the removal code.
         *
         * @return The code used to remove the upload
         */
        public String getRemovalCode() {
            return removalCode;
        }

        /**
         * Get the download link.
         *
         * @return The link to the download page
         */
        public String getDownloadLink() {
            return downloadLink;
        }

        /**
         * Get the direct download links to all files. These direct links will not work if the main download page hasn't been visited before.
         *
         * @return The direct download links to all files
         */
        public String[] getDirectDownloadLinks() {
            return directDownloadLinks;
        }
    }

}
