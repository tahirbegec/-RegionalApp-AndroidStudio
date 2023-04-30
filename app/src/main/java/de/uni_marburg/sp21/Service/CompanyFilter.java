package de.uni_marburg.sp21.Service;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import de.uni_marburg.sp21.Model.Company;
import de.uni_marburg.sp21.Model.DataCategory;
import de.uni_marburg.sp21.Model.FilterCategory;
import de.uni_marburg.sp21.Model.Location;
import de.uni_marburg.sp21.Model.Message;
import de.uni_marburg.sp21.Model.ProductCategory;
import de.uni_marburg.sp21.R;
import de.uni_marburg.sp21.View.CompanyActivity;
import de.uni_marburg.sp21.View.MainActivity;

import static de.uni_marburg.sp21.Model.CompanyType.*;
import static de.uni_marburg.sp21.Model.DataCategory.*;
import static de.uni_marburg.sp21.Model.ProductCategory.*;

public class CompanyFilter {
    public static final int PERMISSION_FINE_LOCATION = 99 ;
    private static CompanyFilter instance;
    public Map<String, List<FilterCategory>> filterCategories = new HashMap<>();
    private List<Company> companies = new ArrayList<>();
    private boolean isDeliveringFilter = false;
    private boolean isOpenFilter = false;
    private boolean isFavoriteFilter = false;
    private boolean openAtFilterEnabled = false;
    private boolean isLocationFilterEnabled = false;
    private MainActivity mainActivity;
    private int openAtHour;
    private int openAtMinute;
    private int openAtDay;
    private Map<DataCategory, Boolean> dataCategorySettings = new HashMap<>();
    private List<String> filterString = new ArrayList<>();
    private int numberOfResults;
    private FusedLocationProviderClient locationProvider;
    private Location userLocation;
    private int circuit;

    private CompanyFilter() {
    }
    public static CompanyFilter getInstance() {
        if(instance == null) {
            instance = new CompanyFilter();
        }
        return instance;
    }

    /**
     * enables the user to chose one or more setting for the company
     */
    public void initDataCategorySettings() {
        dataCategorySettings.put(CompanyName, true);
        dataCategorySettings.put(OwnerName, true);
        dataCategorySettings.put(CompanyType, true);
        dataCategorySettings.put(Address, true);
        dataCategorySettings.put(Description, true);
        dataCategorySettings.put(ProductDescription, true);
        dataCategorySettings.put(ProductGroup, true);
        dataCategorySettings.put(OpeningHoursComments, true);
        dataCategorySettings.put(Organisation, true);
        dataCategorySettings.put(Messages, true);
    }

    /**
     * update the list of companies to make it looks the same as filter criteria, which
     * user have given or chosen
     */
    public void addAllFilterCategories() {
        filterCategories.clear();
        filterCategories.put("ProductCategory", Stream.of(VEGETABLES, FRUITS, MEAT, MEATPRODUCTS, CEREALS, MILK, MILKPRODUCTS, EGGS, HONEY, BEVERAGES, BAKEDGOODS, PASTA).collect(Collectors.toList()));
        filterCategories.put("Organisation", getOrganisations());
        filterCategories.put("CompanyType", Stream.of(PRODUCER, SHOP, RESTAURANT, HOTEL, MART).collect(Collectors.toList()));
    }

    /**
     * add every filterd company to the class Company
     * @param company object company
     */
    public void addCompany(Company company) {
        companies.add(company);
    }

    /** enables the user add a filter criteria to the class FilterCategory, when the class does not have it
     * @param type is the type of company
     * @param filterCategory is an object from class FilterCategory
     */
    public void addFilterCategory(String type, FilterCategory filterCategory) {
        if(!this.filterCategories.get(type).contains(filterCategory)) {
            this.filterCategories.get(type).add(filterCategory);
        }
        mainActivity.updateCompanyList();
    }
    /** enables the user delete a filter criteria from the class FilterCategory, when the class already have it
     * @param type is the type of company
     * @param filterCategory is an object from class FilterCategory
     */
    public void deleteFilterCategory(String type, FilterCategory filterCategory) {
        while(this.filterCategories.get(type).contains(filterCategory)) {
            this.filterCategories.get(type).remove(filterCategory);
        }
        mainActivity.updateCompanyList();
    }

