package com.fragmenttabsdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ProductQuantityFragment extends BaseProductFragment implements OnItemClickListenerVariant {
    private ListView listView;
    private String[] data;
    UpdateProductDetailsListner updateProductDetailsListner;
    private int mFragmentHeight;
    private int mIndex;
    private static final String PRODUCT = "mProduct", PRODUCT_SELECTED_INDEX = "selectedIndex", PRODUCT_INDEX = "index";

    public static ProductQuantityFragment newInstance(String[] data, int index) {
        ProductQuantityFragment ProductQuantityFragment = new ProductQuantityFragment();
        Bundle args = new Bundle();
        args.putStringArray(PRODUCT, data);
        args.putInt(PRODUCT_INDEX, index);
        ProductQuantityFragment.setArguments(args);
        return ProductQuantityFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View lv = (View) inflater.inflate(
                R.layout.fragment_product_color_list, container, false);
        listView = (ListView) lv.findViewById(R.id.listView);
        Bundle args = getArguments();
        this.data = args.getStringArray(PRODUCT);
        this.mIndex = args.getInt(PRODUCT_INDEX);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, this.data);
        listView.setAdapter(adapter);
        listView.setBackgroundColor(Color.parseColor("#cccccc"));
        setmFragmentHeight();
        return lv;
    }

    @Override
    public void onItemClick(View view, int position, String productCode) {
    }
    public void setUpdateProductDetailsListner(UpdateProductDetailsListner updateProductDetailsListner) {
        this.updateProductDetailsListner = updateProductDetailsListner;
    }
    @Override
    public void onFragmentChanged() {
    }

    @Override
    public void setRecyclerViewHeight() {
        this.updateProductDetailsListner.onLayoutHeightChanged(mFragmentHeight);
    }

    public void setmFragmentHeight() {
        int viewHeight = ApplicationUtils.dpToPx(ApplicationUtils.itemHeight) * data.length;
        this.mFragmentHeight = viewHeight;
    }

    @Override
    public int getIndex() {
        return mIndex;
    }
}
