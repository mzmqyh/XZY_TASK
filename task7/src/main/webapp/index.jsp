<%--
  Created by IntelliJ IDEA.
  User: qyh
  Date: 2018/10/30
  Time: 23:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>这是index页面</h1>
"request.getContextPath()的值是:"<%=request.getContextPath()%><br/>
"pageContext.request.contextPath的值是:"${pageContext.request.contextPath}<br/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
</body>
</html>
