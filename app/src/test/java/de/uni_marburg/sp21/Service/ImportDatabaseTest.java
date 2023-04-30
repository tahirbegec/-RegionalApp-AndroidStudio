package de.uni_marburg.sp21.Service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.uni_marburg.sp21.View.MainActivity;

public class ImportDatabaseTest {

    CompanyFilter companyFilter;
    MainActivity mainActivity;

    @Before
    public void setUp() {
        companyFilter = CompanyFilter.getInstance();
    }

    @Test
    public void retrieveDataTest() {
        Assert.assertNotNull(companyFilter.getCompanies());
    }
}