package de.studiocode.gofile4j.request;

import de.studiocode.gofile4j.utils.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MultipartFormDataRequest {

    private final String boundary = StringUtils.randomString(25, StringUtils.ALPHABET);
    private final Charset charset;
    private final HttpURLConnection connection;
    private final OutputStream out;
    private final PrintWriter writer;

    public MultipartFormDataRequest(String requestUrl, Charset charset) throws IOException {
        this.charset = charset;
        connection = (HttpURLConnection) new URL(requestUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        out = connection.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(out, charset));
    }

    public void addHeaderField(String name, String value) {
        writer.println(name + ": " + value);
        writer.flush();
    }

    public void addFormField(String name, String value) {
        writer.println("--" + boundary);
        writer.println("Content-Disposition: form-data; name=" + name);
        writer.println("Content-Type: text/plain; charset=" + charset.toString());
        writer.println();
        writer.println(value);
        writer.flush();
    }

    public void addFormFile(String fieldName, File file, String fileName, String contentType) throws IOException {
        writer.println("--" + boundary);
        writer.println("Content-Disposition: form-data; name=" + fieldName + "; filename=" + fileName);
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Transfer-Encoding: binary");
        writer.println();
        writer.flush();

        InputStream in = new FileInputStream(file);
        copy(in, out);
        out.flush();
        in.close();

        writer.println();
        writer.flush();
    }

    public InputStream complete() throws IOException {
        writer.flush();
        writer.println("--" + boundary + "--");
        writer.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return connection.getInputStream();
        } else throw new IOException("Server responded with non-OK status: " + responseCode);
    }

    public List<String> completeToStringList() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(complete(), charset));

        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[8192];
        int length;
        while ((length = in.read(buffer)) != -1) {
            out.write(buffer, 0, length);
        }
    }

}
