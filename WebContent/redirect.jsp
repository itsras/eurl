<%@ page session="true"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.Iterator"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title dir="ltr">Encrypt URL</title>
</head>
<body>
<center>Redirecting url.... <%
	String url = (String) request.getAttribute("url");
	url = (!url.startsWith("http://")) ? "http://" + url : url;
	if (url != null)
	{
		//request.getRequestDispatcher(url).forward(request, response);
		response.sendRedirect(url);
 	}
 %>
</center>
</body>
