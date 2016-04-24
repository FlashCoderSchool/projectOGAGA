package com.ogaga.flash.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kanet on 4/18/2016.
 */
public class AuthorHelper {
    public static void writeString(Context context, final String KEY, String property) {
        SharedPreferences.Editor editor = context.getSharedPreferences("authorFirebase", context.MODE_PRIVATE).edit();
        editor.putString(KEY, property);
        editor.commit();
    }

    public static String readString(Context context, final String KEY) {
        return context.getSharedPreferences("authorFirebase", context.MODE_PRIVATE).getString(KEY, null);
    }

    public static void clearUser(Context context) {
        context.getSharedPreferences("authorFirebase",context.MODE_PRIVATE).edit().clear();
    }
}
