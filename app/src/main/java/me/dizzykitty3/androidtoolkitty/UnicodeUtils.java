package me.dizzykitty3.androidtoolkitty;

public class UnicodeUtils {
    private UnicodeUtils() {
        // Empty
    }

    public static String convertUnicodeToCharacter(String unicode) {
        var stringBuilder = new StringBuilder();
        for (int i = 0; i < unicode.length(); i += 4) {
            var hexValue = unicode.substring(i, i + 4);
            var decimalValue = Integer.parseInt(hexValue, 16);
            stringBuilder.append((char) decimalValue);
        }
        return stringBuilder.toString();
    }
}