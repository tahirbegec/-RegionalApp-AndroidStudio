package de.uni_marburg.sp21.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductGroupTest {

    ProductCategory category;
    boolean rawProduct;
    int producerID;
    List<String> productTags;
    List<String> seasons;
    ProductGroup productGroup;

    @Before
    public void setUp() {
        category = ProductCategory.BAKEDGOODS;
        rawProduct = false;
        producerID = 1;
        productTags = new ArrayList<>();
        seasons = new ArrayList<>();
    }

    @Test
    public void createProductGroupTest() {
        productGroup = new ProductGroup("bakedgoods", rawProduct, producerID, productTags, seasons);
        Assert.assertNotNull(productGroup);
        Assert.assertEquals(category, productGroup.getCategory());
    }
}
