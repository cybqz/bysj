<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("isShowSideMenu",true);
%>
<%@include file="../../common/taglibs.jsp"%>

<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
</head>
<body>
    <!-- 引入头部 -->
    <%@include file="../../common/header.jsp"%>

    <div class="wrap">
        <div  id="tab_wrap">
            <!-- 引入模块导航栏 -->
            <%@include file="../../common/model_bar.jsp"%>
        </div>
        <div  id="content">
            <!-- 表格的公共信息展示 -->
            <div id="top_text">
                <span class="message_wrap">
                   ${model}共 &nbsp;<span id="count" class="color_blue">0</span>&nbsp; 条
                </span>
                <span class="search_wrap">
                    <input id="name" class="searchInput_120" placeholder="请输入姓名" type="text"/>
                    <input id="userName" class="searchInput_120" placeholder="请输入用户名" type="text"/>
                    <input id="phone" class="searchInput_120" placeholder="请输入手机号" type="text"/>
                    <input id="dateTime" class = "searchInput_275" placeholder="请选择创建时间" type="text"/>
                    <span class="searchBtn">搜索</span>
                </span>
            </div>
            <!-- 表格内容展示-->
            <div id="list"></div>
        </div>
    </div>
</body>
<script type="text/javascript">
    window.onload=function modify(){
        let s=document.body.clientHeight-70;
        document.getElementById("tab_wrap").style.height=s;
    }

    $(document).ready(function () {
        //加载列表数据并渲染
        loadTable({user: {},pagination:{limit:tableSizeDefault}});

        //获取总条数
        new BeastRequest(ctx, modelUrl + "/count", {}, true,
            function (data) {
                if(data && data.validate && data.data){
                    $("#count").html(data.data);
                }
            }, function () {
                console.log("error");
            }).ajaxPost();

        //渲染时间搜索框
        laydate.render({
            elem: '#dateTime'
            ,type: 'datetime'
            ,theme: '#0F5F7E'
            ,range: true
        });

        //搜索事件
        $(".searchBtn").click(function () {
            let user = {};
            $.each($(".search_wrap").children(), function (k, v){
                let id = $(v).attr("id");
                let val = $(v).val();
                if(Util.isNotBlankStr(id) && Util.isNotBlankStr(val)){
                    user[id] = val;
                }
            });
            loadTable({user: user,pagination:{limit:tableSizeDefault}});
        });
    })

    function loadTable(param) {
        let columns = [{key:'name',title:'姓名'},
                      {key:'userName',title:'用户名'},
                      {key:'sex',title:'性别',format:function (value) {return value == 1?'男':'女';}},
                      {key:'phone',title:'手机号'},
                      {key:'email',title:'邮箱'},
                      {key:'introduce',title:'介绍'}];
        let operation = {width: '100px',
                         menus:[{title: '设置角色',funName: 'editRole',class: 'edit'}]};
        let option = {columns: columns, operation: operation};

        new Table('#list', option, param, ctx, modelUrl + "/page").renderingTable();
    }

    function editRole(userId){
        window.location.href = ctx + modelUrl + "/editRole?id=" + userId;
    }

    function pagination_to_first() {
    }
</script>
</html>
