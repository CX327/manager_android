package org.com.manager.bean;

import org.com.manager.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.hua on 2016/3/21.

 * 天气
 */
public class Weather implements Serializable {

    /**
     * 更新时间
     */
    private String reportTime;
    /**
     * 现在温度
     */
    private String currentTemp;
    /**
     * 现在天气
     */
    private String currentWeather;


    /**
     *省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;


    /**
     * 日期
     */
    private String date;
    /**
     * 周几
     */
    private String week;
    /**
     * 白天温度
     */
    private String dayTemp;
    /**
     * 晚上温度
     */
    private String nightTemp;

    /**
     * 白天天气
     */
    private String dayWeather;
    /**
     * 夜晚天气
     */
    private String nightWeather;
    /**
     * 白天风
     */
    private String dayWindDir;
    private String dayWindPower;
    /**
     * 晚上风
     */
    private String nightWindDir;
    private String nightWindPower;


    public static final String WEATHER_ICON_ID = "weather_icon_id";
    public static final String WEATHER_BG_ID = "weather_bg_id";
    public static final String WEATHER_TEMP = "weather_temp";
    public static final String WEATHER_CITY = "weather_city";
    public static final String WEATHER_HUMIDITY = "weather_humidity";
    public static final String WEATHER_WIND = "weather_windDir";
    public static final String WEATHER_STATE = "weather_state";

    public Weather() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(String dayTemp) {
        this.dayTemp = dayTemp;
    }

    public String getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(String nightTemp) {
        this.nightTemp = nightTemp;
    }

    public String getDayWeather() {
        return dayWeather;
    }

    public void setDayWeather(String dayWeather) {
        this.dayWeather = dayWeather;
    }

    public String getNightWeather() {
        return nightWeather;
    }

    public void setNightWeather(String nightWeather) {
        this.nightWeather = nightWeather;
    }

    public String getDayWindDir() {
        return dayWindDir;
    }

    public void setDayWindDir(String dayWindDir) {
        this.dayWindDir = dayWindDir;
    }

    public String getDayWindPower() {
        return dayWindPower;
    }

    public void setDayWindPower(String dayWindPower) {
        this.dayWindPower = dayWindPower;
    }

    public String getNightWindDir() {
        return nightWindDir;
    }

    public void setNightWindDir(String nightWindDir) {
        this.nightWindDir = nightWindDir;
    }

    public String getNightWindPower() {
        return nightWindPower;
    }

