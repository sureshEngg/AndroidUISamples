package com.fragmenttabsdemo;


import android.support.v4.app.Fragment;

/**
 * Created by sureshsharma on 6/25/2015.
 */
public abstract class BaseProductFragment extends Fragment {
    public abstract void setRecyclerViewHeight();
    public abstract void onFragmentChanged();
    public abstract int getIndex();
}
