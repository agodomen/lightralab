/**
 *
 */
package edu.muc.controller.interceptor;

import javax.servlet.http.HttpSession;


import com.jfinal.aop.Interceptor;
import com.jfinal.config.Interceptors;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

import edu.muc.model.UserInfo;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年6月3日 下午3:59:52
 */

public class AuthInterceptor {



	/* (non-Javadoc)
     * @see com.jfinal.aop.Interceptor#intercept(com.jfinal.core.ActionInvocation)
	 */

    public static boolean authCheck(HttpSession session) {

        String state = (String) session.getAttribute("theState");
        if (null != state && state.equals("true"))
            return true;
        else
            return false;
    }

    public static boolean authSave(HttpSession session, UserInfo userInfo) {
        session.setAttribute("theUserInfoID", userInfo.getId());
        UserInfo temp = null;
        try {
            temp = (UserInfo) userInfo.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        temp.setPassword("");
        if (temp.getPower() >= 1024)
            session.setAttribute("powerNav", "super");
        else if (temp.getPower() > 0)
            session.setAttribute("powerNav", "admin");
        else
            session.setAttribute("powerNav", "nomal");
        session.setAttribute("theUserInfo", temp);
        session.setAttribute("theState", "true");
        return true;
    }

    public static boolean authLogoff(HttpSession session) {
        session.setAttribute("theUserInfo", null);
        session.setAttribute("theState", "false");
        session.setAttribute("powerNav", "nomal");
        return false;
    }

    public static String renderAuthCheck(HttpSession session, String url) {
        String state = (String) session.getAttribute("theState");
        if (null != state && state.equals("true"))
            return url;
        else
            return "/platform/login.html";
    }
}
