package com.dit.hp.hospitalapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.dit.hp.hospitalapp.Modals.GenderPojo;

import java.util.List;

public class GenderSpinnerAdapter extends ArrayAdapter<GenderPojo> {

    private Context context;
    private List<GenderPojo> values;

    public GenderSpinnerAdapter(Context context, int textViewResourceId, List<GenderPojo> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public GenderPojo getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public int getPositionForItem(String name) {
        for (int i = 0; i < values.size(); i++) {
            GenderPojo item = values.get(i);
            if (item.getGenderName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1; // Return -1 if no match is found
    }


    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        //label.setTextColor(Color.BLACK);
        label.setTextSize(18);
        label.setTextColor(Color.parseColor("#13914f"));
        label.setPadding(30, 0, 30, 0);
        label.setText(values.get(position).getGenderName());
        return label;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextColor(Color.parseColor("#585566"));
        label.setTextSize(18);
        label.setPadding(15, 15, 15, 15);
        label.setText(values.get(position).getGenderName());
        return label;
    }
}