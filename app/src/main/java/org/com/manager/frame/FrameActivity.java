package org.com.manager.frame;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocalDayWeatherForecast;
import com.amap.api.location.AMapLocalWeatherForecast;
import com.amap.api.location.AMapLocalWeatherListener;
import com.amap.api.location.AMapLocalWeatherLive;
import com.amap.api.location.LocationManagerProxy;

import org.com.manager.R;
import org.com.manager.accounting.AccountingActivity;
import org.com.manager.bean.Weather;
import org.com.manager.note.NoteActivity;
import org.com.manager.recipes.RecipesActivity;
import org.com.manager.train.TrainActivity;
import org.com.manager.util.FrameUtils;
import org.com.manager.bean.Plugin;
import org.com.manager.weather.WeatherForecastActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 */
public class FrameActivity extends Activity implements AMapLocalWeatherListener {
    @Bind(R.id.weather_layout)
    RelativeLayout weatherLayout;

    @Bind(R.id.weather_img)
    ImageView weatherImg;

    @Bind(R.id.weather_temperature)
    TextView temperatureText;

    @Bind(R.id.weather_weather)
    TextView weatherText;

    @Bind(R.id.weather_city)
    TextView cityText;

    @Bind(R.id.weather_humidity)
    TextView humidityText;

    @Bind(R.id.weather_windDir)
    TextView windDirText;

    @Bind(R.id.weather_windmill)
    ImageView windMillImg;

    @Bind(R.id.plugin_list_gridview)
    GridView frameApplicationGrid;

    private ProgressDialog progressDialog = null;
    /**
     * 各部件名称列表
     */
    private List<String> pluginNameList = new ArrayList();
    /**
     * 各部件图标列表
     */
    private List<Integer> pluginIconList = new ArrayList();
    /**
     * 各部件类名
     */
    private List<String> classNameList = new ArrayList();

    private List<Plugin> plugins;
    /**
     * 预测天气
     */
    private List<Weather> forecastList;
    /**
     * 高德地图
     */
    private LocationManagerProxy mLocationManagerProxy;
    /**
     * 是否退出
     */
    private static boolean isExit = false;

