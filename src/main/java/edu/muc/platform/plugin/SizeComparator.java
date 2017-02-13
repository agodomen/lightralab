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
 *         2015年5月10日 下午11:48:53
 */
public class SizeComparator implements Comparator {
    public int compare(Object a, Object b) {
        Hashtable hashA = (Hashtable) a;
        Hashtable hashB = (Hashtable) b;
        if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
            return -1;
        } else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
            return 1;
        } else {
            if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize"))) {
                return 1;
            } else if (((Long) hashA.get("filesize")) < ((Long) hashB.get("filesize"))) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
