package net.levinh.ytmusic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import net.levinh.ytmusic.YTMusicApplication;

/**
 * Created by virus on 19/10/2016.
 */

public class SharedPreferencesUtils {
    private static final String PREF_ACCOUNT_NAME = "DataStore";
    private static final String PREF_ACCOUNT_EMAIL = "email";

    private Context mContext;
    private static SharedPreferencesUtils mInstance;
    private SharedPreferences mShared;

    private SharedPreferencesUtils() {
        mContext = YTMusicApplication.mContext;
        mShared = mContext.getSharedPreferences(PREF_ACCOUNT_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtils getInstance() {
        if (mInstance == null)
            mInstance = new SharedPreferencesUtils();
        return mInstance;
    }

    public String getAccountEmail() {
        return mShared.getString(PREF_ACCOUNT_EMAIL, null);
    }

    public boolean setAccountEmail(String email) {
        try {
            SharedPreferences.Editor editor = mShared.edit();
            editor.putString(PREF_ACCOUNT_EMAIL, email);
            editor.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
