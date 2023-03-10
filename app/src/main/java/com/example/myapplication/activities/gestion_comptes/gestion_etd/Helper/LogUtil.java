package com.example.myapplication.activities.gestion_comptes.gestion_etd.Helper;

import android.util.Log;

public class LogUtil {
    public static void LogD(String Tag, String Message) {
        Log.d(Tag, Message);
    }

    public static void LogE(String Tag, String Message) {
        Log.e(Tag, Message);
    }

    public static void LogI(String Message) {
        Log.i("viewlog", Message);
    }

    public static void LogW(String Tag, String Message) {
        Log.w(Tag, Message);
    }

    public static void log(String Message) {
        Log.i("D384", Message);
    }
}
