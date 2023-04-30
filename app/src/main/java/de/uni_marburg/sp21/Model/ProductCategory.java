package de.uni_marburg.sp21.Model;

import androidx.appcompat.app.AppCompatActivity;

import de.uni_marburg.sp21.R;

public enum ProductCategory implements FilterCategory {
    VEGETABLES, FRUITS, MEAT, MEATPRODUCTS, CEREALS, MILK, MILKPRODUCTS, EGGS, HONEY, BEVERAGES, BAKEDGOODS, PASTA;

    @Override
    public String getString(AppCompatActivity activity) {
        switch(this) {
            case VEGETABLES:
                return activity.getString(R.string.Vegetables);
            case FRUITS:
                return activity.getString(R.string.Fruits);
            case MEAT:
                return activity.getString(R.string.Meat);
            case MEATPRODUCTS:
                return activity.getString(R.string.Meatproducts);
            case CEREALS:
                return activity.getString(R.string.Cereals);
            case MILK:
                return activity.getString(R.string.Milk);
            case MILKPRODUCTS:
                return activity.getString(R.string.Milkproducts);
            case EGGS:
                return activity.getString(R.string.Eggs);
            case HONEY:
                return activity.getString(R.string.Honey);
            case BEVERAGES:
                return activity.getString(R.string.Beverages);
            case BAKEDGOODS:
                return activity.getString(R.string.Bakedgoods);
            case PASTA:
                return activity.getString(R.string.Pasta);
        }
        return null;
    }

}
