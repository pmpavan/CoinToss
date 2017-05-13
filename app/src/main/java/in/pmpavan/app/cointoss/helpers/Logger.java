package in.pmpavan.app.cointoss.helpers;

import android.util.Log;

import in.pmpavan.app.cointoss.BuildConfig;


/**
 * Created by Pavan on 07/05/17.
 */

public class Logger {

    public static boolean loggerSwitch = false;
    private static String LOGGER_TAG = "LOGGER";

    public Logger() {
    }

    /**
     * @param status
     */
    public static void setSwitch(boolean status) {
        loggerSwitch = status;
    }

    /**
     * @param msg Log as Debug
     */
    public static void msg(String msg) {
        if (loggerSwitch) {
            Log.d(LOGGER_TAG, msg);
        }
    }


    /**
     * @param error Log as Error
     */
    public static void error(String error) {
        if (loggerSwitch) {
            Log.e(LOGGER_TAG, error);
        }
    }

    /**
     * @param msg Log as Debug only when app is debug
     */
    public static void msg(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * @param error Log as Error only when app is debug
     */
    public static void error(String tag, String error) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, error);
        }
    }
}
