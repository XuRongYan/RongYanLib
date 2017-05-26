package com.rongyan.aikanvideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.widget.CompoundButton;

import com.rongyan.aikanvideo.R;
import com.rongyan.rongyanlibrary.CommonAdapter.CommonAdapter;
import com.rongyan.rongyanlibrary.CommonAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XRY on 2017/5/24.
 */

public class AddPreferenceAdapter extends CommonAdapter<String> {
    SparseBooleanArray checkStates = new SparseBooleanArray();

    public AddPreferenceAdapter(Context context, List<String> list) {
        super(context, list);
    }

    public AddPreferenceAdapter(Context context, List<String> list, RecyclerView recyclerView) {
        super(context, list, recyclerView);
    }

    @Override
    public int setLayoutId(int position) {
        return R.layout.item_add_preference;
    }

    @Override
    public void onBindVH(final ViewHolder viewHolder, String item, final int position) {
        viewHolder.setTag(R.id.item_add_preference_check, item)
                .setCheckBoxText(R.id.item_add_preference_check, item)
                .addOnCheckedChangedListener(R.id.item_add_preference_check, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String tag = (String) buttonView.getTag();
                        if (isChecked) {
                            checkStates.put(position, true);

                        } else {
                            checkStates.delete(position);
                        }

                        viewHolder.setChecked(R.id.item_add_preference_check, checkStates.get(position, false));

                    }
                });
    }

    public List<String> getCheckedItems() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < checkStates.size(); i++) {

            list.add(this.list.get(checkStates.keyAt(i)));

        }
        return list;
    }

}
