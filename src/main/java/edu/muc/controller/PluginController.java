/**
 *
 */
package edu.muc.controller;

import com.jfinal.core.ActionKey;

import java.io.*;

import sun.misc.*;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.util.*;
import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.*;

import edu.muc.controller.plugin.Controller;
import edu.muc.platform.plugin.tools.SystemTools;
import edu.muc.service.UserInfoService;

/**
 * @author 龚文东
 *         <p>
 *         <p>
 *         2015年8月25日 下午3:03:49
 */
@SuppressWarnings("unused")
@Controller(controllerKey = {"/plugin"})
public class PluginController extends BaseController {

    /**
     *
     */
    public PluginController() {
        // TODO Auto-generated constructor stub
    }

    @ActionKey("/user/picture/upload")
    public void userInfoPictureUpload() throws IOException, FileUploadException {
        ServletContext application = this.getRequest().getServletContext();
        HttpServletRequest request = this.getRequest();
        String contentType = request.getContentType();
        String userId = (String) this.getSession()
                .getAttribute("theUserInfoID");
        if (userId == null)
            userId = "10086";
        UserInfoService aUserInfoService = UserInfoService.getInstance();

        String dir = SystemTools.getWebRootPath();
        String savePath = "/upload/UserInfo/" + userId + "/"; // 保存图片路径 可以修改
        String _savePath = dir + savePath;
        File filePath = new File(_savePath);
        if (filePath.isDirectory())
            FileUtils.deleteDirectory(filePath);
        else {
            filePath.mkdirs();
        }

        if (contentType.indexOf("multipart/form-data") >= 0) {
            Result result = new Result();
            result.avatarUrls = new ArrayList();
            result.success = false;
            result.msg = "Failure!";

            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            FileItemIterator fileItems = null;
            try {
                fileItems = upload.getItemIterator(request);
            } catch (FileUploadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 定义一个变量用以储存当前头像的序号
            int avatarNumber = 1;
            // 取服务器时间+8位随机码作为部分文件名，确保文件名无重复。
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyyMMddHHmmssS");
            String fileName = simpleDateFormat.format(new Date());
            Random random = new Random();
            String randomCode = "";
            for (int i = 0; i < 8; i++) {
                randomCode += Integer.toString(random.nextInt(36), 36);
            }
            fileName = fileName + "_" + randomCode;
            // 基于原图的初始化参数
            String initParams = "";
            BufferedInputStream inputStream;
            BufferedOutputStream outputStream;
            if (!filePath.isDirectory())
                filePath.mkdirs();
            // 遍历表单域
            while (fileItems.hasNext()) {
                FileItemStream fileItem = fileItems.next();
                String fieldName = fileItem.getFieldName();
                // 是否是原始图片 file 域的名称（默认的 file
                // 域的名称是__source，可在插件配置参数中自定义。参数名：src_field_name）
                Boolean isSourcePic = fieldName.equals("__source");
                // 文件名，如果是本地或网络图片为原始文件名（不含扩展名）、如果是摄像头拍照则为 *FromWebcam
                // String name = fileItem.getName();
                // 当前头像基于原图的初始化参数（即只有上传原图时才会发送该数据），用于修改头像时保证界面的视图跟保存头像时一致，提升用户体验度。
                // 修改头像时设置默认加载的原图url为当前原图url+该参数即可，可直接附加到原图url中储存，不影响图片呈现。
                if (fieldName.equals("__initParams")) {
                    inputStream = new BufferedInputStream(fileItem.openStream());
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    initParams = new String(bytes, "UTF-8");
                    inputStream.close();
                }
                // 如果是原始图片 file 域的名称或者以默认的头像域名称的部分“__avatar”打头
                else if (isSourcePic || fieldName.startsWith("__avatar")) {
                    String virtualPath = savePath + fileName + ".jpg_" + avatarNumber + ".jpg";
                    // 原始图片（默认的 file
                    // 域的名称是__source，可在插件配置参数中自定义。参数名：src_field_name）。
                    if (isSourcePic) {
                        result.sourceUrl = virtualPath = savePath
                                + fileName + ".jpg";
                        aUserInfoService.updatePicture(userId, virtualPath);
                    }
                    // 头像图片（默认的 file
                    // 域的名称：__avatar1,2,3...，可在插件配置参数中自定义，参数名：avatar_field_names）。
                    else {
                        result.avatarUrls.add(virtualPath);
                        avatarNumber++;
                    }
                    inputStream = new BufferedInputStream(fileItem.openStream());
                    outputStream = new BufferedOutputStream(
                            new FileOutputStream(application.getRealPath("/")
                                    + virtualPath.replace("/", "\\")));
                    Streams.copy(inputStream, outputStream, true);
                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();
                }
                /*
				 * else { 附加在接口中的其他参数... 如下代码在上传接口upload.jsp中定义了一个user=xxx的参数：
				 * var swf = new fullAvatarEditor("swf", { id: "swf",
				 * upload_url: "Upload.asp?user=xxx" }); 即可如下获取user的值xxx
				 * 
				 * inputStream = new BufferedInputStream(fileItem.openStream());
				 * byte[] bytes = new byte [inputStream.available()];
				 * inputStream.read(bytes); String user = new String(bytes,
				 * "UTF-8"); inputStream.close(); }
				 */
            }
            if (result.sourceUrl != null) {
                result.sourceUrl += initParams;
            }
            result.success = true;
            result.msg = "Success!";
			/*
			 * To Do...可在此处处理储存事项
			 */
            // 返回图片的保存结果（返回内容为json字符串，可自行构造，该处使用fastjson构造）
            renderJson(JSON.toJSONString(result));
        }
    }

    private void avitar() throws IOException {
        ServletContext application = this.getRequest().getServletContext();
        HttpServletRequest request = this.getRequest();
        String userId = (String) this.getSession()
                .getAttribute("theUserInfoID");
        userId = "10086";
        //	System.out.println("userId:" + userId);

        String dir = SystemTools.getWebRootPath();

        String savePath = "/upload/UserInfo/" + userId + "/"; // 保存图片路径 可以修改

        String _savePath = dir + savePath;

        File filePath = new File(_savePath);
        if (!filePath.isDirectory())
            filePath.mkdirs();

        String file_src = _savePath + "src.jpg"; // 保存原图
        String filename162 = _savePath + "162.jpg"; // 保存162
        String filename48 = _savePath + "48.jpg"; // 保存48
        String filename20 = _savePath + "20.jpg"; // 保存20

        String pic = request.getParameter("pic");
        String pic1 = request.getParameter("pic1");
        String pic2 = request.getParameter("pic2");
        String pic3 = request.getParameter("pic3");

        if (!pic.equals("") && pic != null) {
            // 原图
            File file = new File(file_src);
            FileOutputStream fout = null;
            fout = new FileOutputStream(file);
            fout.write(new BASE64Decoder().decodeBuffer(pic));
            fout.close();
        }

        // 图1
        File file1 = new File(filename162);
        FileOutputStream fout1 = null;
        fout1 = new FileOutputStream(file1);
        fout1.write(new BASE64Decoder().decodeBuffer(pic1));
        fout1.close();

        // 图2
        File file2 = new File(filename48);
        FileOutputStream fout2 = null;
        fout2 = new FileOutputStream(file2);
        fout2.write(new BASE64Decoder().decodeBuffer(pic2));
        fout2.close();

        // 图3
        File file3 = new File(filename20);
        FileOutputStream fout3 = null;
        fout3 = new FileOutputStream(file3);
        fout3.write(new BASE64Decoder().decodeBuffer(pic3));
        fout3.close();

        String picUrl = savePath;
        this.renderJson("{\"status\":1,\"picUrl\":\"" + picUrl + "\"}");
    }

    private class Result {
        /**
         * 表示图片是否已上传成功。
         */
        public Boolean success;
        /**
         * 自定义的附加消息。
         */
        public String msg;
        /**
         * 表示原始图片的保存地址。
         */
        public String sourceUrl;
        /**
         * 表示所有头像图片的保存地址，该变量为一个数组。
         */
        public ArrayList avatarUrls;
    }
}
