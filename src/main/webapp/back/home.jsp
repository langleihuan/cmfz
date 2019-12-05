<%--
  Created by IntelliJ IDEA.
  User: GG
  Date: 2019/11/26
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主页</title>
    <link href="${pageContext.request.contextPath}/back/statics/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!--引入bootstrap css-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/back/statics/bootstrap/css/bootstrap.min.css">
    <!--引入jqgrid的bootstrap css-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/back/statics/jqgrid/css/ui.jqgrid-bootstrap.css">
    <!--引入jquery核心js-->
    <script src="${pageContext.request.contextPath}/back/statics/js/jquery-3.4.1.min.js"></script>
    <!--引入jqgrid核心js-->
    <script src="${pageContext.request.contextPath}/back/statics/jqgrid/js/jquery.jqGrid.min.js"></script>
    <!--引入jqgrid国际化js-->
    <script src="${pageContext.request.contextPath}/back/statics/jqgrid/i18n/grid.locale-cn.js"></script>
    <!--引入bootstrap组件js-->
    <script src="${pageContext.request.contextPath}/back/statics/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/back/statics/js/ajaxfileupload.js"></script>

    <script src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>

    <script charset="UTF-8" src="${pageContext.request.contextPath}/back/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/back/echarts/china.js" charset="UTF-8"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>

    <script>
        KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id',{
                // 1. 指定图片上传路径
                uploadJson:"${pageContext.request.contextPath}/article/uploadImg",
                allowFileManager:true,
                fileManagerJson:"${pageContext.request.contextPath}/article/showAllImgs",
                afterBlur:function () {
                    this.sync();
                }
            });
        });
    </script>

</head>
<body>
<div class="container-fluid">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/back/home.jsp">持明法洲管理系统&nbsp<small>V1.0</small></a>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">


                <ul class="nav navbar-nav navbar-right">
                    <span style="color: grey">欢迎:${admin.username}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;退出登录</span>
                    <span class="glyphicon glyphicon-log-out" aria-hidden="true" style="margin-top: 18px;color: grey"></span>&nbsp;<span class="caret"></span>&nbsp;&nbsp;&nbsp;
                </ul>
            </div>


        </div>
    </nav>
    <div class="container-fluid">
        <div class="row">
            <!--菜单-->
            <div class="col-sm-2">
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    用户管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <div class="list-group">
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/user/list.jsp');" class="list-group-item ">
                                        用户列表
                                    </a>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/user/echarts.jsp');" class="list-group-item ">
                                        用户注册时间趋势
                                    </a>
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/user/map.jsp');" class="list-group-item ">
                                        用户注册地区分布
                                    </a>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                    上师管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                            <div class="panel-body">
                                <div class="list-group">
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/master/list.jsp');" class="list-group-item active">
                                        上师列表
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingThree">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                    文章管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="panel-body">
                                <div class="list-group">
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/article/list.jsp');" class="list-group-item active">
                                        文章列表
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingFor">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFor" aria-expanded="true" aria-controls="collapseFor">
                                    专辑管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFor" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFor">
                            <div class="panel-body">
                                <div class="list-group">
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/album/list.jsp');" class="list-group-item ">
                                        内容管理
                                    </a>
                                </div>

                            </div>
                        </div>
                    </div>

                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingFive">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="true" aria-controls="collapseFive">
                                    轮播图管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                            <div class="panel-body">
                                <div class="list-group">
                                    <a href="javascript:$('#content').load('${pageContext.request.contextPath}/back/banner/list.jsp');" class="list-group-item ">
                                        详情管理
                                    </a>
                                </div>

                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <!--     主体       -->
            <div id="content">
                <div class="col-sm-10">
                    <!--   巨幕        -->
                    <div class="panel panel-default">
                        <div class="panel-heading"><h3 style="margin-left: 7%">欢迎使用持明法洲后台管理系统!</h3></div>
                    </div>
                    <!--     警告框       -->
                    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner" role="listbox">
                            <div class="item active">
                                <img src="statics/img/ltb0.jpg" alt="...">
                                <div class="carousel-caption">

                                </div>
                            </div>
                            <div class="item">
                                <img  src="statics/img/ltb0.jpg" alt="...">
                                <div class="carousel-caption">

                                </div>
                            </div>
                            <div class="item">
                                <img src="statics/img/ltb0.jpg" alt="...">
                                <div class="carousel-caption">

                                </div>
                            </div>
                        </div>

                        <!-- Controls -->
                        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>
            </div>

        </div>
    </div>

<%--    底部导航栏--%>
    <br><br>
    <div>
        <footer style="text-align: center">@百知教育 @BaiZhi @Zparkhr.com.cn</footer>
    </div>
    <br><br>
</div>




</body>


</html>
