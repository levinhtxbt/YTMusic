package net.levinh.ytmusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by virus on 03/10/2016.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mFragmentTitles = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fm, String title){
        mFragments.add(fm);
        mFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
