/**
 *
 */
package edu.muc.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;

import edu.muc.controller.interceptor.AuthInterceptor;
import edu.muc.controller.interceptor.AuthenticationInterceptor;
import edu.muc.controller.plugin.Controller;
import edu.muc.model.Title;
import edu.muc.model.Type;
import edu.muc.model.UserInfo;
import edu.muc.service.NewsService;
import edu.muc.service.UserInfoService;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年6月2日 下午1:04:32
 */
@SuppressWarnings("unused")
@Controller(controllerKey = {"/system"})
@Before(AuthenticationInterceptor.class)
public class SystemController extends BaseController {


    @ActionKey("/system/module/title")
    public void viewTypeTitle() {
        String target = this.getPara("target");
        String sEcho = this.getPara("sEcho");
        int iDisplayLength = Integer.parseInt(getPara("iDisplayLength"));
        int iDisplayStart = Integer.parseInt(getPara("iDisplayStart"));
        List<Type> types = null;
        List<Type> temp = new ArrayList<Type>();
        if (target != null) {
            types = NewsService.getInstance().viewTypes("\\Module", target);
            int i = 0;
            while (i < iDisplayLength && types != null && iDisplayStart < types.size()) {
                temp.add(types.get(iDisplayStart));
                iDisplayStart++;
                i++;
            }
        }
        if (types == null) {
            types = new ArrayList<Type>();
            Type type = new Type("", "404! cannot find!", "无", "无备注");
            types.add(type);
            temp = types;
        }

        Map map = new HashMap();
        map.put("list", temp);
        map.put("iTotalRecords", types.size());
        map.put("iTotalDisplayRecords", types.size());
        map.put("sEcho", sEcho);
        System.out.println("this is the map" + map + "this is the list" + types);
        this.renderJson(map);
    }

    @ActionKey("/system/module/add")
    public void addType() {
        Type type = new Type();
        this.setType(type);
        NewsService.getInstance().saveModule(type);
        setAttr("urlState", "module-add");
        render("/platform/index.html");
    }

    @ActionKey("/system/module/list")
    public void listType() {
        //type:id name value
        List<Type> types = NewsService.getInstance().getAllModule();
        System.out.println(types);
        renderJson("list", types);
    }

    @ActionKey("/system/module/delete")
    public void deleteType() {
        String id = getPara(0);
        logger.debug("this is the delete Id:" + id);
        NewsService.getInstance().deleteModule(id);
        setAttr("urlState", "module-list");
        render("/platform/index.html#indexContent");
    }

    @ActionKey("/system/module/title/delete")
    public void deleteTypeTitle() {
        String target = getPara(0);
        String id = getPara(1);
        String is = getPara(2);
        if (is == null)
            is = "no";
        logger.debug("this is the delete Id:" + id);
        NewsService.getInstance().deleteTypeByDir(is, "\\Module", target, id);
        setAttr("urlState", "module-config");
        render("/platform/index.html#indexContent");
    }

    @ActionKey("/system/module/preModify")
    public void preModifyType() {
        String id = getPara(0);
        logger.debug("this if pre for the modify id:" + id);
        Type type = NewsService.getInstance().findModuleById(id);
        setAttr("entity", type);

        render("/platform/module/modify.html");
    }

    @ActionKey("/system/module/rearModify")
    public void rearModifyType() {
        String id = getPara(0);
        logger.debug("this if pre for the modify id:" + id);
        Type type = NewsService.getInstance().findModuleById(id);
        setAttr("entity", type);

        render("/platform/module/edit.html");

    }

    @ActionKey("/system/module/modify")
    public void modifyType() {
        String id = getPara("id");
        if (null != id) {
            Type type = new Type();
            this.setType(type);
            NewsService.getInstance().updateModule(type);
            setAttr("urlState", "module-list");
            render("/platform/index.html");
        } else {
            render("/common/401.html");
        }

    }

    @ActionKey("/system/module/edit")
    public void editorType() {
        String id = getPara("id");
        String name = getPara("name");
        if (null != name) {
            NewsService.getInstance().updateModuleByName(id, name);
            setAttr("urlState", "news-type-index");
            render("/platform/index.html");
        } else {
            render("/common/401.html");
        }
    }

    @ActionKey("/system/module/ajaxCheck")
    public void ajaxCheck() {
        String valueName = getPara("fieldId");
        String value = getPara("fieldValue");
        renderJson("[\"" + valueName + "\","
                + NewsService.getInstance().checkValue(valueName, value, "\\Module.xml") + "]");
    }

    private void setType(Type type) {
        type.setId(getPara("id", Calendar.getInstance().getTime()));
        type.setName(getPara("name"));
        type.setValue(getPara("value"));
        type.setDescription(getPara("description", ""));
    }

    @ActionKey("/module/config")
    public void newsConfig() {
        String id = getPara("id");
        String target = getPara("type");
        //	String target=getPara("action");

        Type type = NewsService.getInstance().findTypeById(id);
        if (null == type)
            type = NewsService.getInstance().findModuleById(id);
        NewsService.getInstance().saveModuleByType(target, type);
        setAttr("urlState", "module-config");
        render("/platform/index.html");
    }
}
