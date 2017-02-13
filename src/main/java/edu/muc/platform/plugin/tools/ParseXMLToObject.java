/**
 *
 */
package edu.muc.platform.plugin.tools;

/**
 * @author 龚文东
 * <p>
 * <p>
 * 2015年4月11日 下午7:11:57
 */

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ParseXMLToObject {
    public ParseXMLToObject() {
    }

    @SuppressWarnings("unchecked")
    public List getObject(String name, String path, String className) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        DocumentBuilder db = null;
        Document doc = null;
        InputStream is = null;
        try {
            List list = new ArrayList();
            db = dbf.newDocumentBuilder();
            is = new FileInputStream(this.getClass().getResource(path)
                    .getPath());
            doc = db.parse(is);
            // 根据要取的对象名称获取相应的节点列表
            NodeList nodes = doc.getElementsByTagName(name);
            if (nodes == null) {
                throw new Exception("null nodes with tagName " + name);
            }
            for (int i = 0; i < nodes.getLength(); i++) {
                Element node = (Element) nodes.item(i);
                Class cls = Class.forName(className);
                Object obj = cls.newInstance();
                // 获取节点下的所有子节点
                NodeList childs = node.getChildNodes();
                if (childs == null) {
                    throw new Exception("null childs! " + node);
                }
                for (int j = 0; j < childs.getLength(); j++) {
                    if (!childs.item(j).getNodeName().equals("#text")) {
                        Element child = (Element) childs.item(j);
                        String childName = child.getNodeName();
                        String type = child.getAttribute("type");
                        String value = child.getAttribute("value");
                        Object valueObj = typeConvert(type, value);
                        String methodName = "set"
                                + Character.toUpperCase(childName.charAt(0))
                                + childName.substring(1);
                        System.out.println("methodName=" + methodName
                                + ", class=" + Class.forName(type));
                        Method method = cls.getMethod(methodName,
                                Class.forName(type));
                        method.invoke(obj, new Object[]{valueObj});
                    }

                }
                list.add(obj);
            }
            return list;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    // 此方法用于将一个字符串转换为相应的数据类型
    @SuppressWarnings("deprecation")
    public Object typeConvert(String className, String value) {
        if (className.equals("java.lang.String")) {
            return value;
        } else if (className.equals("java.lang.Integer")) {
            return Integer.valueOf(value);
        } else if (className.equals("java.lang.Long")) {
            return Long.valueOf(value);
        } else if (className.equals("java.lang.Boolean")) {
            return Boolean.valueOf(value);
        } else if (className.equals("java.util.Date")) {
            return new Date(value);
        } else if (className.equals("java.lang.Float")) {
            return Float.valueOf(value);
        } else if (className.equals("java.lang.Double")) {
            return Double.valueOf(value);
        } else
            return null;
    }
}
