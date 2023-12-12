package com.example.sfspertanian;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "MyAppPreferences";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_SAWAH_ID = "sawah_id";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUserId(String userId) {
        editor.putString(KEY_USER_ID, userId);
        editor.apply();
    }
    public void setSawahId(String sawahId) {
        editor.putString(KEY_SAWAH_ID, sawahId);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }
    public String getSawahId() {
        return sharedPreferences.getString(KEY_SAWAH_ID, null);
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
