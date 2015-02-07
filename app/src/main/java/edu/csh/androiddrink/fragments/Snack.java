package edu.csh.androiddrink.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.csh.androiddrink.interfaces.MachineDataOnComplete;
import edu.csh.androiddrink.DrinkAdapter;
import edu.csh.androiddrink.backgroundtasks.GetMachineItems;
import edu.csh.androiddrink.ItemInfo;

public class Snack extends ListFragment implements MachineDataOnComplete {

    ArrayList<ItemInfo> itemInfoArrayList;

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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Drop a snack?");
        alert.setMessage("You are about to drop a "+itemInfoArrayList.get(position).getItemName()+
                "\n\nEnter seconds until drop");
        final EditText text = new EditText(getActivity());
        text.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(text);
        alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton("Drop!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: Send POST request to drop drink
                Toast.makeText(getActivity(), "DROPPED", Toast.LENGTH_LONG).show();
            }
        });
        alert.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        GetMachineItems sync = new GetMachineItems(this,3);
        sync.execute();

    }

    @Override
    public void onComplete(ArrayList<ItemInfo> items) {
        itemInfoArrayList = items;
        DrinkAdapter adapter = new DrinkAdapter(getActivity(),items);
        setListAdapter(adapter);
    }
}

