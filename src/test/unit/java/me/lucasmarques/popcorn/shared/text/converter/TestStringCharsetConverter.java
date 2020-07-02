package me.lucasmarques.popcorn.shared.text.converter;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class TestStringCharsetConverter {

    @Test
    public void testCanConvertLatinCharsetToUtf8Charset() {
        String expected = "Beyoncé Knowles-Carter";
        String latinString = new String(expected.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        String result = StringCharsetConverter.convert(latinString, StandardCharsets.ISO_8859_1, StandardCharsets.UTF_8);

        Assert.assertEquals(result, expected);
    }

}
