package org.com.manager.train;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.com.manager.R;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.WheelPicker.DatePicker;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 车次查询页
 */
public class NumberQueryFragment extends Fragment {

    @Bind(R.id.number_query_edit)
    EditText numberQueryEdit;
    @Bind(R.id.number_query_departure_time)
    TextView departureTimeTv;
    @Bind(R.id.train_number_query_tv)
    TextView queryTv;
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

    public NumberQueryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NumberQueryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumberQueryFragment newInstance(String param1, String param2) {
        NumberQueryFragment fragment = new NumberQueryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_number_query, container, false);
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
        departureTimeTv.setText(departureDate);
    }

    /**
     * 跳转到结果页（监听器）
     */
    @OnClick(R.id.train_number_query_tv)
    public void goSearchResult() {
        // 先隐藏键盘
        ((InputMethodManager) numberQueryEdit.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getActivity()
                                .getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        String trainNumber = numberQueryEdit.getText().toString();
        if (trainNumber == null || trainNumber.isEmpty()) {
            Toast.makeText(getActivity(), "请输入车次", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(FrameUtils.IT_TRAIN_DETAIL, numberQueryEdit.getText().toString());
            intent.putExtra(FrameUtils.IT_TRAIN_ISCOLLECTION, false);
            intent.setClass(getActivity(), TrainDetailActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        }
    }

    /**
     * wheel
     */
    @OnClick(R.id.number_query_departure_time)
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
        popupWindow.showAtLocation(departureTimeTv, Gravity.CENTER, 0, 0);
        //点击确定
        addTimeOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                departureTimeTv.setText(departureDate);
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
}
