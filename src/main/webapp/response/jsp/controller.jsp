<%@ page language="java" contentType="text/html; charset=UTF-8"
         import="com.baidu.ueditor.ActionEnter"
         pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding("utf-8");
    response.setHeader("Content-Type", "text/html");
    System.out.println("this is the file");
    String rootPath = application.getRealPath("/");

    out.write(new ActionEnter(request, rootPath).exec());

%>