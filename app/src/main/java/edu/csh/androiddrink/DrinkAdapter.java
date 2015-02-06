package edu.csh.androiddrink;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DrinkAdapter extends ArrayAdapter<ItemInfo> {
    private final Context context;
    private final ArrayList<ItemInfo> item;


    public DrinkAdapter(Context context, ArrayList<ItemInfo> item) {
        super(context, R.layout.list_layout, item);
        this.context = context;
        this.item = item;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder = null;

        if(rowView == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_layout, parent,false);

            holder = new ViewHolder();
            holder.itemName = (TextView)rowView.findViewById(R.id.firstLine);
            holder.itemPrice = (TextView)rowView.findViewById(R.id.secondLine);

            rowView.setTag(holder);
        }else{
            holder = (ViewHolder)rowView.getTag();
        }

        ItemInfo items = item.get(position);

        holder.itemName.setText(items.getItemName());
        holder.itemPrice.setText("Price: " + items.getItemPrice());
        return rowView;
    }

    static class ViewHolder{
        TextView itemName;
        TextView itemPrice;
    }

    @Override
    public int getCount() {
        return item.size();
    }
}
