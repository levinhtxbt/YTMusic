package net.levinh.ytmusic;

import android.app.Application;
import android.content.Context;

/**
 * Created by virus on 04/10/2016.
 */

public class YTMusicApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
