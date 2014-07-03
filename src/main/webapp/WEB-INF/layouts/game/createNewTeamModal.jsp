<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!private String email;%>

<%-- <% email = request.getParameter("email");
	String[] splitEmail = email.split("@");%> --%>

<div class="modal" id="createNewTeamModal" tabindex="-1" role="dialog"
	aria-labelledby="welcomeModalLabel" aria-hidden="false"
	data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">

				<h3 class="modal-title" id="welcomeModalLabel">Welcome</h3>
			</div>

			<div class="modal-body">

				<div class="center">
					<p>Please choose your team name:</p>
				</div>

				<div class="col-md-7">
					<form id="newTeamForm" accept-charset="UTF-8" method="post"
						name="modal-form" class="form-horizontal" data-remote="true">

						<div class="col-md-8">
							<input type="text" class="form-control" name="teamName"
								id="teamName" value="" placeholder="Team Name..." />

						</div>

					</form>
				</div>
			</div>

			<div class="modal-footer">

				<button id="submitNewTeam" type="submit" class="btn btn-primary"
					id="save">Proceed</button>

			</div>



			<script>
				$('#submitNewTeam').on('click', function(e) {
					// We don't want this to act as a link so cancel the link action
					e.preventDefault();

					// Find form and submit it
					$('#newTeamForm').submit();
				});

				// Since we want both pressing 'Enter' and clicking the button to work
				// We'll subscribe to the submit event, which is triggered by both

				$('#newTeamForm')
						.on(
								'submit',
								function() {
									//Serialize the form and post it to the server
									var teamName = document
											.getElementById("teamName").value;
									alert("The team name entered: " + teamName);

									$
											.ajax({
												type : "POST",
												url : "createNewTeam.html",
												data : JSON.stringify({
													"teamName" : teamName
												}),
												contentType : "application/json; charset=UTF-8",
												success : function(data) {
													alert('Data: ' + data);
													var parsedDataJSON = $
															.parseJSON(data);
													if (parsedDataJSON.status == "OK") {
														alert('status OK');
														
														if (parsedDataJSON.teamCreated == "true") {
															alert ('TEAM PERSISTED');
															// Hide the modal
															$("#createNewTeamModal")
																	.modal(
																			'hide');
														} else {
															alert ('ERROR WHEN CREATING NEW TEAM');
														}

													} else if (parsedDataJSON.status == "sessionExpired") {
														alert('Session Expired');
													} else if (parsedDataJSON.status == "error") {
														alert(parsedDataJSON.teamNameError);
													}
												},
												error : function() {
													alert('Error occured when calling hasTeam POST!');
												}
											});

									return false;
								});
			</script>


		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->