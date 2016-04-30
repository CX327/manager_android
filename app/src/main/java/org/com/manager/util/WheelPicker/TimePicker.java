package org.com.manager.util.WheelPicker;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jie.hua on 2016/3/23.
 * <p/>
 * 自定义的时间选择器
 */
public class TimePicker extends LinearLayout {

    private Calendar calendar = Calendar.getInstance(); //日历类
    private WheelView hours, mins; //Wheel picker
    private OnChangeListener onChangeListener; //onChangeListener

    //Constructors
    public TimePicker(Context context) {
        super(context);
        init(context);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        hours = new WheelView(context);
        LinearLayout.LayoutParams yearParams = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        yearParams.weight = 1;
        hours.setLayoutParams(yearParams);

        mins = new WheelView(context);
        LinearLayout.LayoutParams monthParams = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        monthParams.weight = 1;
        mins.setLayoutParams(monthParams);


        hours.setAdapter(new NumericWheelAdapter(0, 23, "%02d"));
        hours.setLabel("时");
        hours.setCyclic(true);

        mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
        mins.setLabel("分");
        mins.setCyclic(true);

        hours.addChangingListener(onHoursChangedListener);
        mins.addChangingListener(onMinuteChangedListener);

        addView(hours);
        addView(mins);
    }


    //listeners
    private OnWheelChangedListener onHoursChangedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView hours, int oldValue, int newValue) {
            calendar.set(Calendar.HOUR_OF_DAY, newValue);
            onChangeListener.onChange(getHour(), getMinute());
        }
    };
    private OnWheelChangedListener onMinuteChangedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView mins, int oldValue, int newValue) {
            calendar.set(Calendar.MINUTE, newValue);
            onChangeListener.onChange(getHour(), getMinute());
        }
    };


    /**
     * 定义了监听时间改变的监听器借口
     */
    public interface OnChangeListener {
        void onChange(int hour, int min);
    }

    /**
     * 设置监听器的方法
     *
     * @param onChangeListener
     */
    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    /**
     * 设置小时
     *
     * @param hour
     */
    public void setHour(int hour) {
        hours.setCurrentItem(hour);
    }

    /**
     * 获得小时
     *
     * @return
     */
    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 设置分钟
     */
    public void setMinute(int minute) {
        mins.setCurrentItem(minute);
    }

    /**
     * 获得分钟
     *
     * @return
     */
    public int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //默认设置为系统时间
        setHour(getHour());
        setMinute(getMinute());
    }
}