package edu.muc.platform.handler;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jfinal.handler.Handler;

import edu.muc.platform.beetl.render.MyBeetlRender;
import edu.muc.platform.plugin.DictKeys;
import edu.muc.platform.plugin.I18NPlugin;
import edu.muc.platform.plugin.PropertiesPlugin;
import edu.muc.platform.plugin.tools.ToolDateTime;
import edu.muc.platform.plugin.tools.ToolWeb;

/**
 * 全局Handler，设置一些通用功能
 *
 * @author 董华健
 *         描述：主要是一些全局变量的设置，再就是日志记录开始和结束操作
 */
public class GlobalHandler extends Handler {

    private static Logger log = Logger.getLogger(GlobalHandler.class);


    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        /*log.info("设置 web 路径");
		String contextPath = ToolWeb.getContextPath(request);
		String cxt=ToolWeb.getTomcatPath(request);
		request.setAttribute("cxt", cxt);
		request.setAttribute("contextPath", contextPath);
	
		log.debug("request cookie 处理");
		Map<String, Cookie> cookieMap = ToolWeb.readCookieMap(request);
		request.setAttribute("cookieMap", cookieMap);
		log.debug("request param 请求参数处理");
		request.setAttribute("paramMap", ToolWeb.getParamMap(request));
		log.info("设置Header");
		request.setAttribute("decorator", "none");
		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		response.setDateHeader ("Expires", 0); //prevents caching at the proxy server*/
        request.setAttribute("application", request.getServletContext());
/*		System.out.println("target:"+target);
		if(target.startsWith("/data/xml/"))
			target="/go/error";			
		System.out.println("modify:"+target);*/
        nextHandler.handle(target, request, response, isHandled);

    }
}
