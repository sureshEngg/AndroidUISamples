package com.fragmenttabsdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener, UpdateProductDetailsListner {

    private ViewPager mViewPager;
    private int mSelectedTabIndex = 0;
    private final int PRODUCT_SIZE = 0, PRODUCT_COLOR = 1, PRODUCT_QUANTITY = 2;
    private Adapter mPagerAdaptor;
    private Button mColorTab, mSizeTab, mQuantityTab, mLastSelectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mColorTab = (Button) findViewById(R.id.color_tab);
        mSizeTab = (Button) findViewById(R.id.size_tab);
        mQuantityTab = (Button) findViewById(R.id.quantity_tab);
        mColorTab.setTag(PRODUCT_COLOR);
        mSizeTab.setTag(PRODUCT_SIZE);
        mQuantityTab.setTag(PRODUCT_QUANTITY);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mColorTab.setOnClickListener(this);
        mSizeTab.setOnClickListener(this);
        mQuantityTab.setOnClickListener(this);
        setProductDetailTabLayout();
    }

    private void setProductDetailTabLayout() {
        setupViewPager();
        setSelectedTab();
    }

    private void setupViewPager() {
        // Attach the page change listener inside the activity
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.d("MainActivity", "onPageSelected=" + position);
                Adapter adapter = ((Adapter) mViewPager.getAdapter());
                adapter.getFragment(position).setRecyclerViewHeight();
                adapter.getFragment(position).onFragmentChanged();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

        mPagerAdaptor = null;
        mPagerAdaptor = new Adapter(getSupportFragmentManager());
        mLastSelectedTab = mSizeTab;
        addProductSizeTab();
        addProductColorTab();
        addProductQuantityTab();
        mViewPager.setAdapter(mPagerAdaptor);
    }

    private void setSelectedTab() {
        //setTab(mSelectedTabIndex+1);
        mPagerAdaptor.getFragment(mPagerAdaptor.getIndexFragment(mSelectedTabIndex)).setRecyclerViewHeight();
        //mViewPager.setCurrentItem(mPagerAdaptor.getIndexFragment(mSelectedTabIndex+1));
    }
/*
    public void setTab(int tab) {
        mLastSelectedTab.setActivated(false);
        switch (tab) {
            case PRODUCT_SIZE:
                mSizeTab.setActivated(true);
                mLastSelectedTab = mSizeTab;
                break;
            case PRODUCT_COLOR:
                mColorTab.setActivated(true);
                mLastSelectedTab = mColorTab;
                break;
            case PRODUCT_QUANTITY:
                mQuantityTab.setActivated(true);
                mLastSelectedTab = mQuantityTab;
                break;
        }
    }*/

    private void addProductQuantityTab() {
        String[] data = new String[]{"100", "200", "340", "550", "600", "700", "800", "900", "1000", "400", "750"};
        ProductQuantityFragment productQuantityFragment = ProductQuantityFragment.newInstance(data, PRODUCT_QUANTITY);
        productQuantityFragment.setUpdateProductDetailsListner(this);
        mPagerAdaptor.addFragment(productQuantityFragment, "Quantity");
    }

    private void addProductColorTab() {
        String[] data = new String[]{"Red", "Green", "Yellow", "White", "black", "Grey"};
        ProductColorFragment productColorFragment = ProductColorFragment.newInstance(data, PRODUCT_COLOR);
        productColorFragment.setUpdateProductDetailsListner(this);
        mPagerAdaptor.addFragment(productColorFragment, "Select Color");
    }

    private void addProductSizeTab() {
        String[] data = new String[]{"1", "2", "3", "5", "6", "7","1", "2"};
        ProductSizeFragment productSizeFragment = ProductSizeFragment.newInstance(data, PRODUCT_SIZE);
        productSizeFragment.setUpdateProductDetailsListner(this);
        mPagerAdaptor.addFragment(productSizeFragment, "Select Size");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.size_tab:
            case R.id.color_tab:
            case R.id.quantity_tab:
                Adapter adapter = ((Adapter) mViewPager.getAdapter());
                mViewPager.setCurrentItem(adapter.getIndexFragment((Integer) v.getTag()));
                break;
        }
    }

    class Adapter extends FragmentPagerAdapter {
        private final List<BaseProductFragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(BaseProductFragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            //setTab(position);
            return mFragments.get(position);
        }

        public BaseProductFragment getFragment(int position) {
            return mFragments.get(position);
        }

        public int getIndexFragment(int index) {
            for (int i = 0; i < mFragments.size(); i++) {
                BaseProductFragment fragment = mFragments.get(i);
                if (index == fragment.getIndex())
                    return i;
            }

            return 0;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onLayoutHeightChanged(int height) {
        Log.d("MainActivity", "onLayoutHeightChanged=" + height);
        setViewPagerHeight(height);
    }

    ResizeAnimation resizeAnimation;
    public class ResizeAnimation extends Animation {
        private View mView;
        private float mToHeight;
        private float mFromHeight;

        public ResizeAnimation(View v, float toHeight) {
            mToHeight = toHeight;
            mFromHeight = v.getLayoutParams().height;
            mView = v;
            setDuration(300);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float height =
                    (mToHeight - mFromHeight) * interpolatedTime + mFromHeight;
            mView.getLayoutParams();
            mView.getLayoutParams().height = (int) height;
            mView.requestLayout();
        }
    }
    private void setViewPagerHeight(int height) {
        resizeAnimation = new ResizeAnimation(mViewPager, height);
        resizeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /*setPositionOfBuyCartButtonBar();*/
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mViewPager.startAnimation(resizeAnimation);
    }

}