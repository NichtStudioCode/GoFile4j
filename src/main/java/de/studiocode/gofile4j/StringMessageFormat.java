package de.studiocode.gofile4j;

import java.text.MessageFormat;

public class StringMessageFormat extends MessageFormat {
    
    public StringMessageFormat(String pattern) {
        super(pattern);
    }
    
    public String format(String string) {
        return super.format(new String[] {string});
    }
    
    public String format(String... strings) {
        return super.format(strings);
    }
    
}
