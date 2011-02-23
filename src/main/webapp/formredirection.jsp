<%@ page pageEncoding="utf-8"%>
<%@ page import="com.snda.sdo.openid.interfaces.servlet.Sessions" %>
<%@ page import="com.snda.sdo.openid.domain.model.OpenIdProfile" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>OpenID HTML FORM Redirection</title>
</head>
<body onload="document.forms['openid-form-redirection'].submit();">
    <form name="openid-form-redirection" action="${message.OPEndpoint}" method="post" accept-charset="utf-8">
        <c:forEach var="parameter" items="${message.parameterMap}">
        <input type="hidden" name="${parameter.key}" value="${parameter.value}"/>
        </c:forEach>
        <div>请稍侯, 正在跳转到 <%= Sessions.openIdProfileOf(session).provider().domain() %>...</div>
        <!--  
        <button type="submit">Continue...</button>
        -->
    </form>
</body>
</html>
