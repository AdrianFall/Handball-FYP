<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Handball Manager</a>
		</div>
		<div class="navbar-collapse collapse">

			<form class="navbar-form navbar-right">
				<button type="button" data-toggle="modal" data-target="#myModal"
					class="btn btn-primary">Register</button>
			</form>



			<form id="loginForm" action="login.html"
				class="navbar-form navbar-left">

				<div class="form-group">
					<input id="emailField" type="text" placeholder="Email"
						class="form-control">
				</div>

				<div class="form-group">
					<input id="passwordField" type="password" placeholder="Password"
						class="form-control">

				</div>





				<div class="form-group">
					<button id="submitLogin" type="submit" class="btn btn-success">Sign
						in</button>
				</div>






			</form>

			<!-- Ajax call to check whether an user has already chosen a team name  -->


			<script>
				// Since we want both pressing 'Enter' and clicking the button to work
				// We'll subscribe to the submit event, which is triggered by both

				$('#submitLogin').on('click', function(e) {
					// We don't want this to act as a link so cancel the link action
					e.preventDefault();

					// Find form and submit it
					$('#loginForm').submit();
				});

				// Since we want both pressing 'Enter' and clicking the button to work
				// We'll subscribe to the submit event, which is triggered by both

				$('#loginForm')
						.on(
								'submit',
								function() {
									var email = document
											.getElementById("emailField").value;

									var password = document
											.getElementById("passwordField").value;

									$
											.ajax({
												type : "POST",
												url : "login.html",
												data : JSON.stringify({
													"email" : email,
													"password" : password
												}),
												contentType : "application/json; charset=UTF-8",
												success : function(data) {
													/* alert('Data: ' + data); */
													var parsedDataJSON = $
															.parseJSON(data);
													if (parsedDataJSON.status == "OK") {
														alert('CREDENTIALS OK');
														
														
														
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
																	if (parsedDataJSON.hasTeam == "true") { 
																		alert ('User already has a team.');
																		setTimeout(
																				function() {
																					window.location.href = 'game.html';
																				}, 20);
																	}
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
													
														/* setTimeout(
																function() {
																	window.location.href = 'game.html';
																}, 20); */

													} else
														alert('Wrong credentials.');
												},
												error : function() {
													alert('Error occured!');
												}
											});
									/*  */

									return false;
								});
			</script>


		</div>
		<!--/.navbar-collapse -->
	</div>
</div>

