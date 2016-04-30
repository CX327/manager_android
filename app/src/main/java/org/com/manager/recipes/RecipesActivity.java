package org.com.manager.recipes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.com.manager.R;
import org.com.manager.bean.ApiErrorCodeEnum;
import org.com.manager.bean.RecommendItemModel;
import org.com.manager.bean.RecommendModel;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.RecipesRecommendAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipesActivity extends Activity {
    @Bind(R.id.recipes_recommend_name)
    TextView recommendName;
    @Bind(R.id.recipes_recommend_list)
    ListView recommendListView;
    @Bind(R.id.recipes_search)
    EditText recipesSearch;
    @Bind(R.id.recipes_collection_entrance)
    ImageView recipesCollectionEntrance;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;

    List<RecommendItemModel> itemModels;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        recommendNet();
        recipesSearch.setOnEditorActionListener(new editorClick());
    }


    /**
     * 访问后端,推荐
     */
    private void recommendNet() {
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        ManagerApplication.getInstance().getApiHttpClient()
                .recipesRecommendNet(new AsyncApiResponseHandler(RecipesActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        if (response != null) {
                            try {
                                JSONObject jsonObject = response.getJSONObject("data");
                                RecommendModel recommendModel = JSON.parseObject(jsonObject.toString(), RecommendModel.class);
                                showData(recommendModel);
                            } catch (JSONException e) {
                                Toast.makeText(RecipesActivity.this,
                                        "解析json数据失败，请检查对象类型",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            showData(null);
                        }
                    }
                });

    }

    private void showData(RecommendModel recommendModel) {
        if (recommendModel != null) {
            noDataLayout.setVisibility(View.GONE);
            recommendListView.setVisibility(View.VISIBLE);

            recommendName.setText(recommendModel.getTitle());
            itemModels = recommendModel.getItemModels();
            recommendListView.setAdapter(new RecipesRecommendAdapter(RecipesActivity.this, itemModels));
            recommendListView.setOnItemClickListener(new itemClickListener());
        } else {
            recommendListView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 进入收藏页（监听器）
     */
    @OnClick(R.id.recipes_collection_entrance)
    public void entranceCollection() {
        Intent intent = new Intent();
        intent.setClass(RecipesActivity.this, CollectionActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    /**
     * 进入便签页（监听器）
     */
    class itemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Intent intent = new Intent();
            intent.putExtra(FrameUtils.IT_RECIPES_FLAG_ID, itemModels.get(position).getFlagId());
            intent.putExtra(FrameUtils.IT_RECIPES_FLAG_NAME, itemModels.get(position).getFlagName());
            intent.setClass(RecipesActivity.this, FlagActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        }
    }

    /**
     * 进入搜索结果页（edit 监听器）
     */
    class editorClick implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 先隐藏键盘
                ((InputMethodManager) recipesSearch.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(RecipesActivity.this
                                        .getCurrentFocus()
                                        .getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                Intent intent = new Intent();
                intent.putExtra(FrameUtils.IT_RECIPES_SEARCH, recipesSearch.getText().toString());
                intent.setClass(RecipesActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
            }
            return false;
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
