package com.dit.hp.hospitalapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dit.hp.hospitalapp.Modals.TestsPojo;
import com.dit.hp.hospitalapp.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestSelectionAdapter extends BaseAdapter {

    private Context context;
    private List<TestsPojo> originalList;
    private List<TestsPojo> filteredList;
    private Set<Integer> selectedTestIds;
    private LayoutInflater inflater;

    public TestSelectionAdapter(Context context, List<TestsPojo> testList, List<Integer> preSelectedTestIds) {
        this.context = context;
        this.originalList = new ArrayList<>(testList);
        this.filteredList = new ArrayList<>(testList);
        this.selectedTestIds = new HashSet<>(preSelectedTestIds);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public TestsPojo getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filteredList.get(position).getTestId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.medical_tests_card_view, parent, false);
            holder = new ViewHolder();

            holder.imageView = convertView.findViewById(R.id.imageViewTests);
            holder.headTV = convertView.findViewById(R.id.head_tv);
            holder.secondTV = convertView.findViewById(R.id.second_tv);
            holder.thirdTV = convertView.findViewById(R.id.third_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TestsPojo test = getItem(position);
        holder.headTV.setText(test.getTestName());
        holder.secondTV.setText(test.getTestsTypePojo().getTestTypeName() + " : Rs. " + test.getTestprice() + "/-");
        holder.thirdTV.setText(test.getTestDescription());

        // Show checkmark when selected
        if (selectedTestIds.contains(test.getTestId())) {
            holder.imageView.setImageResource(R.drawable.check);
        } else {
            holder.imageView.setImageResource(R.drawable.medical_test);
        }

        return convertView;
    }

    public void toggleSelectionById(int testId) {
        if (selectedTestIds.contains(testId)) {
            selectedTestIds.remove(testId);
        } else {
            selectedTestIds.add(testId);
        }
        notifyDataSetChanged();
    }

    public List<TestsPojo> getSelectedTests() {
        List<TestsPojo> selectedTests = new ArrayList<>();
        for (TestsPojo test : originalList) {
            if (selectedTestIds.contains(test.getTestId())) {
                selectedTests.add(test);
            }
        }
        return selectedTests;
    }

    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            for (TestsPojo test : originalList) {
                if (test.getTestName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(test);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Clear all selected items
    public void clearSelection() {
        selectedTestIds.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView imageView;
        TextView headTV;
        TextView secondTV;
        TextView thirdTV;
    }
}
