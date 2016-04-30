package org.com.manager.recipes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.com.manager.R;
import org.com.manager.bean.RecipesDetailModel;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.RecipesFlagAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends Activity {
    @Bind(R.id.recipes_flag_name)
    TextView recipesFlagName;
    @Bind(R.id.recipes_flag_list)
    ListView recipesFlagListView;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;

    String key;
    int start;
    int size;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        recipesFlagName.setText("搜索结果");
        key = getIntent()
                .getStringExtra(FrameUtils.IT_RECIPES_SEARCH);
        progressDialog = new ProgressDialog(this);
        start = 0;
        size = 10;
        searchNet(key, start, size);
    }

    /**
     * 根据key获得食谱列表
     */
    private void searchNet(String key, int start, int size) {
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        ManagerApplication.getInstance().getApiHttpClient()
                .keyQueryNet(key, start, size, new AsyncApiResponseHandler(SearchActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        if (response != null) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("data");
                                List<RecipesDetailModel> recipesDetailModelList = JSON.parseArray
                                        (jsonArray.toString(), RecipesDetailModel.class);
                                showData(recipesDetailModelList);
                            } catch (JSONException e) {
                                showData(null);
                            }
                        }
                    }
                });

    }

    private void showData(List<RecipesDetailModel> recipesDetailModelList) {
        if (recipesDetailModelList != null && recipesDetailModelList.size() != 0) {
            noDataLayout.setVisibility(View.GONE);
            recipesFlagListView.setVisibility(View.VISIBLE);
            recipesFlagListView.setAdapter(new RecipesFlagAdapter(SearchActivity.this, recipesDetailModelList));
        } else {
            recipesFlagListView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
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
