package org.com.manager.response;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;
import org.com.manager.R;
import org.com.manager.bean.ApiErrorCodeEnum;
import org.com.manager.bean.RecommendModel;
import org.com.manager.frame.ManagerApplication;
import org.com.manager.util.FrameUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jie.hua on 2016/4/17.
 * 访问api
 */
public class ApiHttpClient {
    /**
     * URI
     */
    /**
     * 端口号，tomcat默认端口号为8080
     */
    public static final String HOME_PORT = ":8080/";

    public static final String RECIPES_RECOMMEND_URI = HOME_PORT + "recipes/recommend.do";
    public static final String RECIPES_FLAG_URI = HOME_PORT + "recipes/flag.do";
    public static final String RECIPES_ID_URI = HOME_PORT + "recipes/id.do";
    public static final String RECIPES_ALL_COLLECTION_URI = HOME_PORT + "recipes/allCollection.do";
    public static final String RECIPES_SEARCH_URI = HOME_PORT + "recipes/keyQuery.do";
    public static final String RECIPES_SAVE_COLLECTION_URI = HOME_PORT + "recipes/saveCollection.do";
    public static final String RECIPES_CHECK_COLLECTION_URI = HOME_PORT + "recipes/checkCollection.do";
    public static final String RECIPES_DELETE_COLLECTION_URI = HOME_PORT + "recipes/deleteCollection.do";
    public static final String TRAIN_STATION_QUERY_URI = HOME_PORT + "train/stationQuery.do";
    public static final String TRAIN_NUMBER_QUERY_URI = HOME_PORT + "train/numberQuery.do";
    public static final String TRAIN_NUMBER_STATION_LIST_URI = HOME_PORT + "train/stationList.do";


    /**
     * get
     */
    private void getNet(String uri, RequestParams params,
                        AsyncApiResponseHandler asyncApiResponseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        uri="http://"+ManagerApplication.getInstance().getHomeUri() + uri;
        if (params == null) {
            client.get(uri, asyncApiResponseHandler);
        } else {
            client.get(uri, params, asyncApiResponseHandler);
        }
    }

    /**
     * post
     */
    private void postNet(String uri, RequestParams params, AsyncApiResponseHandler asyncApiResponseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ManagerApplication.getInstance().getHomeUri() + uri, params, asyncApiResponseHandler);
    }

    /**
     * 推荐食谱
     */
    public void recipesRecommendNet(AsyncApiResponseHandler asyncApiResponseHandler) {
        getNet(RECIPES_RECOMMEND_URI, null, asyncApiResponseHandler);
    }

    /**
     * 根据flag获得食谱
     */
    public void recipesFlagNet(String flagId, int start, int size,
                               AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("flag", flagId);
        params.put("start", start);
        params.put("size", size);
        getNet(RECIPES_FLAG_URI, params, asyncApiResponseHandler);
    }

    /**
     * 根据key获得食谱
     */
    public void keyQueryNet(String key, int start, int size,
                            AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("key", key);
        params.put("start", start);
        params.put("size", size);
        getNet(RECIPES_SEARCH_URI, params, asyncApiResponseHandler);
    }

    /**
     * 检查是否收藏
     */
    public void checkCollectionNet(String recipesId, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("recipesId", recipesId);
        getNet(RECIPES_CHECK_COLLECTION_URI, params, asyncApiResponseHandler);
    }

    /**
     * 新增收藏
     */
    public void saveCollectionNet(String recipesId, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("recipesId", recipesId);
        getNet(RECIPES_SAVE_COLLECTION_URI, params, asyncApiResponseHandler);
    }

    /**
     * 删除收藏
     */
    public void deleteCollectionNet(String recipesId, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("recipesId", recipesId);
        getNet(RECIPES_DELETE_COLLECTION_URI, params, asyncApiResponseHandler);
    }

    /**
     * 全部收藏
     */
    public void allCollectionNet(AsyncApiResponseHandler asyncApiResponseHandler) {
        getNet(RECIPES_ALL_COLLECTION_URI, null, asyncApiResponseHandler);
    }

    /**
     * 全国全部站点列表
     */
    public void allStationListNet(AsyncApiResponseHandler asyncApiResponseHandler) {
        getNet(TRAIN_NUMBER_STATION_LIST_URI, null, asyncApiResponseHandler);
    }

    /**
     * 站站查询
     */
    public void stationQueryNet(String startStation, String endStation,
                                AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("start", startStation);
        params.put("end", endStation);
        getNet(TRAIN_STATION_QUERY_URI, params, asyncApiResponseHandler);
    }

    /**
     * 车次查询
     */
    public void numberQueryNet(String trainNumber,
                               AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("trainNumber", trainNumber);
        getNet(TRAIN_NUMBER_QUERY_URI, params, asyncApiResponseHandler);
    }

}
