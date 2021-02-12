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
    <script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
    <script src="${ctx}/js/cookie_util.js"></script>
    <script src="${ctx}/js/notice.js"></script>
    <script src="${ctx}/my/BeastRequest.js"></script>
    <script src="${ctx}/my/Table.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            //获取用户详情
            new BeastRequest(ctx, modelUrl + "/userDetail", {id:operationId}, false,
                function (data) {
                    if(data && data.validate && data.data){
                        $("#userName").html(data.data['userName']);
                    }
                }, function () {
                    console.log("error");
                }).ajaxPost();

            //加载角色页列表
            loadTable({userRole: {userId: operationId},pagination:{limit:8}});
        });

        function loadTable(param){
            let headClass = 'table_header_inner';
            let columns = [{key:'name',title:'名称'},{key:'remarks',title:'备注'}];
            let operationHav = {width: '70px',
                menus:[{title: '删除',funName: 'deleteUserRole',class: 'delete'}]};
            let optionHav = {headClass: headClass, columns: columns, operation: operationHav};
            let operationNo = {width: '70px',
                menus:[{title: '添加',funName: 'addUserRole',class: 'edit'}]};
            let optionNo = {headClass: headClass, columns: columns, operation: operationNo};

            new Table('#listHav', optionHav, param, ctx, "/userRoleManage/selectPageHav").renderingTable();
            new Table('#listHavNo', optionNo, param, ctx, "/userRoleManage/selectPageHavNo").renderingTable();
        }

        function addUserRole(roleId){

            if(roleId && roleId != null && roleId != ""){

                new BeastRequest(ctx, "/userRoleManage/addUserRole", {userId: operationId, roleId: roleId}, false,
                    function (data) {
                        if(data && data.validate){
                            loadTable({userRole: {userId: operationId},pagination:{limit:8}});
                        }
                    }, function () {
                        console.log("error");
                    }).ajaxPost();
            }else {
                tips("角色编号不能为空");
            }

        }

        function deleteUserRole(roleId){

            if(roleId && roleId != null && roleId != ""){

                new BeastRequest(ctx, "/userRoleManage/deleteUserRole", {userId: operationId, roleId: roleId}, false,
                    function (data) {
                        if(data && data.validate){
                            loadTable({userRole: {userId: operationId},pagination:{limit:8}});
                        }
                    }, function () {
                        console.log("error");
                    }).ajaxPost();
            }else {
                tips("角色编号不能为空");
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
    <div class="header_title">${title}：<span id="userName"></span></div>

    <!-- 用户角色表格内容展示-->
    <div style="display: flex">

        <div style="flex: 1; margin-right: 10px;">
            <span style="font-size: large;">未拥有角色</span>
            <div id="listHavNo" style="margin-top: 5px;"></div>
        </div>

        <span></span>
        <div style="flex: 1; margin-left: 10px;">
            <span style="font-size: large;">已拥有角色</span>
            <div id="listHav" style="margin-top: 5px;"></div>
        </div>
    </div>

    <div class="button_wrap">
        <span class="button_text cancel" onclick="back();">返回</span>
    </div>
</div>
</body>
</html>