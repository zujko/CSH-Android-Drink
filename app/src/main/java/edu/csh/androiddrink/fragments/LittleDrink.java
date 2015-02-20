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

import java.util.ArrayList;

import edu.csh.androiddrink.DrinkAdapter;
import edu.csh.androiddrink.jsonjavaobjects.ItemInfo;
import edu.csh.androiddrink.backgroundtasks.DropDrinkAsync;
import edu.csh.androiddrink.backgroundtasks.GetMachineItems;
import edu.csh.androiddrink.backgroundtasks.GetUserInfo;
import edu.csh.androiddrink.interfaces.MachineDataOnComplete;

public class LittleDrink extends ListFragment implements MachineDataOnComplete {

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
        final ItemInfo item = itemInfoArrayList.get(position);
        alert.setTitle("Drop a drink?");
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
                DropDrinkAsync drop = new DropDrinkAsync("1", item.getSlotNum(),text.getText().toString(),getActivity());
                drop.execute();
                GetUserInfo info = new GetUserInfo(null,null,getActivity());
                info.execute();
            }
        });
        alert.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        GetMachineItems sync = new GetMachineItems(this,2);
        sync.execute();
    }

    @Override
    public void onComplete(ArrayList<ItemInfo> items) {
        itemInfoArrayList = items;
        DrinkAdapter adapter = new DrinkAdapter(getActivity(),items);
        setListAdapter(adapter);
    }
}

