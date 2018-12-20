package com.example.monica.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Lista> {

    private Context context;
    private List<Lista> payments;
    private int layoutResID;

    public CustomAdapter(Context context, int layoutResourceID, List<Lista> payments) {
        super(context, layoutResourceID, payments);
        this.context = context;
        this.payments = payments;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolder.tTitle = (TextView) view.findViewById(R.id.item_list_title);
            itemHolder.tContent = (TextView) view.findViewById(R.id.item_list_content);
            itemHolder.lHeader = (RelativeLayout) view.findViewById(R.id.lHeader);

            view.setTag(itemHolder);

        } else {
            itemHolder = (ItemHolder) view.getTag();
        }

        final Lista pItem = payments.get(position);

        itemHolder.tTitle.setText(pItem.getTitle());
        itemHolder.tContent.setText(pItem.getContent());

        return view;
    }

    private static class ItemHolder {
        TextView tTitle;
        TextView tContent;
        RelativeLayout lHeader;
    }
}
