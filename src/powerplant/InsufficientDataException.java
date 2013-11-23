/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package powerplant;

/**
 * Thrown when attempting to compute a property without sufficient data.
 * @author DoorKip
 */
public class InsufficientDataException extends Exception {

	/**
	 * Creates a new instance of
	 * <code>InsufficientDataException</code> without detail message.
	 */
	public InsufficientDataException() {
	}

	/**
	 * Constructs an instance of
	 * <code>InsufficientDataException</code> with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public InsufficientDataException(String msg) {
		super(msg);
	}
}
