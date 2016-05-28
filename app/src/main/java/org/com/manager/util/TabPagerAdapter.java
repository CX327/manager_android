package org.com.manager.util;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//FragmentStatePagerAdapter

public class TabPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> list;
	private List<String> titleList;

	public TabPagerAdapter(FragmentManager fm, List<Fragment> list,
			List<String> titleList) {
		super(fm);
		this.list = list;
		this.titleList = titleList;
	}

	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	// Title中显示的内容
	@Override
	public CharSequence getPageTitle(int position) {

		// return (titleList.size() > position) ? titleList.get(position) : "";
		return titleList.get(position);
	}

}
