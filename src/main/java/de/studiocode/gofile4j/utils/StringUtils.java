package de.studiocode.gofile4j.utils;

import java.util.Random;

public class StringUtils {

    public static final String ALPHABET = "abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();

    public static String randomString(int length, String letters) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i != length; i++) {
            builder.append(letters.charAt(RANDOM.nextInt(letters.length())));
        }
        return builder.toString();
    }

}
