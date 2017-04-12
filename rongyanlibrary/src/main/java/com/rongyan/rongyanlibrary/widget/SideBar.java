package com.rongyan.rongyanlibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.rongyan.rongyanlibrary.R;


/**
 * ListView的右侧字母索引View
 * Created by XRY on 2016/11/1.
 */

public class SideBar extends View {
    //触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    //26个字母
    private static String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","#"};
    //选中了哪个字母
    private int choose = -1;

    private Paint paint = new Paint();

    private TextView mTextDialog;

    /**
     * 为SideBar设置显示字母的TextView
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取焦点改变背景颜色
        int height = getHeight(); //获取对应高度
        int width = getWidth(); //获取宽度
        int singleHeight = height / letter.length; //获取每一个字母的高度
        setBackgroundResource(R.drawable.sidebar_gray_background);
        for (int i = 0; i < letter.length; i++) {
            paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20);
            //选中的状态
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            //x坐标等于中间字符串宽度的一半
            float xPos = width / 2 - paint.measureText(letter[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(letter[i],xPos,yPos,paint);

            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY(); //点击Y坐标
        final int oldChoose = choose;
        final  OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * letter.length); //点击y坐标所占总高度比例*b数组的长度就等于点击b中的个数
        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundResource(R.drawable.sidebar_gray_background);
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                setBackgroundResource(R.drawable.sidebar_background);
                //如果选择的没有改变
                if (oldChoose != c) {
                    //并且在字母范围内
                    if (c >= 0 && c < letter.length) {
                        //在监听器不为空时回调监听方法
                        if (listener != null) {
                            listener.onTouchingLetterChanged(letter[c]);
                        }
                        //显示text
                        if (mTextDialog != null) {
                            mTextDialog.setText(letter[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener listener){
        this.onTouchingLetterChangedListener = listener;
    }



    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }
}
