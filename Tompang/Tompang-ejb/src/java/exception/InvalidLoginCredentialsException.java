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
public class InvalidLoginCredentialsException extends Exception {

    /**
     * Creates a new instance of <code>InvalidLoginCredentialsException</code>
     * without detail message.
     */
    public InvalidLoginCredentialsException() {
    }

    /**
     * Constructs an instance of <code>InvalidLoginCredentialsException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidLoginCredentialsException(String msg) {
        super(msg);
    }
}
