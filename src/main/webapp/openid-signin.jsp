<%@page import="com.snda.sdo.openid.interfaces.servlet.Sessions"%>
<%@ page pageEncoding="utf-8"%>

<html>
<head>
<title>SDO OpenID Sign In Page</title>
<link type="text/css" href="<%= Sessions.appConfigOf(session).cssURL() %>" rel="stylesheet" />
<script type="text/javascript">

function signinWithoutUsername(op) {
	window.top.location.href = 'consumer?op=' + op;
}

function showUsernameField(op) {
	document.getElementById('op').value = op;
	document.getElementById('provider_label').innerText = '请输入您的 ' + op + ' 用户名';
	document.getElementById('openid_username').style.display = '';
	document.getElementById('signin_button').style.display = '';
	document.getElementById('openid_username').focus();
}

function signinWithUsername() {
	var op = document.getElementById('op').value;
	var openid_username = document.getElementById('openid_username').value;
	window.top.location.href = 'consumer?op=' + op + '&openid_username=' + openid_username;
}

</script>
</head>
<body>


<div align="left" class="other_id">
	<p class="txt3">使用合作网站账号登录 ：</p>	
	<ul class="icon_list">
		<li><a href="javascript:signinWithoutUsername('yahoo')"><img src="images/yahoo_25x25.gif" />Yahoo</a></li>
		<li class="last_li"><a href="javascript:signinWithoutUsername('google')"><img src="images/google_25x25.gif" />Google</a></li>
		<!--  
		<li><a href="javascript:showUsernameField('myopenid')"><img src="images/myopenid.png" border="0" /></a></li>
		<li><a href="javascript:showUsernameField('my0511')"><img src="images/my0511.png" border="0" /></a></li>
		-->
	</ul>
	<div>
		<input type="hidden" id="op" />
		<label id="provider_label"></label><br />
		<input type="text" id="openid_username" style="display: none;"/><br />
		<input type="button" id="signin_button" value="登录" onclick="signinWithUsername()" style="display: none;"/><br />
	</div>
</div>
</body>
</html>