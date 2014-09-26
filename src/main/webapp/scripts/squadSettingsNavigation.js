

function showPitchTable() {
	// pitchTable, autoSubs, captainRoles 
	$('#autoSubs').slideUp(200);
	$('#captainImage').slideUp(100);
	$('#captain1').slideUp(100);
	$('#captain2').slideUp(100);
	$('#captain3').slideUp(100);
	$('#captain4').slideUp(100);
	$('#penaltyTakers').slideUp(200);
	
	$('#pitchTable').slideDown(750);
	
}

function showCaptainTable() {
	// pitchTable, autoSubs, captainRoles 
	$('#autoSubs').slideUp(200);
	$('#pitchTable').slideUp(200);
	$('#penaltyTakers').slideUp(200);
	
	$('#captainRoles').slideDown(500);
	$('#captainImage').slideDown(600);
	$('#captain1').slideDown(650);
	$('#captain2').slideDown(700);
	$('#captain3').slideDown(750);
	$('#captain4').slideDown(800);
	
}

function showAutoSubsTable() {
	// pitchTable, autoSubs, captainRoles 
	$('#pitchTable').slideUp(200);
	$('#autoSubs').slideUp(200);
	$('#captainImage').slideUp(200);
	$('#captain1').slideUp(250);
	$('#captain2').slideUp(300);
	$('#captain3').slideUp(350);
	$('#captain4').slideUp(400);
	$('#penaltyTakers').slideUp(200);
	$('#autoSubs').slideDown(800);
	
}

function showPenaltyTakersTable() {
	$('#autoSubs').slideUp(200);
	$('#captainImage').slideUp(200);
	$('#captain1').slideUp(250);
	$('#captain2').slideUp(300);
	$('#captain3').slideUp(350);
	$('#captain4').slideUp(400);
	$('#autoSubs').slideUp(200);

	$('#penaltyTakers').slideDown(800);
}

