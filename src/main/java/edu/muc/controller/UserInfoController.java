/**
 *
 */
package edu.muc.controller;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.ActionKey;

import edu.muc.controller.interceptor.AuthInterceptor;
import edu.muc.controller.plugin.Controller;
import edu.muc.model.Article;
import edu.muc.model.Title;
import edu.muc.model.Type;
import edu.muc.model.UserInfo;
import edu.muc.service.GroupService;
import edu.muc.service.NewsService;
import edu.muc.service.UserInfoService;
import edu.muc.controller.interceptor.AuthenticationInterceptor;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年6月1日 上午10:37:10
 */
@SuppressWarnings("unused")
@Controller(controllerKey = {"/user"})
@Before(AuthenticationInterceptor.class)
public class UserInfoController extends BaseController {

    private List<UserInfo> userInfos;

    public void logoff() {
        AuthInterceptor.authLogoff(this.getSession());
        render("/platform/login.html");
    }

    @ClearInterceptor(ClearLayer.ALL)
    public void login() {
        String name = getPara("name");
        String password = getPara("password");
        try {
            if (name != null && password != null) {
                int index = UserInfoService.dao.findIndexByName(name);
                if (index != -1) {
                    UserInfo temp = UserInfoService.dao.getAll().get(index);

                    if (temp.getPassword().equals(password)) {
                        AuthInterceptor.authSave(this.getSession(), temp);
                        render("/platform/index.html");
                        return;
                    }
                }
            }
            if (AuthInterceptor.authCheck(getSession())) {
                render("/platform/index.html");
                return;
            }
        } catch (Exception e) {
            System.out.println("Login Exception:" + e.toString());
            render("/platform/login.html");
        }
        render("/platform/login.html");

    }

    public void index() {
        String action = getPara("action");
        if (null != action) {
            setAttr("urlState", action);
            render("/platform/index.html#indexContent");
        } else {
            render("/platform/index.html");
        }
    }

    public void add() {
        UserInfo userInfo = new UserInfo();
        this.setUserInfo(userInfo);

        UserInfoService.dao.save(userInfo);
        setAttr("urlState", "user-add");
        render("/platform/index.html");
    }

    public void list() {
        logger.debug(this.getRequest().getParameter("length"));
        List<UserInfo> userInfos = UserInfoService.dao.getAll();
        renderJson("users", userInfos);
    }

    /*
     * this is the delelte method
     *
     */
    public void delete() {
        String id = getPara(0);
        logger.debug("this is the delete Id:" + id);
        UserInfoService.dao.delete(id);
        List<Type> group = GroupService.getInstance().getAllGroup();
        Iterator<Type> iterator = group.iterator();
        Type type = null;
        while (iterator.hasNext()) {
            type = iterator.next();
            GroupService.getInstance().deleteMemberByName(type.getName(), id);
        }
        setAttr("urlState", "user-list");
        render("/platform/index.html#indexContent");
    }
    /*
	 * this is for the modify servering
	 * 
	 */

    public void modify() {
        String id = getPara("id");
        if (null != id) {
            UserInfo userInfo = new UserInfo();
            this.setUserInfo(userInfo);
            UserInfoService.dao.update(userInfo);
            setAttr("urlState", "user-add");
            render("/platform/index.html");
        } else {
            render("/common/401.html");
        }
    }

    public void edit() {
        String id = getPara("id");
        String name = getPara("name");
        if (null != name) {
            UserInfoService.dao.update(id, name);
            setAttr("urlState", "user-index");
            render("/platform/index.html");
        } else {
            render("/common/401.html");
        }
    }

    public void rearModify() {
        String id = getPara(0);
        logger.debug("this if pre for the modify id:" + id);
        UserInfo userInfo = UserInfoService.dao.findById(id);
        setAttr("userInfo", userInfo);
//		render("/test/page.html#modalContent");
        render("/platform/user/edit.html");

    }

    /*
     * this is the pre for modify
     *
     */
    public void preModify() {
        String id = getPara(0);
        logger.debug("this if pre for the modify id:" + id);
        UserInfo userInfo = UserInfoService.dao.findById(id);
        setAttr("userInfo", userInfo);
//		render("/test/page.html#modalContent");
        render("/platform/user/modify.html");
    }


    private void setUserInfo(UserInfo userInfo) {
        userInfo.setId(getPara("id", Calendar.getInstance().getTime()));
        userInfo.setName(getPara("name"));
        userInfo.setNickname(getPara("nickname"));
        userInfo.setPassword(getPara("password"));
        userInfo.setSex(getParaToInt("sex"));
        userInfo.setPower(this.getParaToInt("power", 0));
        userInfo.setBirthday(getParaToDate("birthday", Calendar.getInstance().getTime()));
        userInfo.setEmail(getPara("email"));
        userInfo.setMobile(getPara("mobile"));
        userInfo.setTelephone(getPara("telephone"));
        userInfo.setQq(getPara("qq"));
        userInfo.setPicture(getPara("picture", "/images/default/logo/" + Integer.toString((new Random()).nextInt(20))
                + ".jpg"));
    }

    public void ajaxCheck() {
        String valueName = getPara("fieldId");
        String value = getPara("fieldValue");
        renderJson("[\"" + valueName + "\"," + UserInfoService.dao.checkValue(valueName, value) + "]");
        System.out.println("[\"" + valueName + "\"," + UserInfoService.dao.checkValue(valueName, value) + "]");

    }

    @ActionKey("/user/password/ajaxCheck")
    public void ajaxCheckUserInfoPassword() {
        String valueName = getPara("fieldId");
        String value = getPara("fieldValue");
        UserInfo temp = (UserInfo) this.getSession().getAttribute("theUserInfo");
        if (null != temp && temp.getPassword().equals(value))
            renderJson("[\"" + valueName + "\"," + "false" + "]");
        else
            renderJson("[\"" + valueName + "\"," + "true" + "]");
    }

    @ActionKey("/user/password/modify")
    public void userPasswordModify() {
        String s_pwd = getPara("pwd");
        String password = getPara("password");
        String id = (String) this.getSession().getAttribute("theUserInfoID");


    }

    @ActionKey("/userInfo/preModify")
    public void userInfoPreModify() {
        String id = getPara(0);
        UserInfo userInfo = UserInfoService.getInstance().findById(id);
        Article article = NewsService.getInstance().getUserInfoNews(id);
        setAttr("article", article);
        setAttr("userInfo", userInfo);
        render("/platform/user/userInfo-modify.html");
    }

    @ActionKey("userInfo/modify")
    public void userInfoModify() {
        Article article = new Article();
        article.setContent(this.getPara("content"));
        article.setId(this.getPara("id"));
        article.setDescription(this.getPara("description"));
        NewsService.getInstance().updateUserInfoNews(article);
        setAttr("urlState", "user-list");
        render("/platform/index.html");
    }
}
