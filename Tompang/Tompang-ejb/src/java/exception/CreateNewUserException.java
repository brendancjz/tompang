/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author seanang
 */
public class CreateNewUserException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewUserException</code> without
     * detail message.
     */
    public CreateNewUserException() {
    }

    /**
     * Constructs an instance of <code>CreateNewUserException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewUserException(String msg) {
        super(msg);
    }
}
