$(document).ready(function loadPage() {
	$('#container').hide();
	$('#infoTable').hide();
	$.get('squad.html').success(function(data) {
		$('#squadSettings').html('');
		$('#squadSettings').html(data);
		$('#squadSettings').hide();

		$.get('schedule.html').success(function(data) {
			$('#schedule').html('');
			$('#schedule').html(data);
			$('#schedule').hide();
			$('#loadingAnimation').html('');
			$('#container').slideDown(600);
			$('#infoTable').slideDown(1200);
			$.get('matchPanel.html').success(function(data) {
				$('#match').html(data);
				$('#match').slideDown(1400);
			});
		});
	});

});