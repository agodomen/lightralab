/**
 *
 */
package edu.muc.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import edu.muc.model.Article;
import edu.muc.model.Title;
import edu.muc.model.Type;
import edu.muc.model.UserInfo;
import edu.muc.model.list.TitlesList;
import edu.muc.model.list.TypesList;
import edu.muc.platform.plugin.CompratorByFileName;
import edu.muc.platform.plugin.tools.SystemTools;
import edu.muc.service.plugin.Dom4jXml;
import edu.muc.service.plugin.XmlUtil;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年6月1日 上午10:39:49
 */
public class NewsService extends BaseService {
    /**
     * save the different type titlesList
     */
    private static Hashtable<String, TitlesList> titlesHashtable = new Hashtable<String, TitlesList>();
    /**
     * the titlesList name by the type,eg:type name is ABC, the titlesList will
     * save the file name ABC.xml
     */
    private static Hashtable<String, TypesList> modulesHashtable = new Hashtable<String, TypesList>();
    private final static String DEFAULT_TYPE_FILENAME = "\\Type.xml";
    private final static String DEFAULT_MODULE_FILENAME = "\\Module.xml";
    private final static String DEFAULT_FILEFOLDER = "\\News";
    private static NewsService newsService;
    private final static int MAX_SIZE = 35;
    /**
     * save the all titlesList
     */
    private static TypesList typesList = null;
    private static TypesList moduleList = null;

    public NewsService() {
        init();
    }

    public static NewsService getInstance() {
        if (newsService == null)
            newsService = new NewsService();
        return newsService;
    }

    protected void init() {
        typesList = (TypesList) XmlUtil.convertXmlFileToObject(TypesList.class,
                PATH + DEFAULT_FILEFOLDER + DEFAULT_TYPE_FILENAME);
        moduleList = (TypesList) XmlUtil.convertXmlFileToObject(
                TypesList.class, PATH + DEFAULT_FILEFOLDER
                        + DEFAULT_MODULE_FILENAME);
        if (typesList == null) {
            typesList = new TypesList();
        } else {
            Iterator<Type> iterator = getAllType().iterator();
            Type temp = null;
            while (iterator.hasNext()) {
                temp = iterator.next();
                loadTitles("\\Type", temp.getName());
            }
        }

        if (moduleList == null) {
            moduleList = new TypesList();
        } else {
            Iterator<Type> iterator = getAllType().iterator();
            Type temp = null;
            while (iterator.hasNext()) {
                temp = iterator.next();
                this.loadTypes("\\Module", temp.getId());
                //loadTitles("\\Module", temp.getName());
            }
        }

        File dir = new File(PATH + DEFAULT_FILEFOLDER + "\\Title");
        File[] files = dir.listFiles();
        if (files.length > 0) {
            for (File file : files) {
                if (file.getName().endsWith(".xml")) {
                    String str = file.getName();
                    loadTitles("\\Title",
                            str.substring(0, str.lastIndexOf(".")));
                }
            }
        }

    }

    /**
     * this is for to get all the type content
     */
    public List<Type> getAllType() {
        return typesList != null ? typesList.toGetTypes() : null;
    }

    public List<Type> getAllModule() {
        return moduleList != null ? moduleList.toGetTypes() : null;
    }

    /**
     * save the typesList
     */
    public boolean saveType() {
        XmlUtil.convertToXml(typesList, PATH + DEFAULT_FILEFOLDER
                + DEFAULT_TYPE_FILENAME);
        return false;
    }

    public boolean saveModule() {
        XmlUtil.convertToXml(moduleList, PATH + DEFAULT_FILEFOLDER
                + DEFAULT_MODULE_FILENAME);
        return false;
    }

    /**
     * this is default save by id
     */
    public boolean saveType(Type entity) {
        List<Type> types = getAllType();
        types.add(entity);
        typesList.toSetTypes(types);
        saveType();
        return true;
    }

    public boolean saveModule(Type entity) {
        List<Type> types = this.getAllModule();
        types.add(entity);
        moduleList.toSetTypes(types);
        saveModule();
        return true;
    }

