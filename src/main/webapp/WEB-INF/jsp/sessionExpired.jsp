<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 
<%! private String email; %>

<% email = (String) session.getAttribute("email"); %> --%>

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
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->