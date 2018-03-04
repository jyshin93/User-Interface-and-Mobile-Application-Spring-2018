package com.example.jinshin.watertracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by jinshin on 04/03/2018.
 */

public class PageAdapter extends FragmentStatePagerAdapter{


    int mNoOfTabs;

    public PageAdapter (FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                DailyDetail dailyDetail = new DailyDetail();
                return dailyDetail;
            case 1:
                DailyTotal dailyTotal = new DailyTotal();
                return dailyTotal;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
