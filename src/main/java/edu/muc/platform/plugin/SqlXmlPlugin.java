package edu.muc.platform.plugin;

import com.jfinal.plugin.IPlugin;

import edu.muc.platform.plugin.tools.ToolSqlXml;

/**
 * 加载sql文件
 *
 * @author 董华健
 */
public class SqlXmlPlugin implements IPlugin {

    public SqlXmlPlugin() {
    }

    @Override
    public boolean start() {
        ToolSqlXml.init(true);
        return true;
    }

    @Override
    public boolean stop() {
        ToolSqlXml.destory();
        return true;
    }

}
