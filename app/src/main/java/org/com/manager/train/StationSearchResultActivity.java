package org.com.manager.train;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.com.manager.R;
import org.com.manager.bean.ApiErrorCodeEnum;
import org.com.manager.bean.StationStationModel;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.TrainSearchResultAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 站站查询结果页
 */
public class StationSearchResultActivity extends Activity {
    @Bind(R.id.search_result_title)
    TextView title;
    @Bind(R.id.search_result_list)
    ListView resultList;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;

    private String startStation;
    private String endStation;
    private String departureTime;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();

        startStation = getIntent().getStringExtra(FrameUtils.IT_TRAIN_STATION_START);
        endStation = getIntent().getStringExtra(FrameUtils.IT_TRAIN_STATION_END);
        departureTime = getIntent().getStringExtra(FrameUtils.IT_TRAIN_STATION_DEPARTURE_TIME);
        title.setText(startStation + " 至 " + endStation);
        // today.setText(departureTime);
        stationQueryNet(startStation, endStation);
    }


    /**
     * 访问后端
     */
    private void stationQueryNet(String startStation, String endStation) {
        ManagerApplication.getInstance().getApiHttpClient()
                .stationQueryNet(startStation, endStation,
                        new AsyncApiResponseHandler(StationSearchResultActivity.this) {
                            @Override
                            public void onApiResponse(JSONObject response) {
                                super.onApiResponse(response);
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                }
                                if (response != null) {
                                    try {
                                        JSONArray jsonArray = response.getJSONArray("data");
                                        List<StationStationModel> stationStationModelList = JSON.parseArray(jsonArray.toString(),
                                                StationStationModel.class);
                                        showData(stationStationModelList);
                                    } catch (JSONException e) {
                                        showData(null);
                                    }
                                }
                            }
                        });

    }

    private void showData(List<StationStationModel> stationStationModels) {
        if (stationStationModels != null && stationStationModels.size() != 0) {
            noDataLayout.setVisibility(View.GONE);
            resultList.setVisibility(View.VISIBLE);
            resultList.setAdapter(new TrainSearchResultAdapter(this, stationStationModels));
            resultList.setOnItemClickListener(new itemClickListener(stationStationModels));
        } else {
            noDataLayout.setVisibility(View.GONE);
            resultList.setVisibility(View.VISIBLE);
        }
    }

    /**
     * list监听器
     */
    class itemClickListener implements AdapterView.OnItemClickListener {
        String trainNumber;
        List<StationStationModel> stationStationModels;

        itemClickListener(List<StationStationModel> stationStationModels) {
            this.stationStationModels = stationStationModels;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent();
            this.trainNumber = stationStationModels.get(position).getTrainOpp();
            intent.putExtra(FrameUtils.IT_TRAIN_DETAIL, this.trainNumber);
            intent.putExtra(FrameUtils.IT_TRAIN_ISCOLLECTION, false);
            intent.setClass(StationSearchResultActivity.this, TrainDetailActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            this.overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
