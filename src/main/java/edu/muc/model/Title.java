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
 *         2015年5月7日 上午10:52:53
 */
@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name = "Title")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {
        "id",
        "name",
        "outline",
        "linkId",
        "logo",
        "creatorId",
        "creatorName",
        "creatDate",
        "editDate",
        "clickCount",
        "description"
})
public class Title extends BaseModel<Title> {

    /**
     *
     */
    private static final long serialVersionUID = 8839251644662208577L;
    private String id;
    private String name;
    private String outline;
    private String linkId;//save file name
    private String logo;
    private String creatorId;
    private String creatorName;
    private Date creatDate;
    private Date editDate;
    private int clickCount;
    private String description;

    /**
     * @return the creatorId
     */
    public String getCreatorId() {
        return creatorId;
    }


    /**
     * @return the outline
     */
    public String getOutline() {
        return outline;
    }


    /**
     * @param outline the outline to set
     */
    public void setOutline(String outline) {
        this.outline = outline;
    }


    /**
     * @param creatorId the creatorId to set
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }


    /**
     * @return the creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }


    /**
     * @param creatorName the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }


    /**
     *
     */
    public Title() {
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
     * @return the linkId
     */
    public String getLinkId() {
        return linkId;
    }


    /**
     * @param linkId the linkId to set
     */
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }


    /**
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }


    /**
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
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


    /**
     * @return the clickCount
     */
    public int getClickCount() {
        return clickCount;
    }


    /**
     * @param clickCount the clickCount to set
     */
    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Title [id=" + id + ", name=" + name + ", outline=" + outline
                + ", linkId=" + linkId + ", logo=" + logo + ", creatorId="
                + creatorId + ", creatorName=" + creatorName + ", creatDate="
                + creatDate + ", editDate=" + editDate + ", clickCount="
                + clickCount + ", description=" + description + "]";
    }


    /**
     * @return the creatDate
     */
    public Date getCreatDate() {
        return creatDate;
    }


    /**
     * @param creatDate the creatDate to set
     */
    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }


    /**
     * @return the editDate
     */
    public Date getEditDate() {
        return editDate;
    }


    /**
     * @param editDate the editDate to set
     */
    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }


    /**
     * @param id
     * @param name
     * @param linkId
     * @param logo
     * @param creatorId
     * @param creatorName
     * @param creatDate
     * @param editDate
     * @param clickCount
     * @param description
     */
    public Title(String id, String name, String linkId, String logo,
                 String creatorId, String creatorName, Date creatDate,
                 Date editDate, int clickCount, String description) {
        super();
        this.id = id;
        this.name = name;
        this.linkId = linkId;
        this.logo = logo;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.creatDate = creatDate;
        this.editDate = editDate;
        this.clickCount = clickCount;
        this.description = description;
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
