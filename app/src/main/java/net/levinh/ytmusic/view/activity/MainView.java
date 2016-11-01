package net.levinh.ytmusic.view.activity;

import android.content.Intent;

/**
 * Created by virus on 19/10/2016.
 */

public interface MainView {

    void showGooglePlayServicesAvailabilityErrorDialog(int connectionStatusCode);

    void onLoginSuccess();

    void onStartActivityForResult(Intent intent, int requestCode);

}
