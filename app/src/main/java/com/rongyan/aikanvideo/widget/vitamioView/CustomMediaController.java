package com.rongyan.aikanvideo.widget.vitamioView;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.linxiao.commondevlib.util.PreferencesUtil;
import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.ChooseIndexAdapter;
import com.rongyan.aikanvideo.adapter.PlotsAdapter;
import com.rongyan.aikanvideo.video.VideoActivity;
import com.rongyan.rongyanlibrary.CommonAdapter.MyDecoration;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Plots;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ApiService;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpResult;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.NetworkApi;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ProgressSubscriber;
import com.rongyan.rongyanlibrary.util.AppUtils;
import com.rongyan.rongyanlibrary.util.LogUtils;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.ui.widget.DanmakuView;
import rx.Observable;
import rx.subjects.PublishSubject;


/**
 * 自定义MediaController
 * Created by XRY on 2017/4/25.
 */

public class CustomMediaController extends MediaController implements View.OnClickListener {
    private static final String TAG = "CustomMediaController";
    private final Point point = new Point();
    private User user;
    private Video video;
    private PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject1;
    private ImageView img_back;
    private TextView fileName;
    private ImageView scale;
    private FrameLayout view_volume;
    private FrameLayout view_brightness;
    private TextView textSeek;
    private Button send;
    private TextView volumePercent;
    private TextView brightnessPercent;
    private TextView danmu;
    private SeekBar mProgress;
    private VideoView mVideoView;
    private Activity activity;
    private Context context;
    private boolean isShow = true;
    private int controllerWidth = 0;
    private MediaPlayerControl player;
    private InputStream inputStream;

