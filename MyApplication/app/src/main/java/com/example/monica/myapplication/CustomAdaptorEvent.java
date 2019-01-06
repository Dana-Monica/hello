package com.example.monica.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomAdaptorEvent extends ArrayAdapter<EventElement> {

    private Context context;
    private List<EventElement> payments;
    private int layoutResID;

    public CustomAdaptorEvent(Context context, int layoutResourceID, List<EventElement> payments) {
        super(context, layoutResourceID, payments);
        this.context = context;
        this.payments = payments;
        this.layoutResID = layoutResourceID;
    }

   @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemHolder2 itemHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder2();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolder.title = (TextView) view.findViewById(R.id.eventtitle);
            itemHolder.date = (TextView) view.findViewById(R.id.eventdate);
            itemHolder.location = (TextView) view.findViewById(R.id.eventlocation);
            itemHolder.budget = (TextView) view.findViewById(R.id.eventbudget);
            itemHolder.guests = (Button) view.findViewById(R.id.eventguests);
            itemHolder.lHeader = (RelativeLayout) view.findViewById(R.id.lHeader);

            view.setTag(itemHolder);

        } else {
            itemHolder = (ItemHolder2) view.getTag();
        }

        final EventElement pItem = payments.get(position);

        itemHolder.title.setText(pItem.getTitle());
        itemHolder.location.setText(pItem.getLocation());
        itemHolder.date.setText(pItem.getDate());
        itemHolder.guests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openG = new Intent(context, GuestsActivity.class);
                openG.putExtra("name",pItem.getName());
                openG.putExtra("position",position);
                if( pItem.getGuests() != null) {
                    for (Map.Entry<String, String> entry : pItem.getGuests().entrySet()) {
                        String key = "" + entry.getKey();
                        Object value = entry.getValue();
                        openG.putExtra(key, "" + value);
                    }
                }
                v.getContext().startActivity(openG);
            }
        });
        int n = pItem.getBudget().size();
        int sum = 0,i = 0;
        for( String key: pItem.getBudget().keySet())
        {
            String value = String.valueOf(pItem.getBudget().get(key));

            int cost = Integer.parseInt(value);

            sum += cost;
        }
        itemHolder.budget.setText("Budget: " + sum + " ");
        return view;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }


    private static class ItemHolder2 {
        TextView title;
        TextView location;
        TextView date;
        TextView time;
        TextView budget;
        RelativeLayout lHeader;
        Button guests;
    }
}
