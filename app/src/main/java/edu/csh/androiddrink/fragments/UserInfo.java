package edu.csh.androiddrink.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.csh.androiddrink.R;

public class UserInfo extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState){
        View userInfo = inflater.inflate(R.layout.fragment_user_info, container,false);

        return userInfo;
    }

}