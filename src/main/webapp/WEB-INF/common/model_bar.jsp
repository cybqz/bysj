<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/left-side-menu.css">
<link rel="stylesheet" type="text/css" href="${ctx}/font/iconfont.css">
<script src="${ctx}/js/jquery.js"></script>
<script src="${ctx}/js/jquery.slimscroll.min.js"></script>
<script src="${ctx}/js/left-side-menu.js"></script>
<div class="left-side-menu" >
    <div class="lsm-expand-btn">
        <div class="lsm-mini-btn">
            <label>
                <input type="checkbox" checked="checked">
                <svg viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
                    <circle cx="50" cy="50" r="30" />
                    <path class="line--1" d="M0 40h62c18 0 18-20-17 5L31 55" />
                    <path class="line--2" d="M0 50h80" />
                    <path class="line--3" d="M0 60h62c18 0 18 20-17-5L31 45" />
                </svg>
            </label>
        </div>
    </div>
    <div class="lsm-container">
        <div class="lsm-scroll" >
            <div class="lsm-sidebar" style="width: 205px;">
                <ul>
                    <%--<li class="lsm-sidebar-item">
                        <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon_1"></i><span>用户管理</span><i class="my-icon lsm-sidebar-more"></i></a>
                        <ul>
                            <li><a href="javascript:;"><span>地爆天星</span></a></li>
                            <li><a class="active"  href="javascript:;"><span>无线月读</span></a></li>
                            <li><a href="javascript:;"><span>一乐拉面</span></a></li>
                            <li class="lsm-sidebar-item">
                                <a href="javascript:;"><i class="my-icon lsm-sidebar-icon "></i><span>二级菜单11</span><i class="my-icon lsm-sidebar-more"></i></a>
                                <ul>
                                    <li><a href="javascript:;"><span>地爆天星</span></a></li>
                                    <li><a href="javascript:;"><span>无线月读</span></a></li>
                                </ul>
                            </li>

                            <li class="lsm-sidebar-item">
                                <a href="javascript:;"><i class="my-icon lsm-sidebar-icon "></i><span>二级菜单22</span><i class="my-icon lsm-sidebar-more"></i></a>
                                <ul >
                                    <li><a href="javascript:;"><span>血继限界</span></a></li>
                                    <li><a href="javascript:;"><span>秽土转生</span></a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>--%>
                    <li class="lsm-sidebar-item">
                        <a href="javascript:;" onclick="toUserManage();"><i class="my-icon lsm-sidebar-icon icon_2"></i><span onclick="toUserManage();">用户管理</span></a>
                    </li>
                    <li class="lsm-sidebar-item">
                        <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon_2"></i><span>访问控制</span><i class="my-icon lsm-sidebar-more"></i></a>
                        <ul>
                            <li><a href="javascript:;"><span>角色管理</span></a></li>
                            <li><a href="javascript:;"><span>权限管理</span></a></li>
                            <li><a href="javascript:;"><span>权限编辑</span></a></li>
                        </ul>
                    </li>
                    <li class="lsm-sidebar-item">
                        <a href="javascript:;" onclick="toModel();"><i class="my-icon lsm-sidebar-icon icon_2"></i><span>示例管理</span></a>
                    </li>
                </ul>
            </div>
        </div>
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
