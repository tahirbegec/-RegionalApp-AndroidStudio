package de.uni_marburg.sp21.Model;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Organisation implements FilterCategory {

    private double ID;
    private String name;
    private String url;

    public Organisation(double ID, String name, String url) {
        this.ID = ID;
        this.name = name;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return Double.compare(that.ID, ID) == 0;
    }

    @Override
    public String getString(AppCompatActivity activity) {
        return name;
    }

    public double getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getUrl() { return url;}
}
