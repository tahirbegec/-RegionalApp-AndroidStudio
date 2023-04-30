package de.uni_marburg.sp21.Service;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.uni_marburg.sp21.Model.Address;
import de.uni_marburg.sp21.Model.Company;
import de.uni_marburg.sp21.Model.CompanyType;
import de.uni_marburg.sp21.Model.Image;
import de.uni_marburg.sp21.Model.Location;
import de.uni_marburg.sp21.Model.Message;
import de.uni_marburg.sp21.Model.OpeningHours;
import de.uni_marburg.sp21.Model.Organisation;
import de.uni_marburg.sp21.Model.ProductGroup;
import de.uni_marburg.sp21.View.MainActivity;


public class ImportDatabase {
    private static ImportDatabase instance;
    FirebaseFirestore db;
    CompanyFilter companyFilter = CompanyFilter.getInstance();
    List<Organisation> allOrganisations = new ArrayList<>();

    private ImportDatabase() {
        initialize();
    }
    public static ImportDatabase getInstance() {
        if (instance == null) {
            instance = new ImportDatabase();
        }
        return instance;
    }

    /**
     * Establishes a connection to firebase fire store
     */
    public void initialize()  {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

    }
    /**
     * In this method all data from the collection of firebase will be imported
     * @param main main activity
     */
    public void importData(MainActivity main) {
        CollectionReference colRef = db.collection("companies");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Company company = new Company(
                                ((Double) document.get("id")).intValue(),
                                ((String) document.get("name")),
                                importAddress((Map<String, String>) document.get("address")),
                                importCompanyType((ArrayList<String>) document.get("types")),
                                ((String) document.get("owner")),
                                ((boolean) document.get("deliveryService")),
                                importProducts((ArrayList<Map<String, Object>>) document.get("productGroups")),
                                ((String) document.get("productsDescription"))
                        );
                        if(document.get("description") != null) {
                            company.setDescription(((String) document.get("description")));
                        } if(document.get("mail") != null) {
                            company.setMail(((String) document.get("mail")));
                        } if(document.get("url") != null) {
                            company.setUrl(((String) document.get("url")));
                        } if(document.get("organizations") != null) {
                            company.setOrganisations(importOrganisations((ArrayList<Map<String, Object>>) document.get("organizations")));
                        } if(document.get("openingHours") != null) {
                            company.setOpeningHours(new OpeningHours((Map<String, List< Map<String, String>>>) document.get("openingHours")));
                        } if(document.get("openingHoursComments") != null) {
                            company.setOpeningHoursComments((String) document.get("openingHoursComments"));
                        } if(document.get("location") != null) {
                            company.setLocation(new Location(((Map<String, Double>) document.get("location")).get("lat"), ((Map<String, Double>) document.get("location")).get("lon")));
                        } if(document.get("geoHash") != null) {
                            company.setGeoHash((String) document.get("geoHash"));
                        } if(document.get("messages") != null) {
                            company.setMessages(importMessages((ArrayList<Map<String, String>>) document.get("messages")));
                        } if(document.get("imagePaths") != null) {
                            company.setImagePaths(importImages((ArrayList<Image>) document.get("imagePaths")));
                        }
                         companyFilter.addCompany(company);
                    }
                }
                main.initCompanyList();
                companyFilter.addAllFilterCategories();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }
    /**
     * This method call the data of address from Address class
     * @param addressMap is a paramter which contains address attribute
     * @return the address(street, city and zip)
     */
    private Address importAddress(Map<String, String> addressMap) {
        return new Address(addressMap.get("street"), addressMap.get("city"), addressMap.get("zip"));
    }

    /**
     * This method call the company types from CompanyType Enum
     * @param typeList is a list of company types
     * @return company types
     */
    private List<CompanyType> importCompanyType(ArrayList<String> typeList) {
        List<CompanyType> companyTypes = new ArrayList<>();
        for(String type : typeList) {
            switch (type) {
                case "producer": companyTypes.add(CompanyType.PRODUCER); break;
                case "shop": companyTypes.add(CompanyType.SHOP); break;
                case "restaurant": companyTypes.add(CompanyType.RESTAURANT); break;
                case "hotel": companyTypes.add(CompanyType.HOTEL); break;
                case "mart": companyTypes.add(CompanyType.MART); break;
            }
        }
        return companyTypes;
    }

    /**
     * The data from ProductGroup class will be called in this mehtod
     * @param productMaps is list of product category
     * @return a list of product category
     */
    private List<ProductGroup> importProducts(ArrayList<Map<String, Object>> productMaps) {
        List<ProductGroup> products = new ArrayList<>();
        for (Map<String, Object> map : productMaps) {
            products.add(new ProductGroup(((String) map.get("category")), ((boolean) map.get("rawProduct")), ((Double) map.get("producer")).intValue(), (ArrayList<String>) map.get("productTags"), ((ArrayList<String>) map.get("seasons"))));
        }
        return products;
    }

    /**
<<<<<<< HEAD
     * Imports the paths to a company's images
     * @param imagePaths is a list of paths which lead to the image
     * @return list of image paths
     */

    private List<Image> importImages(ArrayList<Image> imagePaths) {
        List<Image> images = new ArrayList<>();
        images.addAll(imagePaths);
        return images;
    }


    /**
=======
>>>>>>> fuelle
     * All data about oragnisation such as name, id and url will be called in this method
     * @param organisationMaps is a list of organisations
     * @return list of organisations
     */
    private synchronized List<Organisation> importOrganisations(ArrayList<Map<String, Object>> organisationMaps) {
        List<Organisation> organisations = new ArrayList<>();
        for (Map<String, Object> map : organisationMaps) {
            boolean exists = false;
            for (Organisation org : allOrganisations) {
                if (org.getID() == (Double) map.get("id")) {
                    exists = true;
                    organisations.add(org);
                    break;
                }
            }
            if(!exists) {
                Organisation o = new Organisation((Double) map.get("id"), ((String) map.get("name")), ((String) map.get("url")));
                allOrganisations.add(o);
                organisations.add(o);
            }
        }
        return organisations;
    }

    /**
     * All data about messages such as content and date will be called in this method
     * @param messageMaps is a list of messages
     * @return list of messages
     */
    private List<Message> importMessages(ArrayList<Map<String, String>> messageMaps) {
        List<Message> messages = new ArrayList<>();
        for(Map<String, String> map : messageMaps) {
            messages.add(new Message(map.get("content"), map.get("date")));
        }
        return messages;
    }


}
