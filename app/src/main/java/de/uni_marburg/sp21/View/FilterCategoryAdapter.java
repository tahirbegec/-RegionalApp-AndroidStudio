package de.uni_marburg.sp21.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.uni_marburg.sp21.Model.FilterCategory;
import de.uni_marburg.sp21.Service.CompanyFilter;
import de.uni_marburg.sp21.R;

public class FilterCategoryAdapter extends RecyclerView.Adapter <FilterCategoryAdapter.ViewHolder> {
    private FilterCategory[] localDataSet;
    private MainActivity activity;
    String type;
    CompanyFilter companyFilter = CompanyFilter.getInstance();

    public FilterCategoryAdapter(String type, FilterCategory[] dataSet, MainActivity activity) {
        localDataSet = dataSet;
        this.activity = activity;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        for(FilterCategory filterCategory : companyFilter.getFilterCategories().get(type)) {
            if(localDataSet[position].equals(filterCategory)) {
                holder.checkBox.setChecked(true);
                break;
            }
        }
        holder.textView.setText(localDataSet[position].getString(activity));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(!companyFilter.getFilterCategories().get(type).contains(localDataSet[position])) {
                        companyFilter.addFilterCategory(type, localDataSet[position]);
                    }
                } else {
                    if(companyFilter.getFilterCategories().get(type).contains(localDataSet[position])) {
                        companyFilter.deleteFilterCategory(type, localDataSet[position]);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
