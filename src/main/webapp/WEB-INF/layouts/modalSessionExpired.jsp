<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%! private String email; %>

<% email = (String) session.getAttribute("email"); %>

<div class="modal" id="modalSessionExpired" tabindex="-1" role="dialog"
	aria-labelledby="myModalSessionExpired" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title" id="myModalSessionExpired">Session Expired</h3>
			</div>

			

				<div class="col-md-5">
					<p>&nbsp;</p>
					<font color="red"><p>
							The Session has expired.
						</p></font>
				</div>



			
			<div class="modal-footer">
				<button id="submitRedirect" type="submit"
					class="btn btn-danger" id="save">Redirect</button>

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
			
			setTimeout(
					function() {
						window.location.href = 'main.html';
					}, 3000);
			
			
				$('#submitRedirect').on('click', function(e) {
					// We don't want this to act as a link so cancel the link action
					e.preventDefault();
		
					setTimeout(
							function() {
								window.location.href = 'main.html';
							}, 10);
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