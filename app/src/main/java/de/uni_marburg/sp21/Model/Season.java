package de.uni_marburg.sp21.Model;

import android.app.Activity;

import de.uni_marburg.sp21.R;

public enum Season {
    SPRING, SUMMER, AUTUMN, WINTER;

    public String getString(Activity activity) {
        switch(this) {
            case SPRING: return activity.getString(R.string.Spring);
            case SUMMER: return activity.getString(R.string.Summer);
            case AUTUMN: return activity.getString(R.string.Autumn);
            case WINTER: return activity.getString(R.string.Winter);
        }
        return null;
    }
}
