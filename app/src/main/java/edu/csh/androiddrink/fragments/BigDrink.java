package edu.csh.androiddrink.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.csh.androiddrink.R;

public class BigDrink extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState){
        View bigDrink = inflater.inflate(R.layout.fragment_big_drink, container,false);

        return bigDrink;
    }

}
