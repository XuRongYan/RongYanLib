package com.rongyan.rongyanlibrary.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.rongyan.rongyanlibrary.R;

import java.util.List;

/**
 * Created by XRY on 2016/11/2.
 */

public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private List<SortModel> list;
    private Context context;

    public SortAdapter(Context context, List<SortModel> list) {
        this.context = context;
        this.list = list;
    }

    public void updateListView(List<SortModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        SortModel sortModel = list.get(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item, null);

            view.setTag(new ViewHolder(view));
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        //根据position获取分类的首字母的char ascii值
        int section = getSectionForPosition(i);

        //如果当前位置等于该分类字母的Char位置，则认为是第一次出现
        if (i == getPositionForSection(section)) {
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
            viewHolder.tvTitle.setText(sortModel.getSortLetters());
        }else {
            viewHolder.tvTitle.setVisibility(View.GONE);
        }

        viewHolder.tvLetter.setText(this.list.get(i).getName());

        return view;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int i) {
        for (int j = 0; j < getCount(); j++) {
            String sortStr = list.get(j).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == i) {
                return j;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int i) {
        return list.get(i).getSortLetters().charAt(0);
    }

    private final class ViewHolder{
        TextView tvLetter;
        TextView tvTitle;

        ViewHolder(View view) {
            tvLetter = (TextView) view.findViewById(R.id.tv_title);
            tvTitle = (TextView) view.findViewById(R.id.catalog);
        }
    }
}
