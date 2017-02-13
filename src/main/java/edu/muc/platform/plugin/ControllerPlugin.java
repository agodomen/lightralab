/**
 *
 */
package edu.muc.platform.plugin;

import java.util.List;

import edu.muc.controller.BaseController;
import edu.muc.controller.plugin.Controller;
import edu.muc.platform.plugin.tools.ToolClassSearcher;

import com.jfinal.config.Routes;
import com.jfinal.log.Logger;
import com.jfinal.plugin.IPlugin;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年4月5日 下午4:19:27
 */
public class ControllerPlugin implements IPlugin {

    protected final Logger log = Logger.getLogger(getClass());

    private Routes me;

    public ControllerPlugin(Routes me) {
        this.me = me;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean start() {
        // 查询所有继承FrontController的类
        log.debug("go int the start");
        List<String> jars = (List<String>) PropertiesPlugin.getParamMapValue(DictKeys.config_scan_jar);
        List<Class<? extends BaseController>> controllerClasses = null;
        if (jars.size() > 0) {
            controllerClasses = ToolClassSearcher.of(BaseController.class).includeAllJarsInLib(ToolClassSearcher.isValiJar()).injars(jars).search();// 可以指定查找jar包，jar名称固定，避免扫描所有文件
        } else {
            controllerClasses = ToolClassSearcher.of(BaseController.class).search();
        }

        // 循环处理自动注册映射
        for (Class controller : controllerClasses) {
            // 获取注解对象
            Controller controllerBind = (Controller) controller.getAnnotation(Controller.class);
            if (controllerBind == null) {
                log.error(controller.getName() + "继承了FrontController，但是没有注解绑定映射路径");
                continue;
            }

            // 获取映射路径数组
            String[] controllerKeys = controllerBind.controllerKey();
            for (String controllerKey : controllerKeys) {
                controllerKey = controllerKey.trim();
                if (controllerKey.equals("")) {
                    log.error(controller.getName() + "注解错误，映射路径为空");
                    continue;
                }
                // 注册映射
                me.add(controllerKey, controller);
                log.debug("this is the mapping list," + "cotrollerkey:" + controllerKey + "controller:" + controller.getName());
            }
        }
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }

}
