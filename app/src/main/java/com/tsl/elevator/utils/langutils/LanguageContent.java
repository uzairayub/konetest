package com.tsl.elevator.utils.langutils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LanguageContent {

    private String lanType;
    private String value;

    public static LanguageContent objectFromData(String str) {
        return new Gson().fromJson(str, LanguageContent.class);
    }

    public static List<LanguageContent> arrayLanguageContentFromData(String str) {
        Type listType = new TypeToken<ArrayList<LanguageContent>>() {
        }.getType();
        return new Gson().fromJson(str, listType);
    }

    public String getLanType() {
        return lanType;
    }

    public String getValue() {
        return value;
    }
}
