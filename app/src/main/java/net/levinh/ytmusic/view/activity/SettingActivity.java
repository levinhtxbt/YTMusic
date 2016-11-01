package net.levinh.ytmusic.view.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.levinh.ytmusic.R;
import net.levinh.ytmusic.presenter.SettingPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements SettingView {

    @BindView(R.id.layout_chooseTheme)
    RelativeLayout layout_chooseTheme;
    @BindView(R.id.textViewTheme)
    TextView textViewTheme;
    @BindView(R.id.textViewThemeDescription)
    TextView textViewThemeDescription;

    @BindView(R.id.layout_chooseLanguage)
    RelativeLayout layout_chooseLanguage;
    @BindView(R.id.textViewLanguage)
    TextView textViewLanguage;
    @BindView(R.id.textViewLanguageDescription)
    TextView textViewLanguageDescription;

    SettingPresenter presenter;
    final CharSequence[] lang_list = {"English", "Việt Nam", "bla bla"};
    final CharSequence[] theme_list = {"Dark", "Light", "bla bla"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        presenter = new SettingPresenter(this);
    }

    @Override
    protected void setIconToolbar() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onActionBarClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @OnClick(R.id.layout_chooseTheme)
    public void onLayoutChooseThemeClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Choose theme")
                .setItems(theme_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.setTheme(theme_list[which].toString());
                    }
                });
        builder.create().show();
    }

    @OnClick(R.id.layout_chooseLanguage)
    public void onLayoutChooseLanguageClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Choose a language")
                .setItems(lang_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.setLanguage(lang_list[which].toString());
                    }
                });
        builder.create().show();
    }

    //Implement SettingView
    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetThemeSuccess(String theme) {
        textViewThemeDescription.setText(theme);
    }

    @Override
    public void onSetLanguageSuccess(String lang) {
        textViewLanguageDescription.setText(lang);
    }

}
