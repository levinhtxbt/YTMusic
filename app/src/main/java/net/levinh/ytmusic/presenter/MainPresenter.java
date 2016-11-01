package net.levinh.ytmusic.presenter;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.youtube.YouTubeScopes;

import net.levinh.ytmusic.R;
import net.levinh.ytmusic.YTMusicApplication;
import net.levinh.ytmusic.utils.SharedPreferencesUtils;
import net.levinh.ytmusic.view.activity.MainActivity;
import net.levinh.ytmusic.view.activity.MainView;

import java.lang.ref.WeakReference;
import java.util.Collections;

/**
 * Created by virus on 19/10/2016.
 */

public class MainPresenter implements IMainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();
    public static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
    public static final int REQUEST_AUTHORIZATION = 1;
    public static final int REQUEST_ACCOUNT_PICKER = 2;

    WeakReference<MainView> mMainView;
    private GoogleAccountCredential credential;

    public MainPresenter(MainView mainView) {
        this.mMainView = new WeakReference<MainView>(mainView);
        createAccountCredential();
    }

    private void createAccountCredential() {
        credential = GoogleAccountCredential.usingOAuth2(YTMusicApplication.mContext,
                Collections.singleton(YouTubeScopes.YOUTUBE));
        SharedPreferencesUtils setting = SharedPreferencesUtils.getInstance();
        credential.setSelectedAccountName(setting.getAccountEmail());
    }

    private void haveGooglePlayServices() {
        // check if there is already an account selected
        if (credential.getSelectedAccountName() == null) {
            // ask user to choose account
            chooseAccount();
        } else {
            loginSuccessful();
        }
    }

    private void chooseAccount() {
        //Show dialog choose account
        if (mMainView.get() != null)
            mMainView.get().onStartActivityForResult(credential.newChooseAccountIntent(),
                    REQUEST_ACCOUNT_PICKER);
    }

    private void loginSuccessful() {
        if (mMainView.get() != null)
            mMainView.get().onLoginSuccess();
    }

    @Override
    public void checkGooglePlayServicesAvailable() {
        final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(YTMusicApplication.mContext);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            if (mMainView.get() != null)
                mMainView.get().showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        } else {
            haveGooglePlayServices();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                Log.d(TAG, "on Button Play click: ");
                break;
            case R.id.bottombar_play:
                Log.d(TAG, "on_BottomBar_Play: ");
                break;
            case R.id.btn_backward:
                Log.d(TAG, "on_Btn_Backward_Click: ");
                break;
            case R.id.btn_forward:
                Log.d(TAG, "on_Btn_Forward_Click: ");
                break;
            case R.id.btn_toggle:
                Log.d(TAG, "on_Btn_Toggle_Click: ");
                break;
            case R.id.btn_suffel:
                Log.d(TAG, "on_Btn_Suffel_Click: ");
                break;
            case R.id.bottombar_img_Favorite:
                Log.d(TAG, "on_BottomBar_Favorite: ");
                break;
            case R.id.bottombar_moreicon:
                Log.d(TAG, "on_BottomBar_More: ");
                break;
            default:

                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == Activity.RESULT_OK) {
                    haveGooglePlayServices();
                } else {
                    checkGooglePlayServicesAvailable();
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == Activity.RESULT_OK) {
                    loginSuccessful();
                } else {
                    chooseAccount();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        SharedPreferencesUtils.getInstance().setAccountEmail(accountName);
                        loginSuccessful();
                    }
                }
                break;
        }
    }
}