    /**
     * @return a list of companies which have the given filter criteria
     */
    public List<Company> getFilteredCompanies() {
        List<Company> filteredCompanies = new ArrayList<>();
        for(Company company : getSwitchFilterCompanies()) {
            boolean productCategory = false;
            boolean organisation = false;
            boolean companyType = false;
                for(FilterCategory filterCategory : filterCategories.get("ProductCategory")) {
                    if(company.hasProductCategory((ProductCategory) filterCategory)) {
                        productCategory = true;
                        break;
                    }
                }
                if(!productCategory) {
                    continue;
                }
                for(FilterCategory filterCategory : filterCategories.get("Organisation")) {
                    if(company.hasOrganisation((de.uni_marburg.sp21.Model.Organisation) filterCategory)) {
                        organisation = true;
                        break;
                    }
                }
                if(!organisation) {
                    continue;
                }
                for(FilterCategory filterCategory : filterCategories.get("CompanyType")) {
                    if(company.hasCompanyType((de.uni_marburg.sp21.Model.CompanyType) filterCategory)) {
                        companyType = true;
                        break;
                    }
                }
                if(productCategory && organisation && companyType) {
                    filteredCompanies.add(company);
                }
            }

        return filteredCompanies;
    }

    /**
     * @return a list of companies which have the chosen filter criteria
     */
    public List<Company> getSwitchFilterCompanies() {
        return getDeliveringSwitchFilterCompanies(getFavoritesSwitchFilterCompanies(getOpenSwitchFilterCompanies()));
    }

    /**
     * @return a list of companies which have the chosen filter criteria
     */
    public List<Company> getOpenSwitchFilterCompanies() {
        if(isOpenFilter) {
            List<Company> filteredCompanies = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            for(Company company : filterOpenAt()) {
                if(company.isOpenAt(now.getDayOfWeek().getValue() - 1, now.getHour(), now.getMinute())) {
                    filteredCompanies.add(company);
                }
            }
            return filteredCompanies;
        } else {
            return filterOpenAt();
        }
    }

    /**
     * search for companies which have a delivering service and add it to the list of companies
     * @param companyList is a list of companies which have a delivering service
     * @return companyList
     */
    public List<Company> getDeliveringSwitchFilterCompanies(List<Company> companyList) {
        List<Company> filteredCompanies = new ArrayList<>();
        if(isDeliveringFilter) {
            for(Company company : companyList) {
                if(company.isDelivering()) {
                    filteredCompanies.add(company);
                }
            }
            return filteredCompanies;
        }
        return companyList;
    }

    /**
     * filter the Companies, so only the favorites are getting shown
     * @param companyList List that is getting filtered
     * @return the filtered List
     */
    public List<Company> getFavoritesSwitchFilterCompanies(List<Company> companyList) {
        List<Company> filteredCompanies = new ArrayList<>();
        if(isFavoriteFilter) {
            for(Company company : companyList) {
                if(company.isFavorite()) {
                    filteredCompanies.add(company);
                }
            }
            return filteredCompanies;
        }
        return companyList;
    }

