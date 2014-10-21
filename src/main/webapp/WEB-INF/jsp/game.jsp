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
<!-- <link rel="stylesheet" href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"> -->
<!-- <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css"> -->

<link rel="stylesheet" href="/hb/css/img-fix.css" />
<link rel="stylesheet" href="/hb/css/metro-title.css" />
<link rel="stylesheet" href="/hb/css/fonts.css" />
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








<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title"></tiles:getAsString></title>
</head>
<body id="theBody" style="background-image: url('/hb/img/bgblue.jpg'); background-attachment: fixed; background-size: 100% 100%;">
	<!-- Latest compiled and minified JavaScript -->

	<script type="text/javascript" src="/hb/scripts/jquery-2.1.1.min.js"></script>
	
	<script type="text/javascript" src="/hb/scripts/jquery.color.2.1.2.js"></script>

<!-- 	<script
		src="/hb/scripts/bootstrap.min.js"></script> -->
	
	<script src="/hb/scripts/jquery.animateDialog.js"></script>
	<script type="text/javascript" src="/hb/scripts/jquery.ui-min.js"></script>



	
</div>

	<%! /* Session attributes */
		private String email;%>

	<% email = (String) session.getAttribute("email");
		if (email == null) { %>
	<tiles:insertAttribute name="modalSessionExpired" />
	<% }%>

	<%-- <tiles:insertAttribute  name="postRegistrationModal"/>   	 --%>

	<!-- Ajax call to check whether an user has already chosen a team name  -->
	<!-- <script>
		$.ajax({
			type : "POST",
			url : "hasTeam.html",
			data : "",
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				alert('Data: ' + data);
				var parsedDataJSON = $.parseJSON(data);
				if (parsedDataJSON.status == "OK") {
					alert('status OK');
					if (parsedDataJSON.hasTeam == "true") { alert ('User already has a team.'); }
					else {
						alert('User does not have a team yet');
						
						/* TODO show a modal with team name registration and post it as ajax request  */
						$("#createNewTeamModal")
						.modal(
								'show');
						
					}
				} else if (parsedDataJSON.status == "sessionExpired")
					alert('Session Expired');
			},
			error : function() {
				alert('Error occured when calling hasTeam POST!');
			}
		});
	</script> -->

	<%-- <tiles:insertAttribute name="modalPlayerDetails"/> --%>

	<br>
	<br>
	<tiles:insertAttribute name="header" />


	<tiles:insertAttribute name="body" />


	<%-- <div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<div class="col-md-4">
				<h2>Heading</h2>
				<p>
					<% if (email != null) { %>
					<%= email %>
					<%}; %>


				</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						&raquo;</a>
				</p>
			</div>
			<div class="col-md-4">
				<h2>Heading</h2>
				<p>Donec id elit non mi porta gravida at eget metus. Fusce
					dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
					ut fermentum massa justo sit amet risus. Etiam porta sem malesuada
					magna mollis euismod. Donec sed odio dui.</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						&raquo;</a>
				</p>
			</div>
			<div class="col-md-4">
				<h2>Heading</h2>
				<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,
					egestas eget quam. Vestibulum id ligula porta felis euismod semper.
					Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum
					nibh, ut fermentum massa justo sit amet risus.</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						&raquo;</a>
				</p>
			</div>
		</div>
		<hr>
	</div> --%>
	<!-- /container -->
	<!-- <form class="navbar-form navbar-left">
		<button type="button" data-toggle="modal" data-target="#myModal"
			class="btn btn-primary">Register</button>
	</form> -->
	<%-- <tiles:insertAttribute name="footer" /> --%>



	<script src="/hb/scripts/jquery.knob.js"></script>
	<script src="/hb/scripts/spin.js"></script>
	<script src="/hb/scripts/spin.min.js"></script>

	<script src="/hb/scripts/Chart.js"></script>
	<script src="/hb/scripts/doughnutit.js"></script>

	
<!-- 
	<script>
		$("#myDoughnut").doughnutit({
			// Data for a full doughnut
			dnData : {
				value : 100,
				color : "#819596"
			},
			// Size of the doughnut
			dnSize : 200,
			// Percentage of the inner cutout
			dnInnerCutout : 60,
			// Animate it or not?
			dnAnimation : true,
			// Amount of animation steps
			dnAnimationSteps : 60,
			// Type of animation
			dnAnimationEasing : 'linear',
			// Stroke between the sections of the doughnut
			dnStroke : false,
			// Show the text inside the doughnut
			dnShowText : false,
			// Text inside the doughnut
			dnText : '',
			// Font for the text inside the doughnut
			dnFontFamily : 'Arial',
			// Style for the text inside
			dnFontStyle : 'normal',
			// Size in px for the text inside
			dnFontSize : '190px',
			// Color for the text inside
			dnFontColor : "#666",
			// Offset to stay in center
			dnFontOffset : 35,
			// Start angle for the first slice of the doughnut
			dnStartAngle : -90,
			// Animation in counter clockwise - HAS ISSUES
			dnCounterClockwise : false,
			// If an object is passed it generates a right canvas with some text
			dnRightCanvas : false,
			// If an object is passed it generates a left canvas with some text
			dnLeftCanvas : false
		});
	</script> -->

</body>
</html>