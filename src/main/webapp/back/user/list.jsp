<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function(){
        $("#bannerTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/user/queryAll",
                datatype : "json",
                colNames : [ 'ID', '头像', '用户名', '密码', '盐', '状态' ,"真实姓名","性别","电话","地址","签名","上次登陆时间","创建时间" ],
                colModel : [
                    {name : 'id',hidden:true},
                    {name : 'head',align:"center",formatter:function (data) {
                            return "<img style='width: 100%' src='"+data+"'/>"
                        },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}},
                    {name : 'username',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'password',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'salt',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'status',align:"center",formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            }else return "冻结";
                        },editable:true,edittype:"select",editoptions:{value:"1:展示;2:冻结"}
                    },
                    {name : 'name',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'sex',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'phone',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'address',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'signature',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'lastlogintime',align:"center",editoptions:{required:true}},
                    {name : 'create_time',align:"center",editoptions:{required:true}},
                ],
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#bannerPage',
                mtype : "post",
                viewrecords : true,
                styleUI:"Bootstrap",
                autowidth:true,
                multiselect:true,
                editurl:"${pageContext.request.contextPath}/user/edit"
            });
        $("#bannerTable").jqGrid('navGrid', '#bannerPage',
            {edit : true, add : true, del : true,
                edittext:"编辑",addtext:"添加",deltext:"删除"
            },
            {
                afterSubmit:function (response,postDate) {
                    var userID = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/user/upload",
                        datatype:"json",
                        type:"post",
                        data:{userId:userID},
                        // 指定的上传input框的id
                        fileElementId:"head",
                        success:function (data) {
                            // 输出上传成功
                            // jqgrid重新载入
                            $("#bannerTable").trigger("reloadGrid");
                        },
                        closeAfterEdit:true
                    })
                    return postDate;
                }
            },
            {
                afterSubmit:function (response,postDate) {
                    var userID = response.responseJSON.userId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/user/upload",
                        datatype:"json",
                        type:"post",
                        data:{userId:userID},
                        // 指定的上传input框的id
                        fileElementId:"head",
                        success:function (data) {
                            // 输出上传成功
                            // jqgrid重新载入
                            $("#bannerTable").trigger("reloadGrid");
                        },
                        closeAfterEdit:true
                    })
                    return postDate;
                }
            });
    });
</script>

<div class="col-sm-10">

    <div class="page-header">
        <h1>用户列表</h1>
    </div>

    <%--员工列表--%>
    <table id="bannerTable"></table>

    <%--分页工具栏--%>
    <div id="bannerPage"></div>

</div>