    private boolean showDanmaku;
    private DanmakuView danmakuView;
    private DanmakuContext danmakuContext;
    private AudioManager mAudioManager;
    private GestureDetector mGestureDetector;
    //最大声音
    private int mMaxVolume;
    // 当前声音
    private int mVolume = -1;
    //当前亮度
    private float mBrightness = -1f;
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };


    private PopupWindow danmaWindow;
    private TextView choose;
    private ImageView collect;
    private List<Plots> plotsList = new ArrayList<>();
    private PopupWindow plotsWindow;
    private ImageView imgPlots;
    private PlotsAdapter plotsAdapter;
    private ImageView imgLike;
    private ArrayList<Video> teleplayList;
    private ChooseIndexAdapter chooseIndexAdapter;
    private PopupWindow chooseIndexWindow;
    private TextView tvChooseIndex;

    public CustomMediaController(Context context) {
        super(context);
    }

    public CustomMediaController(Context context, VideoView mVideoView, Activity activity,
                                 DanmakuView danmakuView,
                                 PublishSubject<ActivityLifeCycleEvent> lifeCycleEventPublishSubject,
                                 User user,
                                 Video video,
                                 ArrayList<Video> teleplayList,
                                 boolean showDanmaku) {
        super(context);
        this.context = context;
        this.mVideoView = mVideoView;
        this.activity = activity;
        this.danmakuView = danmakuView;
        this.showDanmaku = showDanmaku;
        this.video = video;
        this.user = user;
        this.teleplayList = teleplayList;
        this.lifeCycleEventPublishSubject1 = lifeCycleEventPublishSubject;
        mGestureDetector = new GestureDetector(context, new MyGestureListener());

    }


    public CustomMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        //mGestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    protected View makeControllerView() {
        View view = ((LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(getResources().getIdentifier("my_media_controller", "layout"
                        , getContext().getPackageName()), this);
        img_back = (ImageView) view.findViewById(R.id.mediacontroller_back);
        fileName = (TextView) view.findViewById(R.id.mediacontroller_file_name);
        scale = (ImageView) view.findViewById(R.id.mediacontroller_scale);
        send = (Button) view.findViewById(R.id.mediacontroller_send);
        mProgress = (SeekBar) view.findViewById(R.id.mediacontroller_seekbar);
        danmu = (TextView) view.findViewById(R.id.mediacontroller_danmu);
        view_volume = (FrameLayout) view.findViewById(R.id.mediacontroller_volume);
        view_brightness = (FrameLayout) view.findViewById(R.id.mediacontroller_brightness);
        textSeek = (TextView) view.findViewById(R.id.mediacontroller_slide_seek);
        mAudioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volumePercent = (TextView) view.findViewById(R.id.mediacontroller_volume_percent);
        brightnessPercent = (TextView) view.findViewById(R.id.mediacontroller_brightness_percent);
        choose = (TextView) view.findViewById(R.id.mediacontroller_choose);
        collect = (ImageView) view.findViewById(R.id.mediacontroller_collect);
        imgPlots = (ImageView) view.findViewById(R.id.mediacontroller_plots);
        imgLike = (ImageView) view.findViewById(R.id.mediacontroller_like);
        tvChooseIndex = (TextView) view.findViewById(R.id.mediacontroller_choose);
        tvChooseIndex.setOnClickListener(this);
        imgLike.setOnClickListener(this);
        choose.setOnClickListener(this);
        scale.setOnClickListener(this);
        danmu.setOnClickListener(this);
        img_back.setOnClickListener(this);
        send.setOnClickListener(this);
        collect.setOnClickListener(this);
        imgPlots.setOnClickListener(this);
        setOnPauseListener(new OnPauseListener() {
            @Override
            public void onPause() {
                danmakuView.pause();
            }
        });
        setOnStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                danmakuView.start();
            }
        });
        getPlots();
        initChooseWindow();
        initPlotsWindow();
        initDanmaWindow();
        initDanmaku();
        return view;
    }

    /**
     * 定时隐藏
     */
    private Handler mDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            view_volume.setVisibility(View.GONE);
            view_brightness.setVisibility(View.GONE);
        }
    };

    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;

        //隐藏
        mDismissHandler.removeMessages(0);
        mDismissHandler.sendEmptyMessageDelayed(0, 500);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ViewGroup.LayoutParams layoutParams = CustomMediaController.this.getLayoutParams();
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        //处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d("MediaController", "dispatchKeyEvent");
        return true;
    }


    public void setFileName(String fileName) {
        this.fileName.setText(fileName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mediacontroller_back:
                onBack();
                break;
            case R.id.mediacontroller_scale:
                // TODO: 2017/5/22 竖屏状态暂时不支持，日后再添加这个功能
                //onScale();
                break;
            case R.id.mediacontroller_send:
                if (PreferencesUtil.getSerializable(context, "user") != null) {
                    View rootView = LayoutInflater.from(activity).inflate(R.layout.activity_main, null);
                    onSend(rootView);
                } else {
                    ToastUtils.showShort(context, getContext().getString(R.string.login_first));
                }
                break;
            case R.id.mediacontroller_danmu:
                openCloseDanmaku();
                break;
            case R.id.mediacontroller_collect:
                if (user != null || video != null) {
                    addCollect();
                }
                break;
            case R.id.mediacontroller_plots:
                showPlots();
                break;
            case R.id.mediacontroller_like:
                addLike();
                break;
            case R.id.mediacontroller_choose:
                showChooseIndex();
                break;
        }
    }

    private void showChooseIndex() {
        if (teleplayList != null) {
            chooseIndexWindow.showAtLocation(mVideoView, Gravity.CENTER, 0, 0);
        } else {
            ToastUtils.showShort(context, getContext().getString(R.string.not_teleplay));
        }
    }

    private void showPlots() {
        if (plotsList.size() == 0) {
            plotsList.add(new Plots(getContext().getString(R.string.no_plots)));
        }
        plotsAdapter.replaceList(plotsList);
        plotsWindow.showAtLocation(activity.findViewById(R.id.video_center_layout), Gravity.CENTER, 0, 0);

    }

    private void addComment(String comments) {
        if (user == null || video == null) {
            ToastUtils.showShort(context, context.getResources().getString(R.string.login_first));
            return;
        }

        ApiService apiService = new ApiService();
        Observable<HttpResult> observable = apiService.getService(NetworkApi.class).addComment(video.getVideoid(), user.getNickname(), comments);
        HttpUtil.getInstance().toSubscribe(observable, new ProgressSubscriber(context) {
            @Override
            protected void _onNext(Object o) {

            }

            @Override
            protected void _onError(String message) {
                LogUtils.e(TAG, "addComment", message);
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject1, false, false, false);
    }

    private void addLike() {
        if (user == null || video == null) {
            ToastUtils.showShort(context, context.getResources().getString(R.string.login_first));
            return;
        }
        ApiService apiService = new ApiService();
        Observable<HttpResult> observable = apiService.getService(NetworkApi.class).addLike(user.getUserid(), video.getVideoid());
        HttpUtil.getInstance().toSubscribe(observable, new ProgressSubscriber(context) {
            @Override
            protected void _onNext(Object o) {
                ToastUtils.showShort(context, getContext().getString(R.string.like_success));
            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShort(context, getContext().getString(R.string.have_been_like));
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject1, false, false, false);
    }

    private void addCollect() {
        ApiService apiService = new ApiService();
        Observable<HttpResult> observable = apiService.getService(NetworkApi.class).addCollection(user.getUserid(), video.getVideoid());
        HttpUtil.getInstance().toSubscribe(observable, new ProgressSubscriber(context) {
            @Override
            protected void _onNext(Object o) {
                ToastUtils.showShort(context, getContext().getString(R.string.add_collection_success));

            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShort(context, getContext().getString(R.string.video_hanve_been_collected));
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject1, false, false, false);


    }

    private void getPlots() {
        ApiService apiService = new ApiService();
        Observable<HttpResult<List<Plots>>> plots = apiService.getService(NetworkApi.class).getPlots(video.getVideoid());
        HttpUtil.getInstance().toSubscribe(plots, new ProgressSubscriber<List<Plots>>(context) {

            @Override
            protected void _onNext(List<Plots> plotses) {
                CustomMediaController.this.plotsList = plotses;
            }

            @Override
            protected void _onError(String message) {
                LogUtils.e(TAG, "onError", message);
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleEventPublishSubject1, false, false, false);
    }


    private void sendMyDanmaku(String string) {
        addComment(string);
        addDanmaku(string, true);
    }

    private void initDanmaWindow() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.send_pop, null);
        final EditText editText = (EditText) contentView.findViewById(R.id.edit_danmu);
        Button button = (Button) contentView.findViewById(R.id.pop_send);
        danmaWindow = new PopupWindow(contentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        danmaWindow.setBackgroundDrawable(new BitmapDrawable());

        danmaWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mVideoView.start();
                danmakuView.show();
                if (danmakuView.isPaused()) {
                    danmakuView.start();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = editText.getText().toString();
                editText.setText("");
                sendMyDanmaku(string);
                danmaWindow.dismiss();
            }
        });

    }

    private void initChooseWindow() {
        if (teleplayList == null) {
            return;
        }
        View popRootView = LayoutInflater.from(context).inflate(R.layout.pop_choose, null);
        RecyclerView popChooseRecycler = (RecyclerView) popRootView.findViewById(R.id.pop_choose_recycler);
        popChooseRecycler.setLayoutManager(new GridLayoutManager(context, 6, LinearLayoutManager.VERTICAL, false));
        chooseIndexAdapter = new ChooseIndexAdapter(context, teleplayList, popChooseRecycler, mVideoView, (VideoActivity) activity);
        popChooseRecycler.setAdapter(chooseIndexAdapter);
        chooseIndexWindow = new PopupWindow(popRootView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        chooseIndexWindow.setBackgroundDrawable(new BitmapDrawable());
        chooseIndexWindow.setOutsideTouchable(true);
    }

    private void initPlotsWindow() {
        View popRootView = LayoutInflater.from(context).inflate(R.layout.pop_plots, null);
        List<Plots> list = new ArrayList<>();
        RecyclerView popPlotsRecycler = (RecyclerView) popRootView.findViewById(R.id.pop_plots_recycler);
        popPlotsRecycler.addItemDecoration(new MyDecoration(context, MyDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        popPlotsRecycler.setLayoutManager(linearLayoutManager);
        plotsAdapter = new PlotsAdapter(context, list, mVideoView, popPlotsRecycler);
        popPlotsRecycler.setAdapter(plotsAdapter);
        plotsWindow = new PopupWindow(popRootView);
        plotsWindow.setBackgroundDrawable(new BitmapDrawable());
        plotsWindow.setOutsideTouchable(true);
        plotsWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        plotsWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void onSend(View view) {
        mVideoView.pause();
        danmakuView.hide();
        danmaWindow.showAtLocation(view, Gravity.TOP, 0, 0);
    }

    private void openCloseDanmaku() {
        if (isShow) {
            danmakuView.hide();
            isShow = false;
            danmu.setTextColor(getResources().getColor(R.color.white));
        } else {
            isShow = true;
            danmakuView.show();
            danmu.setTextColor(getResources().getColor(R.color.colorPrimary));

        }
    }


    /**
     * 返回键事件
     */
    private void onBack() {
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 全屏事件
     */
    private void onScale() {
        if (activity != null) {
            switch (activity.getResources().getConfiguration().orientation) {
                case Configuration.ORIENTATION_LANDSCAPE://横屏
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    break;
                case Configuration.ORIENTATION_PORTRAIT: //竖屏
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
            }
        }
    }


    private void initDanmaku() {
        danmakuView.enableDanmakuDrawingCache(true);
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                danmakuView.start();

            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });

        danmakuContext = DanmakuContext.create();
        danmakuContext.preventOverlapping(overlappingEnablePair);

    }

    private void addDanmaku(String content, boolean withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = AppUtils.spToPx(context, 20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }


    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        danmakuView.prepare(createParser(inputStream), danmakuContext);

    }

    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_ACFUN);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        SimpleDanmakuParser parser = new SimpleDanmakuParser();

        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    public VideoView getmVideoView() {
        return mVideoView;
    }

    public void setmVideoView(VideoView mVideoView) {
        this.mVideoView = mVideoView;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public boolean isShowDanmaku() {
        return showDanmaku;
    }

    public void setShowDanmaku(boolean showDanmaku) {
        this.showDanmaku = showDanmaku;
    }

    public DanmakuView getDanmakuView() {
        return danmakuView;
    }

    public void setDanmakuView(DanmakuView danmakuView) {
        this.danmakuView = danmakuView;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //手势结束且单击结束时控制器隐藏/显示
            toggleMediaControlVisibility();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float oldX = e1.getX();
            float oldY = e1.getY();
            int y = (int) e2.getRawY();
            int x = (int) e2.getRawX();
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int windowWidth = point.x;
            int windowHeight = point.y;
            if (oldX > windowWidth * 3.0 / 4.0) { //右1/4
                onVolumeSlide((oldY - y) / windowHeight);
            } else if (oldX < windowWidth * 1.0 / 4.0) {
                onBrightnessSlide((oldY - y) / windowHeight);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            playOrPause();
            return true;
        }

        private void onVolumeSlide(float percent) {
            if (mVolume == -1) {
                mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if (mVolume < 0) {
                    mVolume = 0;
                }
                view_volume.setVisibility(View.VISIBLE);
            }

            int index = (int) (percent * mMaxVolume) + mVolume;
            if (index > mMaxVolume) {
                index = mMaxVolume;
            } else if (index < 0) {
                index = 0;
            }

            //TODO 音量进度条显示
            volumePercent.setText((int) (index * 1.0 / mMaxVolume * 100) + "%");
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
        }

        private void onBrightnessSlide(float percent) {
            if (mBrightness < 0) {
                mBrightness = activity.getWindow().getAttributes().screenBrightness;

                if (mBrightness <= 0.00f) {
                    mBrightness = 0.50f;
                }
                if (mBrightness < 0.01f) {
                    mBrightness = 0.01f;
                }
            }

            view_brightness.setVisibility(View.VISIBLE);
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            attributes.screenBrightness = mBrightness + percent;
            if (attributes.screenBrightness > 1.0f) {
                attributes.screenBrightness = 1.0f;
            } else if (attributes.screenBrightness < 0.01f) {
                attributes.screenBrightness = 0.01f;
            }
            activity.getWindow().setAttributes(attributes);

            //TODO 显示亮度进度条
            brightnessPercent.setText((int) (attributes.screenBrightness / 1.0f * 100) + "%");
        }


        /**
         * 隐藏或显示控制器
         */
        private void toggleMediaControlVisibility() {
            if (isShowing()) {
                hide();
                view_brightness.setVisibility(View.GONE);
                view_volume.setVisibility(View.GONE);
            } else {
                show();
                view_brightness.setVisibility(View.GONE);
                view_volume.setVisibility(View.GONE);
            }
        }

        /**
         * 播放/暂停
         */
        private void playOrPause() {
            if (mVideoView != null) {
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    if (danmakuView != null && danmakuView.isPrepared()) {
                        danmakuView.pause();
                    }
                } else {
                    mVideoView.start();

                }
            }
        }
    }
}
