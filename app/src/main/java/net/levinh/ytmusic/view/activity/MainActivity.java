package net.levinh.ytmusic.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import net.levinh.uicomponent.PlayPauseView;
import net.levinh.uicomponent.Slider;
import net.levinh.uicomponent.slidinguppanelhelper.SlidingUpPanelLayout;
import net.levinh.ytmusic.R;
import net.levinh.ytmusic.presenter.MainPresenter;
import net.levinh.ytmusic.utils.SharedPreferencesUtils;
import net.levinh.ytmusic.view.adapter.FragmentAdapter;
import net.levinh.ytmusic.view.fragment.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity
        implements Slider.OnValueChangedListener, View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener, MainView {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.content)
    View mContent;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout mLayout;
    @BindView(R.id.image_songAlbumbg_mid)
    ImageView songAlbumbg;
    @BindView(R.id.img_bottom_slideone)
    ImageView img_bottom_slideone;
    @BindView(R.id.img_bottom_slidetwo)
    ImageView img_bottom_slidetwo;
    @BindView(R.id.slidepanel_time_progress)
    TextView txt_timeprogress;
    @BindView(R.id.slidepanel_time_total)
    TextView txt_timetotal;
    @BindView(R.id.btn_backward)
    ImageView imgbtn_backward;
    @BindView(R.id.btn_forward)
    ImageView imgbtn_forward;
    @BindView(R.id.btn_toggle)
    ImageView imgbtn_toggle;
    @BindView(R.id.btn_suffel)
    ImageView imgbtn_suffel;

    @BindView(R.id.btn_play)
    PlayPauseView btn_playpause;
    @BindView(R.id.audio_progress_control)
    Slider audio_progress;
    @BindView(R.id.bottombar_play)
    PlayPauseView btn_playpausePanel;
    @BindView(R.id.bottombar_img_Favorite)
    ImageView img_Favorite;
    @BindView(R.id.txt_songartistname_slidetoptwo)
    TextView txt_songartistname_slidetoptwo;
    @BindView(R.id.slidepanelchildtwo_topviewone)
    RelativeLayout slidepanelchildtwo_topviewone;
    @BindView(R.id.slidepanelchildtwo_topviewtwo)
    RelativeLayout slidepanelchildtwo_topviewtwo;
    @BindView(R.id.txt_playesongname)
    TextView txt_playesongname;
    @BindView(R.id.txt_songartistname)
    TextView txt_songartistname;
    @BindView(R.id.txt_playesongname_slidetoptwo)
    TextView txt_playesongname_slidetoptwo;
    private boolean isExpand = false;
    private FragmentAdapter mViewPagerAdapter;
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);

        setupDrawerLayout();
        setupMaterialSearchView();
        setViewPager();
        initiSlidingUpPanel();
    }

    @Override
    protected void setIconToolbar() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActionBarClick() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void setViewPager() {
        //Setup adapter
        mViewPagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        SearchFragment searchFragment = SearchFragment.newInstance();
        mViewPagerAdapter.addFragment(searchFragment, "Online");

        //Setup Viewpager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        if (viewPager != null) {
            viewPager.setAdapter(mViewPagerAdapter);
        }

        //setup Tablayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void setupMaterialSearchView() {

        mSearchView.setVoiceSearch(true);
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setEllipsize(true);
//        mSearchView.setSuggestions(new String[]{"a", "ab", "ac", "db", "ec"});
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: " + newText);
                return false;
            }
        });

//        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//            @Override
//            public void onSearchViewShown() {
//                Log.d(TAG, "onSearchViewShown: ");
//                //Do some magic
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                Log.d(TAG, "onSearchViewClosed: ");
//                //Do some magic
//            }
//        });
    }

    private void setupDrawerLayout() {
        mNavigationView.setNavigationItemSelectedListener(this);
        ImageView imageView = (ImageView)mNavigationView.getHeaderView(0).findViewById(R.id.avatar);
        if(imageView!=null){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.checkGooglePlayServicesAvailable();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initiSlidingUpPanel() {

        TypedValue typedvaluecoloraccent = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorAccent, typedvaluecoloraccent, true);
        final int coloraccent = typedvaluecoloraccent.data;
        audio_progress.setBackgroundColor(coloraccent);
        audio_progress.setMin(0);
        audio_progress.setMax(100);
        audio_progress.setValue(0);
        btn_playpausePanel.Pause();
        btn_playpause.Pause();
        //Set listener
        audio_progress.setOnValueChangedListener(this);
        slidepanelchildtwo_topviewone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });

        img_bottom_slidetwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });


//        imgbtn_toggle.setSelected((MusicPreferance.getRepeat(context) == 1) ? true : false);
//        MediaController.getInstance().shuffleMusic = imgbtn_toggle.isSelected() ? true : false;
//        DMPlayerUtility.changeColorSet(context, (ImageView) imgbtn_toggle, imgbtn_toggle.isSelected());
//
//        imgbtn_suffel.setSelected(MusicPreferance.getShuffel(context) ? true : false);
//        MediaController.getInstance().repeatMode = imgbtn_suffel.isSelected() ? 1 : 0;
//        DMPlayerUtility.changeColorSet(context, (ImageView) imgbtn_suffel, imgbtn_suffel.isSelected());
//        MediaController.getInstance().shuffleList(MusicPreferance.playlist);

        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
                if (slideOffset == 0.0f) {
                    isExpand = false;
                    slidepanelchildtwo_topviewone.setVisibility(View.VISIBLE);
                    slidepanelchildtwo_topviewtwo.setVisibility(View.INVISIBLE);

                } else if (slideOffset > 0.0f && slideOffset < 1.0f) {
//                    if (isExpand) {
//                        slidepanelchildtwo_topviewone.setAlpha(1.0f);
//                        slidepanelchildtwo_topviewtwo.setAlpha(1.0f - slideOffset);
//                    } else {
//                        slidepanelchildtwo_topviewone.setAlpha(1.0f - slideOffset);
//                        slidepanelchildtwo_topviewtwo.setAlpha(1.0f);
//                    }

                } else {
                    isExpand = true;
                    slidepanelchildtwo_topviewone.setVisibility(View.INVISIBLE);
                    slidepanelchildtwo_topviewtwo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.i(TAG, "onPanelExpanded");
                isExpand = true;
            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.i(TAG, "onPanelCollapsed");
                isExpand = false;
            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "onPanelAnchored");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.i(TAG, "onPanelHidden");
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }

    }

    //Slider
    @Override
    public void onValueChanged(int value) {
        Log.d(TAG, "onValueChanged: " + value);
    }

    @OnClick({R.id.btn_play,
            R.id.bottombar_play,
            R.id.btn_backward,
            R.id.btn_forward,
            R.id.btn_toggle,
            R.id.btn_suffel,
            R.id.bottombar_img_Favorite,
            R.id.bottombar_moreicon})
    public void onButtonClick(View v) {
        presenter.onClick(v);
    }

    public void doSearch(String query) {

        SearchFragment mFragment = (SearchFragment) mViewPagerAdapter.getItemByTitle("Online");
        if (mFragment != null) {
            mFragment.searchVideo(query);
            mViewPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
        item.setCheckable(true);
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
        runOnUiThread(new Runnable() {
            public void run() {
                Dialog dialog =
                        GooglePlayServicesUtil.getErrorDialog(connectionStatusCode, MainActivity.this,
                                MainPresenter.REQUEST_GOOGLE_PLAY_SERVICES);
                dialog.show();
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Login success with " +
                SharedPreferencesUtils.getInstance().getAccountEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent,requestCode);
    }
}
