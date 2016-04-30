package org.com.manager.util;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.com.manager.R;

/**
 * 再次确认 dialog
 */
public class MyAlertDialog extends AlertDialog {

    private boolean singleButton;
    private TextView title;
    private TextView message;
    private Button leftButton;
    private Button rightButton;

    /**
     * 自定义对话框的构造函数
     *
     * @param context      context
     * @param singleButton 是否是单个按键
     */
    public MyAlertDialog(Context context, boolean singleButton) {
        super(context);
        this.singleButton = singleButton;
    }

    /**
     * 设置标题
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        this.title.setText(title);
    }

    /**
     * 设置内容
     */
    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);
        this.message.setText(message);
    }

    /**
     * 设置“确定”键
     *
     * @param text     文字
     * @param listener 监听器
     */
    public void setPositiveButton(CharSequence text,
                                  View.OnClickListener listener) {
        this.leftButton.setText(text);
        this.leftButton.setOnClickListener(listener);
    }

    /**
     * 设置“取消”键
     *
     * @param text     文字
     * @param listener 监听器
     */
    public void setNegativeButton(CharSequence text,
                                  View.OnClickListener listener) {
        this.rightButton.setText(text);
        this.rightButton.setOnClickListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCanceledOnTouchOutside(false);
        this.setContentView(R.layout.alert_dialog);
        this.title = (TextView) findViewById(R.id.alert_dialog_title);
        this.message = (TextView) findViewById(R.id.alert_dialog_message);
        this.leftButton = (Button) findViewById(R.id.alert_dialog_btn_left);
        this.rightButton = (Button) findViewById(R.id.alert_dialog_btn_right);
        this.title.getPaint().setFakeBoldText(true);
        if (this.singleButton) {
            this.leftButton
                    .setBackgroundResource(R.drawable.ic_alert_dialog_btn_pressed_effect);
            this.rightButton.setVisibility(View.GONE);
        }
    }

}
