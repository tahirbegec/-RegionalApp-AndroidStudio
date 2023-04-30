package de.uni_marburg.sp21.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.Locale;

import de.uni_marburg.sp21.Model.CompanyType;
import de.uni_marburg.sp21.Model.FilterCategory;
import de.uni_marburg.sp21.Service.CompanyFilter;
import de.uni_marburg.sp21.Model.ProductCategory;
import de.uni_marburg.sp21.R;
import de.uni_marburg.sp21.Service.ImportDatabase;

import static de.uni_marburg.sp21.Model.CompanyType.*;
import static de.uni_marburg.sp21.Model.ProductCategory.*;

public class MainActivity extends AppCompatActivity {
    CompanyFilter companyFilter = CompanyFilter.getInstance();
    RecyclerView companyView;
    private boolean lang_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImportDatabase i = ImportDatabase.getInstance();
        i.importData(this);
        companyFilter.setMainActivity(this);
        companyFilter.initDataCategorySettings();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode) {
            case CompanyFilter.PERMISSION_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    companyFilter.setToGPSLocation();
                } else {
                    Toast.makeText(this, getString(R.string.PermissionNedded), Toast.LENGTH_SHORT).show();
                }
        }
    }

    /**
     * shows the list of companies and the number of results
     */
    public void initCompanyList() {
        companyView = findViewById(R.id.CompanyList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        companyView.setLayoutManager(manager);
        companyView.setItemAnimator(new DefaultItemAnimator());
        companyView.addItemDecoration(new DividerItemDecoration(companyView.getContext(), DividerItemDecoration.VERTICAL));
        RecyclerView.Adapter companyViewAdapter = new CompanyAdapter(companyFilter.getCompanies());
        companyView.setAdapter(companyViewAdapter);
        companyView.setHasFixedSize(true);
        TextView textView = findViewById(R.id.numberOfResults);
        companyFilter.resetNumberOfResults();
        textView.setText(companyFilter.getNumberOfResults().toString());
    }

    /**
     * shows the list of companies and the number of results after the filter
     */
    public void updateCompanyList() {
        RecyclerView.Adapter companyViewAdapter = new CompanyAdapter(companyFilter.locationFilter(companyFilter.filter()));
        companyView.setAdapter(companyViewAdapter);
        TextView textView = findViewById(R.id.numberOfResults);
        textView.setText(companyFilter.getNumberOfResults().toString());
    }

    /**
     * enables the user to chose one or more product category
     * @param v is a view of companies, which have the chosen filter criteria
     */
    public void popupProductCategory(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_filtercategory, (ViewGroup) findViewById(R.id.popup_1));
            PopupWindow pw = new PopupWindow(layout, 600, 1000, true);
            RecyclerView rv = layout.findViewById(R.id.recyclerView);
            RecyclerView.Adapter rva = new FilterCategoryAdapter("ProductCategory", new ProductCategory[] {VEGETABLES, FRUITS, MEAT, MEATPRODUCTS, CEREALS, MILK, MILKPRODUCTS, EGGS, HONEY, BEVERAGES, BAKEDGOODS, PASTA}, this);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(manager);
            rv.setHasFixedSize(true);
            rv.setAdapter(rva);
            pw.showAtLocation(layout, Gravity.CENTER, 0, -200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * enables the user to chose one or more type of company
     * @param v is a view of companies, which have the chosen filter criteria
     */
    public void popupCompanyType(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_filtercategory, (ViewGroup) findViewById(R.id.popup_1));
            PopupWindow pw = new PopupWindow(layout, 600, 1000, true);
            RecyclerView rv = layout.findViewById(R.id.recyclerView);
            RecyclerView.Adapter rva = new FilterCategoryAdapter("CompanyType", new CompanyType[] {PRODUCER, SHOP, RESTAURANT, HOTEL, MART}, this);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(manager);
            rv.setHasFixedSize(true);
            rv.addItemDecoration(new DividerItemDecoration(companyView.getContext(), DividerItemDecoration.VERTICAL));
            rv.setAdapter(rva);
            pw.showAtLocation(layout, Gravity.CENTER, 0, -200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * enables the user to chose one or more organisation
     * @param v is a view of companies, which have the chosen filter criteria
     */
    public void popupOrganisation(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_filtercategory,findViewById(R.id.popup_1));
            PopupWindow pw = new PopupWindow(layout, 900, 1000, true);
            RecyclerView rv = layout.findViewById(R.id.recyclerView);
            RecyclerView.Adapter rva = new FilterCategoryAdapter("Organisation", companyFilter.getOrganisations().toArray(new FilterCategory[]{}), this);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(manager);
            rv.setHasFixedSize(true);
            rv.addItemDecoration(new DividerItemDecoration(companyView.getContext(), DividerItemDecoration.VERTICAL));
            rv.setAdapter(rva);
            pw.showAtLocation(layout, Gravity.CENTER, 0, -200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * enables the user to chose the opening time and the opening day of company
     * @param v is a view of companies, which have the chosen filter criteria
     */
    public void popupOpenAt(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_open_at, findViewById(R.id.popup_2));
            PopupWindow pw = new PopupWindow(layout, 600, 1800, true);

            Spinner day = layout.findViewById(R.id.weekDay);
            CharSequence[] days = {getText(R.string.Monday), getText(R.string.Tuesday), getText(R.string.Wednesday), getText(R.string.Thursday), getText(R.string.Friday), getText(R.string.Saturday), getText(R.string.Sunday)};
            day.setAdapter(new ArrayAdapter(this, R.layout.enum_item,R.id.item, days));


            TimePicker timePicker = layout.findViewById(R.id.timePicker);
            timePicker.setIs24HourView(true);

            Button apply = layout.findViewById(R.id.apply);
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    companyFilter.setOpenAtFilter(day.getSelectedItemPosition(), timePicker.getHour(), timePicker.getMinute());
                    updateCompanyList();
                    pw.dismiss();
                }
            });
            Button reset = layout.findViewById(R.id.reset);
            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    companyFilter.resetOpenAtFilter();
                    updateCompanyList();
                    pw.dismiss();
                }
            });

            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * In this method the user can filter the opening companies and delivering by clicking on one or two switches
     * @param v is a view of two switches
     */
    public void switchFilter(View v) {
        Switch deliveringSwitch = findViewById(R.id.delivering);
        Switch openSwitch = findViewById(R.id.open);
        Switch favoriteSwitch = findViewById(R.id.favorite);
        Switch locationSwitch = findViewById(R.id.locationSwitch);
        companyFilter.setDeliveringFilter(deliveringSwitch.isChecked());
        companyFilter.setOpenFilter(openSwitch.isChecked());
        companyFilter.setIsFavoriteFilter(favoriteSwitch.isChecked());
        companyFilter.setLocationSwitchFilter(locationSwitch.isChecked());
        updateCompanyList();
    }

    /**
     * enables the user to chose one or more criteria such as owner name, company name and company type etc...
     * @param v is a view of companies, which have the chosen filter criteria
     */
    public void popupDataCategorySettings(View v) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_filtercategory,findViewById(R.id.popup_1));
        PopupWindow pw = new PopupWindow(layout, 900, 1000, true);
        RecyclerView rv = layout.findViewById(R.id.recyclerView);
        RecyclerView.Adapter adapter = new DataCategoryAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(companyView.getContext(), DividerItemDecoration.VERTICAL));
        pw.showAtLocation(layout, Gravity.CENTER, 0, -200);
    }

    /**
     * enables the user to chose a Location and Circuit to filter the Location of companies.
     * @param v is a view of companies, which have the chosen filter criteria
     */

    public void popupLocation(View v) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_location,findViewById(R.id.popup_1));
        PopupWindow pw = new PopupWindow(layout, 1000, 1700, true);

        Button gpsLocation = layout.findViewById(R.id.GPSLocation);
        gpsLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyFilter.setToGPSLocation();
            }
        });

        Button resetLocation = layout.findViewById(R.id.NoLocation);
        resetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyFilter.resetLocation();
                updateCompanyList();
            }
        });

        EditText city = layout.findViewById(R.id.City);
        EditText zip = layout.findViewById(R.id.ZIP_Code);
        EditText street = layout.findViewById(R.id.Street);
        Button addressLocation = layout.findViewById(R.id.setLocationToAddress);
        addressLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(city.getText().length() != 0 && zip.getText().length() != 0) {
                    companyFilter.setToAddressLocation(city.getText().toString(), zip.getText().toString(), street.getText().toString());
                } else {
                    Toast.makeText(companyFilter.getMainActivity(), getString(R.string.AddressUnCompleted), Toast.LENGTH_SHORT).show();
                }
            }
        });

        EditText circuit = layout.findViewById(R.id.Circuit);
        Button search = layout.findViewById(R.id.SearchLocation);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!circuit.getText().toString().isEmpty()) {
                    companyFilter.setCircuit(Integer.parseInt(circuit.getText().toString()) * 1000);
                    companyFilter.setIsLocationFilterEnabled(true);
                    updateCompanyList();
                    pw.dismiss();
                } else {
                    Toast.makeText(companyFilter.getMainActivity(), getText(R.string.NoCircuitGiven), Toast.LENGTH_SHORT).show();;
                }
            }
        });

        Button noCircuit = layout.findViewById(R.id.NoCircuit);
        noCircuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyFilter.resetCircuit();
                updateCompanyList();
            }
        });

        Button reset = layout.findViewById(R.id.ResetLocation);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyFilter.resetLocation();
                companyFilter.resetCircuit();
                companyFilter.setIsLocationFilterEnabled(false);
                updateCompanyList();
                pw.dismiss();
            }
        });
        pw.showAtLocation(layout, Gravity.CENTER, 0, -100);

    }

    /**
     * enable the user to tip and search for companies
     * @param v is a view of companies which correspond the same criteria as the given text
     */
    public void searchFilter(View v) {
        Button search = findViewById(R.id.search);
        EditText text = findViewById(R.id.filterString);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyFilter.setFilterString(text.getText().toString());
                updateCompanyList();
            }
        });
    }
    /**
     * @param lang is a language from type String, it can be "en" or "de"
     * change the language of the view to the chosen language
     */
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        companyFilter.getCompanies().clear();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration conf = getResources().getConfiguration();
        conf.locale = myLocale;
        getResources().updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }

    /**
     * this method enable the user to chose between 2 languages
     * @param view is the view from we can chose between languages
     */
    public void changelang(View view){
        Button changelang = findViewById(R.id.Change_lang);

        changelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] Language = {"ENGLISH", "DEUTSCH"};
                final int checkedItem;
                if(lang_selected)
                {
                    checkedItem=0;
                }
                else
                {
                    checkedItem=1;
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select a Language...")
                        .setSingleChoiceItems(Language, checkedItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lang_selected= Language[which].equals("ENGLISH");
                                //if user select prefered language as English then
                                if(Language[which].equals("ENGLISH"))
                                {
                                    setLocale("en");

                                }
                                //if user select prefered language as German then
                                if(Language[which].equals("DEUTSCH"))
                                {
                                    setLocale("de");

                                }
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

    }
}