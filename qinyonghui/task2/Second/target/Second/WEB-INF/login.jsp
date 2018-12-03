<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/11
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--设置页面的字符（控制的是这个jsp文件中的）--%>
<%--表格名，方法是插入--%>
<form action="login" method="post">
    username:<input type="text" name = "username" ><p>
    password:<input type="password" name = "password" ><p>
    <input type="submit" value="登录">
</form>
