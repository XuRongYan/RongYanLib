package com.rongyan.aikanvideo.advertisement;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.linxiao.commondevlib.util.PreferencesUtil;
import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.video.VideoActivity;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.User;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ActivityLifeCycleEvent;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ApiService;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpResult;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.HttpUtil;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.NetworkApi;
import com.rongyan.rongyanlibrary.rxHttpHelper.http.ProgressSubscriber;
import com.rongyan.rongyanlibrary.util.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;
import rx.Observable;

public class AdvertisementActivity extends BaseActivity {
    private static final String TAG = "LifeStyleActivityB";
    @Bind(R.id.adv_videoview)
    VideoView videoView;
    @Bind(R.id.adv_btn_skip)
    Button btnSkip;
    @Bind(R.id.adv_view)
    View view;
    private Video video;
    private User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Vitamio.isInitialized(getApplicationContext());
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        video = (Video) getIntent().getExtras().get("video");
        user = PreferencesUtil.getSerializable(this, "user");
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.stopPlayback();
                videoView.setVisibility(View.GONE);
                Bundle bundle = new Bundle();
                bundle.putSerializable("video", video);
                bundle.putParcelableArrayList("teleplay", getIntent().getExtras().getParcelableArrayList("teleplay"));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        if (user == null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("video", video);
            goActivity(VideoActivity.class, bundle);
            finish();
        } else {
            getAdv();
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
    protected int getContentView() {
        return R.layout.activity_advertisement;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        videoView.resume();
    }

    private void skip() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_questionnair_survey, null);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.survey_dialog_radio_group);
        RadioButton rbTooLong = (RadioButton) view.findViewById(R.id.survey_dialog_radio_adv_too_long);
        RadioButton rbTooBoring = (RadioButton) view.findViewById(R.id.survey_dialog_radio_adv_too_boring);
        RadioButton rbNotAccept = (RadioButton) view.findViewById(R.id.survey_dialog_radio_adv_not_accept_recommend);
        radioGroup.check(R.id.survey_dialog_radio_adv_too_long);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        videoView.stopPlayback();
                        videoView.setVisibility(View.GONE);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("video", video);
                        bundle.putParcelableArrayList("teleplay", getIntent().getExtras().getParcelableArrayList("teleplay"));
                        Intent intent = new Intent();
                        intent.putExtras(bundle);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }).create();

        dialog.show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @OnClick(R.id.adv_btn_skip)
    public void click(View view) {
        skip();
    }

    private void getAdv() {
        ApiService apiService = new ApiService();
        Observable<HttpResult<Video>> adv = apiService.getService(NetworkApi.class).getAdv(video.getVideoid(), user.getUserid());
        HttpUtil.getInstance().toSubscribe(adv, new ProgressSubscriber<Video>(this) {

            @Override
            protected void _onNext(final Video video) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(video.getDescription()));
                        startActivity(intent);
                    }
                });
                videoView.stopPlayback();
                videoView.setVideoURI(Uri.parse(video.getVideoURL()));
                videoView.start();
            }

            @Override
            protected void _onError(String message) {
                ToastUtils.showShort(AdvertisementActivity.this, message);
                Bundle bundle = new Bundle();
                bundle.putSerializable("video", video);
                goActivity(VideoActivity.class, bundle);
                finish();
            }

            @Override
            protected void _onCompleted() {

            }
        }, null, ActivityLifeCycleEvent.PAUSE, lifeCycleSubject, false, false, false);
    }
}
