package edu.csh.androiddrink;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import edu.csh.androiddrink.fragments.BigDrink;
import edu.csh.androiddrink.fragments.LittleDrink;
import edu.csh.androiddrink.fragments.Snack;


public class TabPageAdapter extends FragmentStatePagerAdapter{
    public TabPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return "Big Drink";
            case 1:
                return "Little Drink";
            case 2:
                return "Snack";
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new BigDrink();
            case 1:
                return new LittleDrink();
            case 2:
                return new Snack();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
