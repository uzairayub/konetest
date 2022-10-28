package com.tsl.elevator.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tsl.elevator.App;

public class SPUtils implements SPConstants {

    private static SPUtils instance = null;
    private final String STRING_PREFERENCES = "StringPreferences";
    private final String BOOLEAN_PREFERENCES = "BooleanPreferences";
    private final String INTEGER_PREFERENCES = "IntegerPreferences";

    private SPUtils() {
//        this.mContext = context;
    }

    public static SPUtils getInstance() {
        if (instance == null) {
            instance = new SPUtils();
        }
        return instance;
    }

    public void saveStringPreferences(String key, String value) {
        SharedPreferences sharedpreferences = App.Companion.getInstance().getApplicationContext().getSharedPreferences(STRING_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveIntegerPreferences(String key, int value) {
        SharedPreferences sharedpreferences = App.Companion.getInstance().getApplicationContext().getSharedPreferences(INTEGER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void saveBooleanPreferences(String key, boolean value) {
        SharedPreferences sharedpreferences = App.Companion.getInstance().getApplicationContext().getSharedPreferences(BOOLEAN_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getStringPreferences(String key) {
        SharedPreferences sharedpreferences = App.Companion.getInstance().getApplicationContext().getSharedPreferences(STRING_PREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "");
    }

    public boolean getBooleanPreferences(String key) {
        SharedPreferences sharedpreferences = App.Companion.getInstance().getApplicationContext().getSharedPreferences(BOOLEAN_PREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(key, false);
    }

    public int getIntegerPreferences(String key) {
        SharedPreferences sharedpreferences = App.Companion.getInstance().getApplicationContext().getSharedPreferences(INTEGER_PREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences.getInt(key, 0);
    }

    public void clearAllSharedPreferences() {
        SharedPreferences stringSharedPreferences = App.Companion.getInstance().getApplicationContext().getSharedPreferences(STRING_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences integerSharedPreferences = App.Companion.getInstance().getApplicationContext().getSharedPreferences(INTEGER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences booleanSharedPreferences = App.Companion.getInstance().getApplicationContext().getSharedPreferences(BOOLEAN_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = stringSharedPreferences.edit();
        editor.clear().apply();
        SharedPreferences.Editor editor1 = integerSharedPreferences.edit();
        editor1.clear().apply();
        SharedPreferences.Editor editor2 = booleanSharedPreferences.edit();
        editor2.clear().apply();
    }

}