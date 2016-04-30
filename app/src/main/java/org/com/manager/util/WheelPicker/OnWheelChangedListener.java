package org.com.manager.util.WheelPicker;

/**
 * Created by jie.hua on 2016/3/23.
 *
 * 滑动监听器接口
 */
public interface OnWheelChangedListener {

    /**
     * Callback method to be invoked when current item changed
     * @param wheel the wheel view whose state has changed
     * @param oldValue the old value of current item
     * @param newValue the new value of current item
     */
    void onChanged(WheelView wheel, int oldValue, int newValue);
}
