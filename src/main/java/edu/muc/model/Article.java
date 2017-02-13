/**
 *
 */
package edu.muc.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月7日 上午10:51:57
 */
@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name = "Article")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {
        "id",
        "content",
        "description"
})
public class Article extends BaseModel<Article> {

    /**
     *
     */
    private static final long serialVersionUID = -1783390635082723548L;

    private String id;
    private String content;

    private String description;

    /**
     *
     */
    public Article() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Article [id=" + id + ", content=" + content + ", description="
                + description + "]";
    }

    @SuppressWarnings("finally")
    public Object toGetObject(String valueName) {
        Object object = null;
        try {
            object = this.getClass()
                    .getMethod("get" + valueName, new Class[]{})
                    .invoke(this, new Object[]{});
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return object;
        }
    }

}
