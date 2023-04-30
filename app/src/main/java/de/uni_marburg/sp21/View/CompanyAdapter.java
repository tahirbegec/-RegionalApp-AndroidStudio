package de.uni_marburg.sp21.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.uni_marburg.sp21.Model.Company;
import de.uni_marburg.sp21.Service.CompanyFilter;
import de.uni_marburg.sp21.R;

public class CompanyAdapter extends RecyclerView.Adapter <CompanyAdapter.ViewHolder> {
    List<Company> companies;
    CompanyFilter companyFilter = CompanyFilter.getInstance();

    public CompanyAdapter(List<Company> companies) {
        this.companies = companies;
    }

    @NonNull
    @Override
    public CompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.ViewHolder holder, int position) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyFilter.startCompanyActivity(companies.get(position));
            }
        };
        holder.companyName.setOnClickListener(listener);
        holder.companyAddress.setOnClickListener(listener);
        holder.companyName.setText(companies.get(position).getName());
        holder.companyAddress.setText(companies.get(position).getAddress().toString());
        holder.favorite.setChecked(companies.get(position).isFavorite());
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.favorite.isChecked()) {
                    companyFilter.addFavorite(companies.get(position));
                } else {
                    companyFilter.removeFavorite(companies.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView companyName;
        private TextView companyAddress;
        private ToggleButton favorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favorite = itemView.findViewById(R.id.Favorite);
            companyName = itemView.findViewById(R.id.CompanyName);
            companyAddress = itemView.findViewById(R.id.CompanyAddress);
        }
    }
}
