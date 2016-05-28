package org.com.manager.recipes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.com.manager.R;
import org.com.manager.bean.RecipesDetailModel;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.RecipesFlagAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipesSearchActivity extends Activity {

    @Bind(R.id.recipes_search)
    EditText recipesSearch;
    @Bind(R.id.recipes_search_list)
    ListView recipesSearchListView;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;

    private String key;
    private int start;
    private int size;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        recipesSearchListView.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.VISIBLE);
        recipesSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) recipesSearch.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(RecipesSearchActivity.this
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    search();
                }
                return false;
            }
        });
    }

    private void search() {
        start = 0;
        size = 10;
        key = recipesSearch.getText().toString();
        if (key == null || key.isEmpty()) {
            Toast.makeText(RecipesSearchActivity.this, "请输入关键词", Toast.LENGTH_SHORT).show();
        }else {
            searchNet(key, start, size);
        }
    }

    /**
     * 根据key获得食谱列表
     */
    private void searchNet(String key, int start, int size) {
        progressDialog = new ProgressDialog(RecipesSearchActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        ManagerApplication.getInstance().getApiHttpClient()
                .keyQueryNet(ManagerApplication.getInstance().getUserId(),key, start, size, new AsyncApiResponseHandler(RecipesSearchActivity.this) {
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
            recipesSearchListView.setVisibility(View.VISIBLE);
            recipesSearchListView.setAdapter(new RecipesFlagAdapter(RecipesSearchActivity.this, recipesDetailModelList));
        } else {
            recipesSearchListView.setVisibility(View.GONE);
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
