package net.levinh.ytmusic.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.levinh.ytmusic.R;

/**
 * Created by virus on 12/10/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        initToolbar();
    }

    protected void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setIconToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onActionBarClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract void setIconToolbar();

    protected abstract int getLayoutResource();

    protected abstract void onActionBarClick();

}
