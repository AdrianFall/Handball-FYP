$('#squadHref').click(function() {

	$('#schedule').hide();
	$('#leaderboard').hide();
	$('#squadSettings').show();
});

$('#scheduleHref').click(function() {
	$('#squadSettings').hide();
	$('#leaderboard').hide();
	$('#schedule').show();
});

$('#leaderboardHref').click(function() {
	$('#squadSettings').hide();
	$('#schedule').hide();
	$('#leaderboard').show();
});