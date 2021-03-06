package com.rongyan.rongyanlibrary.CommonAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rongyan.rongyanlibrary.ImageLoader.ImageLoader;
import com.rongyan.rongyanlibrary.ImageLoader.ImageLoaderUtil;

/**
 * RecyclerView通用ViewHolder
 *
 * Created by XRY on 2017/5/12.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews; //通过SparesArray缓存ItemView
    private View mConvertView;
    private Context mContext;

    private ViewHolder(View itemView, Context mContext) {
        super(itemView);
        this.mContext = mContext;
        this.mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder createViewHolder(View itemView, Context context) {
        return new ViewHolder(itemView, context);
    }

    public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(view, context);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return ((T) view);
    }

    public View getConvertView() {
        return mConvertView;
    }

    /*****************************************TextView****************************************************/
    public ViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int color) {
        TextView textView = getView(viewId);
        textView.setTextColor(color);
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int resColorId) {
        TextView textView = getView(viewId);
        textView.setTextColor(mContext.getResources().getColor(resColorId));
        return this;
    }

    /**
     * 为TextView设置超链接
     *
     * @param viewId
     * @return
     */
    public ViewHolder linkify(int viewId) {
        TextView textView = getView(viewId);
        Linkify.addLinks(textView, Linkify.ALL);
        return this;
    }

    public ViewHolder setTypeFace(Typeface typeFace, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeFace);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }


    /*****************************************ImageView****************************************************/
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setImageUrl(int viewId, String url, int placeHolder) {
        ImageView imageView = getView(viewId);
        ImageLoader loader = new ImageLoader.Builder()
                .imgView(imageView)
                .placeHolder(placeHolder)
                .url(url)
                .build();
        ImageLoaderUtil.getInstance().loadImage(mContext, loader);
        return this;
    }

    public ViewHolder setImageUrl(int viewId, String url) {
        ImageView imageView = getView(viewId);
        ImageLoader loader = new ImageLoader.Builder()
                .imgView(imageView)
                .url(url)
                .build();
        ImageLoaderUtil.getInstance().loadImage(mContext, loader);
        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolder setAlpha(int viewId, float value) {
        getView(viewId).setAlpha(value);
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public ViewHolder setCheckBoxText(int viewId, String text) {
        CheckBox view = getView(viewId);
        view.setText(text);
        return this;
    }

    public ViewHolder addOnCheckedChangedListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CheckBox checkBox = getView(viewId);
        checkBox.setOnCheckedChangeListener(listener);
        return this;
    }

    public ViewHolder setCheckState(int viewId, boolean isCheck) {
        CheckBox checkBox = getView(viewId);
        checkBox.setChecked(isCheck);
        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId,
                                         View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }


}
