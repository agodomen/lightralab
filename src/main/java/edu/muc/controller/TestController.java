/**
 *
 */
package edu.muc.controller;

import edu.muc.controller.plugin.Controller;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年4月6日 上午9:43:48
 */
@SuppressWarnings("unused")
@Controller(controllerKey = {"/test"})
public class TestController extends BaseController {

    /**
     * for the test of the beetl html's view
     */
    public void index() {
        String action = getAttr("action");
        logger.debug("test action:" + action);
        if (null != action) {
            render(action + ".html");
        } else {
            render("/test/index.html");
        }
    }

    public void ajax() {
        setAttr("urlState", 1);
        render("/test/index.html#indexContent");
    }

    public void avatar() {
        render("/tools/avatar/avatar.html");
    }
}
