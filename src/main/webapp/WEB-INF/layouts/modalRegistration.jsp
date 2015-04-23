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
								<font color="#C11B17">
									<p id="emailErrorP">&nbsp;</p>
								</font>
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
					</form>
				</div>

				<div class="col-md-5">
					<p>&nbsp;</p>
					<font id="passwordPolicyFont">Password Policy:</font><br>
					<font id="atLeastSixCharsFont"> At least 6 chars </font><br>
					<font id="atLeastOneDigitFont">Contains at least one digit</font><br>
					<font id="atLeastOneLowerAlphaCharFont">Contains at least one lower alpha char</font><br>
					<font id="doesNotContainTabsEtcFont">Does not contain space, tab, etc.</font>
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

			<script src="/hb/scripts/registration/registration.js"></script>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->