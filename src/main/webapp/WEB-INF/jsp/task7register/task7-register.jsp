<%--
  Created by IntelliJ IDEA.
  User: qyh
  Date: 2018/11/22
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="title" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册</title>
    <script src="https://cdn.bootcss.com/jquery/3.3.0/jquery.min.js"></script>

</head>
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<body>
<div class="main">
    <form action="">
        <h2>请注册</h2>
        <%--<div id="imgdiv">--%>
        <%--<img id="imgShow" width="100" height="100"/>--%>
        <%--<span>上传头像</span>--%>
        <%--</div>--%>
        <%--<input type="file" id="userPhoto"/>--%>

        <div class="form-group">
            <label for="username">用户名</label>
            <input type="text" class="form-control" id="username" placeholder="请输入用户名">
        </div>
        <div class="form-group">
            <label for="password">密码</label>
            <input type="password" class="form-control" id="password" placeholder="请输入密码">
        </div>
        <div class="form-group">
            <label for="mobilephone">手机号</label>
            <input type="text" class="form-control" id="mobilephone" placeholder="请输入手机号">
        </div>
        <div class="form-group">
            <label for="code">验证码</label>
            <div class="sms">
                <input type="text" class="form-control smsCode" id="code"
                       placeholder="请先获取验证码">
                <p id="randCode" class="smsCode1 btn-default">获取验证码</p></div>
        </div>
        <p id="submit" class="btn btn-default">注册</p>
        <p class="message">已有账号?去<a href="/login" methods="get">登录</a></p>
    </form>
</div>
<script src="../js/Mono.js"></script>
<script>
    function isPoneAvailable(mobilephone) {
        var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
        if (!myreg.test(mobilephone)) {
            return false;
        } else {
            return true;
        }
    }

    var a = document.getElementById("randCode");
    $('#randCode').click(function () {
        var userTelephone = document.getElementById('userTelephone').value;
        //Ajax调用处理
        var phone = {
            mobilephone: mobilephone
        };
        $.ajax({
            type: "POST",
            url: "/SendCode",
            data: $.param(phone),
            async: false,
            dataType: 'JSON',
            success: function (res) {
                if (res.code === 0) {
                    alert("发送成功,请查看手机。");
                } else {
                    alert(res.message);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // alert("发送成功,请查看手机。");
            },
            complete: function (XMLHttpRequest, textStatus) {
                this; // 调用本次 AJAX 请求时传递的 options 参数
            }
        })
    });
    $(function () {
        //按钮单击时执行
        $("#submit").click(function () {
            var obj = {
                // userPhoto:$('userPhoto').val(),
                name: $('#username').val(),
                password: $('#password').val(),
                userTelephone: $('#mobilephone').val(),
                code: $('#code').val()
            };
            //Ajax调用处理
            console.log(obj);
            $.ajax({
                type: "POST",
                url: "register",
                data: $.param(obj),
                async: false,
                dataType: 'JSON',
                success: function (res) {
                    if (res.code === 0) {
                        alert('成功');
                        console.log(res);
                        location.href = 'bindEmail'
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                },
                complete: function (XMLHttpRequest, textStatus) {
                    this; // 调用本次 AJAX 请求时传递的 options 参数
                }
            })
        });
    });
</script>
<style>
    .main {
        background-color: pink;
        width: 30%;
        position: absolute;
        left: 50%;
        top: 50%;
        padding: 2rem;
        transform: translate(-50%, -50%);
        padding: 2rem 5%;
        /*text-align: center;*/
    }

    .form-control {
        display: inline-block;
    }

    img {
        width: 100px;
    }

    .sms {
        width: 100%;
    }

    .smsCode {
        width: 60% !important;
        display: inline-block;
    }

    .smsCode1 {
        border: 0px;
        width: 30%;
        display: inline;
        /*background-color: aqua;*/
        border-radius: 1rem;
        padding: .3rem;
        /*color: white;*/
        /*background-color:#eeeeee;*/
    }

    .smsCode1:hover {
        /*color: white;*/
        /*background-color:#eeeeee;*/
    }
</style>
</body>
</html>