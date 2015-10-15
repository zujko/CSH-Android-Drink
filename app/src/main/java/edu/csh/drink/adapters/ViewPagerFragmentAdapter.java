package edu.csh.drink.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import edu.csh.drink.fragments.MachineFragment;

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    final int MACHINES_COUNT = 3;
    private String[] MACHINE_NAMES = new String[]{"Little Drink","Big Drink","Snack"};

    public ViewPagerFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return MachineFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return MACHINES_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MACHINE_NAMES[position];
    }
}
