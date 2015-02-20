package edu.csh.androiddrink;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.securepreferences.SecurePreferences;

import java.util.ArrayList;

public class DrinkAdapter extends ArrayAdapter<ItemInfo> {
    private final Context context;
    private final ArrayList<ItemInfo> item;
    private SecurePreferences prefs;


    public DrinkAdapter(Context context, ArrayList<ItemInfo> item) {
        super(context, R.layout.list_layout, item);
        this.context = context;
        this.item = item;
        prefs = new SecurePreferences(context,"UserData","key",true);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder = null;
        String credits = prefs.getString("credits");
        int creditsInt = Integer.parseInt(credits);
        String itemPr = item.get(position).getItemPrice();
        int itemPrice = Integer.parseInt(itemPr);
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
        if(creditsInt < itemPrice || items.getAvailable().equals("0")){
            holder.itemName.setTextColor(Color.parseColor("#868686"));
            holder.itemPrice.setTextColor(Color.parseColor("#868686"));
            rowView.setEnabled(true);
            rowView.setClickable(true);
            holder.itemPrice.setText("Price: " + itemPr);
        } else{
            holder.itemName.setTextColor(Color.parseColor("#FFFFFF"));
            holder.itemPrice.setTextColor(Color.parseColor("#FFFFFF"));
            rowView.setEnabled(false);
            rowView.setClickable(false);
            holder.itemPrice.setText("Price: " + itemPr);
        }
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
