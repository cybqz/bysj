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
                        <a href="javascript:;"><i class="my-icon lsm-sidebar-icon icon_1"></i><span>测试管理</span><i class="my-icon lsm-sidebar-more"></i></a>
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
                        <a href="javascript:;" index= "1" url= "/userManage" onclick="toModelIndex(this);">
                            <i class="my-icon lsm-sidebar-icon icon_1"></i>
                            <span>用户管理</span>
                        </a>
                    </li>
                    <li class="lsm-sidebar-item">
                        <a href="javascript:;" index= "2">
                            <i class="my-icon lsm-sidebar-icon icon_2"></i>
                            <span>访问控制</span>
                            <i class="my-icon lsm-sidebar-more"></i>
                        </a>
                        <ul>
                            <li><a href="javascript:;" parentAIndex = "2" url= "/permissionManage" onclick="toModelIndex(this);"><span>权限管理</span></a></li>
                            <li><a href="javascript:;" parentAIndex = "2" url= "/roleManage" onclick="toModelIndex(this);"><span>角色管理</span></a></li>
                        </ul>
                    </li>
                    <li class="lsm-sidebar-item">
                        <a href="javascript:;" index= "3" url="/model" onclick="toModelIndex(this);">
                            <i class="my-icon lsm-sidebar-icon icon_3"></i>
                            <span>示例管理</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){

        //设置当前所在导航栏状态为打开
        updateMenuState();
    });

    /**
     * 模块跳转
     */
    function toModelIndex(_this){
        window.location.href = ctx + $(_this).attr("url") + "/";
    }

    /**
     * 设置当前所在导航栏状态为打开
     */
    function updateMenuState(){
        setTimeout(function (){

            //二级菜单处理
            let currTag = $("a[url='" + modelUrl + "']");
            let parentAIndex = $(currTag).attr("parentAIndex");
            if(parentAIndex && parentAIndex != null){

                let target = $("a[index='" + parentAIndex + "']");
                $(target).trigger("click");
                $(currTag).css("background", "#6e809c");
            }else{

                //一级菜单处理
                $(currTag).parent().addClass("lsm-sidebar-show");
            }
        }, 300);
    }
</script>
