package com.dit.hp.hospitalapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dit.hp.hospitalapp.Modals.PatientRecord;
import com.dit.hp.hospitalapp.R;

import java.util.HashSet;
import java.util.List;

public class PatientRecordListAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<PatientRecord> itemList;
    private List<PatientRecord> originalList;
    private HashSet<Integer> selectedPositions = new HashSet<>();
    private Filter planetFilter;

    public PatientRecordListAdapter(Context context, List<PatientRecord> objects) {
        this.context = context;
        this.itemList = objects;
        this.originalList = objects;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public PatientRecord getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.patient_card_view, parent, false);
            holder = new ViewHolder();
            holder.headTV = convertView.findViewById(R.id.name_tv);
            holder.secondTV = convertView.findViewById(R.id.second_tv);
            holder.thirdTV = convertView.findViewById(R.id.third_tv);
            holder.imageView = convertView.findViewById(R.id.imageViewMain);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PatientRecord item = itemList.get(position);
        holder.headTV.setText(item.getPatientName());
        holder.secondTV.setText(item.getGender());
        holder.thirdTV.setText(item.getRecordDate());

//        if (selectedPositions.contains(position)) {
//            holder.imageView.setImageResource(); // Set selected image
//        } else {
//            holder.imageView.setImageResource();
//        }

        return convertView;
    }

    static class ViewHolder {
        TextView headTV;
        TextView secondTV;
        TextView thirdTV;
        ImageView imageView;
    }

    public void toggleSelection(int position) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(position);
        } else {
            selectedPositions.add(position);
        }
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selectedPositions.clear();
    }

    @Override
    public Filter getFilter() {
        if (planetFilter == null) {
            planetFilter = new PlanetFilter();
        }
        return planetFilter;
    }

    private class PlanetFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
//            if (constraint == null || constraint.length() == 0) {
//                results.values = originalList;
//                results.count = originalList.size();
//            } else {
//                List<PatientRecord> filteredList = new ArrayList<>();
//                for (PatientRecord p : itemList) {
//                    if (p.getEstablishmentName().toUpperCase().contains(constraint.toString().toUpperCase())) {
//                        filteredList.add(p);
//                    }
//                }
//                results.values = filteredList;
//                results.count = filteredList.size();
//            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                itemList = (List<PatientRecord>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
