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
import javax.ejb.Local;

/**
 *
 * @author brend
 */
@Local
public interface PhotoSessionBeanLocal {

    public Long createNewPhoto(Photo photo);

    public List<Photo> retrieveAllPhotos() throws EmptyListException;

    public Photo getPhotoByPhotoId(Long photoId) throws EntityNotFoundException;

    public void updatePhoto(Long photoId, String newText) throws EntityNotFoundException;

    public void deletePhoto(Long photoId) throws EntityNotFoundException;
    
}
