package io.arsh.pweather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by arshdeep on 5 October 2015
 *
 *
 * This class is used for saving the user's location.
 */
public class TheLocUtil {
    private static SharedPreferences myPreferences;
    private static String myVill;
    private static String myDist;



    public static void setHomeVillDist(String theVillage, String theDistrict) {
        myVill = theVillage;
        myDist = theDistrict;
        changeToVillage();
    }


    public static void changeToVillage() {
        myPreferences.edit().putString("Village", myVill);
        myPreferences.edit().putString("District", myDist);
    }


    public static void loadVillage(Activity theActivity) {
        initializeVillage(theActivity);
        if (myPreferences.contains("Village") && myPreferences.contains("District")) {
            myVill = myPreferences.getString("Village", "ਲੁਧਿਆਣਾ ਸ਼ਹਿਰ");
            myDist = myPreferences.getString("District", "ਲੁਧਿਆਣਾ");
        }
    }

    private static void initializeVillage(Activity theActivity) {
        if (myPreferences == null) {
            myPreferences = PreferenceManager.getDefaultSharedPreferences(theActivity.getApplicationContext());
        }
    }

    /**
     * Checks myVill variable and myDist variable for what the village is
     * and then finds it's latitude position on the globe.
     *
     * NOTE: FOR NOW, ONLY THE MAIN VILLAGE IN EACH DISTRICT IS BEING RETURNED.
     * @return latitude of village.
     */
    public static double getLatitude() {
        if (myDist.equals("ਲੁਧਿਆਣਾ")) {
            return 30.91;
        } else if (myDist.equals("ਅੰਮ੍ਰਿਤਸਰ")) {
            return 31.64;
        } else if (myDist.equals("ਗੁਰਦਾਸਪੁਰ")) {
            return 32.0333;
        } else if (myDist.equals("ਜਲੰਧਰ")) {
            return 31.326;
        } else if (myDist.equals("ਚੰਡੀਗੜ੍ਹ")) {
            return 30.75;
        } else if (myDist.equals("ਫ਼ਿਰੋਜ਼ਪੁਰ")) {
            return 30.9166;
        } else if (myDist.equals("ਪਟਿਆਲਾ")) {
            return 30.34;
        } else if (myDist.equals("ਸੰਗਰੂਰ")) {
            return 30.2506;
        } else if (myDist.equals("ਹੁਸ਼ਿਆਰਪੁਰ")) {
            return 31.53;
        } else if (myDist.equals("ਬਠਿੰਡਾ")) {
            return 30.23;
        } else if (myDist.equals("ਤਰਨ ਤਾਰਨ")) {
            return 31.4491;
        } else if (myDist.equals("ਮੋਗਾ")) {
            return 30.8;
        } else if (myDist.equals("ਸਾਹਿਬਜ਼ਾਦਾ ਅਜੀਤ ਸਿੰਘ ਨਗਰ")) {
            return 30.78;
        } else if (myDist.equals("ਮੁਕਤਸਰ")) {
            return 30.29;
        } else if (myDist.equals("ਕਪੂਰਥਲਾ")) {
            return 31.38;
        } else if (myDist.equals("ਮਾਨਸਾ")) {
            return 29.98;
        } else if (myDist.equals("ਬਰਨਾਲਾ")) {
            return 30.38;
        } else if (myDist.equals("ਫਰੀਦਕੋਟ")) {
            return 30.40;
        } else if (myDist.equals("ਫਤਿਹਗੜ੍ਹ ਸਾਹਿਬ")) {
            return 30.22;
        } else if (myDist.equals("ਫਾਜ਼ਿਲਕਾ")) {
            return 30.403;
        } else if (myDist.equals("ਪਠਾਨਕੋਟ")) {
            return 32.266814;
        } else if (myDist.equals("ਰੋਪੜ")) {
            return 30.9664;
        } else if (myDist.equals("ਸ਼ਹੀਦ ਭਗਤ ਸਿੰਘ ਨਗਰ")) {
            return 31.1253; //this is Nawanshahr
        } else {
            return 0;
        }
    }


    /**
     * This returns the longitude of a village.
     *
     * @return longitude
     */
    public static double getLongitude() {
        if (myDist.equals("ਲੁਧਿਆਣਾ")) {
            return 75.85;
        } else if (myDist.equals("ਅੰਮ੍ਰਿਤਸਰ")) {
            return 74.86;
        } else if (myDist.equals("ਗੁਰਦਾਸਪੁਰ")) {
            return 75.40;
        } else if (myDist.equals("ਜਲੰਧਰ")) {
            return 75.576;
        } else if (myDist.equals("ਚੰਡੀਗੜ੍ਹ")) {
            return 76.78;
        } else if (myDist.equals("ਫ਼ਿਰੋਜ਼ਪੁਰ")) {
            return 74.6;
        } else if (myDist.equals("ਪਟਿਆਲਾ")) {
            return 76.38;
        } else if (myDist.equals("ਸੰਗਰੂਰ")) {
            return 75.8442;
        } else if (myDist.equals("ਹੁਸ਼ਿਆਰਪੁਰ")) {
            return 75.92;
        } else if (myDist.equals("ਬਠਿੰਡਾ")) {
            return 74.9519;
        } else if (myDist.equals("ਤਰਨ ਤਾਰਨ")) {
            return 74.9205;
        } else if (myDist.equals("ਮੋਗਾ")) {
            return 75.17;
        } else if (myDist.equals("ਸਾਹਿਬਜ਼ਾਦਾ ਅਜੀਤ ਸਿੰਘ ਨਗਰ")) {
            return 76.69;
        } else if (myDist.equals("ਮੁਕਤਸਰ")) {
            return 74.31;
        } else if (myDist.equals("ਕਪੂਰਥਲਾ")) {
            return 75.38;
        } else if (myDist.equals("ਮਾਨਸਾ")) {
            return 75.38;
        } else if (myDist.equals("ਬਰਨਾਲਾ")) {
            return 75.55;
        } else if (myDist.equals("ਫਰੀਦਕੋਟ")) {
            return 74.45;
        } else if (myDist.equals("ਫਤਿਹਗੜ੍ਹ ਸਾਹਿਬ")) {
            return 76.14;
        } else if (myDist.equals("ਫਾਜ਼ਿਲਕਾ")) {
            return 74.025;
        } else if (myDist.equals("ਪਠਾਨਕੋਟ")) {
            return 75.6;
        } else if (myDist.equals("ਰੋਪੜ")) {
            return 76.5331;
        } else if (myDist.equals("ਸ਼ਹੀਦ ਭਗਤ ਸਿੰਘ ਨਗਰ")) {
            return 76.1164; //this is Nawanshahr
        } else {
            return 0;
        }
    }


}
