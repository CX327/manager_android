package org.com.manager.frame;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.com.manager.R;
import org.com.manager.util.FrameUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreActivity extends Activity {
    @Bind(R.id.old_ip_tv)
    TextView oldIpTv;
    @Bind(R.id.new_ip_edit)
    EditText newIpEdit;
    @Bind(R.id.ip_sure_tv)
    TextView ipSureTv;
    private String oldIp;
    private String newIp;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        sp = MoreActivity.this.getSharedPreferences(
                FrameUtils.SP_HOME_URI, Context.MODE_PRIVATE);
        oldIp = sp.getString(FrameUtils.SP_IP, "");
        if (oldIp.equals("")) {
            oldIpTv.setText("暂无");
        } else {
            oldIpTv.setText(oldIp);
        }
    }

    @OnClick(R.id.ip_sure_tv)
    public void sureNewIp() {
        newIp = newIpEdit.getText().toString();
        if (newIp == null || newIp.isEmpty()) {
            Toast.makeText(MoreActivity.this, "请输入新IP", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newIp.contains("192.168.")) {
            Toast.makeText(MoreActivity.this, "IP格式错误", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(FrameUtils.SP_IP, newIp);
        editor.apply();
        Toast.makeText(MoreActivity.this, "成功修改IP", Toast.LENGTH_SHORT).show();
        finish();
    }
}
