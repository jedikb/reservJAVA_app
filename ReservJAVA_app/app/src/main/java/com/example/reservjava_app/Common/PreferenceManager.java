package com.example.reservjava_app.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

  public static final String PREFERENCES_NAME = "settings";
  private static final String DEFAULT_VALUE_STRING = "";
  private static final boolean DEFAULT_VALUE_BOOLEAN = false;
  private static final int DEFAULT_VALUE_INT = -1;
  private static final long DEFAULT_VALUE_LONG = -1L;
  private static final float DEFAULT_VALUE_FLOAT = -1F;

  //자동로그인을 위해
  //settings라는 파일로 사용자 정보를 저장할 것이다.
  //SharedPreferences pref = mContext.getSharedPreferences("settings", MODE_PRIVATE);
  //SharedPreferences.Editor editor = pref.edit();

  private static SharedPreferences getPreferences(Context context) {
    return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
  }

  // -------- 기본 data 저장 -------------
  public static void setString(Context context, String key, String value) {
    SharedPreferences prefs = getPreferences(context);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putString(key, value);
    editor.commit();
  }

  public static void setBoolean(Context context, String key, boolean value) {
    SharedPreferences prefs = getPreferences(context);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putBoolean(key, value);
    editor.commit();
  }

  public static void setInt(Context context, String key, int value) {
    SharedPreferences prefs = getPreferences(context);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putInt(key, value);
    editor.commit();
  }

  public static void setLong(Context context, String key, long value) {
    SharedPreferences prefs = getPreferences(context);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putLong(key, value);
    editor.commit();
  }

  public static void setFloat(Context context, String key, float value) {
    SharedPreferences prefs = getPreferences(context);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putFloat(key, value);
    editor.commit();
  }
  // --------------------------------------
  // ---------- 기본 data 불러오기 -------------
  public static String getString(Context context, String key) {
    SharedPreferences prefs = getPreferences(context);
    String value = prefs.getString(key, DEFAULT_VALUE_STRING);
    return value;
  }

  public static boolean getBoolean(Context context, String key) {
    SharedPreferences prefs = getPreferences(context);
    boolean value = prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN);
    return value;
  }

  public static int getInt(Context context, String key) {
    SharedPreferences prefs = getPreferences(context);
    int value = prefs.getInt(key, DEFAULT_VALUE_INT);
    return value;
  }

  public static long getLong(Context context, String key) {
    SharedPreferences prefs = getPreferences(context);
    long value = prefs.getLong(key, DEFAULT_VALUE_LONG);
    return value;
  }

  public static float getFloat(Context context, String key) {
    SharedPreferences prefs = getPreferences(context);
    float value = prefs.getFloat(key, DEFAULT_VALUE_FLOAT);
    return value;
  }

  public static void removeKey(Context context, String key) {
    SharedPreferences prefs = getPreferences(context);
    SharedPreferences.Editor edit = prefs.edit();
    edit.remove(key);
    edit.commit();
  }

  public static void clear(Context context) {
    SharedPreferences prefs = getPreferences(context);
    SharedPreferences.Editor edit = prefs.edit();
    edit.clear();
    edit.commit();
  }
  // -----------------------------------------
}
