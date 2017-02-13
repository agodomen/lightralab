package edu.muc.platform.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jfinal.log.Logger;
import com.jfinal.plugin.IPlugin;

/**
 * 读取Properties配置文件数据放入缓存
 *
 * @author 董华健
 */
public class PropertiesPlugin implements IPlugin {

    protected final Logger log = Logger.getLogger(getClass());

    /**
     * 保存系统配置参数值
     */
    private static Map<String, Object> paramMap = new HashMap<String, Object>();

    private Properties properties;

    public PropertiesPlugin(Properties properties) {
        this.properties = properties;
    }

    /**
     * 获取系统配置参数值
     *
     * @param key
     * @return
     */
    public static Object getParamMapValue(String key) {
        return paramMap.get(key);
    }

    @Override
    public boolean start() {
        // 数据库连接池信息
        paramMap.put(DictKeys.db_initialSize, Integer.valueOf(properties.getProperty(DictKeys.db_initialSize).trim()));
        paramMap.put(DictKeys.db_minIdle, Integer.valueOf(properties.getProperty(DictKeys.db_minIdle).trim()));
        paramMap.put(DictKeys.db_maxActive, Integer.valueOf(properties.getProperty(DictKeys.db_maxActive).trim()));

        // 把常用配置信息写入map
        String scan_package = properties.getProperty(DictKeys.config_scan_package).trim();
        if (null != scan_package && !scan_package.isEmpty()) {
            List<String> list = new ArrayList<String>();
            String[] pkgs = scan_package.split(",");
            for (String pkg : pkgs) {
                list.add(pkg.trim());
            }
            paramMap.put(DictKeys.config_scan_package, list);
        } else {
            paramMap.put(DictKeys.config_scan_package, new ArrayList<String>());
        }

        String scan_jar = properties.getProperty(DictKeys.config_scan_jar).trim();
        if (null != scan_jar && !scan_jar.isEmpty()) {
            List<String> list = new ArrayList<String>();
            String[] jars = scan_jar.split(",");
            for (String jar : jars) {
                list.add(jar.trim());
            }
            paramMap.put(DictKeys.config_scan_jar, list);
        } else {
            paramMap.put(DictKeys.config_scan_jar, new ArrayList<String>());
        }

        paramMap.put(DictKeys.config_devMode, properties.getProperty(DictKeys.config_devMode).trim());

        paramMap.put(DictKeys.config_securityKey_key, properties.getProperty(DictKeys.config_securityKey_key).trim());

        paramMap.put(DictKeys.config_passErrorCount_key, Integer.valueOf(properties.getProperty(DictKeys.config_passErrorCount_key)));

        paramMap.put(DictKeys.config_passErrorHour_key, Integer.valueOf(properties.getProperty(DictKeys.config_passErrorHour_key)));

        return true;
    }

    @Override
    public boolean stop() {
        paramMap.clear();
        return true;
    }

}
