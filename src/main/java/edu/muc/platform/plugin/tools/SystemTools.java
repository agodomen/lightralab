package edu.muc.platform.plugin.tools;

import java.io.File;

import org.apache.log4j.Logger;
import com.jfinal.kit.PathKit;


public class SystemTools {

    private static final long serialVersionUID = 6761767368352810428L;

    private static final String webRootPath = PathKit.getWebRootPath();
    private static final String rootClassPath = PathKit.getWebRootPath();
    private static final String xmlDataPath = webRootPath + "\\WEB-INF\\data\\xml\\";
    private static final String xmlDataBackupPath = webRootPath + "\\WEB-INF\\data\\Backup";

    private static Logger log = Logger.getLogger(SystemTools.class);

    /**
     * @return the webRootPath
     */
    public static String getWebRootPath() {
        return webRootPath;
    }


    /**
     * @return the rootClassPath
     */
    public static String getRootClassPath() {
        return rootClassPath;
    }


    /**
     * @return the xmldatapath
     */
    public static String getXmlDataPath() {
        return xmlDataPath;
    }


    public static String getXmlDataBackupPath() {
        return xmlDataBackupPath;
    }

}
