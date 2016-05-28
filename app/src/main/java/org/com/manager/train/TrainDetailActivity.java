package org.com.manager.train;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.dao.Dao;

import org.com.manager.R;
import org.com.manager.bean.ApiErrorCodeEnum;
import org.com.manager.bean.NumberDetailModel;
import org.com.manager.bean.StationListModel;
import org.com.manager.bean.TrainInfoModel;
import org.com.manager.database.TrainCollectionTable;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.frame.TimeReceiver;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.AlarmManagerUtil;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.StationDetailAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrainDetailActivity extends Activity {
    @Bind(R.id.detail_title)
    TextView detailTitle;

    @Bind(R.id.detail_list)
    ListView detailList;

    @Bind(R.id.train_collection)
    TextView collectionTv;

    @Bind(R.id.train_collection_remind_check)
    CheckBox collectionCheck;

    @Bind(R.id.detail_collection_layout)
    LinearLayout detail_collectionLayout;

    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;

    /**
     * 站点列表
     */
    private List<StationListModel> stationListModels;
    /**
     * 列车信息
     */
    private TrainInfoModel trainInfoModel;

    private Dao<TrainCollectionTable, String> trainCollectionDao = null;
    /**
     * 是否已收藏
     */
    private boolean isCollection = false;
    private String trainNumber;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        isCollection = getIntent().getBooleanExtra(FrameUtils.IT_TRAIN_ISCOLLECTION, false);
        trainNumber = getIntent().getStringExtra(FrameUtils.IT_TRAIN_DETAIL);
        detailTitle.setText(trainNumber + "次时刻表");
        if (isCollection) {
            detail_collectionLayout.setVisibility(View.GONE);
        } else {
            detail_collectionLayout.setVisibility(View.VISIBLE);
        }
        numberQueryNet();
    }

    /**
     * 访问后端
     */
    private void numberQueryNet() {
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        ManagerApplication.getInstance().getApiHttpClient()
                .numberQueryNet(ManagerApplication.getInstance().getUserId(),trainNumber, new AsyncApiResponseHandler(TrainDetailActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        if (response != null) {
                            try {
                                JSONObject jsonObject = response.getJSONObject("data");
                                NumberDetailModel numberDetail = JSON.parseObject(jsonObject.toString(),
                                        NumberDetailModel.class);
                                showData(numberDetail);
                            } catch (JSONException e) {
                                showData(null);
                            }
                        }
                    }
                });
    }

    private void showData(NumberDetailModel numberDetail) {
        if (numberDetail != null) {
            noDataLayout.setVisibility(View.GONE);
            detailList.setVisibility(View.VISIBLE);
            this.stationListModels = numberDetail.getStation_list();
            this.trainInfoModel = numberDetail.getTrain_info();
            detailList.setAdapter(new StationDetailAdapter(TrainDetailActivity.this, stationListModels));
        } else {
            detailList.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 收藏（监听器）
     */
    @OnClick(R.id.train_collection)
    public void trainColletcion() {
        boolean isChecked = collectionCheck.isChecked();
        int lastHour = -1;
        int addDay = 0;
        int i = 0;
        long timeAlarmLong = 0;
        if (isChecked) {
            for (StationListModel stationListModel : this.stationListModels) {
                String arrivedTime = stationListModel.getArrived_time();
                if (!arrivedTime.equals("-")) {
                    Calendar timeSet = Calendar.getInstance();
                    int year = timeSet.get(Calendar.YEAR);//获取年份
                    int month = timeSet.get(Calendar.MONTH);//获取月份
                    int day = timeSet.get(Calendar.DAY_OF_MONTH);//获取日
                    int hour = timeSet.get(Calendar.HOUR_OF_DAY);//获取小时
                    int minute = timeSet.get(Calendar.MINUTE);//获取分钟

                    int nowTime = day * 10000 + hour * 100 + minute;

                    String hourStr = arrivedTime.substring(0, 2);
                    String minuteStr = arrivedTime.substring(3, 5);
                    int remindHour = Integer.parseInt(hourStr);
                    int remindMinute = Integer.parseInt(minuteStr);
                    if (remindHour < lastHour) {
                        //跨天
                        addDay++;
                    }
                    timeSet.add(Calendar.DAY_OF_MONTH, addDay);
                    int remindDay = timeSet.get(Calendar.DAY_OF_MONTH);//获取日
                    int remindTime = remindDay * 10000 + remindHour * 100 + remindMinute;
                    /**超过此刻*/
                    if (remindTime > nowTime) {
                        timeSet.set(year, month, remindDay, remindHour, remindMinute, 00);
                        long time = timeSet.getTimeInMillis();
                        String title = stationListModel.getStation_name() + "站到了";
                        String content = "本站将停留" + stationListModel.getStay() + "分钟";
                        Random random = new Random();
                        int requestCode = random.nextInt(999999) + i;
                        /*  //获得提醒的最后时间
                        timeAlarmLong = month * 1000000 + remindDay * 10000 + remindHour * 100 + remindMinute;
                      String timeAlarm= (year + "-") + ((month + 1) + "-") + (remindDay + " ")
                                + (remindHour + ":") + (remindMinute + "");
                          AlarmManagerUtil.saveAlarmDB(title,content,requestCode,timeAlarm);*/
                        saveRemind(true, title, content, requestCode, time);
                        i++;
                    }
                    lastHour = remindHour;
                }
            }
            Toast.makeText(TrainDetailActivity.this, "已添加行程提醒", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(TrainDetailActivity.this, "已添加行程", Toast.LENGTH_SHORT).show();
        }
        saveMyTripDB();
    }

    /**
     * 存储在本地数据库
     */
    private void saveMyTripDB() {

        TrainCollectionTable trainCollectionTable = new TrainCollectionTable(
                this.trainInfoModel.getName(),
                "", this.trainInfoModel.getStart(), this.trainInfoModel.getEnd(),
                this.trainInfoModel.getStarttime(), this.trainInfoModel.getEndtime(),
                this.trainInfoModel.getMileage());

        try {
            trainCollectionDao = ManagerApplication.getInstance()
                    .getManagerDBHelper().getDao(TrainCollectionTable.class);
            trainCollectionDao.createOrUpdate(trainCollectionTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存提醒
     */
    private void saveRemind(boolean b, String title, String content, int requestCode, long timeSet) {
        Intent intent = new Intent(this, TimeReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putString(FrameUtils.IT_NOTE_TITLE, title);
        bundle.putString(FrameUtils.IT_NOTE_CONTENT, content);
        intent.putExtras(bundle);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        // create a PendingIntent that will perform a broadcast
        PendingIntent pi = PendingIntent.getBroadcast(TrainDetailActivity.this, requestCode,
                intent, 0);
        if (b) {
            am.set(AlarmManager.RTC_WAKEUP, timeSet, pi);
        } else {
            // cancel current alarm
            am.cancel(pi);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            this.overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