    /**
     * find index by id
     *
     * @param id
     * @return
     */
    public int findTypeIndexById(String id) {
        List<Type> types = getAllType();
        Iterator<Type> iterator = types.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int findTypeIndexById(List<Type> types, String id) {
        Iterator<Type> iterator = types.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int findModuleIndexById(String id) {
        List<Type> types = this.getAllModule();
        Iterator<Type> iterator = types.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int findTypeIndexByName(String name) {
        List<Type> types = getAllType();
        Iterator<Type> iterator = types.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int findModuleIndexByName(String name) {
        List<Type> types = this.getAllModule();
        Iterator<Type> iterator = types.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int findTypeIndexByValue(String valueName, String value) {
        List<Type> types = getAllType();
        Iterator<Type> iterator = types.iterator();
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
     * this is for find the type entity by id
     *
     * @param id
     * @return
     */
    public Type findTypeById(String id) {
        int index = this.findTypeIndexById(id);
        if (index != -1)
            return getAllType().get(index);
        return null;
    }

    public Type findModuleById(String id) {
        int index = this.findModuleIndexById(id);
        if (index != -1)
            return this.getAllModule().get(index);
        return null;
    }

    public Type findModuleByIndex(int index) {
        if (index != -1)
            return this.getAllModule().get(index);
        return null;
    }

    public boolean updateType(Type entity) {
        List<Type> types = getAllType();
        int index = this.findTypeIndexById(entity.getId());
        if (index != -1) {
            types.set(index, entity);
            typesList.toSetTypes(types);
            this.saveType();
        }
        return true;
    }


    public boolean updateModule(Type entity) {
        List<Type> types = this.getAllModule();
        int index = this.findModuleIndexById(entity.getId());
        if (index != -1) {
            types.set(index, entity);
            moduleList.toSetTypes(types);
            this.saveModule();
        }
        return true;
    }

    public boolean updateTypeByName(String id, String name) {
        List<Type> types = getAllType();
        this.deletXmlData("\\Type", name);
        int index = this.findTypeIndexById(id);
        if (index != -1) {
            Type entity = types.get(index);
            entity.setName(name);
            types.set(index, entity);
            typesList.toSetTypes(types);
            saveType();
        }
        return true;
    }

    private boolean deletXmlData(String dir, String key) {
        File file = new File(PATH + dir
                + "\\" + key + ".xml");
        if (file.isFile())
            file.delete();
        return true;
    }

    public boolean updateModuleByName(String id, String name) {
        List<Type> types = this.getAllModule();
        int index = this.findModuleIndexById(id);
        if (index != -1) {
            Type entity = types.get(index);
            entity.setName(name);
            types.set(index, entity);
            moduleList.toSetTypes(types);
            this.saveModule();
        }
        return true;
    }

    public boolean deleteType(String id) {

        List<Type> types = getAllType();
        int index = this.findTypeIndexById(id);
        String key = types.get(index).getName();
        if (titlesHashtable.containsKey(key))
            titlesHashtable.remove(key);
        File file = new File(PATH + DEFAULT_FILEFOLDER + "\\Type" + "\\" + key
                + ".xml");
        if (file.isFile()) {
            file.delete();
        }
        types.remove(index);
        saveType();
        return true;
    }

    public boolean deleteModule(String id) {

        List<Type> types = this.getAllModule();
        int index = this.findModuleIndexById(id);
        String key = types.get(index).getName();
        if (modulesHashtable.containsKey(key))
            modulesHashtable.remove(key);
        types.remove(index);
        this.saveModule();
        return true;
    }

    public int countTitles(String key) {
        int count = 0;
        if (titlesHashtable.containsKey(key)) {
            count = titlesHashtable.get(key).toGetTitles().size();
        } else {
            TitlesList titlesList = loadTitles("\\Title", key);
            if (titlesList != null)
                count = titlesList.toGetTitles().size();
        }
        return count;
    }

    public boolean titlesCount(String key, String id) {
        if (titlesHashtable.containsKey(key)) {
            TitlesList titlesList = titlesHashtable.get(key);
            List<Title> titles = titlesList.toGetTitles();
            int index = this.findTitleIndexById(titles, id);
            if (index != -1) {
                Title title = titles.get(index);
                title.setClickCount(title.getClickCount() + 1);
                titlesList.toSetTitles(titles);
                this.persistentTitles("\\Title", key, titlesList);
                titlesHashtable.put(key, titlesList);
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * this is for default to find Title file by the key
     *
     * @param key
     * @return
     */
    public List<Title> viewtitles(String key) {
        List<Title> titles = null;
        if (titlesHashtable.containsKey(key)) {
            titles = titlesHashtable.get(key).toGetTitles();
        } else {
            TitlesList titlesList = loadTitles("\\Title", key);
            if (titlesList != null)
                titles = titlesList.toGetTitles();
        }
        return titles;
    }

    /**
     * this is the default dir:Type to get key file
     *
     * @param key
     * @return
     */
    public List<Title> viewtypeTitles(String key) {
        List<Title> titles = null;
        if (titlesHashtable.containsKey(key)) {
            titles = titlesHashtable.get(key).toGetTitles();
        } else {
            TitlesList titlesList = loadTitles("\\Type", key);
            if (titlesList != null)
                titles = titlesList.toGetTitles();
        }
        return titles;
    }

    public List<Type> viewModuleTypes(String key) {
        List<Type> types = null;

        if (modulesHashtable.containsKey(key)) {
            types = modulesHashtable.get(key).toGetTypes();
        } else {
            TypesList typesList = loadTypes("\\Module", key);
            if (typesList != null)
                types = typesList.toGetTypes();
        }
        return types;
    }


    /**
     * this you can give the suspect dir to find the Titles by the key.
     *
     * @param dir
     * @param key
     * @return
     */
    public List<Title> viewTitles(String dir, String key) {
        List<Title> titles = null;
        if (titlesHashtable.containsKey(key)) {
            titles = titlesHashtable.get(key).toGetTitles();
        } else {
            TitlesList titlesList = loadTitles(dir, key);
            if (titlesList != null)
                titles = titlesList.toGetTitles();
        }
        return titles;
    }

    public List<Type> viewTypes(String dir, String key) {
        List<Type> types = null;
        if (modulesHashtable.containsKey(key)) {
            types = modulesHashtable.get(key).toGetTypes();
        } else {
            TypesList typesList = loadTypes(dir, key);
            if (typesList != null) {
                types = typesList.toGetTypes();
            }
        }
        return types;
    }

    /**
     * This is the method to delete the title by suspect dir and the id from the
     * key file
     *
     * @param dir
     * @param key
     * @param id
     * @return
     */
/*	public boolean deleteTitle(String dir, String key, String id) {
        List<Title> titles = null;
		boolean flag = true;
		TitlesList titlesList = null;
		Article article = null;
		if (titlesHashtable.containsKey(key)) {
			titlesList = titlesHashtable.get(key);
			titles = titlesList.toGetTitles();
			int index = this.findTitleIndexById(titles, id);
			titles.remove(index);
			titlesList.toSetTitles(titles);
			titlesHashtable.put(key, titlesList);
		} else {
			titlesList = loadTitles(dir, key);
			if (titlesList != null)
				titles = titlesList.toGetTitles();
			if (null != titles) {
				int index = this.findTitleIndexById(titles, id);
				titles.remove(index);
			}
		}
		titlesList.toSetTitles(titles);
		this.persistentTitles(dir, key, titlesList);
		article = this.loadAritcle(id);
		if (null != article.getDescription()
				&& article.getDescription().indexOf("#1024#") != -1) {
			return true;
		} else {
			File file = new File(PATH + DEFAULT_FILEFOLDER + "\\Article" + "\\"
					+ id + ".xml");
			if (file.isFile()) {
				file.delete();
			}
		}
		return flag;
	}*/
    public boolean deleteTitleByDir(String is, String dir, String key, String id) {
        List<Title> titles = null;
        boolean flag = true;
        TitlesList titlesList = null;
        Article article = null;
        if (titlesHashtable.containsKey(key)) {
            titlesList = titlesHashtable.get(key);
            titles = titlesList.toGetTitles();
            int index = this.findTitleIndexById(titles, id);
            if (index != -1)
                titles.remove(index);
            titlesList.toSetTitles(titles);
            titlesHashtable.put(key, titlesList);
        } else {
            titlesList = loadTitles(dir, key);
            if (titlesList != null)
                titles = titlesList.toGetTitles();
            if (null != titles) {
                int index = this.findTitleIndexById(titles, id);
                titles.remove(index);
            }
        }
        titlesList.toSetTitles(titles);
        this.persistentTitles(dir, key, titlesList);
        article = this.loadArticle(id);
        if (is.equals("no") || article == null || (null != article.getDescription()
                && article.getDescription().indexOf("#1024#") != -1)) {
            return true;
        } else {
            File file = new File(PATH + DEFAULT_FILEFOLDER + "\\Article" + "\\"
                    + id + ".xml");
            if (file.isFile()) {
                file.delete();
            }
        }
        return flag;
    }


    public boolean deleteTypeByDir(String is, String dir, String key, String id) {
        List<Type> types = null;
        boolean flag = true;
        TypesList typesList = null;
        Article article = null;
        if (modulesHashtable.containsKey(key)) {
            typesList = modulesHashtable.get(key);
            types = typesList.toGetTypes();
            int index = this.findTypeIndexById(types, id);
            if (index != -1)
                types.remove(index);
            typesList.toSetTypes(types);
            modulesHashtable.put(key, typesList);
        } else {
            typesList = loadTypes(dir, key);
            if (typesList != null)
                types = typesList.toGetTypes();
            if (null != types) {
                int index = this.findTypeIndexById(types, id);
                types.remove(index);
            }
        }
        typesList.toSetTypes(types);
        this.persistentTypes(dir, key, typesList);
        return flag;
    }


    @Test
    public void test() {

    }

    /**
     * this is for get the target type name xml required the foldername and
     * filename
     */
    private TitlesList loadTitles(String folderName, String name) {
        TitlesList titlesList = (TitlesList) XmlUtil.convertXmlFileToObject(
                TitlesList.class, PATH + DEFAULT_FILEFOLDER + folderName + "\\"
                        + name + ".xml");
        if (titlesList != null)
            titlesHashtable.put(name, titlesList);
        return titlesList;
    }


    private TypesList loadTypes(String folderName, String name) {
        TypesList typesList = (TypesList) XmlUtil.convertXmlFileToObject(
                TypesList.class, PATH + DEFAULT_FILEFOLDER + folderName + "\\"
                        + name + ".xml");
        if (typesList != null)
            modulesHashtable.put(name, typesList);
        return typesList;
    }

    /**
     * This is the persistentTitles
     *
     * @param folderName
     * @param name
     * @param titlesList
     * @return
     */
    private boolean persistentTitles(String folderName, String name,
                                     TitlesList titlesList) {
        XmlUtil.convertToXml(titlesList, PATH + DEFAULT_FILEFOLDER + folderName
                + "\\" + name + ".xml");
        return true;
    }

    private boolean persistentTypes(String folderName, String name,
                                    TypesList typesList) {
        XmlUtil.convertToXml(typesList, PATH + DEFAULT_FILEFOLDER + folderName
                + "\\" + name + ".xml");
        return true;
    }

    private Article loadArticle(String name) {
        Article article = (Article) XmlUtil.convertXmlFileToObject(
                Article.class, PATH + DEFAULT_FILEFOLDER + "\\Article\\" + name
                        + ".xml");
        return article;
    }

    public Article getArticle(String id) {
        return this.loadArticle(id);
    }

    public Article findArticle(String id) {
        return this.loadArticle(id);
    }

    public boolean updateArticle(Article article) {

        return this.persistentArticle(article.getId(), article);
    }

    private boolean persistentArticle(String name, Article article) {
        XmlUtil.convertToXml(article, PATH + DEFAULT_FILEFOLDER + "\\Article\\"
                + name + ".xml");
        return true;
    }

    private boolean persistentUserInfoArticle(Article article) {
        XmlUtil.convertToXml(article, PATH + "\\UserInfo" + "\\Article\\"
                + article.getId() + ".xml");
        return true;
    }

    public int findTitleIndexById(List<Title> titles, String id) {
        Iterator<Title> iterator = titles.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getId().endsWith(id))
                return index;
            index++;
        }
        return -1;
    }

    public Title findTitleById(String target, String id) {
        TitlesList temp = null;
        int index = -1;
        if (titlesHashtable.containsKey(target)) {
            temp = titlesHashtable.get(target);

        } else {
            temp = this.loadTitles("\\Title", target);
        }
        if (temp != null)
            index = this.findTitleIndexById(temp.toGetTitles(), id);
        if (index != -1)
            return temp.toGetTitles().get(index);
        else
            return null;
    }

    public Title findTitleById(String dir, String target, String id) {
        TitlesList temp = null;
        int index = -1;

        if (titlesHashtable.containsKey(target)) {
            temp = titlesHashtable.get(target);

        } else {
            temp = this.loadTitles(dir, target);
        }
        if (temp != null)
            index = this.findTitleIndexById(temp.toGetTitles(), id);
        if (index != -1)
            return temp.toGetTitles().get(index);
        else
            return null;
    }

    /**
     * this is to find the title from the type
     *
     * @param target
     * @param id
     * @return
     */
    public Title findTypeTitleById(String target, String id) {
        TitlesList temp = null;
        int index = -1;
        if (titlesHashtable.containsKey(target)) {
            temp = titlesHashtable.get(target);

        } else {
            temp = this.loadTitles("\\Type", target);
        }
        if (temp != null)
            index = this.findTitleIndexById(temp.toGetTitles(), id);
        if (index != -1)
            return temp.toGetTitles().get(index);
        else
            return null;
    }

    /**
     * this is for save the news
     */
    public boolean saveNews(Title title, Article article) {
        boolean flag = true;
        TitlesList temp = null;
        List<Title> titles = null;
        TitlesList titlesList = null;
		/*
		 * first: is the hashtable has the file second:does not has the file
		 * ,creat the file;
		 */
        if (titlesHashtable.containsKey(title.getLinkId())) {
            titlesList = titlesHashtable.get(title.getLinkId());
            titles = titlesList.toGetTitles();
            titles.add(0, title);
            titlesList.toSetTitles(titles);
            titlesHashtable.put(title.getLinkId(), titlesList);
            this.persistentTitles("\\Title", title.getLinkId(), titlesList);

        } else {
            temp = new TitlesList();
            temp.toGetTitles().add(0, title);
            titlesHashtable.put(title.getLinkId(), temp);
            this.persistentTitles("\\Title", title.getLinkId(), temp);

        }
        this.persistentArticle(article.getId(), article);
        return flag;


    }

    public Article getUserInfoNews(String id) {
        boolean flag = true;
        Article article = (Article) XmlUtil.convertXmlFileToObject(
                Article.class, PATH + "\\UserInfo" + "\\Article\\" + id
                        + ".xml");
        return article;
    }


    public boolean saveUserInfoNews(Article article) {
        boolean flag = true;
        File file = new File(PATH + "\\UserInfo" + "\\Article\\"
                + article.getId() + ".xml");
        if (!file.exists()) {
            this.persistentUserInfoArticle(article);
        }
        return flag;
    }

    public boolean updateUserInfoNews(Article article) {
        boolean flag = true;
        this.persistentUserInfoArticle(article);
        return flag;
    }

    public boolean deleteUserInfoNews(String id, String target) {
        boolean flag = true;
        File file = new File(PATH + "\\UserInfo" + "\\Article\\" + id
                + ".xml");
        if (file.isFile()) {
            file.delete();
        }
        return flag;
    }

    public boolean saveTitleByType(String target, Title title) {
        boolean flag = true;
        TitlesList temp = null;
        String string = null;
		/*
		 * first: is the hashtable has the file second:does not has the file
		 * ,creat the file;
		 */
        if (titlesHashtable.containsKey(target)) {
            temp = loadTitles("\\Type", target);
            temp.toGetTitles().add(0, title);
            titlesHashtable.put(target, temp);
            this.persistentTitles("\\Type", target, temp);

        } else {
            temp = new TitlesList();
            temp.toGetTitles().add(0, title);
            titlesHashtable.put(target, temp);
            this.persistentTitles("\\Type", target, temp);
        }
        return true;
    }

    public boolean saveModuleByType(String target, Type type) {
        boolean flag = true;
        TypesList temp = null;
        String string = null;
		/*
		 * first: is the hashtable has the file second:does not has the file
		 * ,creat the file;
		 */
        if (modulesHashtable.containsKey(target)) {
            temp = this.loadTypes("\\Module", target);
            temp.toGetTypes().add(0, type);
            modulesHashtable.put(target, temp);
            this.persistentTypes("\\Module", target, temp);

        } else {
            temp = new TypesList();
            temp.toGetTypes().add(0, type);
            modulesHashtable.put(target, temp);
            this.persistentTypes("\\Module", target, temp);
        }
        return true;
    }

    public boolean updateNews(Title title, Article article) {
        boolean flag = true;
        TitlesList temp = null;
        List<Title> titles = null;
        String key = title.getLinkId();
        if (titlesHashtable.containsKey(key)) {
            temp = titlesHashtable.get(key);
            int index = this.findTitleIndexById(temp.toGetTitles(),
                    title.getId());
            if (index != -1) {
                titles = temp.toGetTitles();
                titles.set(index, title);
                temp.toSetTitles(titles);
                titlesHashtable.put(key, temp);
                this.persistentTitles("\\Title", title.getLinkId(), temp);
            }
        }
        this.persistentArticle(article.getId(), article);
        return flag;
    }

    public boolean updateTitle(Title title) {
        boolean flag = true;
        TitlesList temp = null;
        List<Title> titles = null;
        String key = title.getLinkId();
        if (titlesHashtable.containsKey(key)) {
            temp = titlesHashtable.get(key);
            int index = this.findTitleIndexById(temp.toGetTitles(),
                    title.getId());
            //	System.out.println("####"+index+title.getId()+temp.toGetTitles());
            if (index != -1) {
                titles = temp.toGetTitles();
                Title aTitle = titles.get(index);
                title.setClickCount(aTitle.getClickCount());
                title.setCreatDate(aTitle.getCreatDate());
                title.setCreatorId(aTitle.getCreatorId());
                title.setCreatorName(aTitle.getCreatorName());
                title.setLinkId(aTitle.getLinkId());
                if (title.getLogo() == null)
                    title.setLogo(aTitle.getLogo());
                titles.set(index, title);
                temp.toSetTitles(titles);
                titlesHashtable.put(key, temp);
                System.out.println(new Date() + "####" + title.getOutline() + "debug" + aTitle.getOutline() + "the list" + titlesHashtable.get(key).toGetTitles().get(index).getOutline());
                this.persistentTitles("\\Title", key, temp);
            }
        }
        return flag;
    }


    public boolean updateTypeTitle(String target, Title title) {
        boolean flag = true;
        TitlesList temp = null;
        List<Title> titles = null;
        String key = target;
        if (titlesHashtable.containsKey(key)) {
            temp = titlesHashtable.get(key);
            int index = this.findTitleIndexById(temp.toGetTitles(),
                    title.getId());
            if (index != -1) {
                titles = temp.toGetTitles();
                Title aTitle = titles.get(index);
                title.setClickCount(aTitle.getClickCount());
                title.setCreatDate(aTitle.getCreatDate());
                title.setCreatorId(aTitle.getCreatorId());
                title.setCreatorName(aTitle.getCreatorName());
                title.setLinkId(aTitle.getLinkId());
                title.setLogo(aTitle.getLogo());
                titles.set(index, title);
                temp.toSetTitles(titles);
                titlesHashtable.put(key, temp);
                this.persistentTitles("\\Type", key, temp);
            }
        }
        return flag;
    }

    /**
     * this is for deleting the artitcle and titles [news!!]
     */
    public boolean deleteNews(String id, String target) {
        boolean flag = true;
        TitlesList temp = null;
        List<Title> titles = null;
        String key = target;
        if (titlesHashtable.containsKey(key)) {
            temp = titlesHashtable.get(key);
            int index = this.findTitleIndexById(temp.toGetTitles(), id);
            if (index != -1) {
                titles = temp.toGetTitles();
                titles.remove(index);
                temp.toSetTitles(titles);
                titlesHashtable.put(key, temp);
                this.persistentTitles("\\Title", target, temp);
            }
        }
        File file = new File(PATH + DEFAULT_FILEFOLDER + "\\Article\\" + id
                + ".xml");
        File logFile = new File(SystemTools.getWebRootPath()
                + "\\upload\\logo\\" + id + ".xml");
        if (file.isFile()) {
            file.delete();
        }
        if (logFile.isFile()) {
            logFile.delete();
        }
        return flag;
    }

    /**
     * this is the back up for the fuction
     *
     * @param title
     * @param article
     * @param bakup
     * @return
     */
	/*
	 * private boolean updateNews(Title title, Article article, NewsService
	 * bakup) { boolean flag = true; TitlesList temp = null;
	 * 
	 * 
	 * first: is the hashtable has the file second:does not has the file ,creat
	 * the file;
	 * 
	 * if (hashtable.containsKey(title.getLinkId())) { temp =
	 * hashtable.get(title.getLinkId()); int index =
	 * this.findTitleIndexById(temp.toGetTitles(), title.getId()); if (index !=
	 * -1) { temp.toGetTitles().set(index, title); } else
	 * temp.toGetTitles().add(0, title); this.persistentTitles("\\Title",
	 * title.getLinkId(), temp); hashtable.put(title.getLinkId(), temp);
	 * this.titlesList = temp; } else { String name =
	 * titleHashtable.get(CURRENT_PAGE); CURRENT_PAGE++; if
	 * (title.getLinkId().compareTo(name) > 0) { temp = new TitlesList();
	 * temp.toGetTitles().add(0, title); this.persistentTitles("\\Title",
	 * title.getLinkId(), temp); hashtable.put(title.getLinkId(), temp);
	 * titleHashtable.put(CURRENT_PAGE, title.getLinkId()); this.titlesList =
	 * temp; } else { int i = CURRENT_PAGE - 1; String string = null; while (i >
	 * 1) { string = titleHashtable.get(i - 1); if
	 * (title.getLinkId().compareTo(string) < 0) { temp = new TitlesList();
	 * temp.toGetTitles().add(0, title); this.persistentTitles("\\Title",
	 * title.getLinkId(), temp); hashtable.put(title.getLinkId(), temp);
	 * titleHashtable.put(i, title.getLinkId()); } titleHashtable.put(i,
	 * string); i--; } } } this.persistentArticle(article.getId(), article);
	 * return flag; }
	 */
    public boolean checkValue(String valueName, String value) {
        // TODO Auto-generated method stub
        boolean flag = Dom4jXml.findValue(valueName, value, PATH
                + DEFAULT_FILEFOLDER + DEFAULT_TYPE_FILENAME);
        if (flag == true) {
            if (titlesHashtable.containsKey(value))
                flag = false;
        }
        return flag;
    }

    /*
     * this is for article type
     */
    public boolean checkValue(String valueName, String value, String target) {
        // TODO Auto-generated method stub
        boolean flag = Dom4jXml.findValue(valueName, value, PATH
                + DEFAULT_FILEFOLDER + target);
        if (flag == true) {
            if (titlesHashtable.containsKey(value))
                flag = false;
        }
        return flag;
    }
}
