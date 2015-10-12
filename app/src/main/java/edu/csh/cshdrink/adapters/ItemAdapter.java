package edu.csh.cshdrink.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.csh.androiddrink.R;
import edu.csh.cshdrink.models.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public List<Item> mItems;

    public ItemAdapter(List<Item> items) {
        if(items == null) {
            mItems = new ArrayList<>();
        } else{
            mItems = items;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = mItems.get(position);
        if(item.isAvailable() && item.isEnabled()) {
            holder.itemTextView.setTextColor(Color.parseColor("#000000"));
            holder.itemPriceTextView.setTextColor(Color.parseColor("#000000"));
        } else{
            holder.itemTextView.setTextColor(Color.parseColor("#9B9B9B"));
            holder.itemPriceTextView.setTextColor(Color.parseColor("#9B9B9B"));
        }

        holder.itemTextView.setText(item.item_name);
        holder.itemPriceTextView.setText(item.item_price);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItems(List<Item> items) {
        mItems.addAll(items);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.drink_item_textview) TextView itemTextView;
        @Bind(R.id.drink_item_price_textview) TextView itemPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
