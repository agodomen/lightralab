/**
 *
 */
package edu.muc.model;

import java.lang.reflect.InvocationTargetException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月31日 上午9:37:33
 */
@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识
@XmlRootElement(name = "Type")
//控制JAXB 绑定类中属性和字段的排序

@XmlType(propOrder = {"id", "name", "value", "description",})
public class Type extends BaseModel<Type> {
    /**
     *
     */
    private static final long serialVersionUID = -855427921396791096L;
    private String id;
    private String name;
    private String value;
    private String description;

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

    public Type(String id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.description = null;
    }

    public Type(String id, String name, String value, String description) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.description = description;
    }

    public Type() {

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
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
