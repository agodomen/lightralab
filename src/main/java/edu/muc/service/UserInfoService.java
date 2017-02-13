/**
 *
 */
package edu.muc.service;

import java.util.Iterator;
import java.util.List;


import org.junit.Test;

import edu.muc.model.UserInfo;
import edu.muc.model.list.UserInfosList;
import edu.muc.service.plugin.Dom4jXml;
import edu.muc.service.plugin.XmlUtil;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月31日 上午9:16:16
 */
public class UserInfoService extends BaseService implements BaseServiceInterface<UserInfo> {


    /**
     * private final Hashtable hashtableData = new Hashtable();
     */

    private static UserInfoService userInfoService = new UserInfoService();
    public static BaseServiceInterface<UserInfo> dao = userInfoService; // 唯一实例
    private static UserInfosList userInfosList;

    private final static String DEFAULT_FILENAME = "\\UserInfo\\UserInfo.xml";

    /**
     * Cache the hash code for the string
     */
    private UserInfoService() {
        init();
    }

    public static UserInfoService getInstance() {
        return userInfoService;
    }

    /**
     * @help this for the init()
     */
    protected void init() {
        userInfosList = (UserInfosList) XmlUtil.convertXmlFileToObject(
                UserInfosList.class, PATH + DEFAULT_FILENAME);
        if (userInfosList == null) {
            userInfosList = new UserInfosList();
        }
        // hashtableData.put("all", userInfoList.toGetUserInfos());//to gei the
        // data into the list;

    }


    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        int index = findIndexById(id);
        List<UserInfo> userInfos = getAll();
        userInfos.remove(index);
        save();
        return true;
    }

    /**
     * this is for save the data by default path
     */
    public boolean save() {
        XmlUtil.convertToXml(userInfosList, PATH + DEFAULT_FILENAME);
        return true;
    }


    @Override
    public List<UserInfo> getAll() {
        // TODO Auto-generated method stub
        return userInfosList.toGetUserInfos();
    }


    @Override
    public boolean save(UserInfo entity) {
        // TODO Auto-generated method stub
        List<UserInfo> userInfos = getAll();
        userInfos.add(entity);
        userInfosList.toSetUserInfos(userInfos);
        XmlUtil.convertToXml(userInfosList, PATH + DEFAULT_FILENAME);
        return true;
    }


    @Override
    public boolean update(UserInfo entity) {
        // TODO Auto-generated method stub
        List<UserInfo> userInfos = getAll();
        int index = this.findIndexById(entity.getId());
        if (index != -1) {
            userInfos.set(index, entity);
            userInfosList.toSetUserInfos(userInfos);
            XmlUtil.convertToXml(userInfosList, PATH + DEFAULT_FILENAME);
        }
        return true;
    }

    public boolean update(String id, String name) {
        List<UserInfo> userInfos = getAll();
        int index = this.findIndexById(id);
        if (index != -1) {
            UserInfo entity = userInfos.get(index);
            entity.setName(name);
            userInfos.set(index, entity);
            userInfosList.toSetUserInfos(userInfos);
            XmlUtil.convertToXml(userInfosList, PATH + DEFAULT_FILENAME);
        }
        return true;
    }


    public boolean updatePicture(String id, String picture) {
        List<UserInfo> userInfos = getAll();
        int index = this.findIndexById(id);
        if (index != -1) {
            UserInfo entity = userInfos.get(index);
            entity.setPicture(picture);
            userInfos.set(index, entity);
            userInfosList.toSetUserInfos(userInfos);
            XmlUtil.convertToXml(userInfosList, PATH + DEFAULT_FILENAME);
        }
        return true;
    }


    public boolean updatePower(String id, String power) {
        List<UserInfo> userInfos = getAll();
        int index = this.findIndexById(id);
        if (index != -1) {
            UserInfo entity = userInfos.get(index);
            entity.setPicture(power);
            userInfos.set(index, entity);
            userInfosList.toSetUserInfos(userInfos);
            XmlUtil.convertToXml(userInfosList, PATH + DEFAULT_FILENAME);
        }
        return true;
    }


    @Override
    public UserInfo findById(String id) {
        // TODO Auto-generated method stub
        int index = findIndexById(id);
        if (index != -1) {
            return getAll().get(index);
        }
        return null;
    }

    /*
     * this is the tools find index
     */
    public int findIndexById(String id) {
        // TODO Auto-generated method stub
        List<UserInfo> userInfos = getAll();
        Iterator<UserInfo> iterator = userInfos.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * this is the test
     */
    @Test
    public void test() {

    }

    /*
     * by the name this is the tools find index
     */
    public int findIndexByName(String name) {
        // TODO Auto-generated method stub
        List<UserInfo> userInfos = getAll();
        Iterator<UserInfo> iterator = userInfos.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * by the anvation to reflect the method: this is the tools find index
     */
    public int findIndexByValue(String valueName, String value) {
        // TODO Auto-generated method stub
        List<UserInfo> userInfos = getAll();
        Iterator<UserInfo> iterator = userInfos.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().toGetObject(valueName).equals(value)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * (non-Javadoc)
     *
     * @see edu.muc.service.BaseServiceInterface#updateById(java.lang.String)
     */

    public boolean updateById(String id, UserInfo userInfo) {
        // TODO Auto-generated method stub
        List<UserInfo> userInfos = getAll();
        int index = this.findIndexById(id);
        if (index != -1) {
            userInfos.set(index, userInfo);
            userInfosList.toSetUserInfos(userInfos);
            XmlUtil.convertToXml(userInfosList, PATH + DEFAULT_FILENAME);
        }
        return true;
    }

    /**
     * (non-Javadoc)
     *
     * @see edu.muc.service.BaseServiceInterface#updateByName(java.lang.String)
     */

    public boolean updateByName(String name, UserInfo entity) {
        // TODO Auto-generated method stub
        List<UserInfo> userInfos = getAll();
        int index = this.findIndexByName(name);
        if (index != -1) {
            userInfos.set(index, entity);
            userInfosList.toSetUserInfos(userInfos);
            XmlUtil.convertToXml(userInfosList, PATH + DEFAULT_FILENAME);
        }
        return true;
    }

    /**
     * (non-Javadoc)
     *
     * @see edu.muc.service.BaseServiceInterface#checkValue(java.lang.String)
     */
    @Override
    public boolean checkValue(String valueName, String value) {
        // TODO Auto-generated method stub
        boolean flag = Dom4jXml.findValue(valueName, value, PATH + DEFAULT_FILENAME);
        return flag;
    }


}
