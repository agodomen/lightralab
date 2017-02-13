/**
 *
 */
package edu.muc.platform.plugin;

import java.io.File;

import java.util.Comparator;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年6月12日 上午10:10:49
 */
public class CompratorByFileName implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        if (o1.isDirectory() && o2.isFile())
            return -1;
        if (o1.isFile() && o2.isDirectory())
            return 1;
        return o1.getName().compareTo(o2.getName());
    }

}
