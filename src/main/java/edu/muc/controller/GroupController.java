/**
 *
 */
package edu.muc.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.ActionKey;

import edu.muc.controller.interceptor.AuthInterceptor;
import edu.muc.controller.plugin.Controller;
import edu.muc.model.Article;
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
@Controller(controllerKey = {"/group"})
@Before(AuthenticationInterceptor.class)
public class GroupController extends BaseController {

    private List<UserInfo> userInfos;


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
        Type type = new Type();
        this.setType(type);
        GroupService.getInstance().addGroup(type);
        setAttr("urlState", "news-type-add");
        render("/platform/index.html");
    }


    public void addMember() {
        Type type = new Type();
        type.setId(getPara("id", Calendar.getInstance().getTime()));
        type.setName(this.getPara("userId"));
        String groupName = this.getPara("groupName");
        type.setValue(this.getPara("power"));
        type.setDescription(this.getPara("description"));
        Article article = new Article();
        article.setId(type.getName());
        article.setContent("暂无");
        article.setDescription("暂无");
        NewsService.getInstance().saveUserInfoNews(article);
        GroupService.getInstance().addMember(groupName, type);
        setAttr("urlState", "user-add");
        render("/platform/index.html");
    }

    private void setType(Type type) {
        type.setId(getPara("id", Calendar.getInstance().getTime()));
        type.setName(getPara("name"));
        type.setValue(getPara("value"));
        type.setDescription(getPara("description", ""));
    }

    public void list() {
        List<Type> groups = GroupService.getInstance().getAllGroup();
        renderJson("groups", groups);
    }


    public void delete() {
        String id = this.getPara(0);
        if (null != id)
            GroupService.getInstance().deleteGroup(id);
        setAttr("urlState", "news-type-list");
        render("/platform/index.html#indexContent");
    }

    public void ajaxGroupList() {
        List<Type> groups = GroupService.getInstance().getAllGroup();
        renderJson("list", groups);
    }

    @ActionKey("/member/list")
    public void memberList() {
        String target = this.getPara("target");
        List<Type> memberList = null;
        if (target != null) {
            memberList = GroupService.getInstance().getAllMember(target);
        }
        renderJson("memberList", memberList);

    }


    @ActionKey("/member/delete")
    public void memberDelete() {
        String target = this.getPara(0);
        String id = this.getPara(1);
        if (null != target && null != id) {
            GroupService.getInstance().deleteMember(target, id);
        }
        setAttr("urlState", "user-list");
        render("/platform/index.html#indexContent");
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


}
