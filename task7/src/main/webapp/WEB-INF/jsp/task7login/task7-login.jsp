<%--
  Created by IntelliJ IDEA.
  User: qyh
  Date: 2018/11/22
  Time: 18:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="title" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
    <meta name="author" content="Vincent Garreau"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" media="screen" href="../css/style.css">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/3.3.0/jquery.min.js"></script>
    <style>
        .content {
            margin-top: 10%;
            /*background: url(../images/js3-1.jpg) no-repeat;*/
            width: 100%;
            height: 50%;
            background-color: #eff0f4;
            display: flex; /*Flex布局*/
            display: -webkit-flex; /* Safari */
            align-items: center; /*指定垂直居中*/
        }

        .container {
            background-color: #b9bec8;
            padding-top: 1rem;
            width: 40%;
            margin: auto;
            position: absolute;
            top: 10rem;
            left: 26%;
        }

        .container p {
            text-align: center;
            color: #1faaf1;
            font-size: 1.3rem;
            padding: 1rem;
        }

        .container div {
            background-color: white;
            margin: 2% 2%;
        }

        .container img {
            float: left;
            padding: 1%;
        }

        .container form {
            text-align: center;
            background: white;
        }

        .asd {
            background: white;
            padding-left: 1rem;
        }

        .container input {
            font-size: 1.6em;
            border: none;
            border-left: 1px solid #eff0f4;
            padding: 1%;
            margin: 2% 0;
            width: 80%;
            outline: medium;
        }

        footer {
            margin: 1% 0;
        }

        footer input {
            width: 100%;
            letter-spacing: 1em;
            color: white;
            border: none;
            background-color: #03a9f4;
            padding: 2%;
            font-size: 1.5em;
            border-radius: 1rem;
        }

        footer p {
            float: right;
            color: #000;
            margin-right: 1em;
            padding: 0;
            border-bottom: 1px solid #5fc0cd;
        }
    </style>
</head>
<body>
<script src="../js/Mono.js"></script>
<div id="particles-js">
    <div class="main">
        <form>
            <h2>登录</h2>
            <div class="form-group">
                <label for="input">支持用户名/密码/邮箱登录</label>
                <input type="text" class="form-control" id="input" placeholder="请输入用户名/密码/邮箱">
            </div>
            <div class="form-group">
                <label for="password">密码</label>
                <input type="password" class="form-control" id="password" placeholder="请输入密码">
            </div>
            <p id="submit" class="btn btn-default">登录</p>
            <p class="message">没有账号?去<a href="/register" methods="get">注册</a></p>
        </form>
    </div>

</div>
<!-- scripts -->
<script src="../js/particles.js"></script>
<script src="../js/app.js"></script>

<!-- stats.js -->
<script src="../js/lib/stats.js"></script>
<script>
    var count_particles, stats, update;
    stats = new Stats;
    stats.setMode(0);
    stats.domElement.style.position = 'absolute';
    stats.domElement.style.left = '0px';
    stats.domElement.style.top = '0px';
    document.body.appendChild(stats.domElement);
    count_particles = document.querySelector('.js-count-particles');
    update = function () {
        stats.begin();
        stats.end();
        if (window.pJSDom[0].pJS.particles && window.pJSDom[0].pJS.particles.array) {
            count_particles.innerText = window.pJSDom[0].pJS.particles.array.length;
        }
        requestAnimationFrame(update);
    };
    requestAnimationFrame(update);
</script>

<script src="../js/Mono.js"></script>
<script>
    $(function () {
        //按钮单击时执行
        $("#submit").click(function () {
            var obj = {
                // userPhoto:$('userPhoto').val(),
                input: $('#input').val(),
                password: $('#password').val(),
                // userTelephone:$('#userTelephone').val(),
                // code:$('#code').val()
            };
            //Ajax调用处理
            console.log(obj);
            $.ajax({
                type: "POST",
                url: "login",
                data: $.param(obj),
                async: false,
                dataType: 'JSON',
                success: function (res) {
                    if (res.code === 0) {
                        alert('成功');
                        console.log(res);
                        location.href = 'home';
                    } else if (res.code === 2) {
                        alert(res.message);
                        location.href = "bindEmail";
                    } else {
                        alert(res.message)
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
<!--上传图片-->
<script>
</script>
<!--上传图片-->
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