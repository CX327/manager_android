package org.com.manager.frame;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.com.manager.R;
import org.com.manager.database.ManagerDBHelper;
import org.com.manager.response.ApiHttpClient;

/**
 * Created by jie.hua on 2016/3/26.
 * 单例
 */
public class ManagerApplication extends Application {
    private static ManagerApplication managerApplication;
    private ManagerDBHelper managerDBHelper;
    private DisplayImageOptions options;
    private ApiHttpClient apiHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        managerApplication = this;
    }

    public static ManagerApplication getInstance() {
        return managerApplication;
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
