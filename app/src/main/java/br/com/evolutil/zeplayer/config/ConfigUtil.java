package br.com.evolutil.zeplayer.config;

import android.content.Context;
import android.content.SharedPreferences;

/** Utilidades para configurar o APP
 * Created by Jez on 13/11/2015.
 */
public class ConfigUtil {
    public static final String PREF_ID = "br.com.evolutil.zeplayer";

    public static void setBoolean(Context c, String s, boolean b) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(s, b);
        editor.commit();
    }

    public static boolean getBoolean(Context c, String s) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREF_ID, 0);
        boolean b = sharedPreferences.getBoolean(s, true);
        return  b;
    }

    public static void setFloat(Context c, String s, float f) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(s, f);
        editor.commit();
    }

    public static float getFloat(Context c, String s) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREF_ID, 0);
        float f = sharedPreferences.getFloat(s, 0f);
        return  f;
    }

    public static void setString(Context c, String s, String str) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(s, str);
        editor.commit();
    }

    public static String getString(Context c, String s) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREF_ID, 0);
        String str = sharedPreferences.getString(s, "");
        return  str;
    }

    public static void setInteger(Context c, String s, int i) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(s, i);
        editor.commit();
    }

    public static int getInteger(Context c, String s) {
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREF_ID, 0);
        int i = sharedPreferences.getInt(s, 0);
        return  i;
    }
}
