package de.uni_marburg.sp21.Model;

import androidx.appcompat.app.AppCompatActivity;

import de.uni_marburg.sp21.Service.CompanyFilter;

public interface FilterCategory {
    CompanyFilter companyFilter = CompanyFilter.getInstance();

    public String getString(AppCompatActivity activity);
    
}
