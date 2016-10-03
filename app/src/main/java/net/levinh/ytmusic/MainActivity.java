package net.levinh.ytmusic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.lapism.searchview.SearchView;
import net.levinh.ytmusic.adapter.FragmentAdapter;
import net.levinh.ytmusic.temp.BlankFragment;


public class MainActivity extends AppCompatActivity {

    private View mContent;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContent = findViewById(R.id.content);

        initToolbar();
        initFab();
        setupDrawerLayout();
        setupSearchView();
        setViewPager();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initFab() {

    }

    private void setViewPager() {
        //Setup adapter
        final FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new BlankFragment(), "Install");
        adapter.addFragment(new BlankFragment(), "All");
        adapter.addFragment(new BlankFragment(), "Install");
        adapter.addFragment(new BlankFragment(), "All");
        //Setup Viewpager
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        if(viewPager!=null){
            viewPager.setAdapter(adapter);
        }
        //setup Tablayout
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        if(tabLayout!=null){
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void setupSearchView() {
        mSearchView = (SearchView) findViewById(R.id.searchView);
        if (mSearchView != null) {
            mSearchView.setVersion(SearchView.VERSION_TOOLBAR);
            mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);
            mSearchView.setHint("Searching...");
            mSearchView.setTextSize(16);
            mSearchView.setDivider(false);
            mSearchView.setVoice(true);
            mSearchView.setVoiceText("Set permission on Android 6+ !");
            mSearchView.setAnimationDuration(SearchView.ANIMATION_DURATION);
            mSearchView.setShadowColor(ContextCompat.getColor(this, R.color.search_shadow_layout));
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    doSearch(newText);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
            });
//            mSearchView.setOnOpenCloseListener(new SearchView.OnOpenCloseListener() {
//                @Override
//                public void onClose() {
//
//                }
//
//                @Override
//                public void onOpen() {
//
//                }
//            });
        }

        mSearchView.setNavigationIconArrowHamburger();
        mSearchView.setOnMenuClickListener(new SearchView.OnMenuClickListener() {
            @Override
            public void onMenuClick() {
                mDrawerLayout.openDrawer(GravityCompat.START); // finish();
            }
        });
        mSearchView.setOnVoiceClickListener(new SearchView.OnVoiceClickListener() {
            @Override
            public void onVoiceClick() {
                perm(Manifest.permission.RECORD_AUDIO, 0);
            }
        });
    }

    private void setupDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void doSearch(String query) {

    }

    private void perm(String permission, int permission_request) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, permission_request);
            }
        }
    }


}
