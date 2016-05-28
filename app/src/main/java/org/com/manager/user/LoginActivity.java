package org.com.manager.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.com.manager.R;
import org.com.manager.bean.StationStationModel;
import org.com.manager.frame.FrameActivity;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.frame.MoreActivity;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.FrameUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {
    @Bind(R.id.frame_more)
    ImageView frameMore;
    @Bind(R.id.user_name)
    EditText userNameTv;
    @Bind(R.id.user_pw)
    EditText userPwTv;
    @Bind(R.id.user_login)
    Button userLoginTv;
    @Bind(R.id.user_register)
    Button userRegister;


    private String name;
    private String pw;
    private SharedPreferences sp;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
    }

    @OnClick(R.id.user_login)
    public void login() {
        name = userNameTv.getText().toString();
        pw = userPwTv.getText().toString();
        if (name==null || name.isEmpty()){
            Toast.makeText(LoginActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name==null || name.isEmpty()){
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        loginNet();
    }

    @OnClick(R.id.user_register)
    public void register() {
        name = userNameTv.getText().toString();
        pw = userPwTv.getText().toString();
        if (name==null || name.isEmpty()){
            Toast.makeText(LoginActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (name==null || name.isEmpty()){
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        registerNet();
    }
    /**
     * 更多（监听器）
     */
    @OnClick(R.id.frame_more)
    public void goMore() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MoreActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }
    private void loginNet() {
        ManagerApplication.getInstance().getApiHttpClient().loginNet(
                name, pw,
                new AsyncApiResponseHandler(LoginActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        if (response != null) {
                            try {
                                JSONObject jsonObject = response.getJSONObject("data");
                                int id = jsonObject.getInt("userId");
                                saveUser(id, null);
                            } catch (JSONException e) {
                                saveUser(-1, "登录失败");
                            }
                        }
                    }
                }
        );
    }

    private void registerNet() {
        ManagerApplication.getInstance().getApiHttpClient().registerNet(
                name, pw,
                new AsyncApiResponseHandler(LoginActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        if (response != null) {
                            try {
                                JSONObject jsonObject = response.getJSONObject("data");
                                int id = jsonObject.getInt("userId");
                                saveUser(id, null);
                            } catch (JSONException e) {
                                saveUser(-1, "注册失败");
                            }
                        }
                    }
                }
        );
    }

    private void saveUser(int id, String error) {
        if (id == -1) {
            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            return;
        }
        sp = LoginActivity.this.getSharedPreferences(
                FrameUtils.SP_USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(FrameUtils.SP_USER_ID, id);
        editor.putString(FrameUtils.SP_USER_NAME, name);
        editor.putString(FrameUtils.SP_USER_PW, pw);
        editor.apply();

        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, FrameActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    /**
     * 退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            System.exit(0);
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
