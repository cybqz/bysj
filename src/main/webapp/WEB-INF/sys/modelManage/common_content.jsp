<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="header_title">${title}：</div>
<div class="list">
    <span class="title">标题：</span>
    <input id="title" type="text" class="content">
</div>
<div class="list">
    <span class="title">导航栏：</span>
    <input id="navbar" type="text" class="content">
</div>
<div class="list">
    <span class="title">路径：</span>
    <input id="url" type="text" class="content">
</div>
<div class="list">
    <span class="title">排序：</span>
    <input id="sort" type="number" min="1" class="content">
</div>
<div class="list">
    <span class="radio_wrap">父栏目：</span>
    <input id="parentId" type="text" class="content">
</div>
<!-- 表格内容展示-->
<div id="list"></div>
<div class="list">
    <span class="radio_wrap">备注：</span>
    <textarea id="remarks" type="text" class="content"></textarea>
</div>


