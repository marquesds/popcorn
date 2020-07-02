package me.lucasmarques.popcorn.shared.text.sanitizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSanitizer {

    public static String sanitize(String value) {
        Pattern tokenToRemovePattern = Pattern.compile("(--)+");
        Pattern tokenToReplacePattern = Pattern.compile("[']+");

        Matcher tokenToRemoveMatcher = tokenToRemovePattern.matcher(value);
        Matcher tokenToReplaceMatcher = tokenToReplacePattern.matcher(value);

        while (tokenToRemoveMatcher.find()) {
            value = value.replaceAll(tokenToRemoveMatcher.group(), "");
        }

        while (tokenToReplaceMatcher.find()) {
            value = value.replaceAll(tokenToReplaceMatcher.group(), "\'");
        }

        return value;
    }

}
