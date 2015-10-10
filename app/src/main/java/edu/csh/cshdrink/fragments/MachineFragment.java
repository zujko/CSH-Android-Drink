package edu.csh.cshdrink.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.csh.androiddrink.R;

public class MachineFragment extends Fragment {
    public static final String MACHINE_ID = "MACHINE_ID";
    private String machineId;

    public static MachineFragment newInstance(int machineId) {
        Bundle args = new Bundle();
        args.putInt(MACHINE_ID, machineId);
        MachineFragment fragment = new MachineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_machine_layout,container,false);
        return rootView;
    }
}
