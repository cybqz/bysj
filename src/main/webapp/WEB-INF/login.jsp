<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common/taglibs.jsp"%>

<!doctype html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>${title}</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/styles.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/animate.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/noticejs.css" />
 	<script src="${ctx}/js/jquery.js"></script>
    <script src="${ctx}/js/pintuer.js"></script>
    <script src="${ctx}/js/cookie_util.js"></script>
	<script src="${ctx}/js/notice.js"></script>
	<script src="${ctx}/my/BeastRequest.js"></script>
</head>
<body>
	<div class="jq22-container" style="padding-top:10px">
		<div class="login-wrap">
			<div class="login-html">
				<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">登录</label>
				<input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab">注册</label>
				<div class="login-form">
					<div class="sign-in-htm">
						<div class="group">
							<label for="user" class="label">用户名</label>
							<input id="user" type="text" class="input">
						</div>
						<div class="group">
							<label for="pass" class="label">密码</label>
							<input id="pass" type="password" class="input" data-type="password">
						</div>
						<div class="group">
							<input id="login" type="button" class="button" value="登录">
						</div>
						<div class="hr"></div>
					</div>
					<div class="register-htm">
						<div class="group">
							<label for="user" class="label">用户名</label>
							<input id="user1" name="user1" type="text" class="input">
						</div>
						<div class="group">
							<label for="pass" class="label">密码</label>
							<input id="pass1" name="pass1" type="password" class="input" data-type="password"/>
						</div>
						<div class="group">
							<label for="pass" class="label">确认密码</label>
							<input id="rep_pass1" type="password" class="input" data-type="password">
						</div>
						<div class="group">
							<input id="register" type="button" class="button" value="注册"/>
						</div>
						<div class="hr"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">

		$(document).ready(function () {

			new BeastRequest(ctx, "/user/getUser", null, false,
					function (data) {
						toIndexPage(data);
					}, null).ajaxPost();

			/**
			 * 注册
			 */
			$("#register").click(function () {

				//获取请求参数
				let user = $("#user1").val();
				if(user==null || user==""){
					tips("请输入用户名");
					return false;
				}
				let pass = $("#pass1").val();
				if(pass==null || pass==""){
					tips("请输入密码");
					return false;
				}
				let rep_pass =$("#rep_pass1").val();
				if(rep_pass==null || rep_pass==""){
					tips("请确认密码");
					return false;
				}
				if(pass!=rep_pass){
					tips("密码输入不一致");
					return false;
				}
				new BeastRequest(ctx, "/register/register", {"userName":user,"password":pass}, false,
						function (data) {
							toIndexPage(data);
						}, function () {
							console.log("error");
						}).ajaxPost();
			});

			/**
			 * 登陆
			 */
			$("#login").click(function () {

				//获取请求参数
				let user =$("#user").val();
				if(user==null || user==""){
					tips("请输入用户名");
					return false;
				}
				let pass = $("#pass").val();
				if(pass==null || pass==""){
					tips("请输入密码");
					return false;
				}
				//检查参数格式
				//发送Ajax请求
				new BeastRequest(ctx, "/login/login", {"userName":user,"password":pass}, false,
						function (data) {
							toIndexPage(data);
						}, function () {
							console.log("error");
						}).ajaxPost();
			});
		});

		function toIndexPage(data) {
			if(data && data.validate){
				window.location.href = ctx + indexModelUrl;
			}
		}
	</script>
</body>
</html>