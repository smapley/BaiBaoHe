package com.smapley.baibaohe.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	List<Fragment> list = new ArrayList<Fragment>();
	public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		this.list = list;
	}

	public Fragment getItem(int arg0) {
		
		return list.get(arg0);
	}

	public int getCount() {
		return list.size();
	}

}
