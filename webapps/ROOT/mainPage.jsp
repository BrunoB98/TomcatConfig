<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="objectUtils.*"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<%
String message = "";
MyDatabase md = new MyDatabase();
ResultSet r = md.selectAllProducts();
String title = "Media Shopping BB";
String url, val, dom, name, pass;
dom = request.getScheme()+ "://" + request.getServerName() + ":" + String.valueOf(request.getServerPort()) + request.getContextPath();
url = request.getRequestURI();
val = request.getParameter("Go");
name = request.getParameter("name");
pass = request.getParameter("password");
ArrayList<Product> v = new ArrayList<Product>();
Product p;
try {
	if(r.first()) {
		while (r.next()) {
			p = new Product(r.getString("name"), r.getDouble("price"), r.getString("description"));
			p.setImg("notAvailable.jpg");
			v.add(p);
		}
	}
} catch (Exception e) {
	out.println(e.getMessage());
}
%>
<html>
<head>
<meta charset="UTF-8">
<style>
th, td {
  border: 1px solid black;
  padding: 30px;
}
table.center {
  margin-left: auto;
  margin-right: auto;
  border-spacing: 20px;
}
body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  backgroud-color: #e0ffff
}

.topnav {
  overflow: hidden;
  background-color: #333;
}

.topnav a {
  float: left;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.topnav a:hover {
  background-color: #ddd;
  color: black;
}

.topnav a.active {
  background-color: #5DA5D9;
  color: white;
}
</style>
<title><%= title %></title>
</head>


<%
if(name == null || "".equals(name) || pass == null || "".equals(pass) || md.isPresent(name, pass)) {%>
<body>
<div class="topnav">
  <a href="#home">Home</a>
  <a href="<%= dom %>/index.jsp">Logout</a>
  <a href="#contact">Orders</a>
  <a href="#about">About</a>
</div>
<form action="<%= url%>" method=POST>
<table class=center>
<caption>
<h1>BB shopping</h1>
</caption>
<%
int count = 0, i;
boolean exit = true;
out.println("L'accesso ha avuto successo.");
while(exit) { 
	i=0;
	out.println("<tr>");
	while(i < 5) {
		if ((count*5 + i) < v.size()) {
			p = v.get(count*5 + i); %>
			<td>
			<img src="<%= p.getImg() %>" align="center" width="200" height="200" /><br>
			<%= p.getName() %> <br>
			<%= p.getPrice() %> <br>
			<%= p.getDescription() %> <br>
			<input type="submit" name="Go" value="Add">
			<input type="submit" name="Go" value="Remove">
			</td>
<% 		} else { exit = false; }
		i++; 
	}
	out.println("</tr>");
 	count++; 
} 

} else {
	String redirectURL = dom + "/index.jsp";
    response.sendRedirect(redirectURL);
}%>
</table>
</form>
</body>
</html>