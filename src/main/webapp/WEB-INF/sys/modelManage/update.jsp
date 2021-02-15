<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>

<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/noticejs.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/my/Table.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/my/add.css">
    <script type="text/javascript">
        $(document).ready(function () {

            new BeastRequest(ctx, modelUrl + "/detail", {id:operationId}, false,
                function (data) {
                    if(data && data.validate && data.data){
                        $("#title").val(data.data['title']);
                        $("#navbar").val(data.data['navbar']);
                        $("#url").val(data.data['url']).attr('disabled','disabled');
                        $("#sort").val(data.data['sort']);
                        $("#remarks").val(data.data['remarks']);
                        $("#parentId").val(data.data['parentId']);
                        $("#iconClass").val(data.data['iconClass']);
                    }
                }, function () {
                    console.log("error");
                }).ajaxPost();

            //加载列表数据并渲染
            loadTable({sysModel: {},pagination:{limit:tableSizeDefault}});
        });
        function doUpdate() {
            let param = {
                id: operationId,
                sort: $("#sort").val(),
                title: $("#title").val(),
                navbar: $("#navbar").val(),
                remarks: $("#remarks").val(),
                parentId: $("#parentId").val(),
                iconClass: $("#iconClass").val()
            };
            let request = new BeastRequest(ctx, modelUrl + "/doUpdate", param, false,
                function (data) {
                    if(data && data.validate){
                        window.location.href = ctx + modelUrl + "/";
                    }
                }, function (xhr, textStatus, errorThrown) {
                    console.log(xhr);
                    console.log(textStatus);
                    console.log(errorThrown);
                });
            request.ajaxPost();
        }

        function loadTable(param) {
            let columns = [
                            {key:'title',title:'标题'},
                            {key:'navbar',title:'导航栏'},
                            {key:'remarks',title:'备注'}
                          ];
            let operation = {width: '70px',
                menus:[{title: '选择',funName: 'selectParent',class: 'edit'}]};
            let option = {columns: columns, operation: operation};
            new Table('#list', option, param, ctx, modelUrl + "/parentPage").renderingTable();
        }

        function selectParent(sysModelId){
            $("#parentId").val(sysModelId);
        }

        function cancel() {
            window.location.href = ctx + modelUrl + "/";
        }
    </script>
</head>
<body>
<!-- 引入头部 -->
<%@include file="../../common/header.jsp"%>

<div class="wrap">
    <%@include file="common_content.jsp"%>
    <div class="button_wrap">
        <span class="button_text cancel" onclick="cancel();">取消</span>
        <span class="button_text save" onclick="doUpdate();">保存</span>
    </div>
</div>
</body>
</html>
