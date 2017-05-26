package com.rongyan.aikanvideo.video;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.linxiao.commondevlib.util.PreferencesUtil;
import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.adapter.PopGuessULikeAdapter;
import com.rongyan.aikanvideo.widget.vitamioView.CustomMediaController;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Comment;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Danma;
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
import com.rongyan.rongyanlibrary.util.TimeUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;
import master.flame.danmaku.danmaku.model.Danmaku;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.ui.widget.DanmakuView;
import rx.Observable;

public class VideoActivity extends BaseActivity implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {
    VideoView advVideo;
    Button skip;
    FrameLayout frameAdv;
    private static final String TAG_LOG = "VideoActivity";
    private static final int PER_PAGE = 50;
    private int page = 1;
    private String path = "";
    private Uri uri;
    private ProgressBar pb;
    private TextView downloadRateView, loadRateView;
    private boolean showDanmaku;
    public DanmakuView danmakuView;
    private DanmakuContext danmakuContext;
    private CustomMediaController mCustomMediaController;
    private VideoView mVideoView;
    private Video video;
    private GestureDetector mGestureDetector;
    private PopupWindow popGuessULike;
    private List<Video> list = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();
    private List<Video> recommendList = new ArrayList<>();
    private Random random = new Random();
    private boolean isFirst = true;
    private User user;
    private PopGuessULikeAdapter guessULikeAdapter;
    private ArrayList<Video> teleplayList;
    //private List<Plots> plotsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.d(TAG_LOG, "onCreate", "执行了");
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(getApplicationContext());
        setContentView(R.layout.activity_video);
        mGestureDetector = new GestureDetector(this, new MyGestureListener());
        page = 1;
        user = PreferencesUtil.getSerializable(this, "user");

