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

public class CustomAdapterGuest extends ArrayAdapter<User> {
    private Context context;
    private List<User> payments;
    private int layoutResID;

    public CustomAdapterGuest(Context context, int layoutResourceID, List<User> payments) {
        super(context, layoutResourceID, payments);
        this.context = context;
        this.payments = payments;
        this.layoutResID = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomAdapterGuest.UserHolder itemHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolder = new CustomAdapterGuest.UserHolder();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolder.name = (TextView) view.findViewById(R.id.item_guest_name);
            itemHolder.phone = (TextView) view.findViewById(R.id.item_guest_phone);

            view.setTag(itemHolder);

        } else {
            itemHolder = (CustomAdapterGuest.UserHolder) view.getTag();
        }

        final User pItem = payments.get(position);

        itemHolder.name.setText(pItem.getName());
        itemHolder.phone.setText(pItem.getPhone());

        return view;
    }

    private static class UserHolder {
        TextView name;
        TextView phone;
    }
}
