/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fragmenttabsdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProductColorFragment extends BaseProductFragment implements OnItemClickListenerVariant {
    private ListView listView;
    private String[] data;
    private int mFragmentHeight;
    UpdateProductDetailsListner updateProductDetailsListner;
    private int mIndex;
    private static final String PRODUCT = "mProduct", PRODUCT_SELECTED_INDEX = "selectedIndex", PRODUCT_INDEX = "index";

    public static ProductColorFragment newInstance(String[] data, int index) {
        ProductColorFragment productColorFragment = new ProductColorFragment();
        Bundle args = new Bundle();
        args.putStringArray(PRODUCT, data);
        args.putInt(PRODUCT_INDEX, index);
        productColorFragment.setArguments(args);
        return productColorFragment;
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
        listView.setBackgroundColor(Color.parseColor("#ffe5e5"));
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
    public void setRecyclerViewHeight() {
        this.updateProductDetailsListner.onLayoutHeightChanged(mFragmentHeight);
    }

    @Override
    public void onFragmentChanged() {
    }

    public void setmFragmentHeight() {
        int viewHeight = ApplicationUtils.dpToPx(ApplicationUtils.itemHeight) * data.length;
        this.mFragmentHeight = viewHeight;
        this.updateProductDetailsListner.onLayoutHeightChanged(mFragmentHeight);
    }

    @Override
    public int getIndex() {
        return mIndex;
    }
}
