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
    <script type="text/javascript">
        $(document).ready(function () {

            let request = new BeastRequest(ctx, modelUrl + "/detail", {id:operationId}, false,
                function (data) {
                    if(data && data.validate && data.data){
                        $("#name").val(data.data['name']);
                        $("#userName").val(data.data['userName']);
                        $("#phone").val(data.data['phone']);
                        $("#email").val(data.data['email']);
                        $("#password").val(data.data['password']);
                        $("input[name='sex'][value="+data.data['sex']+"]").attr("checked",true);
                        $("#introduce").val(data.data['introduce']);
                    }
                }, function () {
                    console.log("error");
                });
            request.ajaxPost();
        });
        function doUpdate() {
            let param = {
                id: operationId,
                name: $("#name").val(),
                userName: $("#userName").val(),
                phone: $("#phone").val(),
                email: $("#email").val(),
                password: $("#password").val(),
                sex: $("input[name='sex']:checked").val(),
                introduce: $("#introduce").val(),
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
