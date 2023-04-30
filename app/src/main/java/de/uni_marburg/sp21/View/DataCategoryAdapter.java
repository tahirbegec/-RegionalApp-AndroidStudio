package de.uni_marburg.sp21.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Map;

import de.uni_marburg.sp21.Model.DataCategory;
import de.uni_marburg.sp21.Service.CompanyFilter;
import de.uni_marburg.sp21.R;

import static de.uni_marburg.sp21.Model.DataCategory.*;

public class DataCategoryAdapter extends RecyclerView.Adapter <DataCategoryAdapter.ViewHolder> {
    CompanyFilter companyFilter = CompanyFilter.getInstance();
    Map<DataCategory, Boolean> dataCategories;
    Activity activity;

    public DataCategoryAdapter(Activity activity) {
        dataCategories = companyFilter.getDataCategorySettings();
        this.activity = activity;
    }
    @NonNull
    @Override
    public DataCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_category_item, parent, false);
        DataCategoryAdapter.ViewHolder viewHolder = new DataCategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataCategoryAdapter.ViewHolder holder, int position) {
        DataCategory dataCategory = null;
         switch (position) {
             case 0:
                 holder.textView.setText(activity.getText(R.string.CompanyName));
                 dataCategory = CompanyName;
                 break;
             case 1:
                 holder.textView.setText(activity.getText(R.string.OwnerName));
                 dataCategory = OwnerName;
                 break;
             case 2:
                 holder.textView.setText(activity.getText(R.string.CompanyType));
                 dataCategory = CompanyType;
                 break;
             case 3:
                 holder.textView.setText(activity.getText(R.string.Address));
                 dataCategory = Address;
                 break;
             case 4:
                 holder.textView.setText(activity.getText(R.string.Description));
                 dataCategory = Description;
                 break;
             case 5:
                 holder.textView.setText(activity.getText(R.string.ProductDescription));
                 dataCategory = ProductDescription;
                 break;
             case 6:
                 holder.textView.setText(activity.getText(R.string.ProductGroup));
                 dataCategory = ProductGroup;
                 break;
             case 7:
                 holder.textView.setText(activity.getText(R.string.OpeningHoursComments));
                 dataCategory = OpeningHoursComments;
                 break;
             case 8:
                 holder.textView.setText(activity.getText(R.string.Organisation));
                 dataCategory = Organisation;
                 break;
             case 9:
                 holder.textView.setText(activity.getText(R.string.Messages));
                 dataCategory = Messages;
                 break;
         }
        DataCategory finalDataCategory = dataCategory;
         holder.checkBox.setChecked(dataCategories.get(dataCategory));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 companyFilter.setDataCategorySetting(finalDataCategory, isChecked);
             }
         });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
