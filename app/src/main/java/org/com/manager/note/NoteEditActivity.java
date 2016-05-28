package org.com.manager.note;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.com.manager.R;
import org.com.manager.bean.NoteModel;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.frame.TimeReceiver;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.WheelPicker.DatePicker;
import org.com.manager.util.WheelPicker.TimePicker;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑便签
 */
public class NoteEditActivity extends Activity {

    @Bind(R.id.note_remind_set)
    CheckBox noteRemindSet;

    @Bind(R.id.note_edit_commit)
    ImageView noteRemindCommit;

    @Bind(R.id.edit_note_title)
    EditText editNoteTitle;

    @Bind(R.id.edit_note_time)
    TextView editNoteTime;

    @Bind(R.id.edit_note_remind_time)
    TextView editNoteRemindTime;

    @Bind(R.id.edit_note_content)
    EditText editNoteContent;

    //整体布局
    @Bind(R.id.note_edit_layout)
    LinearLayout noteEditLayout;
    /**
     * wheel
     */
    private TextView txDateAndWeekDay;
    private TextView txTime;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextView timeOk;
    private TextView timeCancel;
    private PopupWindow popupWindow;
    /**
     * 选择时间
     */
    private String selectDate;
    private String selectTime;
    private int selectYear;
    private int selectMonth;
    private int selectDay;
    private int selectHour;
    private int selectMinute;
    /**
     * 便签信息
     */
    private String noteTitle;
    private String noteContent;
    private String noteRemindTime;

    /**
     * 初始化
     */
    private int noteId = -1;
    /**
     * 是否新增提醒
     */
    private boolean remindSure = false;

