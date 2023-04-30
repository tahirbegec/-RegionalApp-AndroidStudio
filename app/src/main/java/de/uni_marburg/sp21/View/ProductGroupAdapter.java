package de.uni_marburg.sp21.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.uni_marburg.sp21.Model.ProductGroup;
import de.uni_marburg.sp21.Model.Season;
import de.uni_marburg.sp21.R;

public class ProductGroupAdapter extends RecyclerView.Adapter <ProductGroupAdapter.ViewHolder> {
    List<ProductGroup> productGroupList;
    CompanyActivity activity;
    public ProductGroupAdapter(List<ProductGroup> productGroupList, CompanyActivity activity) {
        this.productGroupList = productGroupList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProductGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productgroup_item, parent, false);
        return new ProductGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductGroupAdapter.ViewHolder holder, int position) {
        ProductGroup item = productGroupList.get(position);
        holder.productCategory.setText(item.getCategory().getString(activity));
        if(item.isRawProduct()) {
            holder.rawProduct.setText(R.string.raw);
        } else {
            holder.rawProduct.setText(R.string.notRaw);
        }
        //holder.producerID.setText(item.getProducerID());
        holder.producerTags.setAdapter(new ArrayAdapter<>(activity, R.layout.enum_item, R.id.item, item.getProductTags()));
        List<String> seasons = new ArrayList<>();
        for (Season s : item.getSeasons()) {
            seasons.add(s.getString(activity));
        }
        holder.seasons.setAdapter(new ArrayAdapter<>(activity, R.layout.enum_item, R.id.item, seasons));
    }

    @Override
    public int getItemCount() {
        return productGroupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productCategory;
        TextView rawProduct;
        TextView producerID;
        ListView producerTags;
        ListView seasons;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productCategory = itemView.findViewById(R.id.productCategory);
            rawProduct = itemView.findViewById(R.id.rawProduct);
            producerID = itemView.findViewById(R.id.producerId);
            producerTags = itemView.findViewById(R.id.producerTags);
            seasons = itemView.findViewById(R.id.seasons);
        }

    }
}