    public void setNightWindPower(String nightWindPower) {
        this.nightWindPower = nightWindPower;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(String currentWeather) {
        this.currentWeather = currentWeather;
    }
    public Weather(String province, String city, String date,
                   String week, String dayTemp, String nightTemp,
                   String dayWeather, String nightWeather, String dayWindDir,
                   String dayWindPower, String nightWindDir, String nightWindPower) {
        this.province = province;
        this.city = city;
        this.date = date;
        this.week = week;
        this.dayTemp = dayTemp;
        this.nightTemp = nightTemp;
        this.dayWeather = dayWeather;
        this.nightWeather = nightWeather;
        this.dayWindDir = dayWindDir;
        this.dayWindPower = dayWindPower;
        this.nightWindDir = nightWindDir;
        this.nightWindPower = nightWindPower;
    }

    public Weather(String reportTime, String currentTemp, String currentWeather,
                   String province, String city, String date,
                   String week, String dayTemp, String nightTemp,
                   String dayWeather, String nightWeather, String dayWindDir,
                   String dayWindPower, String nightWindDir, String nightWindPower) {
        this.reportTime = reportTime;
        this.currentTemp = currentTemp;
        this.currentWeather = currentWeather;
        this.province = province;
        this.city = city;
        this.date = date;
        this.week = week;
        this.dayTemp = dayTemp;
        this.nightTemp = nightTemp;
        this.dayWeather = dayWeather;
        this.nightWeather = nightWeather;
        this.dayWindDir = dayWindDir;
        this.dayWindPower = dayWindPower;
        this.nightWindDir = nightWindDir;
        this.nightWindPower = nightWindPower;
    }

    public Map<String, Object> getWeatherInfo(String weather,
                                              String temperature, String city, String wind_dir,
                                              String wind_power, String humidity) {
        Map<String, Object> weatherMap = new HashMap();
        weatherMap.put(WEATHER_ICON_ID, getWeatherIcon(weather));
        weatherMap.put(WEATHER_BG_ID, getWeatherBg(weather));
        weatherMap.put(WEATHER_TEMP, temperature + "℃");
        weatherMap.put(WEATHER_CITY, city);
        weatherMap.put(WEATHER_WIND, wind_dir + "风 " + wind_power + "级");
        weatherMap.put(WEATHER_HUMIDITY, "湿度 " + humidity + "%");
        weatherMap.put(WEATHER_STATE, weather);
        return weatherMap;
    }

    public int getWeatherIcon(String weather) {
        int id = R.mipmap.ic_weather_duoyun;
        if (weather.contains("晴")) {
            id = R.mipmap.ic_weather_baitianqing;
        } else if (weather.contains("多云")) {
            id = R.mipmap.ic_weather_duoyun;
        } else if (weather.contains("阴")) {
            id = R.mipmap.ic_weather_yin;
        } else if (weather.contains("阵雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("雷阵雨")) {
            id = R.mipmap.ic_weather_leiyu;
        } else if (weather.contains("雷阵雨并伴有冰雹")) {
            id = R.mipmap.ic_weather_bingbao;
        } else if (weather.contains("雨夹雪")) {
            id = R.mipmap.ic_weather_yuxue;
        } else if (weather.contains("小雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("中雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("大雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("暴雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("大暴雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("特大暴雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("阵雪")) {
            id = R.mipmap.ic_weather_xue;
        } else if (weather.contains("小雪")) {
            id = R.mipmap.ic_weather_xue;
        } else if (weather.contains("中雪")) {
            id = R.mipmap.ic_weather_xue;
        } else if (weather.contains("大雪")) {
            id = R.mipmap.ic_weather_xue;
        } else if (weather.contains("暴雪")) {
            id = R.mipmap.ic_weather_xue;
        } else if (weather.contains("雾")) {
            id = R.mipmap.ic_weather_wumai;
        } else if (weather.contains("冻雨")) {
            id = R.mipmap.ic_weather_bingdong;
        } else if (weather.contains("沙尘暴")) {
            id = R.mipmap.ic_weather_shacheng;
        } else if (weather.contains("小雨-中雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("中雨-大雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("大雨-暴雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("暴雨-大暴雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("大暴雨-特大暴雨")) {
            id = R.mipmap.ic_weather_yu;
        } else if (weather.contains("小雪-中雪")) {
            id = R.mipmap.ic_weather_xue;
        } else if (weather.contains("中雪-大雪")) {
            id = R.mipmap.ic_weather_xue;
        } else if (weather.contains("大雪-暴雪")) {
            id = R.mipmap.ic_weather_xue;
        } else if (weather.contains("浮尘")) {
            id = R.mipmap.ic_weather_shacheng;
        } else if (weather.contains("扬沙")) {
            id = R.mipmap.ic_weather_shacheng;
        } else if (weather.contains("强沙尘暴")) {
            id = R.mipmap.ic_weather_shacheng;
        } else if (weather.contains("飑")) {
            id = R.mipmap.ic_weather_bingbao;
        } else if (weather.contains("龙卷风")) {
            id = R.mipmap.ic_weather_feng;
        } else if (weather.contains("弱高吹雪")) {
            id = R.mipmap.ic_weather_xue;
        } else if (weather.contains("轻霾")) {
            id = R.mipmap.ic_weather_wumai;
        } else if (weather.contains("霾")) {
            id = R.mipmap.ic_weather_wumai;
        }
        return id;
    }
    public int getForecastBg(String weather) {
        int id = R.mipmap.bg_fine;
        if (weather.contains("晴")) {
            id = R.mipmap.bg_fine;
        } else if (weather.contains("多云")) {
            id = R.mipmap.bg_cloudy;
        } else if (weather.contains("阴")) {
            id = R.mipmap.bg_yin;
        } else if (weather.contains("阵雨")) {
            id = R.mipmap.bg_heavy_rain_night;
        } else if (weather.contains("雷阵雨")) {
            id = R.mipmap.bg_heavy_rain_night;
        } else if (weather.contains("雷阵雨并伴有冰雹")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("雨夹雪")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("小雨")) {
            id = R.mipmap.bg_moderate_rain_day;
        } else if (weather.contains("中雨")) {
            id = R.mipmap.bg_moderate_rain_day;
        } else if (weather.contains("大雨")) {
            id = R.mipmap.bg_heavy_rain_night;
        } else if (weather.contains("暴雨")) {
            id = R.mipmap.bg_heavy_rain_night;
        } else if (weather.contains("大暴雨")) {
            id = R.mipmap.bg_heavy_rain_night;
        } else if (weather.contains("特大暴雨")) {
            id = R.mipmap.bg_heavy_rain_night;
        } else if (weather.contains("阵雪")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("小雪")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("中雪")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("大雪")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("暴雪")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("雾")) {
            id = R.mipmap.bg_fog_day;
        } else if (weather.contains("冻雨")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("沙尘暴")) {
            id = R.mipmap.bg_fog_and_haze;
        } else if (weather.contains("小雨-中雨")) {
            id = R.mipmap.bg_moderate_rain_day;
        } else if (weather.contains("中雨-大雨")) {
            id = R.mipmap.bg_moderate_rain_day;
        } else if (weather.contains("大雨-暴雨")) {
            id = R.mipmap.bg_heavy_rain_night;
        } else if (weather.contains("暴雨-大暴雨")) {
            id = R.mipmap.bg_heavy_rain_night;
        } else if (weather.contains("大暴雨-特大暴雨")) {
            id = R.mipmap.bg_heavy_rain_night;
        } else if (weather.contains("小雪-中雪")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("中雪-大雪")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("大雪-暴雪")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("浮尘")) {
            id = R.mipmap.bg_fog_and_haze;
        } else if (weather.contains("扬沙")) {
            id = R.mipmap.bg_fog_and_haze;
        } else if (weather.contains("强沙尘暴")) {
            id = R.mipmap.bg_fog_and_haze;
        } else if (weather.contains("飑")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("龙卷风")) {
            id = R.mipmap.bg_fog_and_haze;
        } else if (weather.contains("弱高吹雪")) {
            id = R.mipmap.bg_snow;
        } else if (weather.contains("轻霾")) {
            id = R.mipmap.bg_fog_and_haze;
        } else if (weather.contains("霾")) {
            id = R.mipmap.bg_fog_and_haze;
        }
        return id;
    }
    private int getWeatherBg(String weather) {
        int bgColor = 0xff89cfe9;
        if (weather.contains("晴")) {
            bgColor = 0xffffb250;
        } else if (weather.contains("多云")) {
            bgColor = 0xff89cfe9;
        } else if (weather.contains("阴")) {
            bgColor = 0xffa4aeb7;
        } else if (weather.contains("阵雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("雷阵雨")) {
            bgColor = 0xff2e4459;
        } else if (weather.contains("雷阵雨并伴有冰雹")) {
            bgColor = 0xffb5ccd2;
        } else if (weather.contains("雨夹雪")) {
            bgColor = 0xff7b95a6;
        } else if (weather.contains("小雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("中雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("大雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("暴雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("大暴雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("特大暴雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("阵雪")) {
            bgColor = 0xff97b2c5;
        } else if (weather.contains("小雪")) {
            bgColor = 0xff97b2c5;
        } else if (weather.contains("中雪")) {
            bgColor = 0xff97b2c5;
        } else if (weather.contains("大雪")) {
            bgColor = 0xff97b2c5;
        } else if (weather.contains("暴雪")) {
            bgColor = 0xff97b2c5;
        } else if (weather.contains("雾")) {
            bgColor = 0xffd0e4eb;
        } else if (weather.contains("冻雨")) {
            bgColor = 0xffe3ecdb;
        } else if (weather.contains("沙尘暴")) {
            bgColor = 0xffb1ada2;
        } else if (weather.contains("小雨-中雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("中雨-大雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("大雨-暴雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("暴雨-大暴雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("大暴雨-特大暴雨")) {
            bgColor = 0xff577388;
        } else if (weather.contains("小雪-中雪")) {
            bgColor = 0xff97b2c5;
        } else if (weather.contains("中雪-大雪")) {
            bgColor = 0xff97b2c5;
        } else if (weather.contains("大雪-暴雪")) {
            bgColor = 0xff97b2c5;
        } else if (weather.contains("浮尘")) {
            bgColor = 0xffb1ada2;
        } else if (weather.contains("扬沙")) {
            bgColor = 0xffb1ada2;
        } else if (weather.contains("强沙尘暴")) {
            bgColor = 0xffb1ada2;
        } else if (weather.contains("飑")) {
            bgColor = 0xffb5ccd2;
        } else if (weather.contains("龙卷风")) {
            bgColor = 0xffe0f4ff;
        } else if (weather.contains("弱高吹雪")) {
            bgColor = 0xff97b2c5;
        } else if (weather.contains("轻霾")) {
            bgColor = 0xffd0e4eb;
        } else if (weather.contains("霾")) {
            bgColor = 0xffd0e4eb;
        }
        return bgColor;
    }

    public String getWeatherWeek(String weekCode) {
        if (weekCode.equals("1")) {
            return "周一";
        } else if (weekCode.equals("2")) {
            return "周二";
        } else if (weekCode.equals("3")) {
            return "周三";
        } else if (weekCode.equals("4")) {
            return "周四";
        } else if (weekCode.equals("5")) {
            return "周五";
        } else if (weekCode.equals("6")) {
            return "周六";
        } else if (weekCode.equals("7")) {
            return "周日";
        } else {
            return "";
        }
    }

}
