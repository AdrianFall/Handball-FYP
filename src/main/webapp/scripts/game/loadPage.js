$(document).ready(function loadPage() {
	// var for loading animation
	var loadingAnimationClass = 'icon-spin refresh icon';

	// Obtain current icon classes
	var squadIconClass = $('#squadIcon').attr('class');
	var scheduleIconClass = $('#scheduleIcon').attr('class');
	var leaderboardIconClass = $('#leaderboardIcon').attr('class');
	
	// Swap the current icon classes with loading animation
	$('#squadIcon').removeClass(squadIconClass).addClass(loadingAnimationClass);
	$('#scheduleIcon').removeClass(scheduleIconClass).addClass(loadingAnimationClass);
	$('#leaderboardIcon').removeClass(leaderboardIconClass).addClass(loadingAnimationClass);
	
	// Hide the container and its table
	$('#container').hide();
	$('#infoTable').hide();
	$.get('squad.html').success(function(data) {
		
		// Swap the loading animation of squadIcon with its previous class
		$('#squadIcon').removeClass(loadingAnimationClass).addClass(squadIconClass);
		
		// Load the squad data into the doc, and hide by default
		$('#squadSettings').html('').html(data).hide();

		$.get('schedule.html').success(function(data) {
			
			// Swap the loading animation of scheduleIcon with its previous class
			$('#scheduleIcon').removeClass(loadingAnimationClass).addClass(scheduleIconClass);

			// Load the squad data into the doc, and hide it by default
			$('#schedule').html('').html(data).hide();
			// Clear the loading animation
			$('#loadingAnimation').html('');
			$('#container').slideDown(600);
			$('#infoTable').slideDown(1200);
			$.get('matchPanel.html').success(function(data) {
				$('#match').html('').html(data).slideDown(1400);
				
				$.get('leaderboard.html').success(function(data) {
					// Swap the loading animation of leaderboardIcon with its previous class
					$('#leaderboardIcon').removeClass(loadingAnimationClass).addClass(leaderboardIconClass);
					$('#leaderboard').html('').html(data).hide();
				});
			});
		});
	});

});