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
<title><tiles:getAsString name="title"></tiles:getAsString></title>
</head>
<body id="theBody">
	<!-- Latest compiled and minified JavaScript -->

	<script src="https://code.jquery.com/jquery-2.1.0.min.js"></script>
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.js"></script>

	<tiles:insertAttribute name="createNewTeamModal"/>

	<%! /* Session attributes */
		private String email;%>

	<% email = (String) session.getAttribute("email");
		
	  
	  %>
	  
	<%-- <tiles:insertAttribute  name="postRegistrationModal"/>   	 --%>

	<!-- Ajax call to check whether an user has already chosen a team name  -->
	<script>
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
	</script>



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


	<br>
	<br>

	<tiles:insertAttribute name="footer" />



</body>
</html>