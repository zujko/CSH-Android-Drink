package edu.csh.androiddrink;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.csh.androiddrink.fragments.BigDrink;
import edu.csh.androiddrink.fragments.LittleDrink;
import edu.csh.androiddrink.fragments.Snack;
import edu.csh.androiddrink.fragments.UserInfo;

public class TabPageAdapter extends FragmentStatePagerAdapter{
    public TabPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new UserInfo();
            case 1:
                return new BigDrink();
            case 2:
                return new LittleDrink();
            case 3:
                return new Snack();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
