<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div class="modal" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="false">&times;</button>
				<h3 class="modal-title" id="myModalLabel">Register</h3>
			</div>

			<div class="modal-body row">
				<div class="col-md-7">
					<form id="registrationForm" accept-charset="UTF-8"
						data-remote="true" method="post" name="modal-form"
						class="form-horizontal">

						<!-- form stuff goes here -->
						<div id="emailError" class="form-group">
						<div class="col-xs-8">
						<font color="#C11B17"> <p id="emailErrorP">&nbsp;</p></font>
						</div>
							<div class="col-xs-8">
								<input type="text" class="form-control" name="email" id="email"
									value="" placeholder="Email" /> <span id="emailErrorSpan"></span>
							</div>
						</div>

						<div id="passwordError" class="form-group">
						
							<div class="col-xs-8">
								<input type="password" class="form-control" id="password"
									name="password" value="" placeholder="Password" /> <span
									id="passwordErrorSpan"></span>
							</div>
						</div>




						<!-- <div class="form-group has-error has-feedback">
						<input type="text" class="form-control" id="inputError2">
						<span class="glyphicon glyphicon-remove form-control-feedback"></span>
					</div> -->
					</form>
				</div>

				<div class="col-md-5">
				<p>&nbsp;</p>
					<font id="passwordPolicyFont" size="1%"><p>
							Password Policy: <br> At least 6 chars <br> Contains at
							least one digit <br> Contains at least one lower alpha char
							<br> Does not contain space, tab, etc.
						</p></font>
				</div>



			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					Close</button>
				<button id="submitRegistration" type="submit"
					class="btn btn-primary" id="save">Proceed</button>

			</div>

			<!-- <div class='modal-body'>
				<form id="modal-form" accept-charset="UTF-8" action="/tagging"
					data-remote="true" method="post">
					<input name="something" value="Some value" />
				</form>
			</div>

			<div class='modal-footer'>
				<a id="modal-form-submit" class='btn btn-primary' href="#">Submit</a>
			</div> -->

			<script>
				$('#submitRegistration').on('click', function(e) {
					// We don't want this to act as a link so cancel the link action
					e.preventDefault();

					

					// Find form and submit it
					$('#registrationForm').submit();
				});

				// Since we want both pressing 'Enter' and clicking the button to work
				// We'll subscribe to the submit event, which is triggered by both

				$('#registrationForm')
						.on(
								'submit',
								function() {
									//Serialize the form and post it to the server

									$
											.post(
													"userRegistration.html",
													$(this).serialize(),
													function(text) {

														var parsedJSON = $
																.parseJSON(text);
														/* var obj = JSON.parse(text); */

														var success = parsedJSON.success;

														if (success == "fail") {
															var failReason = parsedJSON.failReason;
															if (failReason == "emailRegex") {
																document
																		.getElementById("emailError").className = "form-group has-error has-feedback";
																document
																		.getElementById("emailErrorSpan").className = "glyphicon glyphicon-remove form-control-feedback";

																/* Set the password field as not validated yet */
																document
																		.getElementById("passwordError").className = "form-group";
																document
																		.getElementById("passwordErrorSpan").className = "";
																$("p#emailErrorP").text('Wrong email format.');
															} else if (failReason == "passwordRegex") {

																/* Set the email field as successfuly validated */
																document
																		.getElementById("emailError").className = "form-group has-success has-feedback";

																document
																		.getElementById("emailErrorSpan").className = "glyphicon glyphicon-ok form-control-feedback";

																/* Set the password field as not validated */
																document
																		.getElementById("passwordError").className += " has-error has-feedback";
																document
																		.getElementById("passwordErrorSpan").className = "glyphicon glyphicon-remove form-control-feedback";
																$("p#emailErrorP").html('&nbsp;');
																document.getElementById("passwordPolicyFont").color = "red";															} else if (failReason == "emailExists") {
																document
																		.getElementById("emailError").className = "form-group has-error has-feedback";
																document
																		.getElementById("emailErrorSpan").className = "glyphicon glyphicon-remove form-control-feedback";

																/* Set the password field as not validated yet */
																document
																		.getElementById("passwordError").className = "form-group";
																document
																		.getElementById("passwordErrorSpan").className = "";
																 $("p#emailErrorP").text('Email Already Taken.');
															}
														} else { // Success
															alert('SUCCESS!');
															// Hide the modal
															 $("#myModal").modal(
																	'hide');
														}
														

														/* document
																.getElementById("email").value = parsedJSON.text; */

														alert('parsedJson email = '
																+ parsedJSON.text);

														// When this executes, we know the form was submitted

														// To give some time for the animation, 
														// let's add a delay of 200 ms before the redirect
														/* var delay = 200;
														setTimeout(
																function() {
																	window.location.href = 'successUrl.html';
																}, delay); */

														// Hide the modal
														/* $("#my-modal").modal(
																'hide'); */

													});

									// Stop the normal form submission
									return false;
								});
			</script>

			<!-- <script>
			$(#'submitRegistration').on('click', function(e) {
				e.preventDefault();
				
				// Submit the form
				$('#registrationForm').submit();
			});
			</script>


			<script>
				$(function() {
					$('#registrationForm').on('submit', function(e) {
						e.preventDefault();
						$.ajax({
							url : 'notifications/subscribe/',
							type : 'POST',
							data : $('#registrationForm').serialize(),
							success : function(data) {
								$('#responsestatus').val(data);
								$('#subscription-confirm').modal('show');
							}
						});
					});
				});
			</script> -->
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->