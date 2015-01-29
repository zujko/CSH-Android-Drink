package edu.csh.androiddrink.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.csh.androiddrink.R;

public class LittleDrink extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View littleDrink = inflater.inflate(R.layout.fragment_little_drink, container,false);

        return littleDrink;
    }

}