package com.dit.hp.hospitalapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dit.hp.hospitalapp.Modals.RegistrationMode;
import com.dit.hp.hospitalapp.Modals.RegistrationMode;

import java.util.List;

public class RegistrationModesSpinnerAdapter extends ArrayAdapter<RegistrationMode> {

    private Context context;
    private List<RegistrationMode> values;

    public RegistrationModesSpinnerAdapter(Context context, int textViewResourceId, List<RegistrationMode> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public RegistrationMode getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public int getPositionForItem(String name) {
        for (int i = 0; i < values.size(); i++) {
            RegistrationMode item = values.get(i);
            if (item.getRegisrationModeName().equalsIgnoreCase(name)) {
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
        label.setText(values.get(position).getRegisrationModeName());
        return label;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextColor(Color.parseColor("#585566"));
        label.setTextSize(18);
        label.setPadding(15, 15, 15, 15);
        label.setText(values.get(position).getRegisrationModeName());
        return label;
    }
}