/**
 *
 */
package edu.muc.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.jfinal.core.ActionKey;

import edu.muc.controller.plugin.Controller;
import edu.muc.model.Article;
import edu.muc.model.Title;
import edu.muc.model.Type;
import edu.muc.model.UserInfo;
import edu.muc.platform.plugin.tools.SystemTools;
import edu.muc.service.GroupService;
import edu.muc.service.NewsService;
import edu.muc.service.PlatformService;
import edu.muc.service.UserInfoService;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年7月3日 上午11:37:12
 */
@SuppressWarnings("unused")
@Controller(controllerKey = {"/index"})
public class IndexController extends BaseController {

    /**
     *
     */
    public IndexController() {
        // TODO Auto-generated constructor stub
    }

    public void init() {
        ServletContext application = this.getRequest().getServletContext();
        NewsService newsService = NewsService.getInstance();
        List<Type> system = newsService.viewModuleTypes("system");
        //System.out.println(new Date()+""+system);
        Iterator<Type> iterator = system.iterator();
        Type temp = null;
        while (iterator.hasNext()) {
            temp = iterator.next();
            application.setAttribute(temp.getName(), newsService.viewtypeTitles(temp.getName()));
            //	System.out.println(temp.getName()+"###"+newsService.viewtypeTitles(temp.getName()));
        }
        system = newsService.viewModuleTypes("article");
        iterator = system.iterator();
        List<Type> theType = new ArrayList<Type>();
        while (iterator.hasNext()) {
            temp = iterator.next();
            Type aType = new Type();
            aType.setId(temp.getName());
            aType.setName(temp.getValue());
            aType.setValue(newsService.viewtypeTitles(temp.getName()).size() + "");
            theType.add(aType);
        }
        application.setAttribute("articleTypeList", theType);
        system = newsService.viewModuleTypes("notice");
        iterator = system.iterator();
        theType = new ArrayList<Type>();
        while (iterator.hasNext()) {
            temp = iterator.next();
            Type aType = new Type();
            aType.setId(temp.getName());
            aType.setName(temp.getValue());
            try {
                aType.setValue(newsService.viewtypeTitles(temp.getName()).size() + "");
            } catch (Exception e) {
                System.out.println("##:" + new Date() + temp + aType.getId() + aType.getName() + aType.getValue());
                System.out.println("##" + new Date() + e.toString() + temp.getName() + aType.getId() + aType.getName() + aType.getValue());
                aType.setValue("0");
            }
            theType.add(aType);
        }
        application.setAttribute("noticeTypeList", theType);

        system = newsService.viewModuleTypes("news");
        iterator = system.iterator();
        theType = new ArrayList<Type>();
        while (iterator.hasNext()) {
            temp = iterator.next();
            Type aType = new Type();
            aType.setId(temp.getName());
            aType.setName(temp.getValue());
            try {
                aType.setValue(newsService.viewtypeTitles(temp.getName()).size() + "");
            } catch (Exception e) {
                System.out.println("##" + new Date() + e.toString());
                aType.setValue("0");
            }
            theType.add(aType);
        }
        application.setAttribute("newsTypeList", theType);
        List<Type> index = newsService.viewModuleTypes("index");
        iterator = index.iterator();
        while (iterator.hasNext()) {
            temp = iterator.next();
            setAttr(temp.getName(), newsService.viewtypeTitles(temp.getName()));
            //	System.out.println(temp.getName()+"####"+newsService.viewtypeTitles(temp.getName()));
        }
        // System.out.println(new Date()+"#####"+this.getRequest().getServletContext().getAttribute("library")+"****"+getAttr("library"));


        render("/blue/index.html");
    }

    public void article() {
        String target = getPara("0");
        String id = getPara("1");

        render("/blue/single.html");
    }

    @ActionKey("/index/type/article")
    public void typeArticle() {
        String target = getPara(0);
        String id = getPara(1);
        NewsService newsService = NewsService.getInstance();
        Title title = null;
        Title temp = null;
        if (null != target) {
            title = newsService.findTitleById("\\Type", target, id);
            temp = newsService.findTitleById(title.getLinkId(), id);
            temp.setClickCount(1 + temp.getClickCount());
            title.setClickCount(temp.getClickCount());
            newsService.updateTitle(temp);
        }
        Article article = newsService.findArticle(id);

        if (null == title)
            render("/common/404.html");
        setAttr("title", title);
        setAttr("article", article);
        render("/blue/single.html");
    }

