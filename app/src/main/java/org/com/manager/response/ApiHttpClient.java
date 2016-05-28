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
    public static final String USER_CHANGE_PW = HOME_PORT + "user/changePw.do";
    public static final String USER_CHANGE_NAME = HOME_PORT + "user/changeName.do";
    public static final String USER_REGISTER = HOME_PORT + "user/register.do";
    public static final String USER_LOGIN = HOME_PORT + "user/login.do";
    public static final String NOTE_LIST = HOME_PORT + "note/noteList.do";
    public static final String REMIND_NOTE_LIST = HOME_PORT + "note/remindNoteList.do";
    public static final String NOTE_ADD = HOME_PORT + "note/add.do";
    public static final String NOTE_UPDATE = HOME_PORT + "note/update.do";
    public static final String NOTE_DELETE = HOME_PORT + "note/delete.do";
    public static final String CONSUME_LIST = HOME_PORT + "accounting/accountingList.do";
    public static final String CONSUME_ADD = HOME_PORT + "accounting/add.do";


    /**
     * get
     */
    private void getNet(String uri, RequestParams params,
                        AsyncApiResponseHandler asyncApiResponseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        uri = "http://" + ManagerApplication.getInstance().getHomeUri() + uri;
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
    public void recipesRecommendNet(int userId, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        getNet(RECIPES_RECOMMEND_URI, params, asyncApiResponseHandler);
    }

    /**
     * 根据flag获得食谱
     */
    public void recipesFlagNet(int userId, String flagId, int start, int size,
                               AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("flag", flagId);
        params.put("start", start);
        params.put("size", size);
        getNet(RECIPES_FLAG_URI, params, asyncApiResponseHandler);
    }

    /**
     * 根据key获得食谱
     */
    public void keyQueryNet(int userId, String key, int start, int size,
                            AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("key", key);
        params.put("start", start);
        params.put("size", size);
        getNet(RECIPES_SEARCH_URI, params, asyncApiResponseHandler);
    }

    /**
     * 检查是否收藏
     */
    public void checkCollectionNet(int userId, String recipesId, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("recipesId", recipesId);
        getNet(RECIPES_CHECK_COLLECTION_URI, params, asyncApiResponseHandler);
    }

    /**
     * 新增收藏
     */
    public void saveCollectionNet(int userId, String recipesId, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("recipesId", recipesId);
        getNet(RECIPES_SAVE_COLLECTION_URI, params, asyncApiResponseHandler);
    }

    /**
     * 删除收藏
     */
    public void deleteCollectionNet(int userId, String recipesId, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("recipesId", recipesId);
        getNet(RECIPES_DELETE_COLLECTION_URI, params, asyncApiResponseHandler);
    }

    /**
     * 全部收藏
     */
    public void allCollectionNet(int userId, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        getNet(RECIPES_ALL_COLLECTION_URI, params, asyncApiResponseHandler);
    }

    /**
     * 全国全部站点列表
     */
    public void allStationListNet(int userId, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        getNet(TRAIN_NUMBER_STATION_LIST_URI, params, asyncApiResponseHandler);
    }

    /**
     * 站站查询
     */
    public void stationQueryNet(int userId, String startStation, String endStation,
                                AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("start", startStation);
        params.put("end", endStation);
        getNet(TRAIN_STATION_QUERY_URI, params, asyncApiResponseHandler);
    }

    /**
     * 车次查询
     */
    public void numberQueryNet(int userId, String trainNumber,
                               AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("trainNumber", trainNumber);
        getNet(TRAIN_NUMBER_QUERY_URI, params, asyncApiResponseHandler);
    }

    /**
     * 修改密码
     */
    public void changePwNet(int userId, String oldPw, String newPw,
                            AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("oldPw", oldPw);
        params.put("newPw", newPw);
        getNet(USER_CHANGE_PW, params, asyncApiResponseHandler);
    }

    /**
     * 修改昵称
     */
    public void changeNameNet(int userId, String newName,
                              AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("newName", newName);
        getNet(USER_CHANGE_NAME, params, asyncApiResponseHandler);
    }

    /**
     * 注册
     */
    public void registerNet(String name, String password,
                            AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("password", password);
        getNet(USER_REGISTER, params, asyncApiResponseHandler);
    }

    /**
     * 修改密码
     */
    public void loginNet(String name, String password,
                         AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("password", password);
        getNet(USER_LOGIN, params, asyncApiResponseHandler);
    }

    /**
     * 便签列表
     */
    public void noteListNet(int userId, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        getNet(NOTE_LIST, params, asyncApiResponseHandler);
    }

    /**
     * 便签列表
     */
    public void noteRemindListNet(int userId, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        getNet(REMIND_NOTE_LIST, params, asyncApiResponseHandler);
    }

    /**
     * 便签添加
     */
    public void noteAddNet(int userId, String noteTitle,
                           String noteTime, String noteContent, String noteRemindTime,
                           AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("noteTitle", noteTitle);
        params.put("noteTime", noteTime);
        params.put("noteContent", noteContent);
        params.put("noteRemindTime", noteRemindTime);
        getNet(NOTE_ADD, params, asyncApiResponseHandler);
    }

    /**
     * 便签添加
     */
    public void noteUpdateNet(int userId, int noteId, String noteTitle,
                              String noteTime, String noteContent, String noteRemindTime,
                              AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("id", noteId);
        params.put("noteTitle", noteTitle);
        params.put("noteTime", noteTime);
        params.put("noteContent", noteContent);
        params.put("noteRemindTime", noteRemindTime);
        getNet(NOTE_UPDATE, params, asyncApiResponseHandler);
    }

    /**
     * 便签删除
     */
    public void noteDeleteNet(int userId, int noteId,
                              AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("noteId", noteId);
        getNet(NOTE_DELETE, params, asyncApiResponseHandler);
    }

    /**
     * 消费添加
     */
    public void consumeAddNet(int userId, String consumeTime, float consumeMoney,
                              boolean consumeIsPay, int consumeTypeId,
                              String remarks, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("consumeTime", consumeTime);
        params.put("consumeMoney", consumeMoney);
        params.put("consumeIsPay", consumeIsPay);
        params.put("consumeTypeId", consumeTypeId);
        params.put("remarks", remarks);
        getNet(CONSUME_ADD, params, asyncApiResponseHandler);
    }

    /**
     * 消费列表
     */
    public void consumeListNet(int userId, String accountingMonth, AsyncApiResponseHandler asyncApiResponseHandler) {
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("accountingMonth", accountingMonth);
        getNet(CONSUME_LIST, params, asyncApiResponseHandler);
    }
}
