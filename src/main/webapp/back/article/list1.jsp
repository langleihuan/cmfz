<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    $(function () {
        $("#articleTable").jqGrid(
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
                    {name:'option',formatter:function (cellvalue, options, rowObject) {
                            var result = '';
                            result += "<a href='javascript:void(0)' onclick=\"showModel('" + rowObject.id + "')\" class='btn btn-lg' title='查看详情'> <span class='glyphicon glyphicon-th-list'></span></a>";
                            return result;
                        }},
                ],
                rowNum: 5,
                rowList: [5, 10, 20],
                pager: '#articlePager',
                mtype: "post",
                viewrecords: true,
                sortorder: "desc",
                styleUI: "Bootstrap",
                autowidth: true,
                multiselect: true,
                height:"500px",
                editurl:"${pageContext.request.contextPath}/article/editArticle"
            });
        $("#articleTable").jqGrid('navGrid', "#articlePager", {
            add:false,
            edit:false,
            del: true,
            deltext: "删除"
        });
    })
    // 打开模态框
    function addArticle() {
        // 清除表单内数据
        $("#kindfrm")[0].reset();
        // kindeditor 提供的数据回显方法  通过"" 将内容设置为空串
        KindEditor.html("#editor_id", "");
        // 未提供查询上师信息 发送ajax请求查询
        $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>"+
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"insertArticle()\">添加</button>")
        $("#kind").modal("show");
    }

    // 编辑文章
    function showModel(id) {
        // 返回指定行的数据，返回数据类型为name:value，name为colModel中的名称，value为所在行的列的值，如果根据rowid找不到则返回空。在编辑模式下不能用此方法来获取数据，它得到的并不是编辑后的值
        var data = $("#articleTable").jqGrid("getRowData",id);
        $("#title").val(data.title);
        //$("#status").val(data.status);
        $("#id").val(data.id);
        // KindEditor 中的赋值方法 参数1: kindeditor文本框 的id
        KindEditor.html("#editor_id",data.content);
        $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>"+
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"updateArticle()\">修改</button>")
        $("#kind").modal("show");
    }

    // 添加文章
    function insertArticle() {
        $.ajaxFileUpload({
            url:"${pageContext.request.contextPath}/article/insertArticle",
            datatype:"json",
            type:"post",
            fileElementId:"inputfile",
            // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
            //                只支持 Json格式上传数据
            // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式
            data:{id:$("#formid").val(),title:$("#name").val(),guru_id:$("#guruList").val(),content:$("#editor_id").val()},
            success:function (data) {

            }
        })
    }
    function updateArticle() {
        $.ajaxFileUpload({
            url:"${pageContext.request.contextPath}/article/insertArticle",
            datatype:"json",
            type:"post",
            fileElementId:"inputfile",
            // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
            //                只支持 Json格式上传数据
            // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式
            data:{id:$("#formid").val(),title:$("#name").val(),guru_id:$("#guruList").val(),content:$("#editor_id").val()},
            success:function (data) {

            }
        })
    }
</script>
<div class="col-sm-10">
    <div class="page-header">
        <h2><strong>文章管理</strong></h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a>文章列表</a></li>
        <li><a onclick="addArticle()">添加文章</a></li>
    </ul>
    <div class="panel">
        <table id="articleTable"></table>
        <div id="articlePager" style="width: auto;height: 50px"></div>
    </div>
</div>


<div class="modal fade" id="kind" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">文章信息</h4>
            </div>
            <div class="modal-body">
                <form role="form" enctype="multipart/form-data" id="kindfrm">
                    <input name="id" type="hidden" id="formid">
                    <div class="form-group">
                        <label for="name">标题</label>
                        <input type="text" class="form-control" name="title" id="name" placeholder="请输入名称">
                    </div>
                    <div class="form-group">
                        <label for="inputfile">封面上传</label>
                        <input type="file" name="articleImg" id="inputfile">
                    </div>
                    <div class="form-group">
                        <label for="name">所属上师</label>
                        <select class="form-control" id="guruList" name="guru_id" >

                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editor_id" class="label label-important">内容</label>
                            <textarea id="editor_id" name="content" style="width:700px;height:300px;">

                            </textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer" id="modal_foot">
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
