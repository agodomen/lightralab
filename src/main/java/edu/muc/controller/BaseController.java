package edu.muc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;

import edu.muc.controller.interceptor.AuthInterceptor;
import edu.muc.platform.plugin.DictKeys;
import edu.muc.platform.plugin.PropertiesPlugin;
import edu.muc.platform.plugin.SplitPage;
import edu.muc.platform.plugin.tools.ToolString;

/**
 * 公共Controller
 *
 * @author 董华健
 */
public abstract class BaseController extends Controller {

    protected static Logger logger = Logger.getLogger(BaseController.class);

    /**
     * 全局变量
     */
    protected String ids;            // 主键
    protected SplitPage splitPage;    // 分页封装
    protected List<?> list;            // 公共list
/*
    public void renderAuthCheck(String view) {    	
    	render(AuthInterceptor.renderAuthCheck(this.getSession(), view));
	} 
    
    public void renderJsonAuthCheck(String key, Object value) {
    	HttpSession session=this.getSession();
    	String state = (String) session.getAttribute("theState");
	    if (null!=state&&state.equals("true"))
	      renderJson(key,value);
	    else
	       render("/platform/login.html");
	} 
    public void renderJsonAuthCheck(Object value) {
    	HttpSession session=this.getSession();
    	String state = (String) session.getAttribute("theState");
	    if (null!=state&&state.equals("true"))
	      renderJson(value);
	    else
	       render("/platform/login.html");
	} */

    /**
     * 请求/WEB-INF/下的视图文件
     */
    public void toUrl() {
        String toUrl = getPara("toUrl");
        render(toUrl);
    }

    /**
     * 获取当前请求国际化参数
     *
     * @return
     */
    protected String getI18nPram() {
        return getAttr("localePram");
    }

    /**
     * 获取项目请求根路径
     *
     * @return
     */
    protected String getCxt() {
        return getAttr("cxt");
    }

    /**
     * 获取当前用户id
     *
     * @return
     */
    protected String getCUserIds() {
        return getAttr("cUserIds");
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    protected String getCUser() {
        return getAttr("cUser");
    }

    /**
     * 获取ParamMap
     *
     * @return
     */
    protected Map<String, String> getParamMap() {
        return getAttr("paramMap");
    }

    /**
     * 添加值到ParamMap
     *
     * @return
     */
    protected void addToParamMap(String key, String value) {
        Map<String, String> map = getAttr("paramMap");
        map.put(key, value);
    }
/*	
    *//**
     * 重写getPara，进行二次decode解码
     *//*
	@Override
	public String getPara(String name) {
		String value = getRequest().getParameter(name);
		if(null != value && !value.isEmpty()){
			try {
				value = URLDecoder.decode(value, ToolString.encoding);
			} catch (UnsupportedEncodingException e) {
				logger.error("decode异常：" + value + e.getMessage());
				e.printStackTrace();
			}
		}
		return value;
	}
	
	*//**
     * 重写getPara，进行二次decode解码
     *//*

	public String getPara(String name,String defaultValue) {
		String value = getRequest().getParameter(name);
		if(null != value && !value.isEmpty()){
			try {
				value = URLDecoder.decode(value, ToolString.encoding);
			} catch (UnsupportedEncodingException e) {
				logger.error("decode异常：" + value + e.getMessage());
				e.printStackTrace();
			}
		}
		else{
			value=defaultValue;
		}
		return value;
	}
	*/

    /**
     * 重写getPara，进行二次decode解码
     */

    public String getPara(String name, Date date) {
        String value = getRequest().getParameter(name);
        if (null != value && !value.isEmpty()) {
            try {
                value = URLDecoder.decode(value, ToolString.encoding);
            } catch (UnsupportedEncodingException e) {
                logger.error("decode异常：" + value + e.getMessage());
                e.printStackTrace();
            }
        } else {
            value = DictKeys.simpleDateFormat.format(date);
        }
        return value;
    }


    public String generateKey(String sufx, Date date) {
        String value = DictKeys.simpleDateFormat.format(date);
        return sufx + value;
    }

    public String getParaForMonthDate(String name, Date date) {
        String value = getRequest().getParameter(name);
        //	System.out.println("####:"+value+"***"+DictKeys.monthDateFormat.format(date));
        if (null != value && !value.isEmpty()) {
            return value;
        } else {
            return DictKeys.monthDateFormat.format(date);
        }
    }


    public String getId(String name) {
        String value = getRequest().getParameter(name);
        if (null != value && !value.isEmpty()) {
            try {
                value = URLDecoder.decode(value, ToolString.encoding);
            } catch (UnsupportedEncodingException e) {
                logger.error("decode异常：" + value + e.getMessage());
                e.printStackTrace();
            }
        } else {
            value = DictKeys.simpleDateFormat.format(DictKeys.calendar.getTime());
        }
        return value;
    }

    /**
     * 获取checkbox值，数组
     *
     * @param name
     * @return
     */
    protected String[] getParas(String name) {
        return getRequest().getParameterValues(name);
    }

    /**
     * 效验Referer有效性
     *
     * @return
     * @author 董华健 2012-10-30 上午10:26:04
     */
    protected boolean authReferer() {
        String referer = getRequest().getHeader("Referer");
        if (null != referer && !referer.trim().equals("")) {
            referer = referer.toLowerCase();
            String domainStr = (String) PropertiesPlugin.getParamMapValue(DictKeys.config_domain_key);
            String[] domainArr = domainStr.split(",");
            for (String domain : domainArr) {
                if (referer.indexOf(domain.trim()) != -1) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 获取查询参数
     * 说明：和分页分拣一样，但是应用场景不一样，主要是给查询导出的之类的功能使用
     *
     * @return
     */
    protected Map<String, String> getQueryParam() {
        Map<String, String> queryParam = new HashMap<String, String>();
        Enumeration<String> paramNames = getParaNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String value = getPara(name);
            if (name.startsWith("_query") && !value.isEmpty()) {// 查询参数分拣
                String key = name.substring(7);
                if (null != value && !value.trim().equals("")) {
                    queryParam.put(key, value.trim());
                }
            }
        }

        return queryParam;
    }

    /**
     * 设置默认排序
     *
     * @param colunm
     * @param mode
     */
    protected void defaultOrder(String colunm, String mode) {
        if (null == splitPage.getOrderColunm() || splitPage.getOrderColunm().isEmpty()) {
            splitPage.setOrderColunm(colunm);
            splitPage.setOrderMode(mode);
        }
    }

    /**
     * 排序条件
     * 说明：和分页分拣一样，但是应用场景不一样，主要是给查询导出的之类的功能使用
     *
     * @return
     */
    protected String getOrderColunm() {
        String orderColunm = getPara("orderColunm");
        return orderColunm;
    }

    /**
     * 排序方式
     * 说明：和分页分拣一样，但是应用场景不一样，主要是给查询导出的之类的功能使用
     *
     * @return
     */
    protected String getOrderMode() {
        String orderMode = getPara("orderMode");
        return orderMode;
    }


    public void setSplitPage(SplitPage splitPage) {
        this.splitPage = splitPage;
    }

}
