package edu.muc.controller.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.muc.controller.BaseController;
import edu.muc.model.UserInfo;
import edu.muc.platform.handler.GlobalHandler;
import edu.muc.platform.plugin.tools.ToolContext;
import edu.muc.platform.plugin.tools.ToolDateTime;
import edu.muc.platform.plugin.tools.ToolWeb;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

/**
 * 权限认证拦截器
 *
 * @author 董华健
 */
public class AuthenticationInterceptor implements Interceptor {

    private static Logger log = Logger.getLogger(AuthenticationInterceptor.class);

    @Override
    public void intercept(ActionInvocation ai) {
        BaseController contro = (BaseController) ai.getController();
        HttpServletRequest request = contro.getRequest();
        HttpServletResponse response = contro.getResponse();
        if (this.authCheck(request.getSession())) {
            ai.invoke();
        } else
            contro.redirect("/user/login");

    }

    /**
     * 提示信息展示页
     *
     * @param contro
     * @param type
     */
    private void toInfoJsp(BaseController contro, int type) {
        if (type == 1) {// 未登录处理
            contro.redirect("/platform/login.html");
            return;
        }
/*		String referer = contro.getRequest().getHeader("X-Requested-With"); 
        String toPage = "/common/msgAjax.html";
		if(null == referer || referer.isEmpty()){
			toPage = "/common/msg.html";
		}
		
		String msg = null;
		if(type == 2){// 权限验证失败处理
			msg = "权限验证失败!";
			
		}else if(type == 3){// IP验证失败
			msg = "IP验证失败!";
			
		}else if(type == 4){// 表单验证失败
			msg = "请不要重复提交表单数据!";
			
		}else if(type == 5){// 业务代码异常
			msg = "业务代码异常!";
		}
		
		contro.setAttr("referer", referer);
		contro.setAttr("msg", msg);
		contro.render(toPage);*/
    }
	

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
        session.setAttribute("theUserInfo", temp);
        session.setAttribute("theState", "true");
        return true;
    }

    public static boolean authLogoff(HttpSession session) {
        session.setAttribute("theUserInfo", null);
        session.setAttribute("theState", "false");
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
