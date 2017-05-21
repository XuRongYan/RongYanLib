package com.rongyan.aikanvideo.advertisement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rongyan.aikanvideo.R;
import com.rongyan.aikanvideo.video.VideoActivity;
import com.rongyan.rongyanlibrary.base.BaseActivity;
import com.rongyan.rongyanlibrary.rxHttpHelper.entity.Video;

import butterknife.Bind;
import butterknife.OnClick;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class AdvertisementActivity extends BaseActivity {
    private static final String TAG = "LifeStyleActivityB";
    @Bind(R.id.adv_videoview)
    VideoView videoView;
    @Bind(R.id.adv_btn_skip)
    Button btnSkip;
    @Bind(R.id.adv_view)
    View view;
    private Video video;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Vitamio.isInitialized(getApplicationContext());
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        video = (Video) getIntent().getExtras().get("video");
        videoView.setZOrderMediaOverlay(false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri parse = Uri.parse("http://www.baidu.com");
                intent.setData(parse);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_advertisement;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        videoView.setVideoURI(Uri.parse("http://opojgabz0.bkt.clouddn.com/%E7%8E%8B%E7%89%8C%E5%AF%B9%E7%8E%8B%E7%89%8C%20170407_%E6%A0%87%E6%B8%85.mp4"));
        videoView.start();

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
        //mediaPlayer.release();
        videoView.stopPlayback();
        Bundle bundle = new Bundle();
        bundle.putSerializable("video", video);
        goActivity(VideoActivity.class, bundle);
        finish();
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
}
