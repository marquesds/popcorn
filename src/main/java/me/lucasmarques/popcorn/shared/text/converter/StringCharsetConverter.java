package me.lucasmarques.popcorn.shared.text.converter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringCharsetConverter {

    public static String convert(String value, Charset fromCharset, Charset toCharset) {
        return new String(value.getBytes(fromCharset), toCharset);
    }

}
