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
public class CreateNewDisputeException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewDisputeException</code> without
     * detail message.
     */
    public CreateNewDisputeException() {
    }

    /**
     * Constructs an instance of <code>CreateNewDisputeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewDisputeException(String msg) {
        super(msg);
    }
}
