package com.example.xjj.libraryapplication.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.xjj.libraryapplication.Ui.BaseFragment;

import java.util.ArrayList;

public class BookPageAdapter extends FragmentPagerAdapter {
    ArrayList<BaseFragment> baseFragments;
    public BookPageAdapter(FragmentManager fm, ArrayList<BaseFragment> baseFragments) {
        super(fm);
        this.baseFragments=baseFragments;
    }

    @Override
    public Fragment getItem(int i) {
        return baseFragments.get(i);
    }

    @Override
    public int getCount() {
        return baseFragments.size();
    }

    /**
     * 取消重复加载
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}