    public void html() {
        render("/test/index.html");
    }

    public void probe() {
        NewsService newsService = NewsService.getInstance();
        Iterator<Type> iterator = null;
        Type temp = null;
        List<Type> research = newsService.viewModuleTypes("research");
        iterator = research.iterator();
        while (iterator.hasNext()) {
            temp = iterator.next();
            setAttr(temp.getName(), newsService.viewtypeTitles(temp.getName()));
            //	System.out.println(temp.getName()+"####"+newsService.viewtypeTitles(temp.getName()));
        }
        render("/blue/probe.html");
    }

    public void achievements() {
        NewsService newsService = NewsService.getInstance();
        Iterator<Type> iterator = null;
        Type temp = null;
        List<Type> achivements = newsService.viewModuleTypes("achievements");
        iterator = achivements.iterator();
        List<Title> entity = new ArrayList<Title>();
        List<Title> e = null;
        while (iterator.hasNext()) {
            temp = iterator.next();
            e = newsService.viewtypeTitles(temp.getName());
            if (null != e)
                entity.addAll(e);
        }
        setAttr("list", entity);
        render("/blue/achievements.html");
    }

    public void workcenter() {
        int page = this.getParaToInt("page", 0);
        int pre = 0;
        int next = 0;
        int pageSize = 3;

        NewsService newsService = NewsService.getInstance();
        Iterator<Type> iterator = null;
        Type temp = null;
        List<Type> achivements = newsService.viewModuleTypes("achievements");
        iterator = achivements.iterator();
        List<Title> entity = new ArrayList<Title>();
        List<Title> all = new ArrayList<Title>();
        List<Title> e = null;

        int j = 0;
        while (iterator.hasNext()) {
            temp = iterator.next();
            e = newsService.viewtypeTitles(temp.getName());
            if (null != e)
                all.addAll(e);
        }
        j = all.size();
        if (page + pageSize <= j)
            next = page + pageSize;
        else
            next = j;
        if (page - pageSize > 0)
            pre = page - pageSize;


        entity = all.subList(page, next);
        //	System.out.println("Apage:"+page+" next:"+next+" size:"+entity.size());


        if (next == j)
            next = 0;
        setAttr("list", entity);
        setAttr("pre", pre);
        setAttr("next", next);
        render("/blue/workcenter.html");
    }

    @ActionKey("/articleAllType")
    public void articleAllType() {
        String action = this.getPara("action");
        if (action == null)
            action = "tjxw";
        int page = this.getParaToInt("page", 0);
        int pre = 0;
        int next = 0;
        int pageSize = 3;

        NewsService newsService = NewsService.getInstance();
        Iterator<Type> iterator = null;
        List<Title> entity = new ArrayList<Title>();
        List<Title> all = new ArrayList<Title>();
        all = newsService.viewtypeTitles(action);
        if (all == null)
            all = newsService.viewtypeTitles("tjxw");
        int j = 0;
        j = all.size();
        if (page + pageSize <= j)
            next = page + pageSize;
        else
            next = j;
        if (page - pageSize > 0)
            pre = page - pageSize;

        entity = all.subList(page, next);
        if (next == j)
            next = 0;
        setAttr("list", entity);
        setAttr("pre", pre);
        setAttr("next", next);
        setAttr("action", action);
        render("/blue/titles.html");
    }


    @ActionKey("/articleAllModule")
    public void articleAllModule() {
        String action = this.getPara("action");
        if (action == null)
            action = "tjxw";
        int page = this.getParaToInt("page", 0);
        int pre = 0;
        int next = 0;
        int pageSize = 3;

        NewsService newsService = NewsService.getInstance();
        Iterator<Type> iterator = null;
        Type temp = null;
        List<Type> achivements = newsService.viewModuleTypes(action);
        if (achivements == null)
            achivements = newsService.viewModuleTypes("news");
        iterator = achivements.iterator();
        List<Title> entity = new ArrayList<Title>();
        List<Title> all = new ArrayList<Title>();
        List<Title> e = null;

        int j = 0;
        while (iterator.hasNext()) {
            temp = iterator.next();
            e = newsService.viewtypeTitles(temp.getName());
            if (null != e)
                all.addAll(e);
        }
        j = all.size();
        if (page + pageSize <= j)
            next = page + pageSize;
        else
            next = j;
        if (page - pageSize > 0)
            pre = page - pageSize;

        entity = all.subList(page, next);

        if (next == j)
            next = 0;
        setAttr("list", entity);
        setAttr("pre", pre);
        setAttr("next", next);
        setAttr("action", action);
        render("/blue/titles.html");
    }

