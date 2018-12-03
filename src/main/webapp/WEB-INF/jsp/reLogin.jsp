<%--
  Created by IntelliJ IDEA.
  User: qyh
  Date: 2018/10/30
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>login</title>
</head>
<body>
<div style="text-align:center">
    用户名不存在，请重新
    <li><a href="${path}/login" class="btn">登陆/</a></li>
    <li><a href="${path}/register" class="btn">注册/</a></li>
</div>
</body>
</html>
