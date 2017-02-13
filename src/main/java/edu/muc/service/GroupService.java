/**
 *
 */
package edu.muc.service;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import edu.muc.model.Type;
import edu.muc.model.UserInfo;
import edu.muc.model.list.TypesList;
import edu.muc.model.list.UserInfosList;
import edu.muc.service.plugin.Dom4jXml;
import edu.muc.service.plugin.XmlUtil;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月31日 上午9:16:16
 */
public class GroupService extends BaseService implements
        BaseServiceInterface<UserInfo> {

    /**
     * private final Hashtable hashtableData = new Hashtable();
     */

    private static GroupService groupService = new GroupService();
    private static Hashtable<String, TypesList> groupHashtable = new Hashtable<String, TypesList>();

    private static UserInfosList userInfosList;
    private static TypesList groupList;
    private final static String DEFAULT_FILENAME = "\\Group.xml";
    private final static String DEFAULT_FILEFOLDER = "\\Group";

    /**
     * Cache the hash code for the string
     */
    private GroupService() {
        init();
    }

    public static GroupService getInstance() {
        return groupService;
    }

    /**
     * @help this for the init()
     */
    protected void init() {
        groupList = (TypesList) XmlUtil.convertXmlFileToObject(TypesList.class,
                PATH + DEFAULT_FILEFOLDER + DEFAULT_FILENAME);
        if (groupList == null) {
            groupList = new TypesList();
        } else {
            Iterator<Type> iterator = getAllType().iterator();
            Type temp = null;
            while (iterator.hasNext()) {
                temp = iterator.next();
                loadTypes("\\Type", temp.getName());
            }
        }
        // hashtableData.put("all", userInfoList.toGetUserInfos());//to gei the
        // data into the list;

    }

    private TypesList loadTypes(String folderName, String name) {
        TypesList typesList = (TypesList) XmlUtil.convertXmlFileToObject(
                TypesList.class, PATH + DEFAULT_FILEFOLDER + folderName + "\\"
                        + name + ".xml");
        if (typesList != null)
            groupHashtable.put(name, typesList);
        return typesList;
    }

    private TypesList loadTypes(String name) {
        TypesList typesList = (TypesList) XmlUtil.convertXmlFileToObject(
                TypesList.class, PATH + DEFAULT_FILEFOLDER + "\\"
                        + name + ".xml");
        if (typesList != null)
            groupHashtable.put(name, typesList);
        return typesList;
    }

    public List<Type> getAllType() {
        return groupList != null ? groupList.toGetTypes() : null;
    }

    public List<Type> getAllGroup() {
        return this.getAllType();
    }

    public boolean saveGroup() {
        XmlUtil.convertToXml(groupList, PATH + DEFAULT_FILEFOLDER + DEFAULT_FILENAME);
        return true;
    }

    public boolean save(String name, TypesList typesList) {
        XmlUtil.convertToXml(typesList, PATH + DEFAULT_FILEFOLDER + "\\" + name + ".xml");
        return true;
    }

    public boolean saveMember(String name, TypesList typesList) {
        XmlUtil.convertToXml(typesList, PATH + DEFAULT_FILEFOLDER + "\\" + name + ".xml");
        return true;
    }

    private boolean persistentTypes(String folderName, String name,
                                    TypesList typesList) {
        XmlUtil.convertToXml(typesList, PATH + DEFAULT_FILEFOLDER + folderName
                + "\\" + name + ".xml");
        return true;
    }

    public boolean addGroup(Type type) {
        groupList.toGetTypes().add(0, type);
        this.saveGroup();
        return true;
    }

    public boolean deleteGroup(String id) {
        int index = 0;
        List<Type> types = groupList.toGetTypes();
        Iterator<Type> iterator = types.iterator();
        while (iterator.hasNext()) {
            Type temp = iterator.next();
            if (temp.getId().endsWith(id)) {
                groupList.toGetTypes().remove(index);
                File file = new File(PATH + DEFAULT_FILEFOLDER + "\\" + temp.getName() + ".xml");
                if (file.exists()) {
                    file.delete();
                }
                this.saveGroup();
                break;
            }
            index++;
        }

        return true;
    }

    public List<Type> getGroup() {
        return this.getAllGroup();
    }

    public boolean addMember(String groupName, String id, String userInfoId, String newsId) {
        Type type = new Type();
        type.setId(id);
        type.setName(userInfoId);
        type.setValue(newsId);
        if (groupHashtable.containsKey(groupName)) {
            TypesList temp = groupHashtable.get(groupName);
            temp.addType(type);
            groupHashtable.put(groupName, temp);
            this.save(groupName, temp);
        } else {
            TypesList temp = new TypesList();
            temp.addType(type);
            groupHashtable.put(groupName, temp);
            this.save(groupName, temp);
        }
        return true;
    }

    public boolean addMember(String groupName, Type type) {
        if (groupHashtable.containsKey(groupName)) {
            TypesList temp = groupHashtable.get(groupName);
            temp.addType(type);
            groupHashtable.put(groupName, temp);
            this.save(groupName, temp);
        } else {
            TypesList temp = new TypesList();
            temp.addType(type);
            groupHashtable.put(groupName, temp);
            this.save(groupName, temp);
        }
        return true;
    }

    public boolean deleteMember(String groupName, String id) {
        TypesList temp = null;
        List<Type> types = null;
        int index = 0;
        if (groupHashtable.containsKey(groupName)) {
            temp = groupHashtable.get(groupName);
            types = temp.toGetTypes();
            Iterator<Type> iterator = types.iterator();
            while (iterator.hasNext()) {
                Type cache = iterator.next();
                if (cache.getId().endsWith(id)) {
                    types.remove(index);
                    this.saveMember(groupName, temp);
                    groupHashtable.put(groupName, temp);
                    break;
                }
                index++;
            }

        }
        return true;
    }


    public boolean deleteMemberByName(String groupName, String name) {
        TypesList temp = null;
        List<Type> types = null;
        int index = 0;
        if (groupHashtable.containsKey(groupName)) {
            temp = groupHashtable.get(groupName);
            types = temp.toGetTypes();
            Iterator<Type> iterator = types.iterator();
            while (iterator.hasNext()) {
                Type cache = iterator.next();
                if (cache.getName().endsWith(name)) {
                    types.remove(index);
                    this.saveMember(groupName, temp);
                    groupHashtable.put(groupName, temp);
                    break;
                }
                index++;
            }

        }
        return true;
    }


    public List<Type> getAllMember(String groupName) {
        TypesList temp = null;
        if (groupHashtable.containsKey(groupName)) {
            temp = groupHashtable.get(groupName);
            return temp.toGetTypes();
        } else {
            temp = this.loadTypes(groupName);
            groupHashtable.put(groupName, temp);
        }
        return temp != null ? temp.toGetTypes() : null;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.muc.service.dao.BaseDao#findById(java.lang.String)
     */
    @Override
    public UserInfo findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.muc.service.dao.BaseDao#getAll()
     */
    @Override
    public List<UserInfo> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.muc.service.dao.BaseDao#findIndexById(java.lang.String)
     */
    @Override
    public int findIndexById(String id) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.muc.service.dao.BaseDao#findIndexByName(java.lang.String)
     */
    @Override
    public int findIndexByName(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.muc.service.dao.BaseDao#findIndexByValue(java.lang.String,
     * java.lang.String)
     */
    @Override
    public int findIndexByValue(String valueName, String value) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.muc.service.dao.BaseDao#save()
     */
    @Override
    public boolean save() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.muc.service.dao.BaseDao#save(java.lang.Object)
     */
    @Override
    public boolean save(UserInfo entity) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.muc.service.dao.BaseDao#update(java.lang.Object)
     */
    @Override
    public boolean update(UserInfo entity) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.muc.service.dao.BaseDao#update(java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean update(String id, String value) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.muc.service.dao.BaseDao#delete(java.lang.String)
     */
    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.muc.service.dao.BaseDao#checkValue(java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean checkValue(String valueName, String value) {
        // TODO Auto-generated method stub
        return false;
    }

}
