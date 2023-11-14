package com.game.utils;

import com.google.gson.Gson;

public class CommonUtils {

    private CommonUtils() {
    }

    private static final Gson gson = new Gson();

    public static String getJson(Object obj) {
        return gson.toJson(obj);
    }
}