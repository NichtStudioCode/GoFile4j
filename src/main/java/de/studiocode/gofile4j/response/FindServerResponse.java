package de.studiocode.gofile4j.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FindServerResponse {

    private String status;
    private String server;
    
    public FindServerResponse(JsonElement element) {
        parseData(element);
    }
    
    public FindServerResponse(Reader reader) {
        this(JsonParser.parseReader(reader));
    }
    
    public FindServerResponse(InputStream in) {
        this(new InputStreamReader(in));
    }
    
    private void parseData(JsonElement element) {
        JsonObject jsonObject = element.getAsJsonObject();
        status = jsonObject.get("status").getAsString();
        server = jsonObject.get("data").getAsJsonObject().get("server").getAsString();
    }

    public String getStatus() {
        return status;
    }

    public String getServer() {
        return server;
    }
}
