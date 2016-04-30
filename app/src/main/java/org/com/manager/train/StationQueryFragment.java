package org.com.manager.train;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.com.manager.R;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.WheelPicker.DatePicker;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 站站查询页
 */
public class StationQueryFragment extends Fragment {
    @Bind(R.id.train_start_station)
    TextView startStation;

    @Bind(R.id.train_end_station)
    TextView endStation;

    @Bind(R.id.station_query_departure_time)
    TextView departureTime;

    @Bind(R.id.train_station_query)
    TextView stationQuery;
    /**
     * wheel
     */
    private TextView addConsumeWheelTimeTv;
    private DatePicker addDatePicker;
    private TextView addTimeOk;
    private TextView addTimeCancel;
    private PopupWindow popupWindow;
    /**
     * 出发日期
     */
    private String departureDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_station_query, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }
    /**
     * 初始化出发日期
     */
    private void init() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        departureDate = sDateFormat.format(new java.util.Date());
        departureTime.setText(departureDate);
    }
    /**
     * 跳转到结果页（监听器）
     */
    @OnClick(R.id.train_station_query)
    public void goSearchResult() {
        Intent intent = new Intent();
        intent.putExtra(FrameUtils.IT_TRAIN_STATION_START, startStation.getText().toString());
        intent.putExtra(FrameUtils.IT_TRAIN_STATION_END, endStation.getText().toString());
        intent.putExtra(FrameUtils.IT_TRAIN_STATION_DEPARTURE_TIME, departureTime.getText().toString());
        intent.setClass(getActivity(), StationSearchResultActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }
    /**
     * 跳转到站点搜索页（监听器）
     */
    @OnClick(R.id.train_start_station)
    public void getSatrtStation() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), StationSearchActivity.class);
        startActivityForResult(intent, 0);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }
    /**
     * 跳转到站点搜索页（监听器）
     */
    @OnClick(R.id.train_end_station)
    public void getEndStation() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), StationSearchActivity.class);
        startActivityForResult(intent, 1);
        getActivity().overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

    }
    /**
     * wheel
     */
    @OnClick(R.id.station_query_departure_time)
    public void getDepartureTime() {

        View view = View.inflate(getActivity(), R.layout.accounting_dialog_select_time, null);

        addConsumeWheelTimeTv = (TextView) view.findViewById(R.id.add_consume_datepicker_date);
        addDatePicker = (DatePicker) view.findViewById(R.id.add_consume_date_picker);
        addTimeOk = (TextView) view.findViewById(R.id.add_consume_date_cancel_ok);
        addTimeCancel = (TextView) view.findViewById(R.id.add_consume_date_cancel);

        //设置滑动改变监听器
        addDatePicker.setOnChangeListener(dpOnChangeListener);

        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 点击pop以外区域可以去除pop
        popupWindow.setOutsideTouchable(true);

        //出现在布局中心
        popupWindow.showAtLocation(departureTime, Gravity.CENTER, 0, 0);
        //点击确定
        addTimeOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                departureTime.setText(departureDate);
                popupWindow.dismiss();
            }
        });
        //点击取消
        addTimeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });
    }

    //listeners
    DatePicker.OnChangeListener dpOnChangeListener = new DatePicker.OnChangeListener() {
        @Override
        public void onChange(int year, int month, int day, int day_of_week) {
            departureDate = String.format("%04d", year)
                    + "-" + String.format("%02d", month)
                    + "-" + String.format("%02d", day);
            addConsumeWheelTimeTv.setText(departureDate);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String station = data.getStringExtra(FrameUtils.IT_TRAIN_STATION);
            if (requestCode == 0) {
                startStation.setText(station);
            } else if (requestCode == 1) {
                endStation.setText(station);
            }
        }
    }
}
