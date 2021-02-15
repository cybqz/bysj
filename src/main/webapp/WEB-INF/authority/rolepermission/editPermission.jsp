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

            //获取用户详情
            new BeastRequest(ctx, modelUrl + "/roleDetail", {id:operationId}, false,
                function (data) {
                    if(data && data.validate && data.data){
                        $("#roleName").html(data.data['name']);
                    }
                }, function () {
                    console.log("error");
                }).ajaxPost();

            //加载角色页列表
            loadTable({rolePermission: {roleId: operationId},pagination:{limit:tableSizeDefault}});
        });

        function loadTable(param){
            let headClass = 'table_header_inner';
            let columns = [{key:'name',title:'名称'},{key:'remarks',title:'备注'}];
            let operationHav = {width: '70px',
                menus:[{title: '删除',funName: 'deleteRolePermission',class: 'delete'}]};
            let optionHav = {headClass: headClass, columns: columns, operation: operationHav};
            let operationNo = {width: '70px',
                menus:[{title: '添加',funName: 'addRolePermission',class: 'edit'}]};
            let optionNo = {headClass: headClass, columns: columns, operation: operationNo};

            new Table('#listHav', optionHav, param, ctx, "/rolePermissionManage/selectPageHav").renderingTable();
            new Table('#listHavNo', optionNo, param, ctx, "/rolePermissionManage/selectPageHavNo").renderingTable();
        }

        function addRolePermission(permissionId){

            if(permissionId && permissionId != null && permissionId != ""){

                new BeastRequest(ctx, "/rolePermissionManage/addRolePermission", {roleId: operationId, permissionId: permissionId}, false,
                    function (data) {
                        if(data && data.validate){
                            loadTable({rolePermission: {roleId: operationId},pagination:{limit:tableSizeDefault}});
                            return;
                        }
                    }, function () {
                        console.log("error");
                    }).ajaxPost();
            }else {
                tips("权限编号不能为空");
            }
        }

        function deleteRolePermission(permissionId){

            if(permissionId && permissionId != null && permissionId != ""){

                new BeastRequest(ctx, "/rolePermissionManage/deleteRolePermission", {roleId: operationId, permissionId: permissionId}, false,
                    function (data) {
                        if(data && data.validate){
                            loadTable({rolePermission: {roleId: operationId},pagination:{limit:tableSizeDefault}});
                            return;
                        }
                    }, function () {
                        console.log("error");
                    }).ajaxPost();
            }else {
                tips("权限编号不能为空");
            }
        }

        function back() {
            window.location.href = ctx + modelUrl + "/";
        }
    </script>
</head>
<body>
<!-- 引入头部 -->
<%@include file="../../common/header.jsp"%>

<div class="wrap" style="width: 80%;">
    <div class="header_title">${title}：<span id="roleName"></span></div>

    <!-- 用户角色表格内容展示-->
    <div style="display: flex">

        <div style="flex: 1; margin-right: 10px;">
            <span style="font-size: large;">未拥有权限</span>
            <div id="listHavNo" style="margin-top: 5px;"></div>
        </div>

        <span></span>
        <div style="flex: 1; margin-left: 10px;">
            <span style="font-size: large;">已拥有权限</span>
            <div id="listHav" style="margin-top: 5px;"></div>
        </div>
    </div>

    <div class="button_wrap">
        <span class="button_text cancel" onclick="back();">返回</span>
    </div>
</div>
</body>
</html>