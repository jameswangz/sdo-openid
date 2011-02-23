<%@page import="com.snda.sdo.openid.infrastructure.guice.EnvironmentModules"%>
<%@page import="com.snda.sdo.openid.application.util.URLEncoders"%>
<%@ page pageEncoding="utf-8"%>

<html>
<head>
<title>SDO OpenID Sign In Page</title>
</head>
<body>

<%
	String errorURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/"; 
	errorURL = URLEncoders.encodeByUTF_8(errorURL);
	String returnURL = URLEncoders.encodeByUTF_8(EnvironmentModules.get("return.url"));
%>

<div>
	<iframe src="openidsignin?returnURL=<%= returnURL %>&errorURL=<%= errorURL %>&cssURL=style%2Fbase.css&appId=tangguo" 
			width="100%" height="200" frameBorder="0"></iframe>
</div>
<div>
	<hr></hr>
	<h1>Main page goes here</h1>
</div>
</body>
</html>