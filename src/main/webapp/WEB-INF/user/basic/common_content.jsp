<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="header_title">${title}：</div>
<div class="list">
    <span class="title">姓名：</span>
    <input id="name" type="text" class="content">
</div>
<div class="list">
    <span class="title">密码：</span>
    <input id="password" type="text" class="content">
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
    <span class="radio_wrap">用户名：</span>
    <input id="userName" type="text" class="content">
</div>
<div class="list">
    <span class="radio_wrap">手机号：</span>
    <input id="phone" type="text" class="content">
</div>
<div class="list">
    <span class="radio_wrap">邮箱：</span>
    <input id="email" type="text" class="content">
</div>
<div class="list">
    <span class="radio_wrap">地址：</span>
    <textarea id="address" type="text" class="content"></textarea>
</div>
<div class="list">
    <span class="radio_wrap">介绍：</span>
    <textarea id="introduce" type="text" class="content"></textarea>
</div>

