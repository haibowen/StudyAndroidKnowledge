package com.ekwing.studyandroidkonwlegeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ekwing.studyandroidkonwlegeapp.adapter.GuideVpAdapter;
import com.ekwing.studyandroidkonwlegeapp.databinding.ActivityGuideBinding;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private ActivityGuideBinding mActivityGuideBinding;
    private View mViewFirst;
    private View mViewSecond;
    private View mViewThird;
    private Context mContext;
    private List<View> mViewList;
    private GuideVpAdapter mGuideVpAdapter;

    private ImageView [] mImageViews;

    private int mIntCurrent=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivityGuideBinding = DataBindingUtil.setContentView(this, R.layout.activity_guide);
        initViewPotion();
        initViewPager();
        setStabarColor();
        final int[] ColorList = initColor();
        OnViewPagerListener(ColorList);

    }

    /**
     * Viewpager的滑动监听
     * @param colorList
     */
    private void OnViewPagerListener(final int[] colorList) {
        mActivityGuideBinding.vpShow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                final ArgbEvaluator mArgbEvaluator=new ArgbEvaluator();
                int colorUpdate =
                        (Integer) mArgbEvaluator.evaluate(
                                positionOffset,
                                colorList[position],
                                colorList[position == 2 ? position : position + 1]);
                mActivityGuideBinding.vpShow.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                mIntCurrent=position;
                updateIndicators(mIntCurrent);
                switch (position) {
                    case 0:
                        mActivityGuideBinding.vpShow.setBackgroundColor(getResources().getColor(R.color.cyan));
                        break;
                    case 1:
                        mActivityGuideBinding.vpShow.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                    case 2:
                        mActivityGuideBinding.vpShow.setBackgroundColor(getResources().getColor(R.color.orange));
                        break;
                }

                mActivityGuideBinding.btSkip.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                mActivityGuideBinding.btNextMain.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                mActivityGuideBinding.ibNext.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViewPotion() {
        mImageViews=new ImageView[]{
                mActivityGuideBinding.ivFirst,
                mActivityGuideBinding.ivSecond,
                mActivityGuideBinding.ivThird };
    }

    private int[] initColor() {
        final int color1 = ContextCompat.getColor(this, R.color.cyan);
        final int color2 = ContextCompat.getColor(this, R.color.green);
        final int color3 = ContextCompat.getColor(this, R.color.orange);

        return new int[]{
                 color1,
                 color2,
                 color3
         };
    }

    private void setStabarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 沉浸式状态栏
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            // 更改状态栏颜色
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans80));
        }
    }

    private void initViewPager() {
        //数据源
        mViewFirst = getLayoutInflater().from(mContext).inflate(R.layout.view_guide_one, null);
        mViewSecond = getLayoutInflater().from(mContext).inflate(R.layout.view_guide_two, null);
        mViewThird = getLayoutInflater().from(mContext).inflate(R.layout.view_guide_three, null);
        mViewList = new ArrayList<>();
        mViewList.add(mViewFirst);
        mViewList.add(mViewSecond);
        mViewList.add(mViewThird);

        //适配器
        mGuideVpAdapter = new GuideVpAdapter(mViewList);
        mActivityGuideBinding.vpShow.setAdapter(mGuideVpAdapter);
        mActivityGuideBinding.vpShow.setCurrentItem(mIntCurrent);
        updateIndicators(mIntCurrent);
    }

    /**
     * 通过切换两个不同的drawable来更新指示器。
     */
    private void updateIndicators(int position) {
        for (int i = 0; i < mImageViews.length; i++) {
            mImageViews[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }
}
