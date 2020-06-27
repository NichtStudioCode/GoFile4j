package de.studiocode.gofile4j.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileUploadResponse {

    private String status;
    private String code;
    private String removalCode;

    public FileUploadResponse(JsonElement element) {
        parseData(element);
    }

    public FileUploadResponse(Reader reader) {
        this(JsonParser.parseReader(reader));
    }

    public FileUploadResponse(InputStream in) {
        this(new InputStreamReader(in));
    }

    private void parseData(JsonElement element) {
        JsonObject jsonObject = element.getAsJsonObject();
        status = jsonObject.get("status").getAsString();
        JsonObject data = jsonObject.get("data").getAsJsonObject();
        code = data.get("code").getAsString();
        removalCode = data.get("removalCode").getAsString();
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getRemovalCode() {
        return removalCode;
    }
}
