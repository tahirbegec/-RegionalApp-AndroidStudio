package de.uni_marburg.sp21.Model;

import java.util.ArrayList;
import java.util.List;

public class ProductGroup {
    private ProductCategory category;
    private boolean rawProduct;
    private int producerID;
    private List<String> productTags;
    private List<Season> seasons;

    public ProductGroup(String category, boolean rawProduct, int producerID, List<String> productTags, List<String> seasons) {
        switch(category) {
            case "vegetables": this.category = ProductCategory.VEGETABLES; break;
            case "fruits": this.category = ProductCategory.FRUITS; break;
            case "meat": this.category = ProductCategory.MEAT; break;
            case "meatproducts": this.category = ProductCategory.MEATPRODUCTS; break;
            case "cereals": this.category = ProductCategory.CEREALS; break;
            case "milk": this.category = ProductCategory.MILK; break;
            case "milkproducts": this.category = ProductCategory.MILKPRODUCTS; break;
            case "eggs": this.category = ProductCategory.EGGS; break;
            case "honey": this.category = ProductCategory.HONEY; break;
            case "beverages": this.category = ProductCategory.BEVERAGES; break;
            case "bakedgoods": this.category = ProductCategory.BAKEDGOODS; break;
            case "pasta": this.category = ProductCategory.PASTA; break;
        }
        this.rawProduct = rawProduct;
        this.producerID = producerID;
        this.productTags = productTags;
        List<Season> seasonList = new ArrayList<>();
        for (String season : seasons) {
            switch (season) {
                case "spring": seasonList.add(Season.SPRING); break;
                case "summer": seasonList.add(Season.SUMMER); break;
                case "autumn": seasonList.add(Season.AUTUMN); break;
                case "winter": seasonList.add(Season.WINTER); break;
            }
        }
        this.seasons = seasonList;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public boolean isRawProduct() {
        return rawProduct;
    }

    public int getProducerID() {
        return producerID;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public List<String> getProductTags() {
        return productTags;
    }
}
