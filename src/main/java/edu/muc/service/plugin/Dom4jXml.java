/**
 *
 */
package edu.muc.service.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年6月11日 上午8:58:29
 */
public class Dom4jXml {
    public static void read(String path) throws Exception {

    }

    public static boolean findValue(String valueName, String value, String path) {
        boolean flag = true;
        List list = null;
        Element root = null;
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            try {
                document = reader.read(new File(path));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            root = document.getRootElement();
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element element = (Element) i.next();
                if (null != element.elementText(valueName) && element.elementText(valueName).equals(value)) {
                    flag = false;
                    break;
                }

            }
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return flag;
        }
        return flag;
    }

}
