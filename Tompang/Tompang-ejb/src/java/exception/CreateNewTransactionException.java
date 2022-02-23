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
public class CreateNewTransactionException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewTransactionException</code>
     * without detail message.
     */
    public CreateNewTransactionException() {
    }

    /**
     * Constructs an instance of <code>CreateNewTransactionException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewTransactionException(String msg) {
        super(msg);
    }
}