    public void cultrue() {
        this.initStart();
        render("/blue/culture.html");
    }


    @ActionKey("/index/goCulture/9")
    public void culture9() {

        NewsService newsService = NewsService.getInstance();
        Iterator<Type> iterator = null;
        Type temp = null;
        List<Type> achivements = newsService.viewModuleTypes("culture");
        iterator = achivements.iterator();
        List<Title> entity = new ArrayList<Title>();
        List<Title> e = null;
        while (iterator.hasNext()) {
            temp = iterator.next();
            e = newsService.viewtypeTitles(temp.getName());
            if (null != e)
                entity.addAll(e);
        }
        setAttr("list", entity);

        render("/blue/plugin/culture-9.html");

    }

    @ActionKey("/index/goCulture/0")
    public void culture0() {

        NewsService newsService = NewsService.getInstance();
        Iterator<Type> iterator = null;
        Type temp = null;
        List<Type> achivements = newsService.viewModuleTypes("culture");
        iterator = achivements.iterator();
        List<Title> entity = new ArrayList<Title>();
        List<Title> e = null;
        while (iterator.hasNext()) {
            temp = iterator.next();
            e = newsService.viewtypeTitles(temp.getName());
            if (null != e)
                entity.addAll(e);
        }
        setAttr("list", entity);

        render("/blue/plugin/culture-0.html");

    }

    @ActionKey("/index/goCulture/8")
    public void cultrue8() {

        NewsService newsService = NewsService.getInstance();
        Iterator<Type> iterator = null;
        Type temp = null;
        List<Type> achivements = newsService.viewModuleTypes("culture");
        iterator = achivements.iterator();
        List<Title> entity = new ArrayList<Title>();
        List<Title> e = null;
        while (iterator.hasNext()) {
            temp = iterator.next();
            e = newsService.viewtypeTitles(temp.getName());
            if (null != e)
                entity.addAll(e);
        }
        setAttr("list", entity);

        render("/blue/plugin/culture-8.html");

    }


    public void aboutUs() {
        ServletContext application = this.getRequest().getServletContext();

        Article cache = null;

        Map<String, Title> map = new HashMap<String, Title>();
        List<UserInfo> teachers = new ArrayList<UserInfo>();
        Map<String, Article> t2 = new HashMap<String, Article>();
        List<UserInfo> students = new ArrayList<UserInfo>();
        Title temp = null;
        List<Title> titles = NewsService.getInstance().viewtypeTitles("aboutUs");
        Iterator<Title> iterator = titles.iterator();
        while (iterator.hasNext()) {
            temp = iterator.next();

            map.put(temp.getId(), temp);
        }
        application.setAttribute("map", map);

        Type aType = null;
        List<Type> aTypeList = GroupService.getInstance().getAllMember("teacher");
        Iterator<Type> typeIterator = aTypeList.iterator();
        while (typeIterator.hasNext()) {
            aType = typeIterator.next();
            t2.put(aType.getName(), NewsService.getInstance().getUserInfoNews(aType.getName()));
            teachers.add(UserInfoService.getInstance().findById(aType.getName()));
        }
        application.setAttribute("teachersArticle", t2);
        application.setAttribute("teachers", teachers);

        aTypeList = GroupService.getInstance().getAllMember("student");
        typeIterator = aTypeList.iterator();
        while (typeIterator.hasNext()) {
            aType = typeIterator.next();
            students.add(UserInfoService.getInstance().findById(aType.getName()));
        }
        application.setAttribute("students", students);
        render("/blue/aboutUs.html");
    }

