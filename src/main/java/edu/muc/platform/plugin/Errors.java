/**
 *
 */
package edu.muc.platform.plugin;

import org.json.simple.JSONObject;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年5月10日 下午11:44:54
 */
public class Errors {
    public String getError(String message) {
        JSONObject obj = new JSONObject();
        obj.put("error", 1);
        obj.put("message", message);
        return obj.toJSONString();
    }
}
