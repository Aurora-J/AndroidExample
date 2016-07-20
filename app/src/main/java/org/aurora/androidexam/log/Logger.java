package org.aurora.androidexam.log;

import android.util.Log;

import java.util.Arrays;

/**
 * @author Aurora
 * @date 2016-07-07
 */
public class Logger {

    public static void info(String tag, String info){
        Log.d(tag, info);
    }

    public static void info(String tag, Object... info){
        for (Object object : info) {
            Log.d(tag, object.toString());
        }
    }

    public static void info(String info){
        Log.d("", info);
    }

    public static void info(Object... info){
        Log.d("", Arrays.toString(info));
    }
}
