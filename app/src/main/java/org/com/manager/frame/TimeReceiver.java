package org.com.manager.frame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.com.manager.frame.MyRemindActivity;

/**
 * Created by jie.hua on 2016/3/25.
 */
public class TimeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, MyRemindActivity.class);
        context.startActivity(intent);
    }
}
