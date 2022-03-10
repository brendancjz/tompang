/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ListingSessionBeanLocal;
import entity.Listing;
import entity.User;
import exception.CreateNewListingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author brend
 */
@Named(value = "createListingManagedBean")
@ViewScoped
public class CreateListingManagedBean implements Serializable {

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    private List<String> uploadedFilePaths;
    private Boolean showUploadedFile;
    private String country;
    private String city;
    private HashMap<String, HashMap<String, String>> data = new HashMap<>();
    private HashMap<String, String> countries;
    private HashMap<String, String> cities;

    private String category;
    private HashMap<String, String> categories;

    private String title;
    private Double price;
    private Integer quantity;

    private String description;
    private Date expectedArrivalDate;

    public CreateListingManagedBean() {
        uploadedFilePaths = new ArrayList<>();
        categories = new HashMap<>();
        categories.put("FOOD", "FOOD");
        categories.put("APPAREL", "APPAREL");
        categories.put("ACCESSORIES", "ACCESSORIES");
        categories.put("FOOTWEAR", "FOOTWEAR");
        categories.put("GIFTS", "GIFTS");
        categories.put("ELECTRONICS", "ELECTRONICS");

        countries = new HashMap<>();
        countries.put("USA", "USA");
        countries.put("Germany", "Germany");
        countries.put("Brazil", "Brazil");

        HashMap<String, String> map = new HashMap<>();
        map.put("New York", "New York");
        map.put("San Francisco", "San Francisco");
        map.put("Denver", "Denver");
        data.put("USA", map);

        map = new HashMap<>();
        map.put("Berlin", "Berlin");
        map.put("Munich", "Munich");
        map.put("Frankfurt", "Frankfurt");
        data.put("Germany", map);

        map = new HashMap<>();
        map.put("Sao Paulo", "Sao Paulo");
        map.put("Rio de Janerio", "Rio de Janerio");
        map.put("Salvador", "Salvador");
        data.put("Brazil", map);

        String[] isoCodes = Locale.getISOCountries();

        for (int i = 0; i < isoCodes.length; i++) {
            Locale locale = new Locale("", isoCodes[i]);
        }
    }

    public void createListing(AjaxBehaviorEvent event) {
        try {
            System.out.println("Country:" + country);
            System.out.println("City: " + city);
            System.out.println("Title: " + title);
            System.out.println("Price: " + price);
            System.out.println("Quantity: " + quantity);
            System.out.println("Description: " + description);
            System.out.println("Arrival Date: " + expectedArrivalDate.toInstant().toString());
            for (String file : uploadedFilePaths) {
                System.out.println("File: " + file);
            }

            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");

            Listing listing = new Listing(country, city, title, description, category, price, expectedArrivalDate, user, quantity);
            listingSessionBean.createNewListing(listing, user.getUserId());
            
            country = null;
            city = null;
            title = null;
            description = null;
            category = null;
            price = null;
            expectedArrivalDate = null;
            quantity = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Listing created.", null));
        
        } catch (CreateNewListingException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Listing failed to create.", null));
        }

    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + event.getFile().getFileName();

            System.err.println("********** CreateListingManagedBean.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** CreateListingManagedBean.handleFileUpload(): newFilePath: " + newFilePath);

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputStream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();

            uploadedFilePaths.add(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("uploadedFilesPath") + "/" + event.getFile().getFileName());
            showUploadedFile = true;

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public void onCountryChange() {
        if (country != null && !"".equals(country)) {
            cities = data.get(country);
        } else {
            cities = new HashMap<>();
        }
    }

    public List<String> getUploadedFilePaths() {
        return uploadedFilePaths;
    }

    public void setUploadedFilePaths(List<String> uploadedFilePaths) {
        this.uploadedFilePaths = uploadedFilePaths;
    }

    public Boolean getShowUploadedFile() {
        return showUploadedFile;
    }

    public void setShowUploadedFile(Boolean showUploadedFile) {
        this.showUploadedFile = showUploadedFile;
    }

    public Date getExpectedArrivalDate() {
        return expectedArrivalDate;
    }

    public void setExpectedArrivalDate(Date expectedArrivalDate) {
        this.expectedArrivalDate = expectedArrivalDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public HashMap<String, String> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String, String> categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public HashMap<String, HashMap<String, String>> getData() {
        return data;
    }

    public void setData(HashMap<String, HashMap<String, String>> data) {
        this.data = data;
    }

    public HashMap<String, String> getCountries() {
        return countries;
    }

    public void setCountries(HashMap<String, String> countries) {
        this.countries = countries;
    }

    public HashMap<String, String> getCities() {
        return cities;
    }

    public void setCities(HashMap<String, String> cities) {
        this.cities = cities;
    }

}
