/**
 *
 */
package edu.muc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;

import edu.muc.controller.interceptor.AuthenticationInterceptor;
import edu.muc.controller.plugin.Controller;
import edu.muc.model.Article;
import edu.muc.model.Title;
import edu.muc.model.Type;
import edu.muc.platform.plugin.tools.ToolString;
import edu.muc.service.NewsService;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年6月1日 下午4:06:19
 */
@SuppressWarnings("unused")
@Controller(controllerKey = {"/go", "/data"})
public class GoController extends BaseController {
    private static final int iDisplayLength = 7;

    /**
     *
     */
    private static String urlState = "urlState";

    public GoController() {
        // TODO Auto-generated constructor stub
    }

    public void index() {
        setAttr("urlState", 0);
        render("/blue/index.html");
    }

    public void error() {
        render("/common/404.html");
    }

}
