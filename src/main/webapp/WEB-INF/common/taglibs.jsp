<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="/WEB-INF/tlds/c.tld" %>
<%@ taglib prefix="fn"  uri="/WEB-INF/tlds/fn.tld"  %>
<%@ taglib prefix="fmt" uri="/WEB-INF/tlds/fmt.tld"  %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="${pageContext.request.getAttribute('title')}"/>
<c:set var="model" value="${pageContext.request.getAttribute('model')}"/>
<c:set var="opreationId" value="${pageContext.request.getAttribute('opreationId')}"/>
<script type="text/javascript">
    var ctx = "${ctx}";
    var model = "${model}";
    var title = "${title}";
    var opreationId = "${opreationId}";
    var currentUser = null;

    document.onreadystatechange = function() {
        if(document.readyState == "complete"){
            //登陆状态全局校验
            loginedCheck();
        }
    }

    function loginedCheck(){
        var request = new Rquest(ctx, "/user/getUser", null,
            function (data) {
                if(data != null){
                    //window.location.href = ctx
                }
            }, function () {
                //window.location.href = ctx
            });
        request.ajaxpost();
    }

    //全局提示
    function tips(msg) {
        new NoticeJs({
            text: msg,
            position: 'topCenter',
            animation: {
                open: 'animated bounceIn',
                close: 'animated bounceOut'
            }
        }).show();
    }
</script>