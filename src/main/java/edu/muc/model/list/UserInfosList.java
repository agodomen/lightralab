/**
 *
 */
package edu.muc.model.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import edu.muc.model.UserInfo;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月1日 下午8:04:05
 */

//XML文件中的根标识  
@XmlRootElement(name = "UserInfos")
public class UserInfosList {
    @XmlElement(name = "UserInfo")
    private List<UserInfo> userInfos;

    /**
     *
     */
    public UserInfosList() {
        // TODO Auto-generated constructor stub
        userInfos = new ArrayList<>();
    }

    public void add(UserInfo userInfo) {
        userInfos.add(userInfo);
    }

    public Iterator<UserInfo> iterator() {
        return userInfos.iterator();
    }

    /**
     * @return the userInfos
     */
    public List<UserInfo> toGetUserInfos() {
        return userInfos;
    }

    /**
     * @param userInfos the userInfos to set
     */
    public void toSetUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }


}
