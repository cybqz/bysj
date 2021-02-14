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
                        $("#name").val(data.data['name']).attr("disabled", "disabled");
                        $("#userName").val(data.data['userName']).attr("disabled", "disabled");
                        $("#phone").val(data.data['phone']).attr("disabled", "disabled");
                        $("#email").val(data.data['email']).attr("disabled", "disabled");
                        $("#password").val(data.data['password']).attr("disabled", "disabled");
                        $("input[name='sex'][value="+data.data['sex']+"]").attr("checked",true).attr("disabled", "disabled");
                        $("#introduce").val(data.data['introduce']).attr("disabled", "disabled");
                    }
                }, function () {
                    console.log("error");
                });
            request.ajaxPost();
        });
        
        function back() {
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
        <span class="button_text cancel" onclick="back();">返回</span>
    </div>
</div>
</body>
</html>
