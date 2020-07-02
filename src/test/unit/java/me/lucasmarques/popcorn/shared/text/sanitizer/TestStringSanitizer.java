package me.lucasmarques.popcorn.shared.text.sanitizer;

import org.junit.Assert;
import org.junit.Test;

public class TestStringSanitizer {

    @Test
    public void testCanRemoveSqlCommentsFromString() {
        String value = "INSERT INTO --";
        String result = StringSanitizer.sanitize(value);

        Assert.assertEquals(result, "INSERT INTO ");
    }

    @Test
    public void testCanReplaceSqlCommentsFromString() {
        String value = "INSERT INTO movies (name) VALUES ('Edgar's move')";
        String result = StringSanitizer.sanitize(value);

        Assert.assertEquals(result, "INSERT INTO movies (name) VALUES (\'Edgar\'s move\')");
    }

    @Test
    public void testCanReplaceAndRemoveSqlCommentsFromString() {
        String value = "INSERT INTO movies (name) VALUES ('Edgar's move') --";
        String result = StringSanitizer.sanitize(value);

        Assert.assertEquals(result, "INSERT INTO movies (name) VALUES (\'Edgar\'s move\') ");
    }

}
