package com.wouterdevos.starwarsfilms.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static long getIdFromUrl(String url) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(url);
        matcher.find();
        String stringId = matcher.group();
        return Long.valueOf(stringId);
    }
}
