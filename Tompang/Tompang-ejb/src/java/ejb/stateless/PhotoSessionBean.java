/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.Photo;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author brend
 */
@Stateless
public class PhotoSessionBean implements PhotoSessionBeanLocal {

    @PersistenceContext(unitName = "Tompang-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewPhoto(Photo photo) {
        em.persist(photo);
        em.flush();
        
        return photo.getPhotoId();
    }
    
    @Override
    public List<Photo> retrieveAllPhotos() throws EmptyListException {
        Query query = em.createQuery("SELECT p FROM Photo p");
        
        List<Photo> photos = query.getResultList();
        
        if (photos.isEmpty()) throw new EmptyListException("List of photos is empty.\n");
        
        return photos;
    }
    
    @Override
    public Photo getPhotoByPhotoId(Long photoId) throws EntityNotFoundException {
        Photo photo = em.find(Photo.class, photoId);
        
        if (photo == null) throw new EntityNotFoundException("Photo " + photoId + " not found.\n");
        
        return photo;
    }
    
    @Override
    public void updatePhoto(Long photoId, String newText) throws EntityNotFoundException {
        Photo photo = this.getPhotoByPhotoId(photoId);
        photo.setAlternativeText(newText);
    }
    
    @Override
    public void deletePhoto(Long photoId) throws EntityNotFoundException {
        Photo photo = this.getPhotoByPhotoId(photoId);
        em.remove(photo);
    }
}
