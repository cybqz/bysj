<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="header_title">${title}：</div>
<div class="list">
    <span class="title">姓名：</span>
    <input id="name" type="text" class="content">
</div>
<div class="list">
    <span class="title">性别：</span>
    <div class="radio_wrap">
        女
        <input name="sex" type="radio" value="0" class="radio_text">
    </div>
    <div class="radio_wrap">
        男
        <input name="sex" type="radio" value="1" class="radio_text">
    </div>
</div>
<div class="list">
    <span class="title">年龄：</span>
    <input id="age" type="number" class="content">
</div>
<div class="list">
    <span class="title">备注：</span>
    <input id="description" type="text" class="content">
</div>
