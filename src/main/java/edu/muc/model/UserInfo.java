/**
 *
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
 *         2015年5月7日 上午10:54:13
 */
@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "UserInfo")
// 控制JAXB 绑定类中属性和字段的排序
@XmlType(propOrder = {"id", "name", "nickname", "password", "sex", "power", "birthday",
        "email", "mobile", "telephone", "qq", "picture"})
public class UserInfo extends BaseModel<UserInfo> implements Cloneable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String nickname;
    private String password;
    private int sex;
    private int power;
    private Date birthday;
    private String email;
    private String mobile;
    private String telephone;
    private String qq;
    private String picture;

    /**
     * @return the power
     */
    public int getPower() {
        return power;
    }

    /**
     * @param power the power to set
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * @return the picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * @param picture the picture to set
     */
    public void setPicture(String picture) {
        this.picture = picture;
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
     * @return the nickName
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickName the nickName to set
     */
    public void setNickname(String nickName) {
        this.nickname = nickName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the sex
     */
    public int getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(int sex) {
        this.sex = sex;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the qq
     */
    public String getQq() {
        return qq;
    }

    /**
     * @param qq the qq to set
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UserInfo [id=" + id + ", name=" + name + ", nickname="
                + nickname + ", password=" + password + ", sex=" + sex
                + ", power=" + power + ", birthday=" + birthday + ", email="
                + email + ", mobile=" + mobile + ", telephone=" + telephone
                + ", qq=" + qq + ", picture=" + picture + "]";
    }

    /**
     * @param id
     * @param name
     * @param nickName
     * @param password
     * @param sex
     * @param birthday
     * @param email
     * @param mobile
     * @param telephone
     * @param qq
     */
    public UserInfo(String id, String name, String nickname, String password,
                    int sex, Date birthday, String email, String mobile,
                    String telephone, String qq) {
        super();
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.mobile = mobile;
        this.telephone = telephone;
        this.qq = qq;
    }

    public UserInfo() {

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

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
