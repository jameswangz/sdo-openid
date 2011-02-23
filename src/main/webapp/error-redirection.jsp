<%@page import="com.snda.sdo.openid.domain.model.AppConfig"%>
<%@ page pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.snda.sdo.openid.interfaces.servlet.Requests" %>
<%@ page import="com.snda.sdo.openid.interfaces.servlet.Sessions" %>

<script type="text/javascript">
<%
	boolean auto = Sessions.autoOf(session);

	if (!auto) {
		List<String> errors = Requests.errorMessagesOf(request);
		for (String error : errors) {
			out.println("alert('" + error + "')");
		}		
	}
	
	out.println("window.top.location.href = '" + Sessions.appConfigOf(session).errorURL() + "';");
%>
</script>

