<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="objectUtils.*"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<%!
String message = "";
MyDatabase md = new MyDatabase();
int c = 0;

protected boolean controlInput(String name, String pass, String age) {
	c++;
	//controllo che nessuno dei tre parametri sia nullo
	if(name == null || "".equals(name) || pass == null || "".equals(pass) || age == null || "".equals(age)) {
		message = "nome utente, password o età nulli";
		return false;
	}
	//controllo che il parametro age sia un valore numerico maggiore o uguale a 18
	try {
		if(Integer.valueOf(age) < 18) {
		message = "età inferiore a 18 anni";
		return false;
		}
	} catch (Exception e) {
		message = "età inserita non valida";
		return false;
	}
	//la funzione inseririrà l'utente nel database e, nel caso in cui sia già presente restituirà false
	if(md.insertUser(name, pass, Integer.valueOf(age)))
		return true;
	message = "Utente già esistente";
	return false;
	
}
%>

<% 
String title = "Registrati su BB shopping";
String url, name, pass, age, dom;
url = request.getRequestURI();
name = request.getParameter("n");
pass = request.getParameter("p");
age = request.getParameter("age");
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
<form action="<%=url%>" method=POST>
Inserisci nome utente: <input type=text name="n" value="<%=(name != null)?name:""%>"> <br> <br>
Inserisci password:    <input type=password name="p" value="<%=(pass != null)?pass:""%>"> <br> <br>
Inserisci età:         <input type=text name="age" value="<%=(age != null)?age:""%>"> <br> <br>
<input type=submit name="reg" value="register">
</form>
</td>
</tr>
</table>
<%
boolean control = controlInput(name, pass, age);
if(control) {
	out.println("<h1>Utente registrato correttamente.</h1>"); %>
	<button onclick="location.href='<%= dom %>/mainPage.jsp'">Vai al sito</button> <br>
<%} else { 
	if (c != 1)
		out.println(message);
} %>

</body>
</html>