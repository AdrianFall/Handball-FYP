$('#squadHref').click(function() {

	$('#schedule').hide();
	$('#leaderboard').hide();
	$('#squadSettings').show();
	$('#squadIcon').addClass("green");
	$('#leaderboardIcon').removeClass("green");
	$('#scheduleIcon').removeClass("green");
});

$('#scheduleHref').click(function() {
	$('#squadSettings').hide();
	$('#leaderboard').hide();
	$('#schedule').show();
	$('#scheduleIcon').addClass("green");
	$('#leaderboardIcon').removeClass("green");
	$('#squadIcon').removeClass("green");
});

$('#leaderboardHref').click(function() {
	$('#squadSettings').hide();
	$('#schedule').hide();
	$('#leaderboard').show();
	$('#leaderboardIcon').addClass("green");
	$('#scheduleIcon').removeClass("green");
	$('#squadIcon').removeClass("green");
});