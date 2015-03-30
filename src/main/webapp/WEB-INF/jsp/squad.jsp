<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="/hb/css/bootstrap.min.css">

<link rel="stylesheet" href="/hb/css/center.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="/hb/css/bootstrap-theme.min.css"> 

<link rel="stylesheet" href="/hb/css/img-fix.css">
<link rel="stylesheet" href="/hb/css/metro-title.css">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
	<script src="/hb/scripts/bootstrap/3.1.1/bootstrap.js"></script>
	<script src="/hb/scripts/conflict.js"></script>
	<%! /* Session attributes */
		private String email;%>

	<% email = (String) session.getAttribute("email");
		if (email == null) { %>
	  		<tiles:insertAttribute name="modalSessionExpired"/>
	  	<% }%>
	 
	<tiles:insertAttribute name="body" />

</body>
</html>