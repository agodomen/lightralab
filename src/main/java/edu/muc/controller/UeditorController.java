/**
 *
 */
package edu.muc.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import edu.muc.controller.plugin.Controller;
import edu.muc.platform.plugin.tools.ToolWeb;

import com.baidu.ueditor.ActionEnter;
import com.jfinal.upload.UploadFile;

/**
 * @author 龚文东
 *         <p>
 *         /ueditor/index?action=config&&noCache=1433854954275 2015年4月9日
 *         下午8:29:52
 */
@SuppressWarnings("unused")
@Controller(controllerKey = {"/editor/ueditor"})
public class UeditorController extends BaseController {
    public void index() {
        HttpServletRequest request = this.getRequest();
        HttpServletResponse response = this.getResponse();
        ServletContext application = request.getSession().getServletContext();
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        response.setHeader("Content-Type", "text/html");
        String string = (new ActionEnter(request, application.getRealPath("/")))
                .exec();

        System.out.println(string);
        this.renderHtml(string);
        // out.write( new ActionEnter( request, rootPath ).exec() );
        // this.renderJsp("/jsp/controller.jsp?action=config&&noCache=1433854783012");
    }

    private static final Logger logger = Logger
            .getLogger(UeditorController.class);

}
