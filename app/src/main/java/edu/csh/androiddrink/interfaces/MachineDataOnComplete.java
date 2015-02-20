package edu.csh.androiddrink.interfaces;

import java.util.ArrayList;

import edu.csh.androiddrink.jsonjavaobjects.ItemInfo;

public interface MachineDataOnComplete {
    public void onComplete(ArrayList<ItemInfo> items);
}
