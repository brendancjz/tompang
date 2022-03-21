/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ListingSessionBeanLocal;
import entity.Listing;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author GuoJun
 */
@Named(value = "viewAllListingsManagedBean")
@ViewScoped
public class ViewAllListingsManagedBean implements Serializable {

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    @Inject
    private ViewListingDetailsEzCompManagedBean viewListingDetailsEzCompManagedBean;

    private List<Listing> listings;
    private List<Listing> filteredListings;

    private Listing listingToUpdate;
    private Boolean listingToUpdateChangedCountry;
    private List<String> updatedListOfPhotos;

    private String filteredUsername;

    private HashMap<String, HashMap<String, String>> data;
    private HashMap<String, String> countries;
    private HashMap<String, String> cities;

    private HashMap<String, String> categories;

    public ViewAllListingsManagedBean() {
        this.initialiseCategories();
        this.initialiseDataCountriesAndCities();
    }

    @PostConstruct
    public void retrieveAllListings() {
        listings = new ArrayList<>();

        System.out.println("PostConstruct for ViewAllListingManagedBean called.");
        try {
            //This is for when admin wishes to view User's Listings from the View User Details
            filteredUsername = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("username");

            if (filteredUsername == null) {
                listings = listingSessionBean.retrieveAllListings();
            } else {
                listings = listingSessionBean.retrieveUserListings(filteredUsername);
            }

        } catch (EmptyListException ex) {
            System.out.println("List of listings empty.");
        }
    }

//    public void viewListingDetails(ActionEvent event) throws IOException {
//        Long listingIdToView = (Long)event.getComponent().getAttributes().get("listingId");
//        System.err.print(listingIdToView);
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("listingIdToView", listingIdToView);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingDetails.xhtml");
//    }
    public void deleteListing(ActionEvent event) {
        try {
            System.out.println("Deleting Listing in ViewAllListingsManagedBean");

            Listing listingToDelete = (Listing) event.getComponent().getAttributes().get("listingToDelete");

            listingSessionBean.deleteListing(listingToDelete.getListingId());

            try {
                Listing listing = listingSessionBean.getListingByListingId(listingToDelete.getListingId());
                //Means listing is disabled, not deleted.
                this.retrieveAllListings();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing disabled successfully", null));
            } catch (EntityNotFoundException ex) {
                listings.remove(listingToDelete);

                if (filteredListings != null) {
                    filteredListings.remove(listingToDelete);
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing deleted successfully", null));
            }
        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting listing: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void saveListing(ActionEvent event) {
        try {

            if (data.get(listingToUpdate.getCountry()).containsKey(listingToUpdate.getCity())) {
                listingSessionBean.updateListingDetails(listingToUpdate);
                System.out.println("Updated listing details");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully updated listing.", null));

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Unsuccessful update. Input correct city/country.", null));
                System.out.println("Did not Update listing details");
            }

        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
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

            this.updatedListOfPhotos.add(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("uploadedFilesPath") + "/" + event.getFile().getFileName());
            System.out.println("Updated List Of Photos size: " + this.updatedListOfPhotos.size());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public void imageHover() {
        System.out.println("Image Hover");
    }

    public Integer getNumberOfListings() {

        try {
            return listingSessionBean.retrieveAllListings().size();
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
        }

        return 0;
    }

    public String getCreatedOn(Listing listing) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(listing.getCreatedOn());
    }

    public String getArrivalDate(Listing listing) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(listing.getExpectedArrivalDate());
    }

    public void getListingToUpdate(ActionEvent event) {
        Listing listing = (Listing) event.getComponent().getAttributes().get("listing");
        this.listingToUpdate = listing;
        this.updatedListOfPhotos = new ArrayList<>();
        for (String photo : listing.getPhotos()) {
            this.updatedListOfPhotos.add(photo);
        }
        cities = data.get(listingToUpdate.getCountry());
    }

    public void onCountryChange() {
        this.listingToUpdateChangedCountry = true;
        if (listingToUpdate.getCountry() != null && !"".equals(listingToUpdate.getCountry())) {
            cities = data.get(listingToUpdate.getCountry());
        } else {
            cities = new HashMap<>();
        }
    }

    private void initialiseCategories() {
        categories = new HashMap<>();
        categories.put("FOOD", "FOOD");
        categories.put("APPAREL", "APPAREL");
        categories.put("ACCESSORIES", "ACCESSORIES");
        categories.put("FOOTWEAR", "FOOTWEAR");
        categories.put("GIFTS", "GIFTS");
        categories.put("ELECTRONICS", "ELECTRONICS");
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

    public HashMap<String, String> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String, String> categories) {
        this.categories = categories;
    }

    public Boolean getListingToUpdateChangedCountry() {
        return listingToUpdateChangedCountry;
    }

    public void setListingToUpdateChangedCountry(Boolean listingToUpdateChangedCountry) {
        this.listingToUpdateChangedCountry = listingToUpdateChangedCountry;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListOfListings(List<Listing> listings) {
        this.listings = listings;
    }

    public List<Listing> getFilteredListings() {
        return filteredListings;
    }

    public void setFilteredListings(List<Listing> filteredListings) {
        this.filteredListings = filteredListings;
    }

    public String getFilteredUsername() {
        return filteredUsername;
    }

    public void setFilteredUsername(String filteredUsername) {
        this.filteredUsername = filteredUsername;
    }

    public ViewListingDetailsEzCompManagedBean getViewListingDetailsEzCompManagedBean() {
        return viewListingDetailsEzCompManagedBean;
    }

    public void setViewListingDetailsEzCompManagedBean(ViewListingDetailsEzCompManagedBean viewListingDetailsEzCompManagedBean) {
        this.viewListingDetailsEzCompManagedBean = viewListingDetailsEzCompManagedBean;
    }

    public Listing getListingToUpdate() {
        return listingToUpdate;
    }

    public void setListingToUpdate(Listing listingToUpdate) {
        this.listingToUpdate = listingToUpdate;
    }

    public List<String> getUpdatedListOfPhotos() {
        return updatedListOfPhotos;
    }

    public void setUpdatedListOfPhotos(List<String> updatedListOfPhotos) {
        this.updatedListOfPhotos = updatedListOfPhotos;
    }

}
