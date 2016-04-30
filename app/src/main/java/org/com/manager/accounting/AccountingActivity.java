package org.com.manager.accounting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

import org.com.manager.R;
import org.com.manager.bean.ConsumeTypeEnum;
import org.com.manager.database.ConsumeTable;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.util.FrameUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 记账 main activity
 */
public class AccountingActivity extends Activity {

    @Bind(R.id.accounting_consume_mouth)
    TextView accountingConsumeMouth;

    @Bind(R.id.accounting_consume_expenditure)
    TextView accountingConsumeExpenditure;

    @Bind(R.id.accounting_consume_income)
    TextView accountingConsumeIncome;

    @Bind(R.id.accounting_consume_money_sum)
    TextView accountingConsumeSum;

    @Bind(R.id.accounting_last_mouth_consume)
    ImageView accountingLastMouthConsume;

    @Bind(R.id.accounting_next_mouth_consume)
    ImageView accountingNextMouthConsume;

    @Bind(R.id.accounting_consume_mouth_tv)
    TextView accountingConsumeMouthTv;

    @Bind(R.id.accounting_edit)
    LinearLayout accountingEdit;

    @Bind(R.id.accounting_consume_list)
    ListView accountingConsumeListView;

    /**
     * 消费列表 list
     */
    private ArrayList<HashMap<String, Object>> consumeList;
    /**
     * 消费数据库dao
     */
    private Dao<ConsumeTable, Object> consumeTableDao;
    /**
     * 消费列表所属月份
     */
    private String accountingMonth;
    /**
     * 当月支出
     */
    private float thisMonthPay = 0;
    /**
     * 当月收入
     */
    private float thisMonthIncome = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting);
        ButterKnife.bind(this);

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM");
        accountingMonth = sDateFormat.format(new java.util.Date());
        init(accountingMonth);
    }

    /**
     * 初始化
     */
    private void init(String accountingMonth) {
        initConsumeList(accountingMonth);
        initTv(accountingMonth);
    }

    /**
     * 初始化布局上部分的收入支出信息
     */
    private void initTv(String accountingMonth) {
        accountingConsumeMouth.setText(accountingMonth);
        accountingConsumeMouthTv.setText(accountingMonth);
        accountingConsumeExpenditure.setText(thisMonthPay + "");
        accountingConsumeIncome.setText(thisMonthIncome + "");
        float sum = thisMonthPay + thisMonthIncome;
        accountingConsumeSum.setText(sum + "");
    }

    /**
     * 初始化消费列表
     */
    private void initConsumeList(String accountingMonth) {
        consumeList = new ArrayList<>();
        consumeList = getConsumeListFromDB(accountingMonth);
        SimpleAdapter simpleAdapter = new SimpleAdapter(AccountingActivity.this, consumeList,
                R.layout.accounting_cosume_item,
                new String[]{FrameUtils.IT_ACCOUNTING_TYPE_IMAGE, FrameUtils.IT_ACCOUNTING_TIME,
                        FrameUtils.IT_ACCOUNTING_MONEY, FrameUtils.IT_ACCOUNTING_REMARKS},
                new int[]{R.id.accounting_consume_item_type, R.id.accounting_consume_item_date,
                        R.id.accounting_consume_item_money, R.id.accounting_consume_item_remarks});
        accountingConsumeListView.setAdapter(simpleAdapter);
    }

    /**
     * 从本地数据库获得消费信息
     */
    private ArrayList<HashMap<String, Object>> getConsumeListFromDB(String mouth) {
        thisMonthPay = 0;
        thisMonthIncome = 0;
        ArrayList<HashMap<String, Object>> consumeListTmp = new ArrayList<>();
        try {
            consumeTableDao = ManagerApplication.getInstance()
                    .getManagerDBHelper().getDao(ConsumeTable.class);
            for (ConsumeTable consumeTable : consumeTableDao) {
                if (consumeTable.getConsumeTime().contains(mouth)) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    ConsumeTypeEnum consumeType = getConsumeTypeEnum(consumeTable.getConsumeTypeId());
                    hashMap.put(FrameUtils.IT_ACCOUNTING_TYPE_IMAGE, consumeType.getTypeImage());
                    hashMap.put(FrameUtils.IT_ACCOUNTING_TIME, consumeTable.getConsumeTime());
                    hashMap.put(FrameUtils.IT_ACCOUNTING_MONEY, consumeTable.getConsumeMoney());
                    hashMap.put(FrameUtils.IT_ACCOUNTING_REMARKS, consumeTable.getRemarks());
                    consumeListTmp.add(hashMap);
                    if (consumeTable.isConsumeIsPay()) {
                        thisMonthPay += consumeTable.getConsumeMoney();
                    } else {
                        thisMonthIncome += consumeTable.getConsumeMoney();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 按照消费时间降序排列
        Collections.sort(consumeListTmp, new Comparator<HashMap<String, Object>>() {
            @Override
            public int compare(HashMap<String, Object> arg0, HashMap<String, Object> arg1) {
                return ((String) arg1.get(FrameUtils.IT_ACCOUNTING_TIME))
                        .compareTo((String) arg0.get(FrameUtils.IT_ACCOUNTING_TIME));
            }
        });
        return consumeListTmp;
    }

    /**
     * 获得消费类型枚举
     */
    public ConsumeTypeEnum getConsumeTypeEnum(int id) {
        for (ConsumeTypeEnum consumeTypeEnum : ConsumeTypeEnum.values()) {
            if (consumeTypeEnum.getTypeId() == id) {
                return consumeTypeEnum;
            }
        }
        return ConsumeTypeEnum.YIBAN;
    }

    /**
     * 新增消费（监听器）
     */
    @OnClick(R.id.accounting_edit)
    public void gotoAddConsume() {
        Intent intent = new Intent();
        intent.setClass(AccountingActivity.this, ConsumeEditActivity.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    /**
     * 查看上月消费（监听器）
     */
    @OnClick(R.id.accounting_last_mouth_consume)
    public void getLastMouthConsume() {
        int flag = accountingMonth.indexOf("-");
        int year = Integer.parseInt(accountingMonth.substring(0, flag));
        int month = Integer.parseInt(accountingMonth.substring(flag + 1));
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        accountingMonth = String.format("%04d", year)
                + "-" + String.format("%02d", month);
        init(accountingMonth);
    }

    /**
     * 查看下个月消费（监听器）
     */
    @OnClick(R.id.accounting_next_mouth_consume)
    public void getNextMouthConsume() {
        int flag = accountingMonth.indexOf("-");
        int year = Integer.parseInt(accountingMonth.substring(0, flag));
        int month = Integer.parseInt(accountingMonth.substring(flag + 1));
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        accountingMonth = String.format("%04d", year)
                + "-" + String.format("%02d", month);
        init(accountingMonth);
    }

    /**
     * startActivity响应函数
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                init(accountingMonth);
                break;
            default:
                break;
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
