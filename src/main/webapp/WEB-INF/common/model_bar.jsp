<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    #model_bar{
        height: 100%;
        background: #0F5F7E;
        color: white;
    }
    .tabText{
        height: 70px;
        padding: 0 30px;
        font-size: 18px;
        line-height: 70px;
        cursor: pointer;
        text-align: center;
        cursor: pointer;
    }
    .tabText:hover{
        color: #00a0e9;
        background: white;
    }
</style>
<div id="model_bar">
    <div class="tabText">
        <span onclick="toModel();">示例管理</span>
    </div>
    <div class="tabText">
        <span onclick="toUserManage();">用户管理</span>
    </div>
    <div class="tabText">
        <span onclick="toPermissionManage();">权限管理</span>
    </div>
</div>
<script>
    function toModel(){
        window.location.href = ctx + "/model/";
    }

    function toUserManage(){
        window.location.href = ctx + "/userManage/";
    }

    function toPermissionManage() {
        window.location.href = ctx + "/PermissionManage/";
    }
</script>
