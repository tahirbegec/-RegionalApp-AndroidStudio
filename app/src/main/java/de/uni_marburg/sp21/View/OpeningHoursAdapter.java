package de.uni_marburg.sp21.View;

import android.app.Activity;
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

import de.uni_marburg.sp21.Model.OpeningHours;
import de.uni_marburg.sp21.Model.TimeInterval;
import de.uni_marburg.sp21.R;

public class OpeningHoursAdapter extends RecyclerView.Adapter <OpeningHoursAdapter.ViewHolder> {
    List<TimeInterval>[] openingHours;
    Activity activity;

    public OpeningHoursAdapter(OpeningHours openingHours, Activity activity) {
        this.openingHours = openingHours.getOpeningHoursArray();
        this.activity = activity;
    }

    @NonNull
    @Override
    public OpeningHoursAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opening_hours_item, parent, false);
        return new OpeningHoursAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpeningHoursAdapter.ViewHolder holder, int position) {
        List<String> times = new ArrayList<>();
        for (TimeInterval t : openingHours[position]) {
            times.add(t.toString());
        }
        if(times.size()!= 0) {
            switch (position) {
            case 0 : holder.day.setText(R.string.Monday); break;
            case 1 : holder.day.setText(R.string.Tuesday); break;
            case 2 : holder.day.setText(R.string.Wednesday); break;
            case 3 : holder.day.setText(R.string.Thursday); break;
            case 4 : holder.day.setText(R.string.Friday); break;
            case 5 : holder.day.setText(R.string.Saturday); break;
            case 6 : holder.day.setText(R.string.Sunday); break;
            }
            holder.timeIntervals.setAdapter(new ArrayAdapter<>(activity, R.layout.enum_item, R.id.item, times));
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        private ListView timeIntervals;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            timeIntervals = itemView.findViewById(R.id.timeInterval);
        }
    }
}
