package org.com.manager.recipes;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.com.manager.R;
import org.com.manager.bean.ApiErrorCodeEnum;
import org.com.manager.bean.RecipesDetailModel;
import org.com.manager.bean.StepsModel;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.FrameUtils;
import org.com.manager.util.MyListView;
import org.com.manager.util.RecipesDetailAdapter;
import org.com.manager.util.RecipesRecommendAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipesDetailActivity extends Activity {
    @Bind(R.id.recipes_detail_img)
    ImageView recipesDetailImg;
    @Bind(R.id.recipes_detail_title)
    TextView recipesDetailTitle;
    @Bind(R.id.recipes_detail_imtro)
    TextView recipesDetailImtro;
    @Bind(R.id.recipes_detail_ingredients)
    TextView recipesDetailIngredients;
    @Bind(R.id.recipes_detail_burden)
    TextView recipesDetailBurden;
    @Bind(R.id.recipes_detail_steps)
    MyListView recipesDetailSteps;

    @Bind(R.id.recipes_collection_check)
    CheckBox recipesCollectionCheck;

    RecipesDetailModel recipesDetailModel;
    /**
     * 收藏状态是否改变
     */
    boolean isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        recipesDetailModel = (RecipesDetailModel) getIntent()
                .getSerializableExtra(FrameUtils.IT_RECIPES_DETAIL);
        checkCollectionNet(recipesDetailModel.getId());
        String albums = recipesDetailModel.getAlbums();
        if (albums.contains("[")) {
            albums = albums.replace("[\"", "").replace("\"]", "");
        }
        ImageLoader.getInstance().displayImage(albums,
                recipesDetailImg,
                ManagerApplication.getInstance().getImageDisplayOption());
        recipesDetailTitle.setText(recipesDetailModel.getTitle());
        recipesDetailImtro.setText(recipesDetailModel.getImtro());
        recipesDetailIngredients.setText(recipesDetailModel.getIngredients());
        recipesDetailBurden.setText(recipesDetailModel.getBurden());
        List<StepsModel> stepsModels = recipesDetailModel.getSteps();
        recipesDetailSteps.setAdapter(new RecipesDetailAdapter(RecipesDetailActivity.this, stepsModels));
    }

    /**
     * checkBox监听器
     */
    @OnClick(R.id.recipes_collection_check)
    public void checkCollection() {
        isChange = !isChange;
        String toastStr;
        if (recipesCollectionCheck.isChecked()) {
            toastStr = "已收藏该食谱";
        } else {
            toastStr = "取消收藏";
        }
        Toast.makeText(RecipesDetailActivity.this, toastStr, Toast.LENGTH_SHORT).show();
    }

    /**
     * 检测是否已收藏
     */
    private void checkCollectionNet(String recipesId) {
        ManagerApplication.getInstance().getApiHttpClient()
                .checkCollectionNet( ManagerApplication.getInstance().getUserId(), recipesId, new AsyncApiResponseHandler(RecipesDetailActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (response != null) {
                            try {
                                boolean jsonObject = response.getBoolean("data");
                                if (jsonObject) {
                                    recipesCollectionCheck.setChecked(true);
                                } else {
                                    recipesCollectionCheck.setChecked(false);
                                }
                            } catch (JSONException e) {
                                Toast.makeText(RecipesDetailActivity.this,
                                        "解析json数据失败，请检查对象类型",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    /**
     * 新增收藏
     */
    private void saveCollectionNet(String recipesId) {
        ManagerApplication.getInstance().getApiHttpClient()
                .saveCollectionNet( ManagerApplication.getInstance().getUserId(), recipesId, new AsyncApiResponseHandler(RecipesDetailActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                    }
                });
    }

    /**
     * 删除收藏
     */
    private void deleteCollectionNet(String recipesId) {
        ManagerApplication.getInstance().getApiHttpClient()
                .deleteCollectionNet( ManagerApplication.getInstance().getUserId(), recipesId, new AsyncApiResponseHandler(RecipesDetailActivity.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            boolean isChecked = recipesCollectionCheck.isChecked();
            if (isChange && isChecked) {
                //收藏状态改变且新添加收藏
                saveCollectionNet(recipesDetailModel.getId());
            } else if (isChange && !isChecked) {
                //收藏状态改变且取消收藏
                deleteCollectionNet(recipesDetailModel.getId());
            }
            finish();
            RecipesDetailActivity.this.overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