    public Map<String, List<FilterCategory>> getFilterCategories() {
        return filterCategories;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public List<FilterCategory> getOrganisations() {
        List<FilterCategory> organisations = new ArrayList<>();
        for (Company c : companies) {
            for (FilterCategory o : c.getOrganisation()) {
                if (!organisations.contains(o)) {
                    organisations.add(o);
                }
            }
        }
        return organisations;
    }

    public void setDeliveringFilter(boolean deliveringFilter) {
        isDeliveringFilter = deliveringFilter;
    }

    public void setOpenFilter(boolean openFilter) {
        isOpenFilter = openFilter;
    }

    public void setIsFavoriteFilter(boolean favoriteFilter) {
        isFavoriteFilter = favoriteFilter;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void startCompanyActivity(Company company) {
        Intent intent = new Intent(mainActivity, CompanyActivity.class);
        intent.putExtra("Company", company.getId());
        mainActivity.startActivity(intent);
    }

    /** enables the user to give the opening time and day of company
     * @param day day from type int and its domain from 1 to 7
     * @param hour hour from type int and its domain from 0 to 23
     * @param minute minute from type int and its domain from 0 to 59
     */
   public void setOpenAtFilter(int day, int hour, int minute) {
        openAtFilterEnabled = true;
        openAtDay = day;
        openAtHour = hour;
        openAtMinute = minute;
   }


    /**
     * add all companies which correspond the given time to a list
     * @return list of companies which correspond the given time
     */
    public List<Company> filterOpenAt() {
        List<Company> filteredCompanies = new ArrayList<>();
        if(openAtFilterEnabled) {
            for (Company company : companies) {
                if (company.isOpenAt(openAtDay, openAtHour, openAtMinute)) {
                    filteredCompanies.add(company);
                }
            }
        } else {
            return companies;
        }
        return filteredCompanies;
    }

    /**
     * reset all given parameter to zero
     */
    public void resetOpenAtFilter() {
        openAtFilterEnabled = false;
        openAtDay = 0;
        openAtHour = 0;
        openAtMinute = 0;
    }

    public Map<DataCategory, Boolean> getDataCategorySettings() {
        return dataCategorySettings;
    }

    /**
     *
     * @param dataCategory object from DataCategory
     * @param checked boolean
     */
    public void setDataCategorySetting(DataCategory dataCategory, boolean checked) {
        dataCategorySettings.put(dataCategory, checked);
    }

    /** filter the companies
     * @return a list of filterd companies
     */
    public List<Company> filter() {
        if(filterString.isEmpty()) {
            return getFilteredCompanies();
        }
        List<Company> filteredCompanies = new ArrayList<>();
        for(Company company : getFilteredCompanies()) {
            for(String string : filterString) {
                if (company.containsString(dataCategorySettings, string)) {
                    filteredCompanies.add(company);
                    break;
                }
            }
        }
        return filteredCompanies;
    }

    public void addFavorite(Company company) {
        company.setIsFavorite(true);
    }

    public void removeFavorite(Company company) {
        company.setIsFavorite(false);

    }

    /**
     * check if the given text an empty string or not
     * @param string is the given text
     */
    public void setFilterString(String string) {
        filterString.clear();
        if(string != null && string != "") {
            for (String s : string.split("\"")) {
                if(!s.isEmpty() && !s.equals(" ")) {
                    filterString.add(s);
                }
            }
        }
    }

    /**
     * set the user Location to his current GPS Location
     */
    public void setToGPSLocation() {
        locationProvider = LocationServices.getFusedLocationProviderClient(mainActivity);
        if(ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
             locationProvider.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
                @Override
                public void onSuccess(android.location.Location location) {
                    userLocation = new Location(location.getLatitude(), location.getLongitude());
                   // System.out.println(userLocation.getLat() + " " + userLocation.getLon());
                    Toast.makeText(mainActivity, mainActivity.getString(R.string.LocationSet), Toast.LENGTH_SHORT).show();
                    //userLocation = new Location(50.818934,8.7449554);
                    mainActivity.updateCompanyList();
                }
            });
        } else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mainActivity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            }
        }
    }

    /**
     * resets the specified Location
     */
    public void resetLocation() {
        userLocation = null;
        isLocationFilterEnabled = false;
        Toast.makeText(mainActivity, mainActivity.getString(R.string.LocationReset), Toast.LENGTH_SHORT).show();
    }

    /**
     * sets the user Location to the given Address
     * @param city is the city the user wants to set the Location to
     * @param zip is the ZIP-Code the user wants to set the Location to
     * @param street is the Street the user wants to set the Location to
     */
    public void setToAddressLocation(String city, String zip, String street) {
        Geocoder gc = new Geocoder(mainActivity);
        Thread getLocation = new Thread()  {
            public void run()  {
                Looper.prepare();
                if (street.isEmpty()) {
                    try {
                        Address location = gc.getFromLocationName(city + " " + zip, 1).get(0);
                        userLocation = new Location(location.getLatitude(),
                                location.getLongitude());
                        Toast.makeText(mainActivity, mainActivity.getString(R.string.LocationSet), Toast.LENGTH_SHORT).show();
                        System.out.println(userLocation.getLat() + " " + userLocation.getLon());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Address location = gc.getFromLocationName(street + " " + city + " " + zip, 1).get(0);
                        userLocation = new Location(location.getLatitude(),
                               location.getLongitude());
                        Toast.makeText(mainActivity, mainActivity.getString(R.string.LocationSet), Toast.LENGTH_SHORT).show();
                        System.out.println(userLocation.getLat() + " " + userLocation.getLon());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        getLocation.start();
    }

    /**
     * sets the circuit to 100m, sets the user Location to his current GPS Location, set the location Filter to enabled
     * @param enabled says, if the nearby filter should be enabled
     */
    public synchronized void setLocationSwitchFilter(boolean enabled) {
        setIsLocationFilterEnabled(enabled);
        if(enabled) {
            setToGPSLocation();
            setCircuit(100);
        }

    }

    /**
     * filters the companies to the given location and circuit
     * @param companyList given List of companies which are getting filtered
     * @return the filtered Companies
     */
    public List<Company> locationFilter(List<Company> companyList) {
        numberOfResults = companyList.size();
        if(!isLocationFilterEnabled) {
            return companyList;
        } else {
            if(userLocation == null || circuit == 0) {
                Toast.makeText(mainActivity, mainActivity.getString(R.string.NoLocationParameters), Toast.LENGTH_SHORT).show();
                return companyList;
            }
            List<Company> filteredCompanies = new ArrayList<>();
            for(Company company : companyList) {
                if(company.getLocation().distanceSmallerThan(userLocation, circuit)) {
                    filteredCompanies.add(company);
                }
            }
            numberOfResults = filteredCompanies.size();
            return filteredCompanies;
        }
    }

    /**
     * @return number of the result after search
     */
    public Integer getNumberOfResults() {
        return numberOfResults;
    }

    /**
     * reset the number of results to correspond the number of the shown results
     */
    public void resetNumberOfResults() {
        numberOfResults = companies.size();
    }

    /**
     * sets the circuit to the given integer
     * @param circuit given circuit
     */
    public void setCircuit(int circuit) {
        if(circuit > 0) {
            this.circuit = circuit;
        } else {
            Toast.makeText(mainActivity, mainActivity.getString(R.string.CircuitNotLongEnough), Toast.LENGTH_SHORT).show();
        }
    }

    public void resetCircuit() {
        circuit = 0;
        isLocationFilterEnabled = false;
    }

    public void setIsLocationFilterEnabled(boolean enabled) {
        isLocationFilterEnabled = enabled;
    }

    public boolean getDataCategorySettingValue(DataCategory dataCategory) {
        if (dataCategorySettings.get(dataCategory) != null) {
            return dataCategorySettings.get(dataCategory);
        } else return true;
    }
    public boolean getIsDeliveringFilter() {
        return isDeliveringFilter;
    }

    public boolean getIsOpenFilter() {
        return isOpenFilter;
    }

    public int getOpenAtDay() {
        return openAtDay;
    }

    public int getOpenAtHour() {
        return openAtHour;
    }

    public int getOpenAtMinute() {
        return openAtMinute;
    }

    public AppCompatActivity getMainActivity() {
        return mainActivity;
    }
}
