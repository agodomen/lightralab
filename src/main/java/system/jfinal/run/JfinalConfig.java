package system.jfinal.run;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.beetl.core.GroupTemplate;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.activerecord.tx.TxByActionKeys;
import com.jfinal.plugin.activerecord.tx.TxByActionMethods;
import com.jfinal.plugin.activerecord.tx.TxByRegex;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;

import edu.muc.controller.GoController;
import edu.muc.platform.beetl.render.MyBeetlRenderFactory;
import edu.muc.platform.handler.GlobalHandler;
import edu.muc.platform.plugin.ControllerPlugin;
import edu.muc.platform.plugin.DictKeys;
import edu.muc.platform.plugin.PropertiesPlugin;
import edu.muc.platform.plugin.tools.ToolString;
import edu.muc.platform.plugin.tools.ToolWeb;

/**
 * Jfinal API 引导式配置
 */
public class JfinalConfig extends JFinalConfig {


    private static Logger log = Logger.getLogger(JfinalConfig.class);

    /**
     * 配置常量
     */
    public void configConstant(Constants me) {
        log.info("configConstant 缓存 properties");
        new PropertiesPlugin(loadPropertyFile("init.properties")).start();

        log.info("configConstant 设置字符集");
        me.setEncoding(ToolString.encoding);
        log.info("configConstant 设置是否开发模式");
        me.setDevMode(getPropertyToBoolean(DictKeys.config_devMode, false));
        // me.setViewType(ViewType.JSP);//设置视图类型为Jsp，否则默认为FreeMarker
        log.info("configConstant 视图Beetl设置");
        me.setMainRenderFactory(new MyBeetlRenderFactory());
        GroupTemplate groupTemplate = MyBeetlRenderFactory.groupTemplate;
        groupTemplate.setSharedVars(new HashMap<String, Object>());
        //	groupTemplate.getSharedVars().put("application", JFinal.me().getServletContext());
        groupTemplate.getSharedVars().put("ctx", JFinal.me().getContextPath());
        groupTemplate.getSharedVars().put("contextPath", JFinal.me().getContextPath());
        log.info("configConstant 视图error page设置");
        me.setError401View("/common/401.html");
        me.setError403View("/common/403.html");
        me.setError404View("/common/404.html");
        me.setError500View("/common/500.html");
        me.setErrorView(1024, "/index.html");
/*		var httpApplication = serlvet.request.servletContext;
        @httpApplication.getAttribute('')*/
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        log.info("configRoute 路由扫描注册");

        new ControllerPlugin(me).start();
    }

    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
    }

    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {

    }

    /**
     * 配置处理器
     */
    public void configHandler(Handlers me) {
        log.info("configHandler 全局配置处理器，主要是记录日志和request域值处理");
        me.add(new GlobalHandler());
    }

    /**
     * 系统启动完成后执行
     */
    public void afterJFinalStart() {
    }

    /**
     * 系统关闭前调用
     */
    public void beforeJFinalStop() {
    }

    /**
     * 运行此 main 方法可以启动项目 说明： 1. linux 下非root账户运行端口要>1024 2. idea
     * 中运行记得加上当前的module名称
     */
    public static void main(String[] args) {
        JFinal.start("WebRoot", 80, "/Muc908", 5); //
        // JFinal.start("JfinalUIB/WebContent", 89, "/", 5); // idea
        // 中运行记得加上当前的module名称
    }
}
