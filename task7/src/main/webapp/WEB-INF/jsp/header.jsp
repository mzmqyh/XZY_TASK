<%--
  Created by IntelliJ IDEA.
  User: qyh
  Date: 2018/10/21
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html lang="en">
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" isELIgnored="false"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no">
    <title>首页</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/css/base.css">
    <link rel="stylesheet" href="${path}/css/task-91.css">
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<!-- 头部 -->
<header>

    <div class="top w">
        <div class="num">客服热线：010-594-78634</div>
        <div class="logos">
            <c:choose>
                <c:when test="${cookie.name.value == null}">
                    <li><a href="${path}/register" class="btn">注册</a></li>
                    <li><a href="${path}/login" class="btn">登陆</a></li>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${cookie.img.value.equals('')}">
                            <li><a><img src=https://dreamhony.oss-cn-shanghai.aliyuncs.com/water.jpg"
                                        height="100" width="100"></a></li>
                        </c:when>
                        <c:otherwise>
                            <li><img src="${cookie.img.value}"></li>
                        </c:otherwise>
                    </c:choose>
                    <li><a href="${path}/uploadFile" class="btn">修改头像</a></li>
                    <li><span class="btn">用户：${cookie.name.value}</span></li>
                    <span><li><a href="${path}/logout" class="btn">退出</a></li></span>
                </c:otherwise>
            </c:choose>
            <img src="${path}/images/wx.png" alt="">
            <img src="${path}/images/qq.png" alt="">
            <img src="${path}/images/xl.jpg" alt="">
        </div>
    </div>
    <div class="top1">
        登陆&nbsp<span>|</span>&nbsp注册
    </div>
    <nav>
        <ul class=" nav1 w">
            <img src="${path}/images/logo.png" alt="">
            <li><a href="${path}/task-91">首页</a></li>
            <li><a href="${path}/u/task-93">职业</a></li>
            <li><a href="${path}/task-92">推荐</a></li>
            <li><a href="">关于</a></li>
        </ul>
        <div class="dropdown">
            <img class="ji" src="${path}/images/logo.png" alt="">
            <button class="btn dropdown-toggle clearfix" type="button" id="dropdownMenu1" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="true">
                    <span>
                        <img src="${path}/images/btn1.png" alt="">
                    </span>
            </button>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                <li><a href="localhost://8080${path}/task-91">首页</a></li>
                <li><a href="${path}/u/task-93">职业</a></li>
                <li><a href="${path}/task-92">推荐</a></li>
                <li><a href="#">关于</a></li>
            </ul>
        </div>
    </nav>
</header>
</body>
</html>