        initLandView();
        mVideoView.setZOrderOnTop(true);
        mVideoView.setZOrderMediaOverlay(true);
        video = (Video) getIntent().getExtras().get("video");
        teleplayList =  getIntent().getExtras().getParcelableArrayList("teleplay");
        assert video != null;
        uri = Uri.parse(video.getVideoURL());
        mCustomMediaController = new CustomMediaController(this, mVideoView, this, danmakuView, lifeCycleSubject, user, video, teleplayList, showDanmaku);
        getVideoComment();
        initGuessUlike();
        getRecommend();
        mVideoView.setVideoURI(uri);
        LogUtils.d(TAG_LOG, "mVideoView is in layout?", mVideoView.isInLayout() + "");
        danmakuView.bringToFront();
        mVideoView.setMediaController(mCustomMediaController);
        mVideoView.requestFocus();
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnBufferingUpdateListener(this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
            }
        });
        if (savedInstanceState != null) {
            mVideoView.seekTo(savedInstanceState.getLong("position"));
        }

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mVideoView.seekTo(0);
                mVideoView.pause();
            }
        });

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                AlertDialog dialog = new AlertDialog.Builder(VideoActivity.this)
                        .setMessage("Error:视频播放出错")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).create();
                dialog.show();
                return true;
            }
        });
        if (user != null) {
            addHistory();
        }
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void initGuessUlike() {
        View popView = LayoutInflater.from(this).inflate(R.layout.pop_guess_u_like, null);
        RecyclerView popRecycler = (RecyclerView) popView.findViewById(R.id.pop_guess_u_like_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        popRecycler.setLayoutManager(linearLayoutManager);
        List<Video> list = new ArrayList<>();
        guessULikeAdapter = new PopGuessULikeAdapter(this, list, mVideoView);
        popRecycler.setAdapter(guessULikeAdapter);
        popGuessULike = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popGuessULike.setBackgroundDrawable(new BitmapDrawable());
        popGuessULike.setOutsideTouchable(true);
        popGuessULike.setTouchable(true);
    }



    private void initLandView() {
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        pb = (ProgressBar) findViewById(R.id.probar);
        downloadRateView = (TextView) findViewById(R.id.download_rate);
        loadRateView = (TextView) findViewById(R.id.load_rate);
        danmakuView = (DanmakuView) findViewById(R.id.danmuku_view);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("position", mVideoView.getCurrentPosition());
    }

    @Override
    protected void onPause() {
        LogUtils.d(TAG_LOG, "onPause", "执行了");
        super.onPause();
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showDanmaku = false;
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
        LogUtils.d(TAG_LOG, "onDestroy", "执行了");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_video;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                danmakuView.pause();
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    pb.setVisibility(View.VISIBLE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                //mVideoView.seekTo(mVideoView.getCurrentPosition());
                danmakuView.start();
                pb.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRateView.setText(percent + "%");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //屏幕切换时，设置全屏
        if (mVideoView != null) {
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);
    }

    public void getVideoComment() {
        ApiService apiService = new ApiService();
        Observable<HttpResult<List<Comment>>> videoCommentById = apiService.getService(NetworkApi.class).getVideoCommentById(PER_PAGE, page++, 10572);
        HttpUtil.getInstance().toSubscribe(videoCommentById, new ProgressSubscriber<List<Comment>>(this) {
            @Override
            protected void _onNext(List<Comment> comments) {
                commentList.addAll(comments);
                if (list.size() >= PER_PAGE - 1) {
                    getVideoComment();
                } else {
                    handleList();

                }
            }

            @Override
            protected void _onError(String message) {
                LogUtils.e(TAG_LOG, "getVideoComment", message);
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleSubject, false, false, false);
    }

    private void skip() {
        //advVideo.stopPlayback();
        mVideoView.setZOrderOnTop(true);
        danmakuView.bringToFront();
        frameAdv.setVisibility(View.GONE);
    }

    private void addHistory() {
        ApiService apiService = new ApiService();
        Observable<HttpResult> observable = apiService.getService(NetworkApi.class).addHistory(user.getUserid(), video.getVideoid(), video.getTitle(), video.getTitlenew(), 0, video.getTimelength(), video.getTimelength());
        HttpUtil.getInstance().toSubscribe(observable, new ProgressSubscriber(this) {
            @Override
            protected void _onNext(Object o) {

            }

            @Override
            protected void _onError(String message) {
                LogUtils.e(TAG_LOG, "addHistory", message);
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleSubject, false, false, false);
    }

    /**
     * 将评论转换为弹幕
     */
    private void handleList() {
        LogUtils.d(TAG_LOG, "handleList", "执行了");
        List<Danma> danmaList = new ArrayList<>();
        LogUtils.d(TAG_LOG, "handleList", "执行了");
        long totalLenth = video.getTimelength();
        LogUtils.d(TAG_LOG, "handleList", "执行了");
        for (Comment comment :
                commentList) {
            LogUtils.d(TAG_LOG, "handleList", "执行了");
            String time = comment.getComdatetime().replace('T', ' ');
            try {
                String c = Math.abs(random.nextLong()) % totalLenth + "," +
                        Color.WHITE + "," +
                        Danmaku.TYPE_SCROLL_RL + "," +
                        AppUtils.spToPx(this, 20) + "," +
                        "1," +
                        TimeUtils.getTime(time, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
                String m = comment.getComments();
                danmaList.add(new Danma(c, m));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        LogUtils.d(TAG_LOG, "handleList", "执行了");
        Gson gson = new Gson();
        String s = gson.toJson(danmaList);
        InputStream inputStream;
        inputStream = new ByteArrayInputStream(s.getBytes());
        mCustomMediaController.setInputStream(inputStream);
    }

    private void getRecommend() {
        ApiService apiService = new ApiService();
        final Observable<HttpResult<List<Video>>> recommend = apiService.getService(NetworkApi.class).getRecommend(video.getVideoid(), 4);
        HttpUtil.getInstance().toSubscribe(recommend, new ProgressSubscriber<List<Video>>(mContext) {

            @Override
            protected void _onNext(List<Video> list) {
                guessULikeAdapter.replaceList(list);
            }

            @Override
            protected void _onError(String message) {
                LogUtils.e(TAG_LOG, "getRecommend", message);
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleSubject, false, false, false);
    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float oldX = e1.getX();
            float oldY = e1.getY();
            int y = (int) e2.getRawY();
            int x = (int) e2.getRawX();
            Display display = VideoActivity.this.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int windowWidth = point.x;
            int windowHeight = point.y;
            if (distanceY > 0) {
                popGuessULike.showAtLocation(mVideoView, Gravity.CENTER, 0, 0);
            }
//            } else {
//                if (popGuessULike.isShowing()) {
//                    popGuessULike.dismiss();
//                }
//            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }


    }
}
