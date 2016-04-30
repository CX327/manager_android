package org.com.manager.weather;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.com.manager.R;
import org.com.manager.bean.Weather;
import org.com.manager.util.FrameUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeatherForecastActivity extends Activity {

    @Bind(R.id.forecast_layout)
    LinearLayout forecastLayout;

    @Bind(R.id.forecast_line)
    LineChart mLineChart;

    /*星期*/
    @Bind(R.id.week_one)
    TextView weekOne;

    @Bind(R.id.week_two)
    TextView weekTwo;

    @Bind(R.id.week_three)
    TextView weekThree;

    @Bind(R.id.week_four)
    TextView weekFour;

    /*日期*/
    @Bind(R.id.date_one)
    TextView dateOne;

    @Bind(R.id.date_two)
    TextView dateTwo;

    @Bind(R.id.date_three)
    TextView dateThree;

    @Bind(R.id.date_four)
    TextView dateFour;
    /*白天天气*/
    @Bind(R.id.top_weather_one)
    TextView topWeatherOne;

    @Bind(R.id.top_weather_two)
    TextView topWeatherTwo;

    @Bind(R.id.top_weather_three)
    TextView topWeatherThree;

    @Bind(R.id.top_weather_four)
    TextView topWeatherFour;

    /*白天天气图片*/
    @Bind(R.id.top_weather_img_one)
    ImageView topWeatherImgOne;

    @Bind(R.id.top_weather_img_two)
    ImageView topWeatherImgTwo;

    @Bind(R.id.top_weather_img_three)
    ImageView topWeatherImgThree;

    @Bind(R.id.top_weather_img_four)
    ImageView topWeatherImgFour;

    /*夜晚天气图片*/
    @Bind(R.id.bottom_weather_img_one)
    ImageView bottomWeatherImgOne;

    @Bind(R.id.bottom_weather_img_two)
    ImageView bottomWeatherImgTwo;

    @Bind(R.id.bottom_weather_img_three)
    ImageView bottomWeatherImgThree;

    @Bind(R.id.bottom_weather_img_four)
    ImageView bottomWeatherImgFour;

    /*夜晚天气*/
    @Bind(R.id.bottom_weather_one)
    TextView bottomWeatherOne;

    @Bind(R.id.bottom_weather_two)
    TextView bottomWeatherTwo;

    @Bind(R.id.bottom_weather_three)
    TextView bottomWeatherThree;

    @Bind(R.id.bottom_weather_four)
    TextView bottomWeatherFour;


    /*风向*/
    @Bind(R.id.winddir_one)
    TextView winddirOne;

    @Bind(R.id.winddir_two)
    TextView winddirTwo;

    @Bind(R.id.winddir_three)
    TextView winddirThree;

    @Bind(R.id.winddir_four)
    TextView winddirFour;

    /*风力*/
    @Bind(R.id.windpower_one)
    TextView windpowerOne;

    @Bind(R.id.windpower_two)
    TextView windpowerTwo;

    @Bind(R.id.windpower_three)
    TextView windpowerThree;

    @Bind(R.id.windpower_four)
    TextView windpowerFour;

    /*当天天气情况*/
    @Bind(R.id.city)
    TextView city;
    @Bind(R.id.today_day_temp)
    TextView todayDayTemp;

    @Bind(R.id.report_time)
    TextView reportTime;

    @Bind(R.id.today_day_weather)
    TextView todayDayWeather;
    @Bind(R.id.today_night_weather)
    TextView todayNightWeather;
    @Bind(R.id.today_day_weather_img)
    ImageView todayDayWeatherImg;
    @Bind(R.id.today_night_weather_img)
    ImageView todayNightWeatherImg;

    private List<Weather> forecastList;
    private ArrayList<String> xValues;
    private ArrayList<Entry> yDayValues;
    private ArrayList<Entry> yNightValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        getWeatherDate();
        drawLineChart();
    }

    /**
     * 初始化天气数据
     */
    private void getWeatherDate() {
        forecastList = (List<Weather>) getIntent().getSerializableExtra(FrameUtils.IT_WEATHER_FORECAST_LIST);
        xValues = new ArrayList();
        yDayValues = new ArrayList();
        yNightValues = new ArrayList();
        for (int i = 0; i < forecastList.size(); i++) {
            Weather aMap = forecastList.get(i);
            /*白天温度*/
            float dayTemp;
            /**当天可能白天温度为空*/
            if (aMap.getDayTemp() != null && !aMap.getDayTemp().isEmpty()) {
                dayTemp = Float.parseFloat(aMap.getDayTemp());
            } else {
                dayTemp = Float.parseFloat(aMap.getCurrentTemp());
            }

            yDayValues.add(new Entry(dayTemp, i));
            /*晚上温度*/
            float nightTemp = Float.parseFloat(aMap.getNightTemp());
            yNightValues.add(new Entry(nightTemp, i));
            /*设置x坐标轴*/
            xValues.add(i + "");
        }
        Weather weather0 = forecastList.get(0);
        /*当日情况*/
        city.setText(weather0.getCity());
        todayDayTemp.setText(weather0.getCurrentTemp());
        todayDayWeather.setText(weather0.getCurrentWeather());
        todayNightWeather.setText(weather0.getNightWeather());
        reportTime.setText(weather0.getReportTime());
        todayDayWeatherImg.setImageResource(weather0.getWeatherIcon(weather0.getCurrentWeather()));
        todayNightWeatherImg.setImageResource(weather0.getWeatherIcon(weather0.getNightWeather()));
        /*设置背景图片*/
        forecastLayout.setBackgroundResource(weather0.getForecastBg(weather0.getCurrentWeather()));

        weekOne.setText(weather0.getWeatherWeek(weather0.getWeek()));
        dateOne.setText(weather0.getDate());
        topWeatherOne.setText(weather0.getCurrentWeather());
        topWeatherImgOne.setImageResource(weather0.getWeatherIcon(weather0.getCurrentWeather()));
        bottomWeatherImgOne.setImageResource(weather0.getWeatherIcon(weather0.getNightWeather()));
        bottomWeatherOne.setText(weather0.getNightWeather());
        winddirOne.setText(weather0.getNightWindDir());
        windpowerOne.setText(weather0.getNightWindPower());


        Weather weather1 = forecastList.get(1);
        weekTwo.setText(weather1.getWeatherWeek(weather1.getWeek()));
        dateTwo.setText(weather1.getDate());
        topWeatherTwo.setText(weather1.getDayWeather());
        topWeatherImgTwo.setImageResource(weather1.getWeatherIcon(weather1.getDayWeather()));
        bottomWeatherImgTwo.setImageResource(weather1.getWeatherIcon(weather1.getNightWeather()));
        bottomWeatherTwo.setText(weather1.getNightWeather());
        winddirTwo.setText(weather1.getDayWindDir());
        windpowerTwo.setText(weather1.getDayWindPower());

        Weather weather2 = forecastList.get(2);
        weekThree.setText(weather2.getWeatherWeek(weather2.getWeek()));
        dateThree.setText(weather2.getDate());
        topWeatherThree.setText(weather2.getDayWeather());
        topWeatherImgThree.setImageResource(weather2.getWeatherIcon(weather2.getDayWeather()));
        bottomWeatherImgThree.setImageResource(weather2.getWeatherIcon(weather2.getNightWeather()));
        bottomWeatherThree.setText(weather2.getNightWeather());
        winddirThree.setText(weather2.getDayWindDir());
        windpowerThree.setText(weather2.getDayWindPower());

        Weather weather3 = forecastList.get(3);
        weekFour.setText(weather3.getWeatherWeek(weather3.getWeek()));
        dateFour.setText(weather3.getDate());
        topWeatherFour.setText(weather3.getDayWeather());
        topWeatherImgFour.setImageResource(weather3.getWeatherIcon(weather3.getDayWeather()));
        bottomWeatherImgFour.setImageResource(weather3.getWeatherIcon(weather3.getNightWeather()));
        bottomWeatherFour.setText(weather3.getNightWeather());
        winddirFour.setText(weather3.getDayWindDir());
        windpowerFour.setText(weather3.getDayWindPower());
    }

    /**
     * 绘画折线图
     */
    private void drawLineChart() {
        LineData mLineData = getLineData();
        showChart(mLineChart, mLineData);
    }

    /**
     * 生成一个数据
     *
     * @return
     */
    private LineData getLineData() {

        // y轴的数据集合
        LineDataSet dayLineDataSet = new LineDataSet(yDayValues, "");

        //用y轴的集合来设置参数
        dayLineDataSet.setLineWidth(1.75f); // 线宽
        dayLineDataSet.setCircleSize(3f);// 显示的圆形大小
        dayLineDataSet.setColor(getResources().getColor(R.color.orange));// 折线的显示颜色
        dayLineDataSet.setValueTextColor(getResources().getColor(R.color.orange));//设置圆点数值的字体颜色
        dayLineDataSet.setValueTextSize(16);
        dayLineDataSet.setCircleColor(getResources().getColor(R.color.orange));// 圆形的颜色
        dayLineDataSet.setHighLightColor(Color.TRANSPARENT); // （点击网格时）高亮的线的颜色
        dayLineDataSet.disableDashedLine();

        // y轴的数据集合
        LineDataSet nightLineDataSet = new LineDataSet(yNightValues, "");

        //用y轴的集合来设置参数
        nightLineDataSet.setLineWidth(1.75f); // 线宽
        nightLineDataSet.setCircleSize(3f);// 显示的圆形大小
        nightLineDataSet.setColor(getResources().getColor(R.color.blue));// 折线的显示颜色
        nightLineDataSet.setValueTextColor(getResources().getColor(R.color.blue));//设置圆点数值的字体颜色
        nightLineDataSet.setValueTextSize(16);
        nightLineDataSet.setCircleColor(getResources().getColor(R.color.blue));// 圆形的颜色
        nightLineDataSet.setHighLightColor(Color.TRANSPARENT); // （点击网格时）高亮的线的颜色
        nightLineDataSet.disableDashedLine();

        ArrayList<LineDataSet> lineDataSets = new ArrayList();
        lineDataSets.add(dayLineDataSet);
        lineDataSets.add(nightLineDataSet);

        // create a data object with the datasets
        LineData lineData = new LineData(xValues, lineDataSets);

        return lineData;
    }

    // 设置显示的样式
    private void showChart(LineChart lineChart, LineData lineData) {

        // 数据描述,默认为“Description”
        lineChart.setDescription("");
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // 是否显示表格颜色
        lineChart.setDrawGridBackground(false);

        // 设置是否可以触摸
        lineChart.setTouchEnabled(true);

        // 是否可以拖拽
        lineChart.setDragEnabled(true);
        // 是否可以缩放
        lineChart.setScaleEnabled(true);

        // false时，缩放可以在x,y轴分别做
        lineChart.setPinchZoom(false);
        // 设置数据
        lineChart.setData(lineData); //
        // 设置比例图标示，就是那个一组y的value的
        Legend mLegend = lineChart.getLegend();
        mLegend.setForm(LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.RED);// 颜色
        //隐藏横网格线
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        //隐藏竖网格线
        lineChart.getXAxis().setDrawGridLines(false);
        //getXAxis()横轴，getAxisLeft()左边竖轴，getAxisRight()右边竖轴
        lineChart.getXAxis().setEnabled(false);
        lineChart.getAxisLeft().setAxisLineColor(Color.TRANSPARENT);
        lineChart.getAxisLeft().setTextColor(Color.TRANSPARENT);
        lineChart.getAxisRight().setAxisLineColor(Color.TRANSPARENT);
        lineChart.getAxisRight().setTextColor(Color.TRANSPARENT);
        //隐藏左下角的说明
        lineChart.getLegend().setEnabled(false);
        // 让x轴在下面
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        // 立即执行的动画,x轴
        lineChart.animateX(2500);
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
