/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author brend
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
