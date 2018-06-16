package it.digitalgarage.marketplace.commons.restinvoker.utils;

// TODO: Auto-generated Javadoc
/**
 * Utilities.
 *
 * @author George Georgovassilis
 */
public class Utils {

    /**
     * Returns true if arr!=null and arr.length > 0
     *
     * @param arr the arr
     * @return true, if successful
     */
    public static boolean hasValue(Object[] arr) {
	return arr!=null && arr.length>0;
    }

    /**
     * Returns true when s is neither null nor an empty string.
     *
     * @param s the s
     * @return true, if successful
     */
    public static boolean hasValue(String s) {
	return s!=null && !s.isEmpty();
    }
}
