<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="objectUtils.*"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<% 
String title = "Benvenuto su BB Shopping";
String url, url2, name, pass, but, dom;
url = request.getRequestURI();
dom = request.getScheme()+ "://" + request.getServerName() + ":" + String.valueOf(request.getServerPort()) + request.getContextPath();
%>
<html>
<head>
<meta charset="UTF-8">
<title><%= title %></title>
</head>
<body style="background-color:#e0ffff;">
<table>
<tr>
<td><IMG SRC="logo.jpg" ALIGN="LEFT" VSPACE="20" HSPACE="20"/></td>
<td>
<h1><%= title %></h1>
<form action="<%= dom %>/mainPage.jsp" method=POST >
Username: <input type=text name="name" value=""> <br><br>
Password: <input type=password name="password" value=""> <br><br>
<input type=submit name="button" value="login">
</form>
<br>
<button onclick="location.href='<%= dom %>/register.jsp'">Register</button> <br>
</td>
</tr>
</table>
</body>
</html>