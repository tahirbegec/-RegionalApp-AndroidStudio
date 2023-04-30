package de.uni_marburg.sp21.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.uni_marburg.sp21.Model.Company;

public class CompanyTest {

    Company testCompany;
    Address address;
    List<CompanyType> companyTypeList;
    List<String> productTags;
    List<String> seasonList;
    List<ProductGroup> products;

    @Before
    public void createTestCompany() {
        //Setup
        productTags = new ArrayList<String>();
        productTags.add("Tag1");
        seasonList = new ArrayList<>();
        seasonList.add("summer");
        products = new ArrayList<>();
        products.add(new ProductGroup("vegetables", true, 1, productTags, seasonList));
        address = new Address("Jungfernstieg", "Hamburg", "22397");
        companyTypeList = new ArrayList<>();
        companyTypeList.add(CompanyType.PRODUCER);

        //Company erstellen
        testCompany = new Company(1, "test", address, companyTypeList, "Mark",
                true, products, "TestBeschreibung");
    }

    @Test
    public void setUpCompanyTest() {

        //Überprüfen, ob Company erstellt wurde
        Assert.assertNotNull(testCompany);
        //Überprüfen, ob Parameter richtig gesetzt (stichprobenartig)
        Assert.assertEquals(1, testCompany.getId());
        Assert.assertEquals("test", testCompany.getName());
        Assert.assertEquals(address, testCompany.getAddress());
        Assert.assertEquals(companyTypeList, testCompany.getTypes());
        //Setter Methoden
        testCompany.setDescription("TEST");
        Assert.assertEquals("TEST", testCompany.getDescription());
        testCompany.setUrl("test.com");
        Assert.assertEquals("test.com", testCompany.getUrl());
    }

}
