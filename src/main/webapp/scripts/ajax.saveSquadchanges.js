function postClientSideFormationList() {

	// Set the button not clickable and add class for spinner
	$('#saveSquadButton').attr('onclick', '');
	$('#saveSquadButton').toggleClass('active');
	
	var squadTable = document.getElementById('squadTable');
	var playerIdList = [];
	// Count first squad players
	var countFsqp = 0;
	// Count bench players
	var countBp = 0;
	// Count reserve players
	var countRp = 0;

	// Loop for the players in squad table
	for ( var i = 0; i < squadTable.rows.length; i++) {

		
		if (i != 0) { // Skips table head
			var trId = squadTable.rows[i].id;
			// Split the table row ID, in order to obtain the
			// type of the row (which designates whether the
			// row holds first squad, bench or reserve player)
			var splitTrId = trId.split("_tr");
			var trType = splitTrId[0];
			var trIndex = splitTrId[1];
			

			// Obtain the player ID from the hidden cell holding it
			var playerId = squadTable.rows[i].cells[9]
					.getElementsByTagName('div')[0].innerHTML;

			if (new String(trType).valueOf() == new String('fsqp').valueOf()) {
				countFsqp++;
				playerIdList.push(playerId);
			} else if (new String(trType).valueOf() == new String('bp')
					.valueOf()) {
				countBp++;
				playerIdList.push(playerId);
			} else if (new String(trType).valueOf() == new String('rp')
					.valueOf()) {
				countRp++;
				playerIdList.push(playerId);
			}

		} // END if (i != 0)

	} // END Loop for players in squad table
	
    // Obtain the 
	

	// POST the list to the server
	$.ajax({
	type : "POST",
	url : "updateSquad.html",
	data : JSON.stringify({
		"playerIdList" : playerIdList,
		"captainsList" : captainsList
		
	}),
	contentType : "application/json; charset=UTF-8",
	success : function(data) {

		var parsedDataJSON = $.parseJSON(data);
		if (parsedDataJSON.status == "OK") {

			alert('OK');
			// TODO Set the button to clickable
			$('#saveSquadButton').attr('onclick', 'postClientSideFormationList()');
			$('#saveSquadButton').toggleClass('');


			// TODO Animate the button to be hidden
			$('#saveSquadButton').fadeOut(2000);

			
		} else if (parsedDataJSON.status == "sessionExpired") {
			alert('Session Expired');
		}
			
		else if (parsedDataJSON.status == "error")
			alert('status error');
			
	},
	error : function() {
		alert('error');
	}
});
	
	// END Posting list to the server


	

	
	
	
}