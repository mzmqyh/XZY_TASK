<%--@elvariable id="contextPath" type=""--%>
<%--
  Created by IntelliJ IDEA.
  User: qyh
  Date: 2018/10/24
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" isELIgnored="false"
         pageEncoding="UTF-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>注册</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;">
    <meta name="format-detection" content="telephone=no">
    <title>首页</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${path}/base.css">
    <link rel="stylesheet" href="${path}/css/task-91.css">
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<div style="width:100%;text-align:center">

    <form id="sighupForm" action="${path}/register" method="post" onsubmit="formCheck()">
        用户名：
        <input type="text" name="username" required pattern="^[a-zA-Z0-9]{6,12}$" placeholder="英文或者数字,长度6到12位"
               style="width: 200px;height: 35px"><br>
        密码：
        <input type="text" name="password" required pattern="^[a-zA-Z0-9]{6,12}$" id="pwd1"
               placeholder="英文或者数字,长度6到12位" style="width: 200px;height: 35px"><br>
        重复密码：
        <input type="text" name="password1" required="required" id="pwd2" placeholder="英文或者数字,长度6到12位"
               style="width: 200px;height: 35px"><br><br>
        手机号码：
        <input id="mobilephone" type="text" name="mobilephone" style="width: 200px;height: 35px"><br>
        验证码： <input type="text" name="smsCode"/><br>
        <input class="button" type="submit" value="注册">
    </form>
    "request.getContextPath()的值是:"<%=request.getContextPath()%><br/>
    "pageContext.request.contextPath的值是:"${pageContext.request.contextPath}<br/>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        System.out.println("path的值是：" + path + "\n");
        System.out.println("basePath：" + basePath + "\n");
    %>
    <input type="button" id="btn" value="免费获取验证码" onclick="settime(this)"/><br>

    <a href="${path}/task-91" class="btn">去首页</a>
    <a href="${path}/login" class="btn">去登录</a>
    <a href="${path}/email/register">使用邮箱注册</a>

    <script type="text/javascript">
        function formCheck() {
            var pwd1 = document.getElementById("pwd1").value;
            var pwd2 = document.getElementById("pwd2").value;
            if (pwd1 != pwd2) {
                alert("两次输入的密码不一致！");
                return false;
            }
            return true;
        }
    </script>

    <script>
        $("#btn").click(function () {
            if (checkPhone()) {
                getCode();
                var wait = 60;//时间
                function time(o, p) {//o为按钮的对象，p为可选，这里是60秒过后，提示文字的改变
                    if (wait == 0) {
                        o.removeAttr("disabled");
                        o.val("获取验证码");//改变按钮中value的值
                        p.html("如果您在1分钟内没有收到验证码，请检查您填写的手机号码是否正确或重新发送");
                        wait = 60;
                    } else {
                        o.attr("disabled", true);//倒计时过程中禁止点击按钮
                        o.val("倒数" + wait + "秒");//改变按钮中value的值
                        wait--;
                        setTimeout(function () {
                                time(o, p);//循环调用
                            },
                            1000)
                    }
                }
            }
            var get_code = $("#btn");
            time(get_code);
        })

        function checkPhone() {
            var phone = document.getElementById('mobilephone').value;
            if (!(/^1[34578]\d{9}$/.test(phone))) {
                alert("手机号码有误，请重填");
                return false;
            }
            return true
        }

        // 获取验证码
        function getCode() {
            var mobilephone = $("#mobilephone").val();

            var get_code_url = "${path}/sms/code";
            $.ajax({
                type: "POST",
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                url: get_code_url,
                async: false,
                data: {"mobilephone": mobilephone},
                dataType: "json",

                // complete: function () { },

                success: function (data) {
                    console.debug(data);

                    if (data.num == '-101') {
                        alert("该手机号码已被注册")
                    } else {
                        alert("发送验证码")
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        }

        //验证码倒计时
    </script>
</div>
</html>



