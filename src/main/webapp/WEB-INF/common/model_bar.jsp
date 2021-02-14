<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/left-side-menu.css">
<link rel="stylesheet" type="text/css" href="${ctx}/font/iconfont.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/noticejs.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/styles.css">
<link rel="stylesheet" type="text/css" href="${ctx}/my/Search.css">
<link rel="stylesheet" type="text/css" href="${ctx}/my/Table.css">

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
            </div>
        </div>
    </div>
</div>
<script>
    /**
     * 模块跳转
     */
    function toModelIndex(_this){
        window.location.href = ctx + $(_this).attr("url") + "/";
    }
</script>
