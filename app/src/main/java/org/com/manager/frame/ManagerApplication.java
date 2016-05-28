package org.com.manager.frame;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.com.manager.R;
import org.com.manager.database.ManagerDBHelper;
import org.com.manager.response.ApiHttpClient;
import org.com.manager.util.FrameUtils;

/**
 * Created by jie.hua on 2016/3/26.
 * 单例
 */
public class ManagerApplication extends Application {
    private static ManagerApplication managerApplication;
    private static ManagerDBHelper managerDBHelper;
    private static DisplayImageOptions options;
    private static ApiHttpClient apiHttpClient;
    private static String HOME_URI;
    private static int userId = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        managerApplication = this;
    }

    public static ManagerApplication getInstance() {
        return managerApplication;
    }

    public int getUserId() {
        if (userId == -1) {
            SharedPreferences sp = getInstance().getSharedPreferences(
                    FrameUtils.SP_USER_INFO, Context.MODE_PRIVATE);
            userId = sp.getInt(FrameUtils.SP_USER_ID, -1);
        }
        return userId;
    }

    public ManagerDBHelper getManagerDBHelper() {
        if (managerDBHelper == null) {
            managerDBHelper = new ManagerDBHelper(this);
        }
        return managerDBHelper;
    }

    public ApiHttpClient getApiHttpClient() {
        if (apiHttpClient == null) {
            apiHttpClient = new ApiHttpClient();
        }
        return apiHttpClient;
    }

    public String getHomeUri() {
        if (HOME_URI == null || HOME_URI.isEmpty()) {
            SharedPreferences sp = getInstance().getSharedPreferences(
                    FrameUtils.SP_HOME_URI, Context.MODE_PRIVATE);
            HOME_URI = sp.getString(FrameUtils.SP_IP, "");
        }
        return HOME_URI;
    }

    /**
     * 初始化DisplayImageOptions
     */
    public DisplayImageOptions getImageDisplayOption() {
        if (options == null) {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.recipes_default)
                            // 设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.recipes_default)
                            // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.mipmap.recipes_default)
                            // 设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(true).cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }
        return options;
    }

}
