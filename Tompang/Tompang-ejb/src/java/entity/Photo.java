/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author brend
 */
@Entity
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;
    private String photoURL;
    private String alternativeText;

    public Photo() {
    }

    public Photo(Long photoId, String photoURL, String alternativeText) {
        this();
        this.photoId = photoId;
        this.photoURL = photoURL;
        this.alternativeText = alternativeText;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getAlternativeText() {
        return alternativeText;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setAlternativeText(String alternativeText) {
        this.alternativeText = alternativeText;
    }
    
    
    
}
