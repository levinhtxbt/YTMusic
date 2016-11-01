package net.levinh.ytmusic.presenter;

import android.util.Log;

import net.levinh.ytmusic.view.activity.SettingView;

/**
 * Created by virus on 13/10/2016.
 */

public class SettingPresenter implements ISettingPresenter,IBasePresenter{
    private static final String TAG = SettingPresenter.class.getSimpleName();
    SettingView mSettingView;

    public SettingPresenter(SettingView settingView) {
        mSettingView = settingView;
    }

    @Override
    public void setTheme(String theme) {
        Log.d(TAG, "setTheme: " + theme);
        mSettingView.onSetThemeSuccess(theme);
    }

    @Override
    public void setLanguage(String language) {
        Log.d(TAG, "setLanguage: " + language);
        mSettingView.onSetLanguageSuccess(language);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        mSettingView = null;
    }
}
