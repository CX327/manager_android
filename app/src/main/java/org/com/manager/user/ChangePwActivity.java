package org.com.manager.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.com.manager.R;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.FrameUtils;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePwActivity extends Activity {
    @Bind(R.id.old_pw)
    EditText oldPw;
    @Bind(R.id.new_pw)
    EditText newPw;
    @Bind(R.id.sure_new_pw)
    EditText sureNewPw;
    @Bind(R.id.user_change_pw_tv)
    TextView userChangePwTv;

    private String oldPassword;
    private String oldPasswordInput;
    private String newPassword1;
    private String newPassword2;
    private SharedPreferences sp;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_change_pw);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        sp = ChangePwActivity.this.getSharedPreferences(
                FrameUtils.SP_USER_INFO, Context.MODE_PRIVATE);
        oldPassword = sp.getString(FrameUtils.SP_USER_PW, "");
    }

    @OnClick(R.id.user_change_pw_tv)
    public void changePw() {
        if (!checkInput()) {
            return;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        changePwNet();
    }

    private void changePwNet() {
        ManagerApplication.getInstance().getApiHttpClient().changePwNet(
                ManagerApplication.getInstance().getUserId(), oldPassword, newPassword1,
                new AsyncApiResponseHandler(ChangePwActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        saveNewPw();
                    }
                }
        );
    }

    private void saveNewPw() {
        Toast.makeText(ChangePwActivity.this, R.string.change_success,
                Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(FrameUtils.SP_USER_PW,newPassword1);
        editor.apply();
        finish();
        Intent intent = new Intent(ChangePwActivity.this, UserActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    /**
     * 检查输入信息是否正确
     */
    private boolean checkInput() {
        oldPasswordInput = oldPw.getText().toString();
        newPassword1 = newPw.getText().toString();
        newPassword2 = sureNewPw.getText().toString();

        if (oldPasswordInput.length() == 0) {
            Toast.makeText(ChangePwActivity.this, R.string.change_pw_input_old_pw,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!oldPasswordInput.equals(oldPassword)) {
            Toast.makeText(ChangePwActivity.this, R.string.change_pw_wrong,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPassword1.contains(" ")) {
            Toast.makeText(ChangePwActivity.this, R.string.change_pw_no_space,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPassword1.length() < 3 || newPassword1.length() > 16) {
            Toast.makeText(ChangePwActivity.this, R.string.change_pw_length_error,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!newPassword1.equals(newPassword2)) {
            Toast.makeText(ChangePwActivity.this, R.string.change_pw_not_same,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
