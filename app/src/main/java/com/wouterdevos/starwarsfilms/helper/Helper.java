package com.wouterdevos.starwarsfilms.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    public static long getIdFromUrl(String url) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(url);
        matcher.find();
        String stringId = matcher.group();
        return Long.valueOf(stringId);
    }
}
