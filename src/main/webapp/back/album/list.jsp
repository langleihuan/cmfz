<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        // 创建父级JqGrid表格
        $("#table").jqGrid(
            {
                url :"${pageContext.request.contextPath}/album/queryAll",
                datatype : "json",
                height : 500,
                colNames : [ 'id', '专辑名',"封面","作者","评分","朗读者","创建时间","简介","章节数" ],
                colModel : [
                    {name : 'id',},
                    {name : 'name',align:"center",editable:true,editrules:{required:true}},
                    {name : 'cover',align:"center",formatter:function (data) {
                            return "<img style='width: 100%' src='"+data+"'/>"
                        },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}},
                    {name : 'author',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'star',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'announcer',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'create_time',align:"center",editoptions:{required:true}},
                    {name : 'introduce',align:"center",editable:true,editoptions:{required:true}},
                    {name : 'chapter_num',align:"center",editable:true,editoptions:{required:true}},
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
                caption : "Subgrid案例",
                autowidth:true,
                styleUI:"Bootstrap",
                editurl:"${pageContext.request.contextPath}/album/edit",
                // 重写创建子表格方法
                subGridRowExpanded : function(subgrid_id, row_id) {
                    addTable(subgrid_id,row_id);
                },
                // 删除表格方法
                subGridRowColapsed : function(subgrid_id, row_id) {

                }
            });
        $("#table").jqGrid('navGrid', '#page',
        {edit : true, add : true, del : true,
            edittext:"编辑",addtext:"添加",deltext:"删除"
        },
        {
            afterSubmit:function (response,postDate) {
                var albumID = response.responseJSON.albumId;
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/album/upload",
                    datatype:"json",
                    type:"post",
                    data:{albumId:albumID},
                    // 指定的上传input框的id
                    fileElementId:"cover",
                    success:function (data) {
                        // 输出上传成功
                        // jqgrid重新载入
                        $("#table").trigger("reloadGrid");
                    },
                    closeAfterEdit:true
                })
                return postDate;
            }
        },
            { closeAfterEdit:true,
                afterSubmit:function (response,postDate) {
                    var albumID = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/upload",
                        datatype:"json",
                        type:"post",
                        data:{albumId:albumID},
                        // 指定的上传input框的id
                        fileElementId:"cover",
                        success:function (data) {
                            // 输出上传成功
                            // jqgrid重新载入
                            $("#table").trigger("reloadGrid");
                        },

                    })
                    return postDate;
                }
            });
    })
    // subgrid_id 下方空间的id  row_id 当前行id数据
    function addTable(subgrid_id,row_id) {

        // 声明子表格|工具栏id
        var subgridTable = subgrid_id + "table";
        var subgridPage = subgrid_id + "page";
        // 根据下方空间id 创建表格及工具栏
        $("#"+subgrid_id).html("<table id='"+subgridTable+"'></table><div style='height: 50px' id='"+subgridPage+"'></div>")
        // 子表格JqGrid声明
        $("#"+subgridTable).jqGrid({
            url : "${pageContext.request.contextPath}/chapter/queryAll?album_id="+row_id,
            datatype : "json",
            colNames : [ 'id', '名字',"时长","大小","专辑ID","操作"],
            colModel : [
                {name : "id",},
                {name : "name",editable:true},
                // {name : "audiopath", edittype: "file",align:'center',editable:true,width:500,
                //     editoptions: {enctype: "multipart/form-data"},
                //     formatter: function (value, option, rows) {
                //         return "<audio controls='controls'  style='width:80%;height:10s%;' " +
                //             "src='" + rows.audiopath + "'/>";
                //     }},
                {name : "time",},
                {name : "size",},
                {name : "album_id",editable:true},
                {
                    name: "audiopath", align: "center", formatter(data) {
                        var result = "";
                        result += "<a href='javascript:void(0)' onclick=\"playAudio('" + data + "')\" class='btn btn-lg' title='播放'><span class='glyphicon glyphicon-play-circle'></span></a>";
                        // result += "<a href='javascript:void(0)' onclick=\"downloadAudio('" + data + "')\" class='btn btn-lg' title='下载'><span class='glyphicon glyphicon-download'></span></a>";
                        return result;
                    }
                },
            ],
            rowNum : 20,
            pager : "#"+subgridPage,
            sortname : 'num',
            sortorder : "asc",
            height : '100%',
            styleUI:"Bootstrap",
            editurl: "edit",
            autowidth:true,
            editurl:"${pageContext.request.contextPath}/chapter/edit?album_id="+row_id,
        });
        $("#" + subgridTable).jqGrid('navGrid',
            "#" + subgridPage, {
                edit : true, add : true, del : true,
                edittext:"编辑",addtext:"添加",deltext:"删除"
            },
            { closeAfterEdit:true,
                afterSubmit:function (response,postDate) {
                    var chapterID = response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/chapter/upload",
                        datatype:"json",
                        type:"post",
                        data:{chapterId:chapterID},
                        // 指定的上传input框的id
                        fileElementId:"audiopath",
                        success:function (data) {
                            // 输出上传成功
                            // jqgrid重新载入
                            $("#table").trigger("reloadGrid");
                        },

                    })
                    return postDate;
                }
            },
            { closeAfterEdit:true,
                afterSubmit:function (response,postDate) {
                    var chapterID = response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/chapter/upload",
                        datatype:"json",
                        type:"post",
                        data:{chapterId:chapterID},
                        // 指定的上传input框的id
                        fileElementId:"audiopath",
                        success:function (data) {
                            // 输出上传成功
                            // jqgrid重新载入
                            $("#table").trigger("reloadGrid");
                        },

                    })
                    return postDate;
                }
            }
            );
    }
    function playAudio(data) {
        $("#myModal").modal("show");
        $("#myaudio").attr("src",data);
    }
    <%--function downloadAudio(data) {--%>
    <%--    location.href = "${pageContext.request.contextPath}/chapter/download?audiopath="+data;--%>
    <%--}--%>
</script>
<div class="col-sm-10">
<table id="table"></table>
<div style="height: 50px" id="page"></div>
</div>

<div class="col-sm-10" style="text-align: center;">
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <audio src="" id="myaudio" controls class="col-sm-10 col-sm-offset-1" style="position:fixed;bottom:0;left:0;" ></audio>
    </div>
</div>
