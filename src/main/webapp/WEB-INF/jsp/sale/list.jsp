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
    <link rel="stylesheet" type="text/css" href="${ctx}/my/list.css">
 	<script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
    <script src="${ctx}/js/cookie_util.js"></script>
    <script src="${ctx}/js/notice.js"></script>
    <script src="${ctx}/my/Request.js"></script>
    <script src="${ctx}/my/Table.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //加载列表数据并渲染
            load(null);

            //获取总条数
            var request = new Rquest(ctx, "/sale/count", null,
                function (data) {
                    if(data && data.validate && data.data){
                        $("#count").html(data.data);
                    }
                }, function () {
                    console.log("error");
                });
            request.ajaxpost();
        })
        
        function add() {
            window.location.href = ctx + "/sale/add"
        }

        function load(param) {
            var request = new Rquest(ctx, "/sale/page", param,
                function (data) {
                    if(data && data.validate && data.data){
                        var column = [{buyingPeopleName:'姓名'},{phone:'电话'},{description:'描述'},
                            {buyName:'购买名称'},{price:'价格'},{category:'类别'},{profit:'利润'}];
                        new Table('#list', column, data.data).renderingTable();
                    }
                }, function () {
                    console.log("error");
                });
            request.ajaxpost();
        }
        function update(id){

            window.location.href = ctx + "/sale/update?id=" + id;
        }
        function remove(id){

            var request = new Rquest(ctx, "/sale/delete", {id:id},
                function (data) {
                    tips(data.msg);
                    load(null);
                }, function () {
                    console.log("error");
                });
            request.ajaxpost();
        }
    </script>
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
             <span>
               ${model}共 <span id="count" class="color_blue">0</span> 条
            </span>
            <span class="add_wrap">
                 <span id="add" class="add" onclick="add()">
                    添加
                 </span>
            </span>
        </div>
        <!-- 表格内容展示-->
        <div id="list"></div>

    </div>

</div>
</body>
<script>
    window.onload=function modify(){
        var s=document.body.clientHeight-70;
        document.getElementById("tab_wrap").style.height=s;
    }
</script>
</html>
