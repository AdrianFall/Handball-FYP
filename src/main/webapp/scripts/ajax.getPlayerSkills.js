//Ajax POST to obtain the skills of the player
function showPlayerDetailsOnModal(playerId, playerPosition) {
	
	// Serialize the form and post it to the server
	$('#modalPlayerDetails').on('hidden.bs.modal', function(e) {
		$('#physicalSectionLabel').css('display', 'none');
		$('#physicalLine').css('display', 'none');

		$('#accelerationLabel').css('display', 'none');
		$('#accelerationText').css('display', 'none');
		$('#accelerationBlock').css('display', 'none');

		$('#sprintSpeedLabel').css('display', 'none');
		$('#sprintSpeedText').css('display', 'none');
		$('#sprintSpeedBlock').css('display', 'none');

		$('#jumpingLabel').css('display', 'none');
		$('#jumpingText').css('display', 'none');
		$('#jumpingBlock').css('display', 'none');
		
		
		$('#balanceLabel').css('display', 'none');
		$('#balanceText').css('display', 'none');
		$('#balanceBlock').css('display', 'none');
		
		$('#agilityLabel').css('display', 'none');
		$('#agilityText').css('display', 'none');
		$('#agilityBlock').css('display', 'none');
		
		$('#staminaLabel').css('display', 'none');
		$('#staminaText').css('display', 'none');
		$('#staminaBlock').css('display', 'none');
		
		$('#strengthLabel').css('display', 'none');
		$('#strengthText').css('display', 'none');
		$('#strengthBlock').css('display', 'none');
		
		$('#reactionsLabel').css('display', 'none');
		$('#reactionsText').css('display', 'none');
		$('#reactionsBlock').css('display', 'none');
		
		$('#blockingLabel').css('display', 'none');
		$('#blockingText').css('display', 'none');
		$('#blockingBlock').css('display', 'none');
		
		$('#fitnessLabel').css('display', 'none');
		$('#fitnessText').css('display', 'none');
		$('#fitnessBlock').css('display', 'none');
		
		$('#mentalSectionLabel').css('display', 'none');
		$('#mentalLine').css('display', 'none');
		
		$('#aggressionLabel').css('display', 'none');
		$('#aggressionText').css('display', 'none');
		$('#aggressionBlock').css('display', 'none');
		
		$('#interceptionsLabel').css('display', 'none');
		$('#interceptionsText').css('display', 'none');
		$('#interceptionsBlock').css('display', 'none');
		
		$('#attack_positionLabel').css('display', 'none');
		$('#attack_positionText').css('display', 'none');
		$('#attack_positionBlock').css('display', 'none');
		
		$('#visionLabel').css('display', 'none');
		$('#visionText').css('display', 'none');
		$('#visionBlock').css('display', 'none');
		
		$('#creativityLabel').css('display', 'none');
		$('#creativityText').css('display', 'none');
		$('#creativityBlock').css('display', 'none');
		
		$('#technicalSectionLabel').css('display', 'none');
		$('#technicalLine').css('display', 'none');
		
		$('#ball_controlLabel').css('display', 'none');
		$('#ball_controlText').css('display', 'none');
		$('#ball_controlBlock').css('display', 'none');
		
		$('#long_shotsLabel').css('display', 'none');
		$('#long_shotsText').css('display', 'none');
		$('#long_shotsBlock').css('display', 'none');
		
		$('#fk_accuracyLabel').css('display', 'none');
		$('#fk_accuracyText').css('display', 'none');
		$('#fk_accuracyBlock').css('display', 'none');
		
		$('#shot_powerLabel').css('display', 'none');
		$('#shot_powerText').css('display', 'none');
		$('#shot_powerBlock').css('display', 'none');
		
		$('#dribblingLabel').css('display', 'none');
		$('#dribblingText').css('display', 'none');
		$('#dribblingBlock').css('display', 'none');
		
		$('#short_passingLabel').css('display', 'none');
		$('#short_passingText').css('display', 'none');
		$('#short_passingBlock').css('display', 'none');
		
		$('#long_passingLabel').css('display', 'none');
		$('#long_passingText').css('display', 'none');
		$('#long_passingBlock').css('display', 'none');
		
		$('#stand_tacklesLabel').css('display', 'none');
		$('#stand_tacklesText').css('display', 'none');
		$('#stand_tacklesBlock').css('display', 'none');
		
		$('#markingLabel').css('display', 'none');
		$('#markingText').css('display', 'none');
		$('#markingBlock').css('display', 'none');
		
		$('#penaltiesLabel').css('display', 'none');
		$('#penaltiesText').css('display', 'none');
		$('#penaltiesBlock').css('display', 'none');
		
		$('#curveLabel').css('display', 'none');
		$('#curveText').css('display', 'none');
		$('#curveBlock').css('display', 'none');
		
		$('#finishingLabel').css('display', 'none');
		$('#finishingText').css('display', 'none');
		$('#finishingBlock').css('display', 'none');
		
		$('#six_m_shotsLabel').css('display', 'none');
		$('#six_m_shotsText').css('display', 'none');
		$('#six_m_shotsBlock').css('display', 'none');
		
		$('#lob_shotsLabel').css('display', 'none');
		$('#lob_shotsText').css('display', 'none');
		$('#lob_shotsBlock').css('display', 'none');
		
		$('#nine_m_shotsLabel').css('display', 'none');
		$('#nine_m_shotsText').css('display', 'none');
		$('#nine_m_shotsBlock').css('display', 'none');
	});

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
						if (new String(playerPosition).valueOf() != new String(
								"GK").valueOf()) {
							// Set the label divs style to display none, to
							// properly
							// animate the fade in

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

							$('#mentalSectionLabel').fadeIn(4200);
							$('#mentalLine').fadeIn(4400);
							
							var aggressionQuality = parsedDataJSON.aggression;
							var aggressionPercentage = parsedDataJSON.aggression_quality_perc;
							$('#aggressionLabel').fadeIn(4400);
							$('#aggressionBlock').css('background',
									obtainColor(aggressionPercentage));
							$('#aggressionText').html(aggressionQuality);
							$('#aggressionText').fadeIn(4400);
							fade('#aggressionBlock', 4600);
							
							var interceptionsQuality = parsedDataJSON.interceptions;
							var interceptionsPercentage = parsedDataJSON.interceptions_quality_perc;
							$('#interceptionsLabel').fadeIn(4600);
							$('#interceptionsBlock').css('background',
									obtainColor(interceptionsPercentage));
							$('#interceptionsText').html(interceptionsQuality);
							$('#interceptionsText').fadeIn(4600);
							fade('#interceptionsBlock', 4800);
							
							var attack_positionQuality = parsedDataJSON.attack_position;
							var attack_positionPercentage = parsedDataJSON.attack_position_quality_perc;
							$('#attack_positionLabel').fadeIn(4800);
							$('#attack_positionBlock').css('background',
									obtainColor(attack_positionPercentage));
							$('#attack_positionText').html(attack_positionQuality);
							$('#attack_positionText').fadeIn(4800);
							fade('#attack_positionBlock', 5000);
							
							var visionQuality = parsedDataJSON.vision;
							var visionPercentage = parsedDataJSON.vision_quality_perc;
							$('#visionLabel').fadeIn(5000);
							$('#visionBlock').css('background',
									obtainColor(visionPercentage));
							$('#visionText').html(visionQuality);
							$('#visionText').fadeIn(5000);
							fade('#visionBlock', 5200);
							
							var creativityQuality = parsedDataJSON.creativity;
							var creativityPercentage = parsedDataJSON.creativity_quality_perc;
							$('#creativityLabel').fadeIn(5200);
							$('#creativityBlock').css('background',
									obtainColor(creativityPercentage));
							$('#creativityText').html(creativityQuality);
							$('#creativityText').fadeIn(5200);
							fade('#creativityBlock', 5400);
							
							$('#technicalSectionLabel').fadeIn(5400);
							$('#technicalLine').fadeIn(5600);
							
							var ball_controlQuality = parsedDataJSON.ball_control;
							var ball_controlPercentage = parsedDataJSON.ball_control_quality_perc;
							$('#ball_controlLabel').fadeIn(5400);
							$('#ball_controlBlock').css('background',
									obtainColor(ball_controlPercentage));
							$('#ball_controlText').html(ball_controlQuality);
							$('#ball_controlText').fadeIn(5400);
							fade('#ball_controlBlock', 5600);
							
							var long_shotsQuality = parsedDataJSON.long_shots;
							var long_shotsPercentage = parsedDataJSON.long_shots_quality_perc;
							$('#long_shotsLabel').fadeIn(5600);
							$('#long_shotsBlock').css('background',
									obtainColor(long_shotsPercentage));
							$('#long_shotsText').html(long_shotsQuality);
							$('#long_shotsText').fadeIn(5600);
							fade('#long_shotsBlock', 5800);
							
							var fk_accuracyQuality = parsedDataJSON.fk_accuracy;
							var fk_accuracyPercentage = parsedDataJSON.fk_accuracy_quality_perc;
							$('#fk_accuracyLabel').fadeIn(5800);
							$('#fk_accuracyBlock').css('background',
									obtainColor(fk_accuracyPercentage));
							$('#fk_accuracyText').html(fk_accuracyQuality);
							$('#fk_accuracyText').fadeIn(5800);
							fade('#fk_accuracyBlock', 6000);
							
							var shot_powerQuality = parsedDataJSON.shot_power;
							var shot_powerPercentage = parsedDataJSON.shot_power_quality_perc;
							$('#shot_powerLabel').fadeIn(6000);
							$('#shot_powerBlock').css('background',
									obtainColor(shot_powerPercentage));
							$('#shot_powerText').html(shot_powerQuality);
							$('#shot_powerText').fadeIn(6000);
							fade('#shot_powerBlock', 6200);
							
							var dribblingQuality = parsedDataJSON.dribbling;
							var dribblingPercentage = parsedDataJSON.dribbling_quality_perc;
							$('#dribblingLabel').fadeIn(6200);
							$('#dribblingBlock').css('background',
									obtainColor(dribblingPercentage));
							$('#dribblingText').html(dribblingQuality);
							$('#dribblingText').fadeIn(6200);
							fade('#dribblingBlock', 6400);
							
							var short_passingQuality = parsedDataJSON.short_passing;
							var short_passingPercentage = parsedDataJSON.short_passing_quality_perc;
							$('#short_passingLabel').fadeIn(6400);
							$('#short_passingBlock').css('background',
									obtainColor(short_passingPercentage));
							$('#short_passingText').html(short_passingQuality);
							$('#short_passingText').fadeIn(6400);
							fade('#short_passingBlock', 6600);
							
							var long_passingQuality = parsedDataJSON.long_passing;
							var long_passingPercentage = parsedDataJSON.long_passing_quality_perc;
							$('#long_passingLabel').fadeIn(6600);
							$('#long_passingBlock').css('background',
									obtainColor(long_passingPercentage));
							$('#long_passingText').html(long_passingQuality);
							$('#long_passingText').fadeIn(6600);
							fade('#long_passingBlock', 6800);
							
							var stand_tacklesQuality = parsedDataJSON.stand_tackles;
							var stand_tacklesPercentage = parsedDataJSON.stand_tackles_quality_perc;
							$('#stand_tacklesLabel').fadeIn(6800);
							$('#stand_tacklesBlock').css('background',
									obtainColor(stand_tacklesPercentage));
							$('#stand_tacklesText').html(stand_tacklesQuality);
							$('#stand_tacklesText').fadeIn(6800);
							fade('#stand_tacklesBlock', 7000);
							
							
							var markingQuality = parsedDataJSON.marking;
							var markingPercentage = parsedDataJSON.marking_quality_perc;
							$('#markingLabel').fadeIn(6800);
							$('#markingBlock').css('background',
									obtainColor(markingPercentage));
							$('#markingText').html(markingQuality);
							$('#markingText').fadeIn(6800);
							fade('#markingBlock', 7000);
							
							var penaltiesQuality = parsedDataJSON.penalties;
							var penaltiesPercentage = parsedDataJSON.penalties_quality_perc;
							$('#penaltiesLabel').fadeIn(6800);
							$('#penaltiesBlock').css('background',
									obtainColor(penaltiesPercentage));
							$('#penaltiesText').html(penaltiesQuality);
							$('#penaltiesText').fadeIn(6800);
							fade('#penaltiesBlock', 7000);
							
							var curveQuality = parsedDataJSON.curve;
							var curvePercentage = parsedDataJSON.curve_quality_perc;
							$('#curveLabel').fadeIn(6800);
							$('#curveBlock').css('background',
									obtainColor(curvePercentage));
							$('#curveText').html(curveQuality);
							$('#curveText').fadeIn(6800);
							fade('#curveBlock', 7000);
							
							var finishingQuality = parsedDataJSON.finishing;
							var finishingPercentage = parsedDataJSON.finishing_quality_perc;
							$('#finishingLabel').fadeIn(6800);
							$('#finishingBlock').css('background',
									obtainColor(finishingPercentage));
							$('#finishingText').html(finishingQuality);
							$('#finishingText').fadeIn(6800);
							fade('#finishingBlock', 7000);
							
							var six_m_shotsQuality = parsedDataJSON.six_m_shots;
							var six_m_shotsPercentage = parsedDataJSON.six_m_shots_quality_perc;
							$('#six_m_shotsLabel').fadeIn(6800);
							$('#six_m_shotsBlock').css('background',
									obtainColor(six_m_shotsPercentage));
							$('#six_m_shotsText').html(six_m_shotsQuality);
							$('#six_m_shotsText').fadeIn(6800);
							fade('#six_m_shotsBlock', 7000);
							
							var lob_shotsQuality = parsedDataJSON.lob_shots;
							var lob_shotsPercentage = parsedDataJSON.lob_shots_quality_perc;
							$('#lob_shotsLabel').fadeIn(6800);
							$('#lob_shotsBlock').css('background',
									obtainColor(lob_shotsPercentage));
							$('#lob_shotsText').html(lob_shotsQuality);
							$('#lob_shotsText').fadeIn(6800);
							fade('#lob_shotsBlock', 7000);
							
							var nine_m_shotsQuality = parsedDataJSON.nine_m_shots;
							var nine_m_shotsPercentage = parsedDataJSON.nine_m_shots_quality_perc;
							$('#nine_m_shotsLabel').fadeIn(6800);
							$('#nine_m_shotsBlock').css('background',
									obtainColor(nine_m_shotsPercentage));
							$('#nine_m_shotsText').html(nine_m_shotsQuality);
							$('#nine_m_shotsText').fadeIn(6800);
							fade('#nine_m_shotsBlock', 7000);

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
		display : 'block',
		'-webkit-animation' : 'fadeIn ' + ms + 'ms',
		'-moz-animation' : 'fadeIn ' + ms + 'ms',
		'-o-animation' : 'fadeIn ' + ms + 'ms',
		'-animation' : 'fadeIn ' + ms + 'ms'
	});
}

function obtainColor(perc) {
	var color = '';
	if (perc >= 0 && perc <= 5)
		color = '#B30707';
	else if (perc > 5 && perc <= 15)
		color = '#cb5858';
	else if (perc > 15 && perc <= 30)
		color = '#C9BD3A';
	else if (perc > 30 && perc <= 40)
		color = '#d4ca62';
	else if (perc > 40 && perc <= 50)
		color = '#BEE339';
	else if (perc > 50 && perc <= 65)
		color = '#99E339';
	else if (perc > 65 && perc <= 80)
		color = '#5BE339';
	else if (perc > 80)
		color = '#32F039';
	return color;
}
