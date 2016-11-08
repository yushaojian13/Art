package com.ysj.art;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ysj.log.L;

import java.util.ArrayList;
import java.util.List;

public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private final List<String> mTabTitles = new ArrayList<>();

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    public void setTabTitles(List<String> tabTitles) {
        if (tabTitles == null) {
            return;
        }

        mTabTitles.clear();
        mTabTitles.addAll(tabTitles);
    }

    @Override
    public int getCount() {
        return mTabTitles.size();
    }

    @Override
    public Fragment getItem(int position) {
        String title = getPageTitle(position).toString();
        PageFragment pageFragment = PageFragment.newInstance(title);
        L.d(title + " " + position + " " + pageFragment);
        return pageFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return mTabTitles.get(position);
    }

    /**
     * 丢弃所有原有的fragments
     */
    @Override
    public int getItemPosition(Object item) {
        return POSITION_NONE;
    }
}
