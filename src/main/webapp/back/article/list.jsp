<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
<script src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>

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

<script>

    // 1. 获取上师信息 在表单回显
    $.get("${pageContext.request.contextPath}/master/showMaster",function (data) {
        var option = "<option value='0'>通用文章</option>";
        data.forEach(function (master) {
            option += "<option value='"+master.id+"'>"+master.username+"</option>"
            if (master.id=="1"){
                option += "<option selected value='"+master.id+"'>"+master.username+"</option>"
            }
        });
        $("#guruList").html(option);
    },"json");

    // 2. 异步提交数据并上传文件
    function sub() {
        alert($("#guruList").val())
        $.ajaxFileUpload({

            url:"${pageContext.request.contextPath}/article/insertArticle",
            datatype:"json",
            type:"post",
            fileElementId:"inputfile",
            // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
            //                只支持 Json格式上传数据
            // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式

            data:{id:$("#formid").val(),name:$("#name").val(),master_id:$("#guruList").val(),content:$("#editor_id").val()},
            success:function (data) {
                $("#table").trigger("reloadGrid");
            }

        })
    }
</script>

<script>


    $(function () {
        $("#table").jqGrid(
            {
                url :"${pageContext.request.contextPath}/article/queryAll",
                datatype : "json",
                height : 300,
                colNames : [ 'id', '文章名',"图片","内容","创建时间","出版时间","上师ID","操作" ],
                colModel : [
                    {name : 'id',},
                    {name : 'name',align:"center",editable:true,editrules:{required:true}},
                    {name : 'picpath',align:"center",formatter:function (data) {
                            return "<img style='width: 100%' src='"+data+"'/>"
                        },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}},
                    {name : 'content',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'create_time',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'publish_date',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'master_id',align:"center",editable:true,editoptions:{required:true}},
                    {
                        name: "id", align: "center",width:"300px" ,formatter: function (value, option, rows) {
                            var result = "";
                            result += "<button class=\"btn btn-info\" onclick=\"editRow('" + rows.id + "');\"><span class='glyphicon glyphicon-th-list'></span></button>&nbsp;&nbsp;";
                            result += "<button class=\"btn btn-danger\" onclick=\"delRow('" + rows.id + "');\">删除</button>&nbsp;&nbsp;";

                            return result;
                        }
                    },
                ],
                rowNum : 4,
                rowList : [4, 8, 10, 20 ],
                pager : '#page',
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
                multiselect : false,
                // 开启多级表格支持
                subGrid : true,
                autowidth:true,
                styleUI:"Bootstrap",
                editurl:"${pageContext.request.contextPath}/article/option",
            });
        $("#table").jqGrid('navGrid', '#page',
            {edit : true, add : true, del : true,
                edittext:"编辑",addtext:"添加",deltext:"删除"
            });
    });

    // 编辑文章
    function showModel(id) {
        // 返回指定行的数据，返回数据类型为name:value，name为colModel中的名称，value为所在行的列的值，如果根据rowid找不到则返回空。在编辑模式下不能用此方法来获取数据，它得到的并不是编辑后的值
        var data = $("#table").jqGrid("getRowData",id);

        $("#name").val(data.name);
        //$("#status").val(data.status);
        $("#formid").val(data.id);
        // KindEditor 中的赋值方法 参数1: kindeditor文本框 的id
        KindEditor.html("#editor_id",data.content);
        $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>"+
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"updateArticle()\" data-dismiss=\"modal\">修改</button>")
        $("#kind").modal("show");
    }

    // 编辑文章
    function editRow(id) {

        $("#kind").modal("show");
        // 返回指定行的数据，返回数据类型为name:value，name为colModel中的名称，value为所在行的列的值，如果根据rowid找不到则返回空。在编辑模式下不能用此方法来获取数据，它得到的并不是编辑后的值
        var data = $("#table").jqGrid("getRowData",id);
        $("#name").val(data.name);
        //$("#status").val(data.status);
        $("#formid").val(id);
        // KindEditor 中的赋值方法 参数1: kindeditor文本框 的id
        KindEditor.html("#editor_id",data.content);
    }

    function updateArticle() {
        var url="${pageContext.request.contextPath}/article/updateArticle";
        if($("#formid")=="") url="${pageContext.request.contextPath}/article/insertArticle";
        $.ajaxFileUpload({
            url:url,
            datatype:"json",
            type:"post",
            fileElementId:"inputfile",
            // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
            //                只支持 Json格式上传数据
            // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式
            data:{id:$("#formid").val(),name:$("#name").val(),master_id:$("#guruList").val(),content:$("#editor_id").val()},
            success:function (data) {
                $("#table").trigger("reloadGrid");
            }
        })
    }

    function addRow(data) {
        $("#kind").modal("show");
    }
    function delRow(id) {
        $.get("${pageContext.request.contextPath}/article/deleteArticle",{id:id},function (id) {
            $("#table").trigger("reloadGrid");
        },"json");
    }
</script>
<div class="col-sm-10">
    <div class="page-header">
        <h2>文章管理</h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a>文章列表</a>
        <li onclick="editRow()"><a>添加文章</a>
    </ul>
    <div class="panel">
        <table id="table"></table>
        <div style="height: 50px" id="page"></div>
    </div>
</div>


<!-- KindEditor模态框 -->
<div class="modal fade" id="kind" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 750px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">文章信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" enctype="multipart/form-data" id="myForm">
                    <input name="id" type="hidden" id="formid">
                    <div class="form-group">
                        <label for="name">标题</label>
                        <input type="text" class="form-control" name="name" id="name" placeholder="请输入名称">
                    </div>
                    <div class="form-group">
                        <label for="inputfile">封面上传</label>
                        <input type="file" name="articleImg" id="inputfile">
                    </div>
                    <div class="form-group">
                        <label for="name">所属上师</label>
                        <select class="form-control" id="guruList" name="master_id">

                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editor_id">内容</label>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;">

                            </textarea>
                    </div>
                    <button type="button" class="btn btn-default" onclick="updateArticle()" data-dismiss="modal">提交</button>
                </form>
            </div>
            <div class="modal-footer" id="modal_foot">

            </div>
        </div>
    </div>
</div>


