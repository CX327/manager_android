package org.com.manager.train;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.com.manager.R;
import org.com.manager.bean.ApiErrorCodeEnum;
import org.com.manager.bean.StationListModel;
import org.com.manager.bean.StationModel;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.StationDetailAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 站点搜索页
 */
public class StationSearchActivity extends Activity {

    @Bind(R.id.station_search_edit)
    AutoCompleteTextView stationView;
    @Bind(R.id.station_search_list)
    ListView stationList;

    private String station;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_search);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        allStationListNet();
    }

    /**
     * 访问后端
     */
    private void allStationListNet() {
        ManagerApplication.getInstance().getApiHttpClient()
                .allStationListNet(ManagerApplication.getInstance().getUserId(),new AsyncApiResponseHandler(StationSearchActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        if (response != null) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("data");
                                List<StationModel> stationModels = JSON.parseArray(jsonArray.toString(),
                                        StationModel.class);
                                showData(stationModels);
                            } catch (JSONException e) {
                                showData(null);
                            }
                        }
                    }
                });

    }

    private void showData(final List<StationModel> stationModels) {
        if (stationModels == null) {
            return;
        }
        final List<String> stationNames = new ArrayList<>();
        for (StationModel stationModel : stationModels) {
            stationNames.add(stationModel.getSta_name());
        }
        //实例化适配器，指定显示格式及数据源
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                StationSearchActivity.this,
                android.R.layout.simple_dropdown_item_1line, stationNames);
        stationList.setAdapter(adapter);
        stationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stationView.setText(stationModels.get(position).getSta_name());
            }
        });
        //指定自动完成控件的适配器
        stationView.setAdapter(adapter);
        stationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                station = stationView.getText().toString();

                station = parent.getItemAtPosition(position).toString();

                Intent it = new Intent();
                it.putExtra(FrameUtils.IT_TRAIN_STATION, station);
                setResult(Activity.RESULT_OK, it);
                finish();
                StationSearchActivity.this.overridePendingTransition(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent it = new Intent();
            it.putExtra(FrameUtils.IT_TRAIN_STATION, station);
            setResult(Activity.RESULT_OK, it);
            finish();
            this.overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
