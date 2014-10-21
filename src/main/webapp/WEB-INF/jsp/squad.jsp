<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<link rel="stylesheet" href="/hb/css/center.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css"> 

<link rel="stylesheet" href="/hb/css/img-fix.css">
<link rel="stylesheet" href="/hb/css/metro-title.css">






<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body id="theBody">
	<!-- Latest compiled and minified JavaScript -->

	<script src="https://code.jquery.com/jquery-2.1.0.min.js"></script>
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.js"></script>

	

	<%! /* Session attributes */
		private String email;%>

	<% email = (String) session.getAttribute("email");
		if (email == null) { %>
	  		<tiles:insertAttribute name="modalSessionExpired"/>
	  	<% }%>
	 
	<tiles:insertAttribute name="body" />


	



</body>
</html>