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

public class ChangeNameActivity extends Activity {

    @Bind(R.id.new_name)
    EditText newNameEdit;
    @Bind(R.id.user_change_name_tv)
    TextView userChangeNameTv;

    private String oldName;
    private String newName;
    private String pw;
    private SharedPreferences sp;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_change_name);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        sp = ChangeNameActivity.this.getSharedPreferences(
                FrameUtils.SP_USER_INFO, Context.MODE_PRIVATE);
        oldName = sp.getString(FrameUtils.SP_USER_NAME, "");
        pw = sp.getString(FrameUtils.SP_USER_PW, "");
    }

    @OnClick(R.id.user_change_name_tv)
    public void changeName() {
        if (checkInput()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            newName = newNameEdit.getText().toString();
            changeNameNet();
        }
    }

    private void changeNameNet() {
        ManagerApplication.getInstance().getApiHttpClient().changeNameNet(
                ManagerApplication.getInstance().getUserId(), newName,
                new AsyncApiResponseHandler(ChangeNameActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        saveNewName();
                    }
                }
        );
    }

    private void saveNewName() {
        Toast.makeText(ChangeNameActivity.this, R.string.change_success,
                Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(FrameUtils.SP_USER_NAME, newName);
        editor.apply();
        finish();
        Intent intent = new Intent(ChangeNameActivity.this, UserActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    private boolean checkInput() {
        newName = newNameEdit.getText().toString();
        if (newName.length() == 0) {
            Toast.makeText(ChangeNameActivity.this, R.string.change_name_input_empty,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newName.contains(" ")) {
            Toast.makeText(ChangeNameActivity.this, R.string.change_name_no_space,
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