    private ProgressDialog progressDialog = null;
    private NoteModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化，若传进便签内容，则显示
     */
    private void init() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        selectDate = "";
        selectTime = "";
        NoteModel noteModel = (NoteModel) getIntent().getExtras().getSerializable(FrameUtils.IT_NOTE_MAP);
        editNoteRemindTime.setVisibility(View.INVISIBLE);
        if (noteModel != null && noteModel.getNoteTitle() != null) {
            noteId = noteModel.getId();
            editNoteTitle.setText(noteModel.getNoteTitle());
            editNoteContent.setText(noteModel.getNoteContent());
            editNoteTime.setText(noteModel.getNoteTime());
            noteRemindTime = noteModel.getNoteRemindTime();
            if (!noteRemindTime.isEmpty()) {
                editNoteRemindTime.setVisibility(View.VISIBLE);
                editNoteRemindTime.setCompoundDrawablePadding(10);
                editNoteRemindTime.setText(noteRemindTime);

            }
        } else {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日HH时mm分");
            String noteDate = sDateFormat.format(new java.util.Date());
            editNoteTime.setText(noteDate);
        }
    }

    /**
     * 设置提醒（监听器）
     */
    @OnClick(R.id.note_remind_set)
    public void setNoteRemind() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow()
                .getDecorView().getWindowToken(), 0); // 强制隐藏键盘

        View view = View.inflate(NoteEditActivity.this, R.layout.wheel_dialog_select_time, null);

        txDateAndWeekDay = (TextView) view.findViewById(R.id.datepicker_date_and_weekday);
        txTime = (TextView) view.findViewById(R.id.timepicker_time);

        datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        timeOk = (TextView) view.findViewById(R.id.tv_ok);
        timeCancel = (TextView) view.findViewById(R.id.tv_cancel);

        //设置滑动改变监听器
        datePicker.setOnChangeListener(dpOnChangeListener);
        timePicker.setOnChangeListener(tpOnChangeheListener);

        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //出现在布局底端
        popupWindow.showAtLocation(noteEditLayout, 0, 0, Gravity.END);
        //点击确定
        timeOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*判断选择时间是否过期*/
                boolean timeOut = judgeSelectTime();

                if (timeOut) {
                    remindSure = true;
                    editNoteRemindTime.setText(transString(selectDate + selectTime));
                    editNoteRemindTime.setVisibility(View.VISIBLE);
                    editNoteRemindTime.setCompoundDrawablePadding(10);
                    noteRemindSet.setChecked(false);
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "设置时间必须超过5分钟\n        请重新选择",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        //点击取消，则提醒取消
        timeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                selectDate = "";
                selectTime = "";
                remindSure = false;
                editNoteRemindTime.setVisibility(View.INVISIBLE);
                popupWindow.dismiss();
                noteRemindSet.setChecked(false);
            }
        });
    }

    /**
     * 判断选择的时间是否符合要求
     */
    private boolean judgeSelectTime() {

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String nowTime = sDateFormat.format(new java.util.Date());

        String chooseTime = selectDate + selectTime;

        long chooseIntTime = Long.parseLong(chooseTime);
        long nowIntTime = Long.parseLong(nowTime);
        long cha = chooseIntTime - nowIntTime;
        if (cha < 5) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 提交(监听器)
     */
    @OnClick(R.id.note_edit_commit)
    public void commitNoteEdit() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        String noteDate = sDateFormat.format(new java.util.Date());
        noteTitle = editNoteTitle.getText().toString();
        noteContent = editNoteContent.getText().toString();
        if (noteContent.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "便签内容不能为空~",
                    Toast.LENGTH_SHORT).show();
            return;
        } else if (noteTitle.length() > 20) {
            Toast.makeText(getApplicationContext(),
                    "便签标题长度不能超过20字~",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        if (noteId == -1) {
            //此为新增便签
            addNoteNet(noteTitle, noteDate,
                    noteContent, transString(selectDate + selectTime));
        } else {
            //此为修改便签
            if (remindSure) {
                //新增提醒，update note
                updateNet(noteId, noteTitle, noteDate,
                        noteContent, transString(selectDate + selectTime));
            } else {
                //未新增提醒 ，update note,提醒时间不变
                updateNet(noteId, noteTitle, noteDate,
                        noteContent, noteRemindTime);
            }
        }

        //新增提醒
        if (remindSure) {
            Random random = new Random();
            int requestCode = random.nextInt(999999);
            saveRemind(remindSure, noteTitle, noteContent, requestCode);
        }

    }

    private void addNoteNet(String noteTitle, String noteDate,
                            String noteContent, String noteRemindTime) {
        ManagerApplication.getInstance().getApiHttpClient().noteAddNet(
                ManagerApplication.getInstance().getUserId(),
                noteTitle, noteDate,
                noteContent, noteRemindTime, new AsyncApiResponseHandler(NoteEditActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        setResult(RESULT_OK);
                        finish();
                        NoteEditActivity.this.overridePendingTransition(
                                android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right);
                    }
                }
        );
    }

    private void updateNet(int noteId, String noteTitle, String noteDate,
                           String noteContent, String noteRemindTime) {
        ManagerApplication.getInstance().getApiHttpClient().noteUpdateNet(
                ManagerApplication.getInstance().getUserId(),
                noteId, noteTitle, noteDate,
                noteContent, noteRemindTime, new AsyncApiResponseHandler(NoteEditActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        setResult(RESULT_OK);
                        finish();
                        NoteEditActivity.this.overridePendingTransition(
                                android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right);
                    }
                }

        );
    }

    /**
     * 修改日期格式
     */
    private String transString(String oldDate) {
        try {
            SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
            Date newDate = oldDateFormat.parse(oldDate);
            SimpleDateFormat newDateFormat = new SimpleDateFormat("MM月dd日HH时mm分");
            return newDateFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return oldDate;
    }

    /**
     * 保存提醒，alarm
     */
    private void saveRemind(boolean b, String noteTitle, String noteContent, int requestCode) {
        Calendar timeSet = Calendar.getInstance();
        /**Calendar中月份是0-11 ，3代表4月份*/
        timeSet.set(selectYear, selectMonth - 1, selectDay, selectHour, selectMinute, 00);
        Intent intent = new Intent(this, TimeReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putString(FrameUtils.IT_NOTE_TITLE, noteTitle);
        bundle.putString(FrameUtils.IT_NOTE_CONTENT, noteContent);
        intent.putExtras(bundle);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(NoteEditActivity.this, requestCode,
                intent, 0);
        if (b) {
            am.set(AlarmManager.RTC_WAKEUP, timeSet.getTimeInMillis(), pi);
        } else {
            am.cancel(pi);
        }
    }

    /**
     * wheel listeners
     */
    DatePicker.OnChangeListener dpOnChangeListener = new DatePicker.OnChangeListener() {
        @Override
        public void onChange(int year, int month, int day, int day_of_week) {
            selectYear = year;
            selectMonth = month;
            selectDay = day;

            selectDate = String.format("%04d", year) + String.format("%02d", month)
                    + String.format("%02d", day);
            String selectDateTmp;
            if (month < 10 && day < 10) {
                selectDateTmp = year + "-0" + month + "-0" +
                        day + " 星期" + DatePicker.getDayOfWeekCN(day_of_week);
            } else if (month >= 10 && day < 10) {
                selectDateTmp = year + "-" + month + "-0" +
                        day + " 星期" + DatePicker.getDayOfWeekCN(day_of_week);
            } else if (month < 10 && day >= 10) {
                selectDateTmp = year + "-0" + month + "-" +
                        day + " 星期" + DatePicker.getDayOfWeekCN(day_of_week);
            } else {
                selectDateTmp = year + "-" + month + "-" +
                        day + " 星期" + DatePicker.getDayOfWeekCN(day_of_week);
            }
            txDateAndWeekDay.setText(selectDateTmp);
        }
    };

    TimePicker.OnChangeListener tpOnChangeheListener = new TimePicker.OnChangeListener() {
        @Override
        public void onChange(int hour, int minute) {
            selectTime = String.format("%02d", hour)
                    + String.format("%02d", minute);

            selectHour = hour;
            selectMinute = minute;
            txTime.setText(hour + "点" + ((minute < 10) ? ("0" + minute) : minute) + "分");
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(RESULT_OK);
            finish();
            NoteEditActivity.this.overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
