
//Ajax POST to obtain the skills of the player
function showPlayerDetailsModal(playerId, playerPosition) {
	// Serialize the form and post it to the server

	$
			.ajax({
				type : "POST",
				url : "getPlayerSkills.html",
				data : JSON.stringify({
					"playerId" : playerId
				}),
				contentType : "application/json; charset=UTF-8",
				success : function(data) {

					var parsedDataJSON = $.parseJSON(data);
					if (parsedDataJSON.status == "OK") {

						spinner.stop();
						if (new String(playerPosition).valueOf() != new String("GK").valueOf()) {
						
						$('#physicalSectionLabel').fadeIn(850);
						$('#physicalLine').fadeIn(1050);
						
						var accelerationQuality = parsedDataJSON.acceleration;
						var accelerationPercentage = parsedDataJSON.acceleration_quality_perc;
						
						
						$('#accelerationLabel').fadeIn(1450);
						$('#accelerationBlock').css('background',
						obtainColor(accelerationPercentage));
						$('#accelerationText').html(accelerationQuality);
						$('#accelerationText').fadeIn(1800);
						fade('#accelerationBlock', 2200)
						
						var sprintSpeedQuality = parsedDataJSON.sprint_speed;
						var sprintSpeedPercentage = parsedDataJSON.sprint_speed_quality_perc;
						$('#sprintSpeedLabel').fadeIn(2150);
						$('#sprintSpeedBlock').css('background',
							obtainColor(sprintSpeedPercentage));
						$('#sprintSpeedText').html(sprintSpeedQuality);
						$('#sprintSpeedText').fadeIn(2400);
						fade('#sprintSpeedBlock', 2400);
						
						var jumpingQuality = parsedDataJSON.jumping;
						var jumpingPercentage = parsedDataJSON.jumping_quality_perc;
						$('#jumpingLabel').fadeIn(2600);
						$('#jumpingBlock').css('background',
							obtainColor(jumpingPercentage));
						$('#jumpingText').html(jumpingQuality);
						$('#jumpingText').fadeIn(2800);
						fade('#jumpingBlock', 2800);
						
						var balanceQuality = parsedDataJSON.balance;
						var balancePercentage = parsedDataJSON.balance_quality_perc;
						$('#balanceLabel').fadeIn(2800);
						$('#balanceBlock').css('background',
							obtainColor(balancePercentage));
						$('#balanceText').html(balanceQuality);
						$('#balanceText').fadeIn(2800);
						fade('#balanceBlock', 3000);
						
						var agilityQuality = parsedDataJSON.agility;
						var agilityPercentage = parsedDataJSON.agility_quality_perc;
						$('#agilityLabel').fadeIn(3000);
						$('#agilityBlock').css('background',
							obtainColor(agilityPercentage));
						$('#agilityText').html(agilityQuality);
						$('#agilityText').fadeIn(3000);
						fade('#agilityBlock', 3200);
						
						var staminaQuality = parsedDataJSON.stamina;
						var staminaPercentage = parsedDataJSON.stamina_quality_perc;
						$('#staminaLabel').fadeIn(3200);
						$('#staminaBlock').css('background',
							obtainColor(staminaPercentage));
						$('#staminaText').html(staminaQuality);
						$('#staminaText').fadeIn(3200);
						fade('#staminaBlock', 3400);
						
						var strengthQuality = parsedDataJSON.strength;
						var strengthPercentage = parsedDataJSON.strength_quality_perc;
						$('#strengthLabel').fadeIn(3400);
						$('#strengthBlock').css('background',
							obtainColor(strengthPercentage));
						$('#strengthText').html(strengthQuality);
						$('#strengthText').fadeIn(3400);
						fade('#strengthBlock', 3600);
						
						var reactionsQuality = parsedDataJSON.reactions;
						var reactionsPercentage = parsedDataJSON.reactions_quality_perc;
						$('#reactionsLabel').fadeIn(3600);
						$('#reactionsBlock').css('background',
							obtainColor(reactionsPercentage));
						$('#reactionsText').html(reactionsQuality);
						$('#reactionsText').fadeIn(3600);
						fade('#reactionsBlock', 3800);
						
						var blockingQuality = parsedDataJSON.blocking;
						var blockingPercentage = parsedDataJSON.blocking_quality_perc;
						$('#blockingLabel').fadeIn(3800);
						$('#blockingBlock').css('background',
							obtainColor(blockingPercentage));
						$('#blockingText').html(blockingQuality);
						$('#blockingText').fadeIn(4000);
						fade('#blockingBlock', 4000);
						
						var fitnessQuality = parsedDataJSON.fitness;
						var fitnessPercentage = parsedDataJSON.fitness_quality_perc;
						$('#fitnessLabel').fadeIn(3800);
						$('#fitnessBlock').css('background',
							obtainColor(fitnessPercentage));
						$('#fitnessText').html(fitnessQuality);
						$('#fitnessText').fadeIn(4000);
						fade('#fitnessBlock', 4000);

						} // END if playerPosition != GK
					} else if (parsedDataJSON.status == "sessionExpired")
						alert('Session Expired');
					else if (parsedDataJSON.status == "error")
						alert('error.');
					spinner.stop();
				},
				error : function() {
					alert('Error occured when fetching player skills');
					spinner.stop();
				}
			});
};

function fade(id, ms) {
	$(id).css({
		display:'block',
		    '-webkit-animation': 'fadeIn ' + ms + 'ms',
		    '-moz-animation':    'fadeIn ' + ms + 'ms',
		    '-o-animation':      'fadeIn ' + ms + 'ms',
		    '-animation':         'fadeIn ' + ms + 'ms'
	});
}


function obtainColor(perc) {
	var color = '';
if (perc >= 0 && perc <= 5)  color = '#B30707';
else if (perc > 5 && perc <= 15) color = '#cb5858';
else if (perc > 15 && perc <= 30) color = '#C9BD3A';
else if (perc > 30 && perc <= 40) color = '#d4ca62';
else if (perc > 40 && perc <= 50) color = '#BEE339';
else if (perc > 50 && perc <= 65) color = '#99E339';
else if (perc > 65 && perc <= 80) color = '#5BE339';
else if (perc > 80) color = '#32F039';
return color;
}
