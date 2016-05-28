package org.com.manager.frame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.com.manager.R;
import org.com.manager.user.LoginActivity;
import org.com.manager.util.FrameUtils;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        init();

    }

    private void init() {
        initImageLoader();
        Handler handler = new Handler();
        final String ip = ManagerApplication.getInstance().getHomeUri();
        final int userId = ManagerApplication.getInstance().getUserId();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                if (ip==null ||ip.isEmpty()){
                    intent.setClass(WelcomeActivity.this, MoreActivity.class);
                } else if (userId == -1) {
                    intent.setClass(WelcomeActivity.this, LoginActivity.class);
                } else {
                    intent.setClass(WelcomeActivity.this, FrameActivity.class);
                }
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
            }
        }, 3000);
    }

    /**
     * 初始化ImageLoaderConfiguration
     */
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                WelcomeActivity.this).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024)
                        // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove
                        // for
                        // release
                        // app
                .build();

        ImageLoader.getInstance().init(config);
    }
}
