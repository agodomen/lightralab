package edu.muc.platform.plugin.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import edu.muc.platform.plugin.DictKeys;
import edu.muc.platform.plugin.PropertiesPlugin;

/**
 * WEB上下文工具类
 *
 * @author 董华健 2012-9-7 下午1:51:04
 */
public class ToolContext {

    private static Logger log = Logger.getLogger(ToolContext.class);

    /**
     * 输出servlet文本内容
     *
     * @param response
     * @param content
     * @author 董华健 2012-9-14 下午8:04:01
     */
    public static void outPage(HttpServletResponse response, String content) {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding(ToolString.encoding);
        // PrintWriter out = response.getWriter();
        // out.print(content);
        try {
            response.getOutputStream().write(content.getBytes(ToolString.encoding));// char to byte 性能提升
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出CSV文件下载
     *
     * @param response
     * @param content  CSV内容
     * @author 董华健 2012-9-14 下午8:02:33
     */
    public static void outDownCsv(HttpServletResponse response, String content) {
        response.setContentType("application/download; charset=gb18030");
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(ToolDateTime.format(ToolDateTime.getDate(), ToolDateTime.pattern_ymd_hms_s) + ".csv", ToolString.encoding));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        // PrintWriter out = response.getWriter();
        // out.write(content);
        try {
            response.getOutputStream().write(content.getBytes(ToolString.encoding));
        } catch (IOException e) {
            e.printStackTrace();
        }// char to byte 性能提升
        // out.flush();
        // out.close();
    }


    /**
     * 设置验证码
     *
     * @param response
     * @param authCode
     */
    public static void setAuthCode(HttpServletResponse response, String authCode) {
        byte[] authTokenByte = null;
        try {
            authTokenByte = authCode.getBytes(ToolString.encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String securityKey = (String) PropertiesPlugin.getParamMapValue(DictKeys.config_securityKey_key);
        byte[] keyByte = Base64.decodeBase64(securityKey);

        // 加密
        byte[] securityByte = null;
        try {
            securityByte = ToolSecurityIDEA.encrypt(authTokenByte, keyByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String securityCookie = Base64.encodeBase64String(securityByte);

        // Base64编码
        try {
            securityCookie = ToolString.encode(securityCookie);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 登陆认证cookie
        int maxAgeTemp = ((Integer) PropertiesPlugin.getParamMapValue(DictKeys.config_maxAge_key)).intValue();
        ToolWeb.addCookie(response, "", "/", true, "authCode", securityCookie, maxAgeTemp);
    }

    /**
     * 获取验证码
     *
     * @param request
     * @return
     */
    public static String getAuthCode(HttpServletRequest request) {
        String authCode = ToolWeb.getCookieValueByName(request, "authCode");
        if (null != authCode && !authCode.equals("")) {
            // Base64解码
            try {
                authCode = ToolString.decode(authCode);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 解密
            byte[] securityByte = Base64.decodeBase64(authCode);

            String securityKey = (String) PropertiesPlugin.getParamMapValue(DictKeys.config_securityKey_key);
            byte[] keyByte = Base64.decodeBase64(securityKey);

            byte[] dataByte = null;
            try {
                dataByte = ToolSecurityIDEA.decrypt(securityByte, keyByte);
            } catch (Exception e) {
                e.printStackTrace();
            }
            authCode = new String(dataByte);
        }
        return authCode;
    }

    /**
     * 获取请求参数
     *
     * @param request
     * @param name
     * @return
     */
    public static String getParam(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (null != value && !value.isEmpty()) {
            try {
                value = URLDecoder.decode(value, ToolString.encoding).trim();
            } catch (UnsupportedEncodingException e) {
                log.error("decode异常：" + value);
            }
        }
        return value;
    }

    /**
     * 请求流转字符串
     *
     * @param request
     * @return
     */
    public static String requestStream(HttpServletRequest request) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            request.setCharacterEncoding(ToolString.encoding);
            inputStream = (ServletInputStream) request.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, ToolString.encoding);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            log.error("request.getInputStream() to String 异常", e);
            return null;
        } finally { // 释放资源
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error("bufferedReader.close()异常", e);
                }
                bufferedReader = null;
            }

            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    log.error("inputStreamReader.close()异常", e);
                }
                inputStreamReader = null;
            }

            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream.close()异常", e);
                }
                inputStream = null;
            }
        }
    }
}
