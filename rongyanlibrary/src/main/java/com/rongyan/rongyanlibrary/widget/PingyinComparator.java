package com.rongyan.rongyanlibrary.widget;

import java.util.Comparator;

/**
 * ListView中的数据根据A-Z进行排序
 * Created by XRY on 2016/11/2.
 */

public class PingyinComparator implements Comparator<SortModel> {
    @Override
    public int compare(SortModel sortModel, SortModel t1) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (t1.getSortLetters().equals('#')) {
            return -1;
        }else if (sortModel.getSortLetters().equals("#")){
            return 1;
        }else {
            return sortModel.getSortLetters().compareTo(t1.getSortLetters());
        }
    }


}
