package com.rongyan.aikanvideo.widget.vitamioView;

import android.graphics.Color;

import com.rongyan.rongyanlibrary.util.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.android.JSONSource;
import master.flame.danmaku.danmaku.util.DanmakuUtils;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by XRY on 2017/5/2.
 */

public class SimpleDanmakuParser extends BaseDanmakuParser {
    private static final String TAG = "SimpleDanmakuParser";
    private DanmakuView danmakuView;

    @Override
    public Danmakus parse() {
        if (mDataSource != null && mDataSource instanceof JSONSource) {

            JSONSource jsonSource = (JSONSource) mDataSource;
            return doParse(jsonSource.data());
        }
        return new Danmakus();
    }

    /**
     * @param danmakuListData 弹幕数据
     *                        传入的数组内包含普通弹幕，会员弹幕，锁定弹幕。
     * @return 转换后的Danmakus
     */
    private Danmakus doParse(JSONArray danmakuListData) {

        Danmakus danmakus = new Danmakus();

        if (danmakuListData == null || danmakuListData.length() == 0) {
            return danmakus;
        }
        for (int i = 0; i < danmakuListData.length(); i++) {
            try {
                JSONObject danmakuArray = danmakuListData.getJSONObject(i);
                if (danmakuArray != null) {
                    danmakus = _parse(danmakuArray, danmakus);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return danmakus;
    }

    private Danmakus _parse(JSONObject jsonObject, Danmakus danmakus) {
        LogUtils.d(TAG, "_parse", "执行了");
        if (danmakus == null) {
            danmakus = new Danmakus();
        }
        if (jsonObject == null || jsonObject.length() == 0) {
            return danmakus;
        }
        try {
            JSONObject obj = jsonObject;
            String c = obj.getString("c");
            String[] values = c.split(",");
            if (values.length > 0) {
                int type = Integer.parseInt(values[2]); // 弹幕类型

                long time = Long.valueOf(values[0]); // 出现时间
                int color = Integer.parseInt(values[1]) | 0xFF000000; // 颜色
                float textSize = Float.parseFloat(values[3]); // 字体大小
                BaseDanmaku item = mContext.mDanmakuFactory.createDanmaku(type, mContext);
                if (item != null) {
                    item.setTime(time);
                    item.textSize = textSize;
                    item.textColor = color;
                    item.textShadowColor = color <= Color.BLACK ? Color.WHITE : Color.BLACK;
                    DanmakuUtils.fillText(item, obj.optString("m", "...."));
                    //item.index = i;
                    item.flags = mContext.mGlobalFlagValues;
                    //item.priority = 1;
                    item.setTimer(mTimer);
                    Object lock = danmakus.obtainSynchronizer();
                    synchronized (lock) {
                        danmakus.addItem(item);
                        LogUtils.d(TAG, "_parse", "time：" + jsonObject.length());
                    }
                }
            }
        } catch (JSONException e) {
        } catch (NumberFormatException e) {
        }

        return danmakus;
    }
}
