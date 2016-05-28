package org.com.manager.accounting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.j256.ormlite.dao.Dao;

import org.com.manager.R;
import org.com.manager.bean.ConsumeModel;
import org.com.manager.bean.ConsumeTypeEnum;
import org.com.manager.database.ConsumeTable;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.WheelPicker.DatePicker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConsumeEditActivity extends Activity {
    @Bind(R.id.add_consume_edit_layout)
    LinearLayout addConsumeEditLayout;

    @Bind(R.id.add_consume_edit_commit)
    ImageView addConsumeEditCommit;

    @Bind(R.id.add_consume_rddiogroup)
    RadioGroup addConsumeRddiogroup;

    @Bind(R.id.add_consume_radiobutton_pay)
    RadioButton addCheckboxPay;

    @Bind(R.id.add_consume_radiobutton_income)
    RadioButton addCheckboxIncome;

    @Bind(R.id.add_consume_type)
    TextView addConsumeTypeTv;

    @Bind(R.id.add_consume_type_img)
    ImageView addConsumeTypeImag;

    @Bind(R.id.add_consume_grid)
    GridView addConsumeGrid;

    @Bind(R.id.add_consume_money)
    EditText addConsumeMoney;

    @Bind(R.id.add_consume_remarks)
    EditText addConsumeRemarks;

    @Bind(R.id.add_consume_date)
    TextView addConsumeDate;
    /**
     * wheel
     */
    private TextView addConsumeWheelTimeTv;
    private DatePicker addConsumeDatePicker;
    private TextView addConsumeTimeOk;
    private TextView addConsumeTimeCancel;
    private PopupWindow popupWindow;
    /**
     * 消费类型map
     */
    private HashMap<String, Object> typeHashMap;
    /**
     * 消费类型list
     */
    private ArrayList<HashMap<String, Object>> consumeTypeEnums;
    /**
     * 消费时间
     */
    private String consumeDate;
    /**
     * 本地消费数据库dao
     */
    private Dao<ConsumeTable, String> consumeDao;
    /**
     * 消费是否是支出
     */
    private boolean isPay = true;

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_edit);
        ButterKnife.bind(this);
        initType();
        initTime();
    }

    /**
     * 初始化消费日期
     */
    private void initTime() {
        addConsumeRddiogroup.setOnCheckedChangeListener(new onCheckedChangeListener());
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        consumeDate = sDateFormat.format(new java.util.Date());
        addConsumeDate.setText(consumeDate);
    }

    /**
     * 初始化消费类型
     */
    private void initType() {
        typeHashMap = new HashMap<>();
        typeHashMap.put(FrameUtils.UI_ACCOUNTING_TYPE_ID, ConsumeTypeEnum.YIBAN.getTypeId());
        typeHashMap.put(FrameUtils.UI_ACCOUNTING_TYPE_IMAGE, ConsumeTypeEnum.YIBAN.getTypeImage());
        typeHashMap.put(FrameUtils.UI_ACCOUNTING_TYPE_NAME, ConsumeTypeEnum.YIBAN.getTypeName());
        addConsumeTypeTv.setText(ConsumeTypeEnum.YIBAN.getTypeName());

        Drawable typeImage = getResources().getDrawable(ConsumeTypeEnum.YIBAN.getTypeImage());
        addConsumeTypeImag.setImageDrawable(typeImage);

        addConsumeRemarks.setText(typeHashMap.get(FrameUtils.UI_ACCOUNTING_TYPE_NAME).toString());

        consumeTypeEnums = new ArrayList<>();
        for (ConsumeTypeEnum consumeTypeEnum : ConsumeTypeEnum.values()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(FrameUtils.UI_ACCOUNTING_TYPE_ID, consumeTypeEnum.getTypeId());
            hashMap.put(FrameUtils.UI_ACCOUNTING_TYPE_IMAGE, consumeTypeEnum.getTypeImage());
            hashMap.put(FrameUtils.UI_ACCOUNTING_TYPE_NAME, consumeTypeEnum.getTypeName());
            consumeTypeEnums.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(ConsumeEditActivity.this, consumeTypeEnums,
                R.layout.accounting_type_item,
                new String[]{FrameUtils.UI_ACCOUNTING_TYPE_IMAGE, FrameUtils.UI_ACCOUNTING_TYPE_NAME},
                new int[]{R.id.consume_type_image, R.id.consume_type_name});
        addConsumeGrid.setAdapter(simpleAdapter);
        addConsumeGrid.setOnItemClickListener(new itemOnClick());
    }

    /**
     * 保存消费（消费）
     */
    @OnClick(R.id.add_consume_edit_commit)
    public void saveEdit() {
        String money = addConsumeMoney.getText().toString();
        String remarks = addConsumeRemarks.getText().toString();
        String date = addConsumeDate.getText().toString();
        int typeId = (Integer) typeHashMap.get(FrameUtils.UI_ACCOUNTING_TYPE_ID);
        if (remarks != null && remarks.length() > 6) {
            Toast.makeText(getApplicationContext(),
                    "备注不能超过6个字",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (money.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "金额不能为空",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        float moneyInt = Float.parseFloat(money);
        if (isPay) {
            moneyInt = -moneyInt;
        }
        consumeAddNet(date, moneyInt, isPay, typeId, remarks);
        /*try {
            consumeDao = ManagerApplication.getInstance().getManagerDBHelper().getDao(ConsumeTable.class);
            consumeDao.createOrUpdate(consumeTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

    }

    private void consumeAddNet(String consumeTime, float consumeMoney,
                               boolean consumeIsPay, int consumeTypeId,
                               String remarks) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        ManagerApplication.getInstance().getApiHttpClient().consumeAddNet(
                ManagerApplication.getInstance().getUserId(), consumeTime,
                consumeMoney, consumeIsPay, consumeTypeId, remarks,
                new AsyncApiResponseHandler(ConsumeEditActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        setResult(RESULT_OK);
                        finish();
                        ConsumeEditActivity.this.overridePendingTransition(
                                android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right);
                    }
                }
        );
    }

    /**
     * 选择时间（监听器）
     */
    @OnClick(R.id.add_consume_date)
    public void chooseConsumeDate() {
           /*得到软键盘*/
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow()
                .getDecorView().getWindowToken(), 0); // 强制隐藏键盘

        View view = View.inflate(ConsumeEditActivity.this, R.layout.accounting_dialog_select_time, null);

        addConsumeWheelTimeTv = (TextView) view.findViewById(R.id.add_consume_datepicker_date);
        addConsumeDatePicker = (DatePicker) view.findViewById(R.id.add_consume_date_picker);
        addConsumeTimeOk = (TextView) view.findViewById(R.id.add_consume_date_cancel_ok);
        addConsumeTimeCancel = (TextView) view.findViewById(R.id.add_consume_date_cancel);

        //设置滑动改变监听器
        addConsumeDatePicker.setOnChangeListener(dpOnChangeListener);

        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 点击pop以外区域可以去除pop
        popupWindow.setOutsideTouchable(true);

        //出现在布局中心
        popupWindow.showAtLocation(addConsumeEditLayout, Gravity.CENTER, 0, 0);
        //点击确定
        addConsumeTimeOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                addConsumeDate.setText(consumeDate);
                popupWindow.dismiss();
            }
        });
        //点击取消
        addConsumeTimeCancel.setOnClickListener(new View.OnClickListener() {
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
            consumeDate = String.format("%04d", year)
                    + "-" + String.format("%02d", month)
                    + "-" + String.format("%02d", day);
            addConsumeWheelTimeTv.setText(consumeDate);
        }
    };

    /**
     * RadioGroup监听器
     */
    class onCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == addCheckboxPay.getId()) {
                isPay = true;
            } else {
                isPay = false;
            }
        }
    }

    /**
     * gridView 监听器
     */
    class itemOnClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            typeHashMap = new HashMap<>();
            typeHashMap = consumeTypeEnums.get(position);
            addConsumeTypeTv.setText((String) typeHashMap.get(FrameUtils.UI_ACCOUNTING_TYPE_NAME));

            int iconId = (Integer) typeHashMap.get(FrameUtils.UI_ACCOUNTING_TYPE_IMAGE);
            Drawable typeImage = getResources().getDrawable(iconId);
            addConsumeTypeImag.setImageDrawable(typeImage);

            addConsumeRemarks.setText(typeHashMap.get(FrameUtils.UI_ACCOUNTING_TYPE_NAME).toString());
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(RESULT_OK);
            finish();
            ConsumeEditActivity.this.overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
