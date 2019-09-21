package com.ekwing.studyandroidkonwlegeapp.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class GuideVpAdapter  extends PagerAdapter {
    List<View> mViewList;

    public GuideVpAdapter(List<View> viewList) {
        mViewList = viewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }
}
