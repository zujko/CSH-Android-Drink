package edu.csh.androiddrink.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;

import edu.csh.androiddrink.interfaces.MachineDataOnComplete;
import edu.csh.androiddrink.DrinkAdapter;
import edu.csh.androiddrink.backgroundtasks.GetMachineItems;
import edu.csh.androiddrink.ItemInfo;

public class Snack extends ListFragment implements MachineDataOnComplete {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public ListAdapter getListAdapter() {
        return super.getListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        GetMachineItems sync = new GetMachineItems(this,3);
        sync.execute();

    }

    @Override
    public void onComplete(ArrayList<ItemInfo> items) {
        DrinkAdapter adapter = new DrinkAdapter(getActivity(),items);
        setListAdapter(adapter);
    }
}

