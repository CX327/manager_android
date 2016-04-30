package org.com.manager.train;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.com.manager.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 列车主页面
 */
public class TrainActivity extends FragmentActivity {

    @Bind(android.R.id.tabhost)
    FragmentTabHost mTabHost;
    @Bind(R.id.train_title)
    TextView trainTitle;

    private static String[] mTabId = {"站次查询", "车次查询", "我的行程"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        ButterKnife.bind(this);
        initTab();
        mTabHost.setOnTabChangedListener(new tabChangedListener());
    }

    private void initTab() {
        mTabHost.setup(this, getSupportFragmentManager(),
                android.R.id.tabcontent);

        List<Class> fragmentList = new ArrayList<>();
        List<Integer> iconList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        fragmentList.add(StationQueryFragment.class);
        fragmentList.add(NumberQueryFragment.class);
        fragmentList.add(MyTripFragment.class);

        iconList.add(R.drawable.ic_train_station_query_selector);
        iconList.add(R.drawable.ic_train_number_query_selector);
        iconList.add(R.drawable.ic_train_trip_selector);

        titleList.add(mTabId[0]);
        titleList.add(mTabId[1]);
        titleList.add(mTabId[2]);
        for (int i = 0; i < fragmentList.size(); i++) {
            View view = LayoutInflater.from(this).inflate(
                    R.layout.frame_tab_indicator, null);
            ImageView imageView = (ImageView) view
                    .findViewById(R.id.frame_tab_indicator_image);
            imageView.setImageResource(iconList.get(i));

            TextView textView = (TextView) view
                    .findViewById(R.id.frame_tab_indicator_text);
            textView.setText(titleList.get(i));

            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(titleList.get(i))
                    .setIndicator(view);
            mTabHost.addTab(tabSpec, fragmentList.get(i), null);
        }
    }

    private class tabChangedListener implements TabHost.OnTabChangeListener {
        @Override
        public void onTabChanged(String tabId) {
            trainTitle.setText(tabId);
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
