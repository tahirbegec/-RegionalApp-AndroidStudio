package de.uni_marburg.sp21.Model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import de.uni_marburg.sp21.Service.CompanyFilter;

import static de.uni_marburg.sp21.Model.DataCategory.Address;
import static de.uni_marburg.sp21.Model.DataCategory.CompanyName;
import static de.uni_marburg.sp21.Model.DataCategory.CompanyType;
import static de.uni_marburg.sp21.Model.DataCategory.Description;
import static de.uni_marburg.sp21.Model.DataCategory.Messages;
import static de.uni_marburg.sp21.Model.DataCategory.OpeningHoursComments;
import static de.uni_marburg.sp21.Model.DataCategory.Organisation;
import static de.uni_marburg.sp21.Model.DataCategory.OwnerName;
import static de.uni_marburg.sp21.Model.DataCategory.ProductDescription;
import static de.uni_marburg.sp21.Model.DataCategory.ProductGroup;

public class Company {

    public int ID;
    public String name;
    private Address address;
    private String description;
    private Location location;
    private String mail;
    private String url;
    private List<CompanyType> types;
    private String owner;
    private boolean isDelivering;
    private List<Organisation> organisations;
    private OpeningHours openingHours;
    private String openingHoursComments;
    private List<Message> messages;
    private List<ProductGroup> products;
    private String productDescription;
    private String geoHash;
    private boolean isFavorite = false;
    private List<Image> imagePaths;
    private CompanyFilter companyFilter = CompanyFilter.getInstance();

    public Company(int ID, String name, Address address, List<CompanyType> types, String owner, boolean isDelivering, List<ProductGroup> products, String productDescription) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.types = types;
        this.owner = owner;
        this.isDelivering = isDelivering;
        this.products = products;
        this.productDescription = productDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }

    public void setOpeningHours (OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public void setOpeningHoursComments (String openingHoursComments) {
        this.openingHoursComments = openingHoursComments;
    }

    public void setLocation (Location location) {
        this.location = location;
    }

    public void setGeoHash(String geoHash) {
        this.geoHash = geoHash;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getUrl() { return url; }

    public int getId() {
        return ID;
    }

    public List<Organisation> getOrganisation() {
        return organisations;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<ProductGroup> getProducts() {
        return products;
    }

    public List<CompanyType> getTypes() {
        return types;
    }

    public boolean isDelivering() {
        return isDelivering;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public String getOpeningHoursComments() {
        return openingHoursComments;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public boolean isOpenAt(int day, int hour, int minute) {
                for(TimeInterval timeInterval : openingHours.getOpeningHoursArray()[day]) {
                    if (timeInterval.isInbetween(hour, minute)) {
                        return true;
                    }
                }
        return false;
    }

    public List<Image> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<Image> images) {
        this.imagePaths = images;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean hasProductCategory(ProductCategory productCategory) {
        for (ProductGroup productGroup : getProducts()) {
            if(productGroup.getCategory().equals(productCategory)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasOrganisation(Organisation organisation) {
        if(organisations.isEmpty()) {
            return true;
        } else {
            for (Organisation o: organisations) {
                if(o.equals(organisation)) {
                    return true;
                }
            }
            return false;
        }
    }

    public boolean hasCompanyType(CompanyType type) {
        for(CompanyType c : types) {
            if(type.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsString(Map<DataCategory, Boolean> dataCategorySettings, String string) {
        if (dataCategorySettings.get(CompanyName) && getName().contains(string)) {
            return true;
        }
        if (dataCategorySettings.get(OwnerName) && getOwner() != null && getOwner().contains(string)) {
            return true;
        }
        if (dataCategorySettings.get(CompanyType)) {
            for (de.uni_marburg.sp21.Model.CompanyType type : getTypes()) {
                if (type.getString(companyFilter.getMainActivity()).contains(string)) {
                    return true;
                }
            }
        }
        if (dataCategorySettings.get(Address) && getAddress().toString().contains(string)) {
            return true;
        }
        if(dataCategorySettings.get(Description) && getDescription().contains(string)) {
            return true;
        }
        if (dataCategorySettings.get(ProductDescription) && getProductDescription().contains(string)) {
            return true;
        }
        if(dataCategorySettings.get(ProductGroup)) {
            for(de.uni_marburg.sp21.Model.ProductGroup productGroup : getProducts()) {
                for(String tag : productGroup.getProductTags()) {
                    if(tag.contains(string)) {
                        return true;
                    }
                }
            }
        }
        if(dataCategorySettings.get(OpeningHoursComments) && getOpeningHoursComments().contains(string)) {
            return true;
        }
        if(dataCategorySettings.get(Organisation)) {
            for(de.uni_marburg.sp21.Model.Organisation organisation : getOrganisation()) {
                if(organisation.getName().contains(string)) {
                    return true;
                }
            }
        }
        if (dataCategorySettings.get(Messages)) {
            for (Message s : getMessages()) {
                if(s.getContent().contains(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Location getLocation() {
        return location;
    }
}
