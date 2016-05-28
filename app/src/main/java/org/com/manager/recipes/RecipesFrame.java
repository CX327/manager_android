package org.com.manager.recipes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.com.manager.R;
import org.com.manager.bean.FlagModel;
import org.com.manager.bean.RecommendModel;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.response.AsyncApiResponseHandler;
import org.com.manager.util.PagerSlidingTabStrip;
import org.com.manager.util.TabPagerAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipesFrame extends FragmentActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.recipes_tabstrip)
    PagerSlidingTabStrip recipesTabstrip;
    @Bind(R.id.recipes_viewpager)
    ViewPager recipesViewpager;
    @Bind(R.id.recipes_collection_entrance)
    ImageView recipesCollectionEntrance;
    @Bind(R.id.recipes_search_entrance)
    ImageView recipesSearchEntrance;
    @Bind(R.id.tab_layout)
    LinearLayout tabLayout;
    @Bind(R.id.no_layout)
    LinearLayout noDataLayout;

    private List<String> mTabTiles;
    private List<Fragment> mFragmentList;

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_frame);
        ButterKnife.bind(this);
        recommendNet();
    }


    /**
     * 访问后端,推荐
     */
    private void recommendNet() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        ManagerApplication.getInstance().getApiHttpClient()
                .recipesRecommendNet(ManagerApplication.getInstance().getUserId(), new AsyncApiResponseHandler(RecipesFrame.this) {
                    @Override
                    public void onApiResponse(JSONObject response) {
                        super.onApiResponse(response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        if (response != null) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("data");
                                List<RecommendModel> recommendModels = JSON.parseArray(jsonArray.toString(), RecommendModel.class);
                                showData(recommendModels);
                            } catch (JSONException e) {
                                Toast.makeText(RecipesFrame.this,
                                        "解析json数据失败，请检查对象类型",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            showData(null);
                        }
                    }
                });
    }

    private void showData(List<RecommendModel> recommendModels) {
        if (recommendModels == null || recommendModels.size() == 0) {
            tabLayout.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        } else {
            tabLayout.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.GONE);
            mFragmentList = new ArrayList();
            mTabTiles = new ArrayList();
            for (RecommendModel recommendModel : recommendModels) {
                mTabTiles.add(recommendModel.getFlagModel().getName());
                Fragment fragment = new RecipesFragment(recommendModel.getItemModels());
                mFragmentList.add(fragment);
            }
            recipesViewpager.setAdapter(new TabPagerAdapter(
                    getSupportFragmentManager(), mFragmentList, mTabTiles));
            recipesViewpager.setOffscreenPageLimit(2);// 设置缓存的viewpager个数
            recipesViewpager.setCurrentItem(0);// 设置当前页的item
            recipesTabstrip.setViewPager(recipesViewpager);
            recipesTabstrip.setOnPageChangeListener(this);
        }
    }

    /**
     * 进入收藏页（监听器）
     */
    @OnClick(R.id.recipes_collection_entrance)
    public void entranceCollection() {
        Intent intent = new Intent();
        intent.setClass(RecipesFrame.this, CollectionActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    /**
     * 进入收藏页（监听器）
     */
    @OnClick(R.id.recipes_search_entrance)
    public void entranceSearch() {
        Intent intent = new Intent();
        intent.setClass(RecipesFrame.this, RecipesSearchActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
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
