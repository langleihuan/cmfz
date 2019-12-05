<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="${pageContext.request.contextPath}/back/statics/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/back/statics/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        function ChangeCapCha() {
            var captchaimg = document.getElementById('captchaimg');
            captchaimg.src = '${pageContext.request.contextPath }/captcha/captcha?'+Math.random();

        }

        //js登录
        function login(){

            <%--$.post('${pageContext.request.contextPath}/admin/login',{username:$('#username').val(),password:$('#password').val(),captchaCode:$('#captchaCode').val()},result=>{--%>
            <%--    if (result=="success"){--%>
            <%--        window.location.href='${pageContext.request.contextPath}/back/home.jsp';--%>
            <%--    }else{--%>
            <%--        $('#msg').text(result);--%>
            <%--    }--%>
            <%--});--%>


            var username = $.trim($("#username").val());
            var password = $.trim($("#password").val());
            var captchaCode = $.trim($("#captchaCode").val());
            if(username == ""){
                $('#msg').text("请输入用户名");
                //alert("请输入用户名");
                return false;
            }else if(password == ""){
                $('#msg').text("请输入密码");
                // alert("请输入密码");
                return false;
            }

            //ajax去服务器端校验
            var data= {username:username,password:password,captchaCode:captchaCode};
            $.post('${pageContext.request.contextPath}/admin/login',{username:$('#username').val(),password:$('#password').val(),captchaCode:$('#captchaCode').val()},(result)=>{

                if (result=='success'){
                    window.location.href='${pageContext.request.contextPath}/back/home.jsp';
                }else{
                    $('#msg').text(result);

                }
            });


        }
    </script>
</head>
<body style="background: url(statics/img/ltb0.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="">
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username" id="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password" id="password">
            </div>

            <div class="Captcha-operate">
                <div class="Captcha-imageConatiner">
                    <a class="code_pic" id="vcodeImgWrap" name="captchaCode" href="javascript:void(0);">
                        <img src="${pageContext.request.contextPath }/captcha/captcha" id="captchaimg"/>
                    </a>
                    <a href="javascript:void(0)" onclick="ChangeCapCha()">看不清，换一张!~</a>&nbsp;<span style="color:red"></span><br/>
                </div>
            </div>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="验证码" autocomplete="off" name="captchaCode" id="captchaCode">
            </div>
            <span id="msg" style="color: red">${message}</span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
            </div>
            <div class="form-group">
                <button type="reset" class="btn btn-default form-control">重置</button>
            </div>

        </div>
        </form>
    </div>
</div>
</body>
</html>
