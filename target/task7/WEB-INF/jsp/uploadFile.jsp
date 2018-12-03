<%--
  Created by IntelliJ IDEA.
  User: qyh
  Date: 2018/11/28
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Upload File</title>
</head>
<body>
<div style="text-align:center">
    <h2>文件上传实例</h2>
</div>
<div style="text-align:center">
    文件上传
    <form action="${path}/uploadFile" method="post" enctype="multipart/form-data">

        选择文件:  <input type="file" name="file">
        <img src="file" height="200" alt="Image preview area..." title="preview-img">
        <input type="submit" value="确定">
    </form>
</div>
</body>
</html>

