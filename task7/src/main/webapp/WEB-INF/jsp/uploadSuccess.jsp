<%--
  Created by IntelliJ IDEA.
  User: qyh
  Date: 2018/11/28
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>文件上传成功</title>
</head>
<body>
<div style="text-align:center">
    <h2>文件${msg}上传成功！</h2><br>
    <a href="${path}/task-91"><h2>首页</h2></a>
</div>
</body>
</html>