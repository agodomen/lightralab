/**
 *
 */
package edu.muc.service;

import org.apache.log4j.Logger;

import edu.muc.platform.plugin.tools.SystemTools;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月31日 下午4:52:48
 */
public class BaseService {
    protected final static String PATH = SystemTools.getXmlDataPath();
    /*
     * this is test of logger service
     */
    public Logger logger = Logger.getLogger(BaseService.class);

    protected void init() {

    }
}
