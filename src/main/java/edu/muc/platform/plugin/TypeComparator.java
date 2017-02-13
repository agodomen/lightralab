/**
 *
 */
package edu.muc.platform.plugin;

import java.util.Comparator;
import java.util.Hashtable;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月10日 下午11:49:51
 */
public class TypeComparator implements Comparator {

    public int compare(Object a, Object b) {
        Hashtable hashA = (Hashtable) a;
        Hashtable hashB = (Hashtable) b;
        if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
            return -1;
        } else if (!((Boolean) hashA.get("is_dir"))
                && ((Boolean) hashB.get("is_dir"))) {
            return 1;
        } else {
            return ((String) hashA.get("filetype")).compareTo((String) hashB
                    .get("filetype"));
        }
    }

}
