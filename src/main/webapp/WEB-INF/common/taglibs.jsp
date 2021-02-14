<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="${pageContext.request.getAttribute('title')}"/>
<c:set var="model" value="${pageContext.request.getAttribute('model')}"/>
<c:set var="modelUrl" value="${pageContext.request.getAttribute('modelUrl')}"/>
<c:set var="operationId" value="${pageContext.request.getAttribute('operationId')}"/>
<c:set var="isShowSideMenu" value="${pageContext.request.getAttribute('isShowSideMenu')}"/>
<c:set var="parentPageName" value="${pageContext.request.getAttribute('parentPageName')}"/>

<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/jquery.slimscroll.min.js"></script>
<script src="${ctx}/js/pintuer.js"></script>
<script src="${ctx}/js/cookie_util.js"></script>
<script src="${ctx}/js/notice.js"></script>
<script src="${ctx}/laydate/laydate.js"></script>
<script src="${ctx}/my/BeastRequest.js"></script>
<script src="${ctx}/my/SideMenu.js"></script>
<script src="${ctx}/my/Table.js"></script>
<script src="${ctx}/my/Util.js"></script>

<script type="text/javascript">
    let ctx = "${ctx}";
    let model = "${model}";
    let title = "${title}";
    let modelUrl = "${modelUrl}";
    let sideMenu = null;
    let operationId = "${operationId}";
    let isShowSideMenu = "${isShowSideMenu}";
    let parentPageName = "${parentPageName}";
    let signedIndUserId = null;
    let userSysModelList = null;

    document.onreadystatechange = function() {
        if(document.readyState == "complete") {
            //登陆状态全局校验
            loginSuccessCheck();

        }
    }

    function loginSuccessCheck(){
        new BeastRequest(ctx, "/user/getSignedIndUser", null, false,
            function (data) {
                if(data.data != null && data.data.userName) {
                    signedIndUserId = data.data.id;
                    $("#login_info").text("欢迎：" + data.data.userName);

                    //加载用户系统模块列表
                    loadUserSysModelList();
                    if (null == userSysModelList || userSysModelList.length == 0) {
                        tips("当前用户需授权系统模块权限");
                        return
                    }

                    //获取默认模块URL
                    getDefaultIndexModel(userSysModelList);
                    if (null != isShowSideMenu && "" != isShowSideMenu && isShowSideMenu) {
                        setMenuList(userSysModelList);
                    } else if (parentPageName && parentPageName === "login") {
                        window.location.href = ctx + modelUrl;
                    }
                }
            }, function (xhr, textStatus, errorThrown) {
                console.log(xhr);
                console.log(textStatus);
                console.log(errorThrown);
                //window.location.href = ctx
            }).ajaxPost();
    }

    /**
     * 加载用户系统模块列表
     */
    function loadUserSysModelList() {
        new BeastRequest(ctx, "/userSysModelManage/selectListHav", {userSysModel: {userId: signedIndUserId}}, false,
            function (data) {
                if(data && data.validate && data.data){
                    userSysModelList = data.data;
                }
            }, function () {
                console.log("error");
            }).ajaxPost();
    }

    /**
     * 获取默认进入的模块URL
     * @param data
     */
    function getDefaultIndexModel(data){
        if(null == modelUrl || modelUrl === ""){
            if(data[0].children && data[0].children.length > 0){
                modelUrl = data[0].children[0].url;
            }else {
                modelUrl = data[0].url;
            }
        }
    }

    /**
     * 渲染导航栏
     * @param data
     */
    function setMenuList(data){
        sideMenu = new SideMenu('.lsm-sidebar', data);
        sideMenu.render();
        sideMenu.addEvent();
        sideMenu.openLevelTwo();
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