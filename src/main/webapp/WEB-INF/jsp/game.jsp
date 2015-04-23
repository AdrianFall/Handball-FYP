<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="/hb/css/bootstrap.min.css">


<link rel="stylesheet" href="/hb/css/center.css">

<!-- Optional theme -->
<link rel="stylesheet" href="/hb/css/bootstrap-theme.min.css">

<link rel="stylesheet" href="/hb/css/img-fix.css" />
<link rel="stylesheet" href="/hb/css/metro-title.css" />
<link rel="stylesheet" href="/hb/css/squad/table.css" />
<link rel="stylesheet" href="/hb/css/tables.css" />
<link rel="stylesheet" href="/hb/css/jquery.animateDialog.css" />
<link rel="stylesheet" href="/hb/css/jquery.animateDialog.junk.css" />
<link rel="stylesheet" href="/hb/css/test.css" />
<link rel="stylesheet" href="/hb/css/fade.css" />
<link rel="stylesheet" href="/hb/css/animate.css" />
<link rel="stylesheet" href="/hb/css/spinner.css" />
<link rel="stylesheet" href="/hb/css/font-awesome.css" />
<link rel="stylesheet" href="/hb/css/autosubs.css">
<link rel="stylesheet" href="/hb/css/animationLoader.css">
<link rel="stylesheet" href="/hb/semantic-ui/semantic.css">
<link rel="stylesheet" href="/hb/css/vertical_timeline_style.css">
<link rel="stylesheet" href="/hb/css/leaderboards/table.css">







<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title"></tiles:getAsString></title>
</head>
<body id="theBody" style="background-image: url('/hb/img/bgblue.jpg'); background-attachment: fixed; background-size: 100% 100%;">
	<!-- Latest compiled and minified JavaScript -->

	<script type="text/javascript" src="/hb/scripts/jquery-2.1.1.min.js"></script>
	
	<script type="text/javascript" src="/hb/scripts/jquery.color.2.1.2.js"></script>
	
	<script src="/hb/semantic-ui/semantic.js"></script>

	
	<script src="/hb/scripts/jquery.animateDialog.js"></script>
	<script type="text/javascript" src="/hb/scripts/jquery.ui-min.js"></script>

	




	<%! /* Session attributes */
		private String email;%>

	<% email = (String) session.getAttribute("email");
		if (email == null) { %>
	<tiles:insertAttribute name="modalSessionExpired" />
	<% }%>
	<br><br>
	<tiles:insertAttribute name="body" />

	<script src="/hb/scripts/jquery.knob.js"></script>
	<script src="/hb/scripts/spin.js"></script>
	<script src="/hb/scripts/spin.min.js"></script>

	<script src="/hb/scripts/Chart.js"></script>
	<script src="/hb/scripts/doughnutit.js"></script>

	<div id="modalMatchSpace"></div>
	<div id="modalLiveMatchSpace"></div>
</body>
</html>