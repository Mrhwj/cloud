<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>后台管理-登陆</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" href="../lib/layui-v2.5.5/css/layui.css" media="all">
    <link rel="stylesheet" href="../css/login.css" media="all">
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="../js/jquery1.4.2.min.js" charset="utf-8"></script>
    <script type="text/javascript">
        var jq = jQuery.noConflict();
    </script>
    <script src="../js/jquery1.11.1.js" charset="utf-8"></script>
    <script src="../js/jquery.ez-bg-resize.js" charset="utf-8"></script>
    <script src="../js/jquery.placeholder.min.js" charset="utf-8"></script>
    <script type="text/javascript">
	    if(location.href.indexOf("hc.zjcz.net.cn")>0){
			location.href = "http://117.149.147.10:6443/manage/login.do";
		}
        $(function () {
            $('.i-form .layui-input').placeholder();
            $(".admin-button").hover(function () {
                $(this).css("opacity", 0.8)
            }, function () {
                $(this).css("opacity", 1)
            })
            $("body").ezBgResize({
                img: "../images/i_body_main_bg.jpg", // Relative path example.
                opacity: 1, // Opacity. 1 = 100%.  This is optional.
                center: true // Boolean (true or false). This is optional. Default is true.
            });
        })
    </script>
    <!--style>
        body {
            background-image: url("../images/loginbg.png");
            height: 100%;
            width: 100%;
        }

        #container {
            height: 100%;
            width: 100%;
        }

        input:-webkit-autofill {
            -webkit-box-shadow: inset 0 0 0 1000px #fff;
            background-color: transparent;
        }

        .admin-login-background {
            width: 672px;
            height: 300px;
            position: absolute;
            left: 50%;
            top: 40%;
            margin-left: -336px;
            margin-top: -100px;
        }

        .admin-header {
            text-align: center;
            margin-bottom: 20px;
            color: #ffffff;
            font-weight: bold;
            font-size: 39px
        }

        .admin-input {
            border-top-style: none;
            border-right-style: solid;
            border-bottom-style: solid;
            border-left-style: solid;
            height: 50px;
            width: 300px;
            padding-bottom: 0px;
            padding-left: 50px;
        }

        .layui-icon-username {
            color: #a78369 !important;
        }

        .layui-icon-username:hover {
            color: #9dadce !important;
        }

        .layui-icon-password {
            color: #a78369 !important;
        }

        .layui-icon-password:hover {
            color: #9dadce !important;
        }

        .admin-button {
            margin-top: 20px;
            font-weight: bold;
            font-size: 18px;
            width: 218px;
            height: 32px;
            background: url(../images/loginBtn.png) no-repeat center center;
        }

        .admin-icon {
            margin-left: 260px;
            margin-top: 10px;
            font-size: 30px;
        }

        i {
            position: absolute;
        }

        .admin-captcha {
            position: absolute;
            margin-left: 205px;
            margin-top: -40px;
        }

        .bg1 {
            position: fixed;
            top: 0;
            left: 100px;
            width: 444px;
            height: 170px;
        }

        .bg2 {
            position: fixed;
            bottom: 0;
            right: 0;
            width: 228px;
            height: 400px;
        }

        .loginPanelImg {
            width: 261px;
            height: 357px;
            border-radius: 8px 0px 0px 8px;
            overflow: hidden;
            float: left;
        }

        .loginPanelImg img {
            width: 261px;
            height: 357px;
        }

        .loginPanel form {
            float: right;
            background: #fff;
            border-radius: 0px 8px 8px 0px;
            width: 361px;
            height: 327px;
            padding-left: 50px;
            padding-top: 30px;
        }

        .formImg {
            position: absolute;
            margin-top: 11px;
            margin-left: 12px;

        }

        .layui-input {
            border: none;
            border-bottom: 2px solid rgba(204, 204, 204, 1);
            font-size: 13px;
        }
    </style-->
</head>

<body>
    <form class="layui-form" action="">
        <div class="loginForm">
            <div class="pc_header">
                <span class="inline-block sys_name">货物管理系统</span>
                <span class="inline-block sys_desc">—— 货物盘点</span>
            </div>
            <div class="pc_content">
                <div class="pc_form">
                    <div class="pc_form_leftSide">
                        <img src="../images/i_login_form_left_side_bg.png" alt="" />
                    </div>
                    <div class="pc_form_rightSide">
                        <h1>用户登陆</h1>
                        <ul class="i-form">
                            <li>
                                <input type="text" name="name" placeholder="请输入用户名" autocomplete="off"
                                    class="layui-input admin-input admin-input-username">
                            </li>
                            <li>
                                <input type="password" name="pwd" placeholder="请输入密码" autocomplete="off"
                                    class="layui-input admin-input">
                            </li>
                            <li class="code">
                                <input type="text" name="code" id="code" placeholder="请输入验证码" autocomplete="off"
                                    class="layui-input admin-input admin-input-verify" value="">
                                <img class="admin-captcha" id="captchaPic" width="90" height="30" src="./captcha.do">
                            </li>
                        </ul>
                        <button class="admin-button" lay-submit lay-filter="login">登录</button>
                    </div>
                    <div class="clearFloat"></div>
                </div>
                <img src="../images/i_login_form_main_bg.png" class="pc_form_main_bg" alt="" />
            </div>
        </div>
    </form>
    <script src="../lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
    <script src="../js/v_basic.js" charset="utf-8"></script>
    <script>
        layui.use(['form'], function () {
            var form = layui.form,
                layer = layui.layer;

            // 登录过期的时候，跳出ifram框架
            if (top.location != self.location) top.location = self.location;

            // 进行登录操作
            form.on('submit(login)', function (data) {
                data = data.field;
                if (data.name == '') {
                    layer.msg('用户名不能为空');
                    return false;
                }
                if (data.pwd == '') {
                    layer.msg('密码不能为空');
                    return false;
                }
                if (data.code == '') {
                    layer.msg('验证码不能为空');
                    return false;
                }
                data.mathRandom = Math.random()
                $.post("./login.do", data, function (result) {
                    if (result.status == true) {
                    	layer.msg('登录成功');
                        location.href = './index.do';
                    }
                    else {
                        layer.msg('登录失败,' + result.msg.split(",")[1]);
                    }
                });

                return false;
            });



            $('#captchaPic').click(function () {
                $('#captchaPic').attr("src", "./captcha.do?timestamp=" + (new Date()).valueOf());
            });
        });
    </script>
</body>

</html>