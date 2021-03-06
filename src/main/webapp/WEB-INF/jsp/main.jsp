<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="/hb/css/bootstrap.min.css">
	
<link rel="stylesheet" href="/hb/css/reset.css">


<!-- Optional theme -->
<link rel="stylesheet"
	href="/hb/css/bootstrap-theme.min.css">
<!-- Text Animation stylesheet -->
<link rel="stylesheet" href="/hb/css/animationText.css">





<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title"></tiles:getAsString></title>
</head>
<body>
	<!-- Latest compiled and minified JavaScript -->

	<!-- <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script> -->
	<script type="text/javascript" src="/hb/scripts/jquery-2.1.1.min.js"></script>
	<script src="/hb/scripts/bootstrap/3.1.1/bootstrap.js"></script>
	
	<tiles:insertAttribute name="modalRegistration"></tiles:insertAttribute>
	
	<tiles:insertAttribute name="createNewTeamModal"/>

	<tiles:insertAttribute name="header" />

	<tiles:insertAttribute name="body" />
	
	<br><br>



</body>
</html>