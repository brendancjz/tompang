/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author ignit
 */
public class CreateNewListingException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewListingException</code> without
     * detail message.
     */
    public CreateNewListingException() {
    }

    /**
     * Constructs an instance of <code>CreateNewListingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewListingException(String msg) {
        super(msg);
    }
}
