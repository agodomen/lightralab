/**
 *
 */
package edu.muc.platform.plugin;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import edu.muc.platform.plugin.tools.SystemTools;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月6日 上午10:19:56
 */
public class Connection {
    protected final static String PATH = SystemTools.getXmlDataPath();
    private final Hashtable hashtableData = new Hashtable();
    private static Connection instance; // 唯一实例
    private static int clients;
    private final Hashtable pools = new Hashtable();
    private static int minConnection = 7;
    private static int maxConnection = 28;
    private static int acquireIncrement = 3;
    private static int maxIdleTimeExcessConnections = 1800;
    private static int maxIdleTime = 7200;
    private static int checkoutTimeout = 5000;
    private static Vector connections = null;

    /**
     *
     */
    private Connection() {
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * 检索此 Connection 对象是否已经被关闭。
     *
     * @return
     */
    public boolean isClosed() {
        // TODO Auto-generated method stub
        //
        return false;
    }

    /**
     * 立即更新此 Connection 对象的资源，而不是等待它们被自动释放
     */
    public void close() {
        // TODO Auto-generated method stub

    }

    private void init() {
        InputStream inStream = getClass().getResourceAsStream(
                "jxdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(inStream);
        } catch (Exception e) {
            System.err
                    .println("can not read the file, please be sure of the path. At Class BaseService init() method.");
        }
        createPools(properties);
    }

    private void createPools(Properties properties) {
        Enumeration propNames = properties.propertyNames();

    }

    public static synchronized Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        clients++;
        return instance;
    }
}
