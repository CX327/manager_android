package org.com.manager.recipes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.com.manager.R;
import org.com.manager.bean.ApiErrorCodeEnum;
import org.com.manager.bean.RecipesDetailModel;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.MyAlertDialog;
import org.com.manager.util.RecipesFlagAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 收藏页面
 */
public class CollectionActivity extends Activity {
    @Bind(R.id.recipes_flag_name)
    TextView recipesFlagName;
    @Bind(R.id.recipes_flag_list)
    ListView recipesCollectionListView;
    @Bind(R.id.no_data_layout)
    LinearLayout noDataLayout;

    List<RecipesDetailModel> recipesDetailModels;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag);
        ButterKnife.bind(this);
    }

    /**
     * 初始化
     */
    private void init() {
        recipesFlagName.setText("我的收藏");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        allCollectionNet();
    }

    /**
     * 删除收藏，net
     */
    private void deleteCollectionNet(String recipesId) {
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        ManagerApplication.getInstance().getApiHttpClient()
                .deleteCollectionNet(recipesId, new AsyncApiResponseHandler(CollectionActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    /**
     * 获得全部收藏，net
     */
    private void allCollectionNet() {
        ManagerApplication.getInstance().getApiHttpClient()
                .allCollectionNet(new AsyncApiResponseHandler(CollectionActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        try {
                            if (response != null) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                List<RecipesDetailModel> recipesDetailModels = JSON.parseArray(jsonArray.toString(),
                                        RecipesDetailModel.class);
                                showData(recipesDetailModels);
                            }
                        } catch (JSONException e) {
                            showData(null);
                        }
                    }
                });
    }

    private void showData(List<RecipesDetailModel> recipesDetailModels) {
        this.recipesDetailModels = recipesDetailModels;
        if (recipesDetailModels != null && recipesDetailModels.size() != 0) {
            noDataLayout.setVisibility(View.GONE);
            recipesCollectionListView.setVisibility(View.VISIBLE);
            recipesCollectionListView.setAdapter(new RecipesFlagAdapter(CollectionActivity.this, recipesDetailModels));
        } else {
            recipesCollectionListView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 是否删除该收藏
     */
    private void alertDlg(final String recipesId) {
        final MyAlertDialog alertDialog = new MyAlertDialog(CollectionActivity.this,
                false);
        alertDialog.show();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("是否删除该收藏？");
        alertDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCollectionNet(recipesId);
                alertDialog.cancel();
            }
        });
        alertDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
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
