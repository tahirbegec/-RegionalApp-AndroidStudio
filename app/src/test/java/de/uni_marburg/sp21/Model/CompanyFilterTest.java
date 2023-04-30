package de.uni_marburg.sp21.Model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.uni_marburg.sp21.Service.CompanyFilter;


public class CompanyFilterTest {

    CompanyFilter testCompanyFilter;
    Company testCompany;
    List<Company> companies;
    Map<DataCategory, Boolean> dataCategorySettings;
    Map<String, List<FilterCategory>> filterCategories;


    public void createTestCompany() {
        //init
        List<String> productTags = new ArrayList<String>();
        productTags.add("Tag1");
        List<String> seasonList = new ArrayList<>();
        seasonList.add("summer");
        List<ProductGroup> products = new ArrayList<>();
        products.add(new ProductGroup("vegetables", true, 1, productTags, seasonList));
        Address address = new Address("Jungfernstieg", "Hamburg", "22397");
        List<CompanyType> companyTypeList = new ArrayList<>();
        companyTypeList.add(CompanyType.PRODUCER);

        //create
        testCompany = new Company(1, "test", address, companyTypeList, "Mark",
                true, (List<ProductGroup>) products, "TestBeschreibung");
    }

    @Before
    public void setUp() {
        testCompanyFilter = CompanyFilter.getInstance();
        createTestCompany();
        filterCategories = testCompanyFilter.filterCategories;
        dataCategorySettings = testCompanyFilter.getDataCategorySettings();
        companies = new ArrayList<>();
        testCompanyFilter.setDeliveringFilter(true);
        testCompanyFilter.setOpenFilter(true);
        testCompanyFilter.setOpenAtFilter(1, 1, 1);
    }

    @After
    public void tearDown() {
        testCompanyFilter.setDeliveringFilter(false);
        testCompanyFilter.setOpenFilter(false);
        testCompanyFilter = null;
        testCompany = null;
    }


    @Test
    public void createModelTest(){
        Assert.assertNotNull(testCompanyFilter);
    }

    @Test
    public void initDataCategorySettingsTest() {
        testCompanyFilter.initDataCategorySettings();
        Assert.assertTrue(testCompanyFilter.getDataCategorySettingValue(DataCategory.CompanyName) &&
                testCompanyFilter.getDataCategorySettingValue(DataCategory.OwnerName) &&
                testCompanyFilter.getDataCategorySettingValue(DataCategory.CompanyType) &&
                testCompanyFilter.getDataCategorySettingValue(DataCategory.Address) &&
                testCompanyFilter.getDataCategorySettingValue(DataCategory.Description) &&
                testCompanyFilter.getDataCategorySettingValue(DataCategory.ProductDescription) &&
                testCompanyFilter.getDataCategorySettingValue(DataCategory.ProductGroup) &&
                testCompanyFilter.getDataCategorySettingValue(DataCategory.OpeningHoursComments) &&
                testCompanyFilter.getDataCategorySettingValue(DataCategory.Organisation) &&
                testCompanyFilter.getDataCategorySettingValue(DataCategory.Messages));
    }

    @Test
    public void addAllFilterCategoriesTest() {
        testCompanyFilter.addAllFilterCategories();
        Assert.assertFalse(testCompanyFilter.getFilterCategories().isEmpty());
        Assert.assertTrue(testCompanyFilter.getFilterCategories().containsKey("ProductCategory") &&
                testCompanyFilter.getFilterCategories().containsKey("Organisation") &&
                testCompanyFilter.getFilterCategories().containsKey("CompanyType"));
    }

    @Test
    public void addCompanyTest() {
        testCompanyFilter.addCompany(testCompany);
        Assert.assertTrue(testCompanyFilter.getCompanies().contains(testCompany));
    }

    /*
    @Test
    public void getFilteredCompaniesTest() {
        Assert.assertTrue(testModel.getFilteredCompanies().isEmpty());
        testModel.addAllFilterCategories();
        testModel.setOpenFilter(true);
        testModel.setDeliveringFilter(true);
        Assert.assertEquals(!testModel.getFilteredCompanies().isEmpty(), false);
    }
     */

    @Test
    public void getSwitchFilterCompanies() {
        testCompanyFilter.setOpenFilter(true);
        testCompanyFilter.setDeliveringFilter(true);
        Assert.assertFalse(!testCompanyFilter.getSwitchFilterCompanies().isEmpty());
    }

    @Test
    public void getOpenSwitchFilterCompaniesTest() {
        Assert.assertNotNull(testCompanyFilter.getOpenSwitchFilterCompanies());
    }

    @Test
    public void getDeliveringSwitchFilterCompanies() {
        Assert.assertNotNull(testCompanyFilter.getDeliveringSwitchFilterCompanies(companies));
    }

    @Test
    public void setDeliveringFilterTest() {
        testCompanyFilter.setDeliveringFilter(true);
        Assert.assertTrue(testCompanyFilter.getIsDeliveringFilter());
    }

    @Test
    public void setOpeningFilterTest() {
        testCompanyFilter.setOpenFilter(true);
        Assert.assertTrue(testCompanyFilter.getIsOpenFilter());
    }

    @Test
    public void filterOpenAtTest() {
        Assert.assertTrue((testCompanyFilter.getOpenAtDay() == 1) &&
                (testCompanyFilter.getOpenAtHour() == 1) &&
                (testCompanyFilter.getOpenAtMinute() == 1));
    }

    @Test
    public void resetOpenAtFilterTest() {
        testCompanyFilter.resetOpenAtFilter();
        Assert.assertTrue((testCompanyFilter.getOpenAtDay() == 0) &&
                (testCompanyFilter.getOpenAtHour() == 0) &&
                (testCompanyFilter.getOpenAtMinute() == 0));
    }

}