    private void initStart() {
        ServletContext application = this.getRequest().getServletContext();
        NewsService newsService = NewsService.getInstance();
        List<Type> system = newsService.viewModuleTypes("system");
        //System.out.println(new Date()+""+system);
        Iterator<Type> iterator = system.iterator();
        Type temp = null;
        while (iterator.hasNext()) {
            temp = iterator.next();
            application.setAttribute(temp.getName(), newsService.viewtypeTitles(temp.getName()));
            //	System.out.println(temp.getName()+"###"+newsService.viewtypeTitles(temp.getName()));
        }
        system = newsService.viewModuleTypes("article");
        iterator = system.iterator();
        List<Type> theType = new ArrayList<Type>();
        while (iterator.hasNext()) {
            temp = iterator.next();
            Type aType = new Type();
            aType.setId(temp.getName());
            aType.setName(temp.getValue());
            aType.setValue(newsService.viewtypeTitles(temp.getName()).size() + "");
            theType.add(aType);
        }
        application.setAttribute("articleTypeList", theType);
        system = newsService.viewModuleTypes("notice");
        iterator = system.iterator();
        theType = new ArrayList<Type>();
        while (iterator.hasNext()) {
            temp = iterator.next();
            Type aType = new Type();
            aType.setId(temp.getName());
            aType.setName(temp.getValue());
            try {
                aType.setValue(newsService.viewtypeTitles(temp.getName()).size() + "");
            } catch (Exception e) {
                System.out.println("##" + new Date() + e.toString());
                aType.setValue("0");
            }
            theType.add(aType);
        }
        application.setAttribute("noticeTypeList", theType);

        system = newsService.viewModuleTypes("news");
        iterator = system.iterator();
        theType = new ArrayList<Type>();
        while (iterator.hasNext()) {
            temp = iterator.next();
            Type aType = new Type();
            aType.setId(temp.getName());
            aType.setName(temp.getValue());
            try {
                aType.setValue(newsService.viewtypeTitles(temp.getName()).size() + "");
            } catch (Exception e) {
                System.out.println("##" + new Date() + e.toString());
                aType.setValue("0");
            }
            theType.add(aType);
        }
        application.setAttribute("newsTypeList", theType);
    }

    public void refresh() {
        ServletContext application = this.getRequest().getServletContext();
        NewsService newsService = NewsService.getInstance();
        List<Type> system = newsService.viewModuleTypes("system");
        //System.out.println(new Date()+""+system);
        Iterator<Type> iterator = system.iterator();
        Type temp = null;
        while (iterator.hasNext()) {
            temp = iterator.next();
            application.setAttribute(temp.getName(), newsService.viewtypeTitles(temp.getName()));
            //	System.out.println(temp.getName()+"###"+newsService.viewtypeTitles(temp.getName()));
        }
        system = newsService.viewModuleTypes("article");
        iterator = system.iterator();
        List<Type> theType = new ArrayList<Type>();
        while (iterator.hasNext()) {
            temp = iterator.next();
            Type aType = new Type();
            aType.setId(temp.getName());
            aType.setName(temp.getValue());
            aType.setValue(newsService.viewtypeTitles(temp.getName()).size() + "");
            theType.add(aType);
        }
        application.setAttribute("articleTypeList", theType);
        system = newsService.viewModuleTypes("notice");
        iterator = system.iterator();
        theType = new ArrayList<Type>();
        while (iterator.hasNext()) {
            temp = iterator.next();
            Type aType = new Type();
            aType.setId(temp.getName());
            aType.setName(temp.getValue());
            try {
                aType.setValue(newsService.viewtypeTitles(temp.getName()).size() + "");
            } catch (Exception e) {
                System.out.println("##:" + new Date() + temp + aType.getId() + aType.getName() + aType.getValue());
                System.out.println("##" + new Date() + e.toString() + temp.getName() + aType.getId() + aType.getName() + aType.getValue());
                aType.setValue("0");
            }
            theType.add(aType);
        }
        application.setAttribute("noticeTypeList", theType);

        system = newsService.viewModuleTypes("news");
        iterator = system.iterator();
        theType = new ArrayList<Type>();
        while (iterator.hasNext()) {
            temp = iterator.next();
            Type aType = new Type();
            aType.setId(temp.getName());
            aType.setName(temp.getValue());
            try {
                aType.setValue(newsService.viewtypeTitles(temp.getName()).size() + "");
            } catch (Exception e) {
                System.out.println("##" + new Date() + e.toString());
                aType.setValue("0");
            }
            theType.add(aType);
        }
        application.setAttribute("newsTypeList", theType);
        //	PlatformService.dataBackUp();
        render("/index.html");
    }
}
