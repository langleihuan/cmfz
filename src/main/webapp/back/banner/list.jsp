<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function(){
        $("#bannerTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/rotationpic/queryAll",
                datatype : "json",
                colNames : [ 'ID', '标题', '图片', '超链接', '创建时间','描述', '状态' ],
                colModel : [
                    {name : 'id',hidden:true},
                    {name : 'name',align:"center",editable:true,editrules:{required:true}},
                    {name : 'picpath',align:"center",formatter:function (data) {
                            return "<img style='width: 100%' src='"+data+"'/>"
                        },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}},
                    {name : 'link',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'create_time',align:"center"},
                    {name : 'introduce',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'status',align:"center",formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            }else return "冻结";
                        },editable:true,edittype:"select",editoptions:{value:"1:展示;2:冻结"}}
                ],
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#bannerPage',
                mtype : "post",
                viewrecords : true,
                styleUI:"Bootstrap",
                autowidth:true,
                multiselect:true,
                editurl:"${pageContext.request.contextPath}/rotationpic/edit"
            });
        $("#bannerTable").jqGrid('navGrid', '#bannerPage',
            {edit : true, add : true, del : true,
                edittext:"编辑",addtext:"添加",deltext:"删除"
            },
            {
                afterSubmit:function (response,postDate) {
                    var rotationpicID = response.responseJSON.rotationpicId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/rotationpic/upload",
                        datatype:"json",
                        type:"post",
                        data:{rotationpicId:rotationpicID},
                        // 指定的上传input框的id
                        fileElementId:"picpath",
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
                            var rotationpicID = response.responseJSON.rotationpicId;
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/rotationpic/upload",
                                datatype:"json",
                                type:"post",
                                data:{rotationpicId:rotationpicID},
                                // 指定的上传input框的id
                                fileElementId:"picpath",
                                success:function (data) {
                                    // 输出上传成功
                                    // jqgrid重新载入
                                    $("#bannerTable").trigger("reloadGrid");
                                }
                    })
                    return postDate;
                }
            });
    });
</script>

<div class="col-sm-10">

    <div class="page-header">
        <h1>轮播图列表</h1>
    </div>

    <%--员工列表--%>
    <table id="bannerTable"></table>

    <%--分页工具栏--%>
    <div id="bannerPage"></div>

</div>
