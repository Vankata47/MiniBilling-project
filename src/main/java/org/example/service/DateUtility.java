package org.example.service;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtility {
    public static String getBulgarianMonth() {
        return LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("bg"));
    }
}