    private static final int UPDATE_FINISH = 0;
    /**
     * 更新时间
     */
    String reportTime;
    /**
     * 当前温度
     */
    String currentTemp;
    /**
     * 当前天气
     */
    String currentWeather;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_FINISH) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Intent intent = new Intent();
                intent.setClass(FrameActivity.this, WeatherForecastActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(FrameUtils.IT_WEATHER_FORECAST_LIST, (Serializable) forecastList);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
            } else {
                isExit = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        initPlugin();
        updateWeather();
        ArrayList frameItem = new ArrayList();
        for (int i = 0; i < plugins.size(); i++) {
            HashMap hashTmp = new HashMap();
            hashTmp.put("itemIcon", plugins.get(i).getIcon());
            hashTmp.put("itemName", plugins.get(i).getPluginName());
            frameItem.add(hashTmp);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(FrameActivity.this, frameItem,
                R.layout.frame_application_item,
                new String[]{"itemIcon", "itemName"},
                new int[]{R.id.item_icon, R.id.item_name});
        frameApplicationGrid.setAdapter(simpleAdapter);
        frameApplicationGrid.setOnItemClickListener(new itemOnClick());
    }

    /**
     * 初始化部件
     */
    private void initPlugin() {
        pluginNameList = Arrays.asList(getResources().getStringArray(R.array.frame_name));
        pluginIconList.add(R.mipmap.ic_frame_recipes);
        pluginIconList.add(R.mipmap.ic_frame_train);
        pluginIconList.add(R.mipmap.ic_frame_note);
        pluginIconList.add(R.mipmap.ic_frame_accounting);
        classNameList.add(RecipesActivity.class.getName());
        classNameList.add(TrainActivity.class.getName());
        classNameList.add(NoteActivity.class.getName());
        classNameList.add(AccountingActivity.class.getName());

        plugins = new ArrayList<>();
        for (int i = 0; i < pluginNameList.size(); i++) {
            Plugin plugin = new Plugin();
            plugin.setPluginName(pluginNameList.get(i));
            plugin.setClassName(classNameList.get(i));
            plugin.setIcon(pluginIconList.get(i));
            plugin.setPackageName(FrameActivity.this.getPackageName());
            plugins.add(plugin);
        }

    }

    /**
     * 更新天气信息
     */
    private void updateWeather() {
        Animation anim = AnimationUtils.loadAnimation(FrameActivity.this, R.anim.weather_windmill);
        LinearInterpolator lin = new LinearInterpolator();
        anim.setInterpolator(lin);
        windMillImg.startAnimation(anim);
        updateWeatherView();
        mLocationManagerProxy = LocationManagerProxy
                .getInstance(FrameActivity.this);
        mLocationManagerProxy.requestWeatherUpdates(
                LocationManagerProxy.WEATHER_TYPE_LIVE, this);
    }

    /**
     * 更新添加View
     */
    private void updateWeatherView() {
        SharedPreferences sp = FrameActivity.this.getSharedPreferences(
                FrameUtils.SP_WEATHER, Context.MODE_PRIVATE);
        currentWeather = sp.getString(FrameUtils.SP_WEATHER_STATE, "");
        currentTemp = sp.getString(FrameUtils.SP_WEATHER_TEMP, "");
        String city = sp.getString(FrameUtils.SP_WEATHER_CITY, "");
        String wind_dir = sp.getString(FrameUtils.SP_WEATHER_WINDDIR, "");
        String wind_power = sp.getString(FrameUtils.SP_WEATHER_WINDPOWER, "");
        String humidity = sp.getString(FrameUtils.SP_WEATHER_HUMIDITY, "");
        if (TextUtils.isEmpty(currentWeather)) {
            return;
        }
        Map<String, Object> weatherMap = new Weather().getWeatherInfo(
                currentWeather, currentTemp, city, wind_dir, wind_power, humidity);
        weatherImg.setImageResource((Integer) weatherMap
                .get(Weather.WEATHER_ICON_ID));
        weatherLayout.setBackgroundColor((Integer) weatherMap
                .get(Weather.WEATHER_BG_ID));
        weatherText.setText((String) weatherMap
                .get(Weather.WEATHER_STATE));
        cityText.setText((String) weatherMap.get(Weather.WEATHER_CITY));
        temperatureText.setText((String) weatherMap
                .get(Weather.WEATHER_TEMP));
        windDirText.setText((String) weatherMap.get(Weather.WEATHER_WIND));//风向和风力一起
        humidityText.setText((String) weatherMap.get(Weather.WEATHER_HUMIDITY));
    }

    /**
     * 当天温度
     */
    @Override
    public void onWeatherLiveSearched(AMapLocalWeatherLive aMapLocalWeatherLive) {
        if (aMapLocalWeatherLive != null
                && aMapLocalWeatherLive.getAMapException().getErrorCode() == 0) {
            String weather = aMapLocalWeatherLive.getWeather();//天气
            String temperature = aMapLocalWeatherLive.getTemperature();//温度
            String city = aMapLocalWeatherLive.getCity();// 城市
            String wind_dir = aMapLocalWeatherLive.getWindDir();// 风向
            String wind_power = aMapLocalWeatherLive.getWindPower();// 风力
            String humidity = aMapLocalWeatherLive.getHumidity();// 空气湿度
            reportTime = aMapLocalWeatherLive.getReportTime();//更新时间
            reportTime = reportTime.substring(11);
            saveWeatherInfo(weather, reportTime, temperature, city, wind_dir, wind_power,
                    humidity);
            updateWeatherView();
        } else {
            // 获取天气预报失败
            Toast.makeText(FrameActivity.this, "获取天气失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 保存天气到SharedPreferences
     */
    private void saveWeatherInfo(String weather, String reportTime, String temperature,
                                 String city, String wind_dir, String wind_power, String humidity) {
        SharedPreferences sp = FrameActivity.this.getSharedPreferences(
                FrameUtils.SP_WEATHER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(FrameUtils.SP_WEATHER_STATE, weather);
        editor.putString(FrameUtils.SP_WEATHER_TIME, reportTime);
        editor.putString(FrameUtils.SP_WEATHER_TEMP, temperature);
        editor.putString(FrameUtils.SP_WEATHER_CITY, city);
        editor.putString(FrameUtils.SP_WEATHER_WINDDIR, wind_dir);
        editor.putString(FrameUtils.SP_WEATHER_WINDPOWER, wind_power);
        editor.putString(FrameUtils.SP_WEATHER_HUMIDITY, humidity);
        editor.apply();


    }

    /**
     * 未来天气预报
     *
     * @param aMapLocalWeatherForecast
     */
    @Override
    public void onWeatherForecaseSearched(AMapLocalWeatherForecast aMapLocalWeatherForecast) {
        forecastList = new ArrayList<>();
        List<AMapLocalDayWeatherForecast> amapForecastList = aMapLocalWeatherForecast.getWeatherForecast();
        for (int i = 0; i < amapForecastList.size(); i++) {
            AMapLocalDayWeatherForecast amapWeather = amapForecastList.get(i);
            String date = amapWeather.getDate().substring(5);
            Weather weather;
            if (i == 0) {
                weather = new Weather(reportTime, currentTemp, currentWeather,
                        amapWeather.getProvince(), amapWeather.getCity(),
                        date, amapWeather.getWeek(),
                        amapWeather.getDayTemp(), amapWeather.getNightTemp(),
                        amapWeather.getDayWeather(), amapWeather.getNightWeather(),
                        amapWeather.getDayWindDir(), amapWeather.getDayWindPower(),
                        amapWeather.getNightWindDir(), amapWeather.getNightWindPower());
            } else {
                weather = new Weather(amapWeather.getProvince(), amapWeather.getCity(),
                        date, amapWeather.getWeek(),
                        amapWeather.getDayTemp(), amapWeather.getNightTemp(),
                        amapWeather.getDayWeather(), amapWeather.getNightWeather(),
                        amapWeather.getDayWindDir(), amapWeather.getDayWindPower(),
                        amapWeather.getNightWindDir(), amapWeather.getNightWindPower());
            }
            forecastList.add(weather);
        }
        handler.sendEmptyMessage(UPDATE_FINISH);
    }

    /**
     * 获得预测天气（监听器）
     */
    @OnClick(R.id.weather_img)
    public void goWeatherForecase() {
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.show();
        mLocationManagerProxy.requestWeatherUpdates(
                LocationManagerProxy.WEATHER_TYPE_FORECAST, this);
    }

    /**
     * gridView 监听器
     */
    class itemOnClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Plugin plugin = plugins.get(position);
            String pkg = plugin.getPackageName();
            String cls = plugin.getClassName();
            ComponentName componentName = new ComponentName(pkg, cls);
            Intent intent = new Intent();
            intent.setComponent(componentName);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
        }
    }

    /**
     * 退出
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            overridePendingTransition(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

