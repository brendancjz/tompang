/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ListingSessionBeanLocal;
import entity.Listing;
import entity.User;
import enumeration.CategoryEnum;
import exception.CreateNewListingException;
import exception.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author GuoJun
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
    private HashMap<String, HashMap<String, String>> data;
    private HashMap<String, String> countries;
    private HashMap<String, String> cities;

    private CategoryEnum category;
    private HashMap<String, CategoryEnum> categories;

    private String title;
    private Double price;
    private Integer quantity;

    private String description;
    private Date expectedArrivalDate;
    
    private Boolean hasCreatedNewListing;
    private Listing newListing;

    public CreateListingManagedBean() {
        uploadedFilePaths = new ArrayList<>();
        this.hasCreatedNewListing = false;
        this.initialiseCategories();
        this.initialiseDataCountriesAndCities();
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

            Listing listing = new Listing(country, city, title, description, category, price, expectedArrivalDate, user, quantity, uploadedFilePaths);
            Long newListingId = listingSessionBean.createNewListing(listing, user.getUserId());

            country = null;
            city = null;
            title = null;
            description = null;
            category = null;
            price = null;
            expectedArrivalDate = null;
            quantity = null;
            uploadedFilePaths.clear();
            
            this.hasCreatedNewListing = true;
            this.newListing = listingSessionBean.getListingByListingId(newListingId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing created.", ""));

        } catch (CreateNewListingException | EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing failed to create.", ""));
        }

    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1")
                    + System.getProperty("file.separator") + event.getFile().getFileName();

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

    public void viewNewListing(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listingToView", newListing);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
    }
    
    public void onCountryChange() {
        if (country != null && !"".equals(country)) {
            cities = data.get(country);
        } else {
            cities = new HashMap<>();
        }
    }

    private void initialiseCategories() {
        categories = new HashMap<>();
        categories.put("FOOD", CategoryEnum.FOOD);
        categories.put("APPAREL", CategoryEnum.APPAREL);
        categories.put("ACCESSORIES", CategoryEnum.ACCESSORIES);
        categories.put("FOOTWEAR", CategoryEnum.FOOTWEAR);
        categories.put("GIFTS", CategoryEnum.GIFTS);
        categories.put("ELECTRONICS", CategoryEnum.ELECTRONICS);
    }

    private void initialiseDataCountriesAndCities() {
        data = new HashMap<>();
        countries = new HashMap<>();
        countries.put("USA", "USA");
        countries.put("Singapore", "Singapore");
        countries.put("Japan", "Japan");
        countries.put("Korea", "Korea");
        countries.put("Malaysia", "Malaysia");

        HashMap<String, String> map = new HashMap<>();
        map.put("Alabama", "Alabama");
        map.put("Alaska", "Alaska");
        map.put("Arizona", "Arizona");
        map.put("Arkansas", "Arkansas");
        map.put("California", "California");
        map.put("Colorado", "Colorado");
        map.put("Connecticut", "Connecticut");
        map.put("Delaware", "Delaware");
        map.put("Florida", "Florida");
        map.put("Georgia", "Georgia");
        map.put("Hawaii", "Hawaii");
        map.put("Idaho", "Idaho");
        map.put("Illinois", "Illinois");
        map.put("Indiana", "Indiana");
        map.put("Iowa", "Iowa");
        map.put("Kansas", "Kansas");
        map.put("Kentucky", "Kentucky");
        map.put("Louisiana", "Louisiana");
        map.put("Maine", "Maine");
        map.put("Maryland", "Maryland");
        map.put("Massachusetts", "Massachusetts");
        map.put("Michigan", "Michigan");
        map.put("Minnesota", "Minnesota");
        map.put("Missouri", "Missouri");
        map.put("Montana", "Montana");
        map.put("Nebraska", "Nebraska");
        map.put("Nevada", "Nevada");
        map.put("New Hampshire", "New Hampshire");
        map.put("New Jersey", "New Jersey");
        map.put("New Mexico", "New Mexico");
        map.put("New York", "New York");
        map.put("North Carolina", "North Carolina");
        map.put("North Dakota", "North Dakota");
        map.put("Ohio", "Ohio");
        map.put("Oklahoma", "Oklahoma");
        map.put("Oregon", "Oregon");
        map.put("Pennsylvania", "Pennsylvania");
        map.put("Rhode Island", "Rhode Island");
        map.put("South Carolina", "South Carolina");
        map.put("South Dakota", "South Dakota");
        map.put("Tennessee", "Tennessee");
        map.put("Texas", "Texas");
        map.put("Utah", "Utah");
        map.put("Vermont", "Vermont");
        map.put("Washington", "Washington");
        map.put("West Virginia", "West Virginia");
        map.put("Wisconsin", "Wisconsin");
        map.put("Wyoming", "Wyoming");
        data.put("USA", map);

        map = new HashMap<>();
        map.put("Singapore", "Singapore");
        data.put("Singapore", map);
        
        //Korea Cities
        map = new HashMap<>();
        map.put("Seoul", "Seoul");
        map.put("Busan", "Busan");
        map.put("Incheon", "Incheon");
        map.put("Daegu", "Daegu");
        map.put("Daejeon", "Daejeon");
        map.put("Gwangju", "Gwangju");
        map.put("Suwon", "Suwon");
        map.put("Ulsan", "Ulsan");
        map.put("Yongin", "Yongin");
        map.put("Goyang", "Goyang");
        map.put("Changwon", "Changwon");
        map.put("Seongnam", "Seongnam");
        map.put("Hwaseong", "Hwaseong");
        map.put("Cheongju", "Cheongju");
        data.put("Korea", map);
        
        //Japan Cities
        map = new HashMap<>();
        map.put("Tokyo", "Tokyo");
        map.put("Yokohama", "Yokohama");
        map.put("Osaka", "Osaka");
        map.put("Nagoya", "Nagoya");
        map.put("Sapporo", "Sapporo");
        map.put("Fukuoka", "Fukuoka");
        map.put("Kobe", "Kobe");
        map.put("Kawasaki", "Kawasaki");
        map.put("Kyoto", "Kyoto");
        map.put("Saitama", "Saitama");
        map.put("Hiroshima", "Hiroshima");
        map.put("Sendai", "Sendai");
        map.put("Chiba", "Chiba");
        map.put("Kitakyushu", "Kitakyushu");
        data.put("Japan", map);
        
        //Malaysia Cities
        map = new HashMap<>();
        map.put("George Town", "George Town");
        map.put("Kuala Lumpur", "Kuala Lumpur");
        map.put("Ipoh", "Ipoh");
        map.put("Kuching", "Kuching");
        map.put("Johor Bahru", "Johor Bahru");
        map.put("Kota Kinabula", "Kota Kinabula");
        map.put("Shah Alam", "Shah Alam");
        map.put("Malacca City", "Malacca City");
        map.put("Alor Setar", "Alor Setar");
        map.put("Miri", "Miri");
        map.put("Petaling Jaya", "Petaling Jaya");
        map.put("Iskandar Puteri", "Iskandar Puteri");
        map.put("Seberang Perai", "Seberang Perai");
        map.put("Seremban", "Seremban");
        data.put("Malaysia", map);
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

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public HashMap<String, CategoryEnum> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String, CategoryEnum> categories) {
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

    public Boolean getHasCreatedNewListing() {
        return hasCreatedNewListing;
    }

    public void setHasCreatedNewListing(Boolean hasCreatedNewListing) {
        this.hasCreatedNewListing = hasCreatedNewListing;
    }

    public Listing getNewListing() {
        return newListing;
    }

    public void setNewListing(Listing newListing) {
        this.newListing = newListing;
    }

}
