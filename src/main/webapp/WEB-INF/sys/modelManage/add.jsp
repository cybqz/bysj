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
    <link rel="stylesheet" type="text/css" href="${ctx}/my/add.css">
 	<script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
    <script src="${ctx}/js/cookie_util.js"></script>
    <script src="${ctx}/js/notice.js"></script>
    <script src="${ctx}/my/BeastRequest.js"></script>
    <script type="text/javascript">
        function save() {
            let param = {
                title: $("#title").val(),
                navbar: $("#navbar").val(),
                url: $("#url").val(),
                sort: $("#sort").val(),
                remarks: $("#remarks").val(),
            };
            let request = new BeastRequest(ctx, modelUrl + "/save", param, false,
                function (data) {
                    if(data && data.validate){
                        window.location.href = ctx + modelUrl + "/";
                    }
                }, function () {
                    console.log("error");
                });
            request.ajaxPost();
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
        <span class="button_text save" onclick="save();">保存</span>
    </div>
</div>
</body>
</html>
