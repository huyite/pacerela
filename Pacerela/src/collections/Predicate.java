package collections;

/**
* A Predicate allows to check if an object verify some property. 
* 
* @since 1.0
*/
public interface Predicate<T> {
	/**
	 * Check if an object verifies the predicate.
	 * 
	 * @param o object to check.
	 * @return Returns true if the predicate is verified by the specified object.
	 */
	public boolean predicate(T o);
}
