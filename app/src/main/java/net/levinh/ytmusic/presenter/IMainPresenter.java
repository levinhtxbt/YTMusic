package net.levinh.ytmusic.presenter;

import android.content.Intent;
import android.view.View;

/**
 * Created by virus on 19/10/2016.
 */

public interface IMainPresenter {

    void checkGooglePlayServicesAvailable();

    void onClick(View v);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
