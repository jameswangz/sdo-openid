<%@ page import="java.util.List" %>
<%@ page import="com.snda.sdo.openid.interfaces.servlet.Requests" %>

<ul>
<%
	List<String> errors = Requests.errorMessagesOf(request);
	for (String error : errors) {
		out.println("<li><font color='red'>" + error + "</font></li>");		
	}
%>
</ul>