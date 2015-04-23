$('#submitRegistration').on('click', function(e) {
	// We don't want this to act as a link so cancel the link action
	e.preventDefault();

	// Find form and submit it
	$('#registrationForm').submit();
});

function clearCurrentHighlights() {
	$("p#emailErrorP").text('');
	$('#emailError').removeClass('form-group has-error has-success has-feedback');
	$('#passwordError').removeClass('has-error has-feedback');
	document.getElementById("passwordPolicyFont").color = "black";
	document.getElementById("atLeastSixCharsFont").color = "black";
	document.getElementById("atLeastOneDigitFont").color = "black";
	document.getElementById("atLeastOneLowerAlphaCharFont").color = "black";
	document.getElementById("doesNotContainTabsEtcFont").color = "black";
}


// Since we want both pressing 'Enter' and clicking the button to work
// We'll subscribe to the submit event, which is triggered by both

$('#registrationForm').on('submit',function() {
	// Call method to clear the current highlights
	clearCurrentHighlights();

	// Serialize the form and post it to the server
	
	$.post("userRegistration.html", $(this).serialize(), function(text) {

		var parsedJSON = $.parseJSON(text);
		/* var obj = JSON.parse(text); */

		var success = parsedJSON.success;

		if (success == "fail") {
			var failReason = parsedJSON.failReason;
			if (failReason == "emailRegex") {
				document.getElementById("emailError").className = "form-group has-error has-feedback";
				document.getElementById("emailErrorSpan").className = "glyphicon glyphicon-remove form-control-feedback";
				
				/*
				 * Set the password field as not
				 * validated yet
				 */
				document.getElementById("passwordError").className = "form-group";
				document.getElementById("passwordErrorSpan").className = "";
				$("p#emailErrorP").text('Wrong email format.');
			} else if (failReason == "passwordRegex") {

				/*
				 * Set the email field as
				 * successfuly validated
				 */
				document.getElementById("emailError").className = "form-group has-success has-feedback";

				document.getElementById("emailErrorSpan").className = "glyphicon glyphicon-ok form-control-feedback";

				/*
				 * Set the password field as not
				 * validated
				 */
				document.getElementById("passwordError").className += " has-error has-feedback";
				document.getElementById("passwordErrorSpan").className = "glyphicon glyphicon-remove form-control-feedback";
				$("p#emailErrorP").html('&nbsp;');
				document.getElementById("passwordPolicyFont").color = "red";
				// Obtain the specific error
				var passwordRegexFailReason = parsedJSON.passwordRegexFailReason
				// Highlight the specific error
				if (passwordRegexFailReason == "doesNotContainSixChars") 
					document.getElementById("atLeastSixCharsFont").color = "red";
				else if (passwordRegexFailReason == "doesNotContainAtLeastOneDigit")
					document.getElementById("atLeastOneDigitFont").color = "red";
				else if (passwordRegexFailReason == "doesNotContainAtLeastOneLowerAlphaChar")
					document.getElementById("atLeastOneLowerAlphaCharFont").color = "red";
				else if (passwordRegexFailReason == "doesContainBlankSpace")
					document.getElementById("doesNotContainTabsEtcFont").color = "red";
			} else if (failReason == "emailExists") {
				document.getElementById("emailError").className = "form-group has-error has-feedback";
				document.getElementById("emailErrorSpan").className = "glyphicon glyphicon-remove form-control-feedback";

				/*
				 * Set the password field as not
				 * validated yet
				 */
				document.getElementById("passwordError").className = "form-group";
				document.getElementById("passwordErrorSpan").className = "";
				$("p#emailErrorP").text('Email Already Taken.');
			}
		} else { // Success
			alert('Account Created.');
			var email = document.getElementById("email").value;
			// Hide the modal
			$("#myModal").modal('hide');
			/*
			 * setTimeout( function() {
			 * window.location.href =
			 * 'game.html'; }, 200);
			 */

			var jsonObj = JSON.stringify({
				"email" : email,
				"forwardedFrom" : "registration"
			});

			$("#createNewTeamModal").modal('show');
		} // END else (success)
	}); // END $.post

	// Stop the normal form submission
	return false;
});