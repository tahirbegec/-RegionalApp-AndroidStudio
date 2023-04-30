package de.uni_marburg.sp21.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.uni_marburg.sp21.Model.Company;
import de.uni_marburg.sp21.Model.CompanyType;
import de.uni_marburg.sp21.Service.CompanyFilter;
import de.uni_marburg.sp21.Model.Organisation;
import de.uni_marburg.sp21.R;

public class CompanyActivity extends AppCompatActivity {
    CompanyFilter companyFilter = CompanyFilter.getInstance();
    Company company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        Intent i = getIntent();
        for(Company c : companyFilter.getCompanies()) {
            if(c.getId() == i.getIntExtra("Company", -1)) {
                company = c;
            }
        }
        if(company == null) {
            throw new NullPointerException();
        }
        showTextViews();
        showInformation();
    }

    /**
     * shows the information of the chosen company in the activity_company layout
     */
    private void showInformation() {

        ListView companyTypes = findViewById(R.id.companyTypes);
        companyTypes.setClickable(false);
        List<String> types = new ArrayList<>();
        for(CompanyType c : company.getTypes()) {
            types.add(c.getString(this));
        }
        companyTypes.setAdapter(new ArrayAdapter<>(this, R.layout.enum_item, R.id.item,types));

        RecyclerView productGroups = findViewById(R.id.productGroups);
        productGroups.addItemDecoration(new DividerItemDecoration(productGroups.getContext(), DividerItemDecoration.VERTICAL));
        RecyclerView.Adapter adapter = new ProductGroupAdapter(company.getProducts(), this);
        productGroups.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        productGroups.setLayoutManager(manager);
        productGroups.setHasFixedSize(true);


        RecyclerView openingHours = findViewById(R.id.openingHours);
        openingHours.addItemDecoration(new DividerItemDecoration(openingHours.getContext(), DividerItemDecoration.VERTICAL));
        RecyclerView.Adapter openingHoursAdapter = new OpeningHoursAdapter(company.getOpeningHours(), this);
        openingHours.setAdapter(openingHoursAdapter);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        openingHours.setLayoutManager(manager1);
        openingHours.setHasFixedSize(true);

        ListView organisations = findViewById(R.id.companyOrganisations);
        List<String> organisation = new ArrayList<>();
        for(Organisation o : company.getOrganisation()) {
            organisation.add(o.getName());
        }
        organisations.setAdapter(new ArrayAdapter<>(this, R.layout.enum_item, R.id.item, organisation));
    }

    /**
     * shows the texts such as name and address etc.. of the chosen company in the activity_company layout
     */
    public void showTextViews() {
        TextView name = findViewById(R.id.companyName);
        name.setText(company.getName());

        TextView address = findViewById(R.id.companyAdress);

        address.append(" " + company.getAddress().toString());

        TextView owner = findViewById(R.id.companyOwner);
        owner.append(" " + company.getOwner());

        TextView description = findViewById(R.id.companyDescription);
        description.setText(company.getDescription());

        TextView productDescription = findViewById(R.id.productDescription);
        productDescription.setText(company.getProductDescription());

        TextView openingHoursComments = findViewById(R.id.openingHoursComments);
        openingHoursComments.setText(company.getOpeningHoursComments());
    }
}