package de.uni_marburg.sp21.Model;

import androidx.appcompat.app.AppCompatActivity;

import de.uni_marburg.sp21.R;

public enum CompanyType implements FilterCategory {
    PRODUCER, SHOP, RESTAURANT, HOTEL, MART;

    @Override
    public String getString(AppCompatActivity activity) {
        switch(this) {
            case PRODUCER:
                return activity.getString(R.string.Producer);
            case SHOP:
                return activity.getString(R.string.Shop);
            case RESTAURANT:
                return activity.getString(R.string.Restaurant);
            case HOTEL:
                return activity.getString(R.string.Hotel);
            case MART:
                return activity.getString(R.string.Mart);
        }
        return null;
    }
}
