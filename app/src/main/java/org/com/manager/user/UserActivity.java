package org.com.manager.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.com.manager.R;
import org.com.manager.util.FrameUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends Activity {
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_logout)
    Button userLogout;
    @Bind(R.id.user_change_name)
    Button userChangeName;
    @Bind(R.id.user_change_pw)
    Button userChangePw;

    private String name;
    private  SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        sp = UserActivity.this.getSharedPreferences(
                FrameUtils.SP_USER_INFO, Context.MODE_PRIVATE);
        name = sp.getString(FrameUtils.SP_USER_NAME, "");
        if (name.isEmpty()) {
            //跳到登录页面
            finish();
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        } else {
            userName.setText(name);
        }
    }

    @OnClick(R.id.user_logout)
    public void logoutUser() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
        finish();
        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    @OnClick(R.id.user_change_name)
    public void changeName() {
        Intent intent = new Intent(UserActivity.this, ChangeNameActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    @OnClick(R.id.user_change_pw)
    public void changePw() {
        Intent intent = new Intent(UserActivity.this, ChangePwActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }
    /**
     * 退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
