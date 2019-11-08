package com.hjkj.fuduoduo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Author：Created by shihongfei on 2019/9/25 09:37
 * Email：1511808259@qq.com
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentLists;
    private List<String> titleLists;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentLists) {
        super(fm);
        this.fragmentLists = fragmentLists;
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentLists, List<String> titleLists) {
        super(fm);
        this.fragmentLists = fragmentLists;
        this.titleLists = titleLists;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentLists.get(position);
    }

    @Override
    public int getCount() {
        return fragmentLists.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleLists != null) {
            return titleLists.get(position % titleLists.size());
        }
        return null;
    }
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}

