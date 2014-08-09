// Obtain and display the player details on the modal
function showPlayerDetailsOnModal(playerId, playerPosition) {
	
	// Serialize the form and post it to the server
	$('#modalPlayerDetails').on('hidden.bs.modal', function(e) {
		$('#physicalSectionLabel').css('display', 'none');
		$('#physicalLine').css('display', 'none');

	

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
		
		
		
		$('#blockingLabel').css('display', 'none');
		$('#blockingText').css('display', 'none');
		$('#blockingBlock').css('display', 'none');
		
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
		
		$('#goalKeepingSectionLabel').css('display', 'none');
		$('#goalKeepingLine').css('display', 'none');

		$('#reflexesLabel').css('display', 'none');
		$('#reflexesText').css('display', 'none');
		$('#reflexesBlock').css('display', 'none');
		
		$('#handlingLabel').css('display', 'none');
		$('#handlingText').css('display', 'none');
		$('#handlingBlock').css('display', 'none');
		
		$('#positioningLabel').css('display', 'none');
		$('#positioningText').css('display', 'none');
		$('#positioningBlock').css('display', 'none');
		
		$('#leg_savesLabel').css('display', 'none');
		$('#leg_savesText').css('display', 'none');
		$('#leg_savesBlock').css('display', 'none');
		
		$('#penalty_savesLabel').css('display', 'none');
		$('#penalty_savesText').css('display', 'none');
		$('#penalty_savesBlock').css('display', 'none');
		
		$('#six_m_savesLabel').css('display', 'none');
		$('#six_m_savesText').css('display', 'none');
		$('#six_m_savesBlock').css('display', 'none');
		
		$('#nine_m_savesLabel').css('display', 'none');
		$('#nine_m_savesText').css('display', 'none');
		$('#nine_m_savesBlock').css('display', 'none');
		
		$('#communicationLabel').css('display', 'none');
		$('#communicationText').css('display', 'none');
		$('#communicationBlock').css('display', 'none');
		
		$('#anglesLabel').css('display', 'none');
		$('#anglesText').css('display', 'none');
		$('#anglesBlock').css('display', 'none');
		
		$('#catchingLabel').css('display', 'none');
		$('#catchingText').css('display', 'none');
		$('#catchingBlock').css('display', 'none');

		$('#passingLabel').css('display', 'none');
		$('#passingText').css('display', 'none');
		$('#passingBlock').css('display', 'none');
	});


	

	// AJAX post for player skills
	$.ajax({ 
				type : "POST",
				url : "getPlayerDetails.html",
				data : JSON.stringify({
					"playerId" : playerId
				}),
				contentType : "application/json; charset=UTF-8",
				success : function(data) {

					var parsedDataJSON = $.parseJSON(data);
					if (parsedDataJSON.status == "OK") {
					
						// Set the player details
						$('#height').html(parsedDataJSON.height);
						$('#weight').html(parsedDataJSON.weight);
						$('#handed').html(parsedDataJSON.handed);
						$('#specialAbility').html(parsedDataJSON.special_ability);
						
						// Set the contract details
						$('#seasonWage').html("$" + parsedDataJSON.season_wage);
						$('#marketValue').html("$" + parsedDataJSON.market_value);
						$('#yearsLeft').html(parsedDataJSON.years_left + " years left at " + parsedDataJSON.team_name);
					
						// Set the health details
						$('#conditionProgressBar').data("perc", parsedDataJSON.condition);
						$('#moraleProgressBar').data("perc", parsedDataJSON.morale);
						if (parsedDataJSON.injury_days == 0) {
							$('#injuryCause').html("The player is in good shape");
							$('#hireDoctorButton').fadeOut(500);
							$('#hireDoctorText').fadeOut(500);
						} else {
							$('#injuryCause').html(parsedDataJSON.injury + " for " + parsedDataJSON.injury_days + " days.");
							$('#hireDoctorButton').fadeIn(500);
							$('#hireDoctorText').fadeIn(500);
						}
						
						

						

						// Call function to load the progress bar
						preload();
						
						// Call function to animate stripes
						stripesAnimate();  
						
						if (new String(playerPosition).valueOf() != new String(
								"GK").valueOf()) {
							
								
							
							
							// Set the label divs style to display none, to
							// properly
							// animate the fade in
							
							
							$('#physicalSectionLabel').css({
								'-webkit-animation-duration': '3s',
								  '-webkit-animation-delay': '0s',
							'-moz-animation-duration': '3s',
							  '-moz-animation-delay': '0s',
							'-ms-animation-duration': '3s',
							  '-ms-animation-delay': '0s',
							});

							$('#mentalSectionLabel').css({
								'-webkit-animation-duration': '3s',
								  '-webkit-animation-delay': '1s',
							'-moz-animation-duration': '3s',
							  '-moz-animation-delay': '1s',
							'-ms-animation-duration': '3s',
							  '-ms-animation-delay': '1s',
							});

							$('#technicalSectionLabel').css({
								'-webkit-animation-duration': '3s',
								  '-webkit-animation-delay': '2.2s',
							'-moz-animation-duration': '3s',
							  '-moz-animation-delay': '2.2s',
							'-ms-animation-duration': '3s',
							  '-ms-animation-delay': '2.2s',
							});
							
							$('#goalKeepingSectionLabel').css({
								'-webkit-animation-duration': '3s',
								  '-webkit-animation-delay': '0.2s',
							'-moz-animation-duration': '3s',
							  '-moz-animation-delay': '0.2s',
							'-ms-animation-duration': '3s',
							  '-ms-animation-delay': '0.2s',
							});



							$('#physicalSectionLabel').fadeIn(850);
							$('#physicalLine').fadeIn(1050);
							$('#physicalSectionLabel').addClass('animated bounceIn');

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

						

							var blockingQuality = parsedDataJSON.blocking;
							var blockingPercentage = parsedDataJSON.blocking_quality_perc;
							$('#blockingLabel').fadeIn(3800);
							$('#blockingBlock').css('background',
									obtainColor(blockingPercentage));
							$('#blockingText').html(blockingQuality);
							$('#blockingText').fadeIn(4000);
							fade('#blockingBlock', 4000);

						

							$('#mentalSectionLabel').fadeIn(4200);
							$('#mentalLine').fadeIn(4400);
							$('#mentalSectionLabel').addClass('animated bounceIn');
							
							var aggressionQuality = parsedDataJSON.aggression;
							var aggressionPercentage = parsedDataJSON.aggression_quality_perc;
							$('#aggressionLabel').fadeIn(4400);
							$('#aggressionBlock').css('background',
									obtainColor(aggressionPercentage));
							$('#aggressionText').html(aggressionQuality);
							$('#aggressionText').fadeIn(4400);
							fade('#aggressionBlock', 4600);
							
							
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
							$('#technicalSectionLabel').addClass('animated bounceIn');
							
							var catchingQuality = parsedDataJSON.catching;
							var catchingPercentage = parsedDataJSON.catching_quality_perc;
							$('#catchingLabel').fadeIn(5400);
							$('#catchingBlock').css('background',
									obtainColor(catchingPercentage));
							$('#catchingText').html(catchingQuality);
							$('#catchingText').fadeIn(5400);
							fade('#catchingBlock', 5600);
							
							
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
							
							var passingQuality = parsedDataJSON.passing;
							var passingPercentage = parsedDataJSON.passing_quality_perc;
							$('#passingLabel').fadeIn(6400);
							$('#passingBlock').css('background',
									obtainColor(passingPercentage));
							$('#passingText').html(passingQuality);
							$('#passingText').fadeIn(6400);
							fade('#passingBlock', 6600);
							
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
							
							
							
							var nine_m_shotsQuality = parsedDataJSON.nine_m_shots;
							var nine_m_shotsPercentage = parsedDataJSON.nine_m_shots_quality_perc;
							$('#nine_m_shotsLabel').fadeIn(6800);
							$('#nine_m_shotsBlock').css('background',
									obtainColor(nine_m_shotsPercentage));
							$('#nine_m_shotsText').html(nine_m_shotsQuality);
							$('#nine_m_shotsText').fadeIn(6800);
							fade('#nine_m_shotsBlock', 7000);

						} else { // playerPosition == GK
							$('#goalKeepingSectionLabel').fadeIn(850);
							$('#goalKeepingLine').fadeIn(1050);
							$('#goalKeepingSectionLabel').addClass('animated rubberBand');
							
							var reflexesQuality = parsedDataJSON.reflexes;
							var reflexesPercentage = parsedDataJSON.reflexes_quality_perc;
							$('#reflexesLabel').fadeIn(1450);
							$('#reflexesBlock').css('background',
									obtainColor(reflexesPercentage));
							$('#reflexesText').html(reflexesQuality);
							$('#reflexesText').fadeIn(1800);
							fade('#reflexesBlock', 2200)


							var positioningQuality = parsedDataJSON.positioning;
							var positioningPercentage = parsedDataJSON.positioning_quality_perc;
							$('#positioningLabel').fadeIn(2600);
							$('#positioningBlock').css('background',
									obtainColor(positioningPercentage));
							$('#positioningText').html(positioningQuality);
							$('#positioningText').fadeIn(2800);
							fade('#positioningBlock', 2800);

							var leg_savesQuality = parsedDataJSON.leg_saves;
							var leg_savesPercentage = parsedDataJSON.leg_saves_quality_perc;
							$('#leg_savesLabel').fadeIn(2800);
							$('#leg_savesBlock').css('background',
									obtainColor(leg_savesPercentage));
							$('#leg_savesText').html(leg_savesQuality);
							$('#leg_savesText').fadeIn(2800);
							fade('#leg_savesBlock', 3000);

							var penalty_savesQuality = parsedDataJSON.penalty_saves;
							var penalty_savesPercentage = parsedDataJSON.penalty_saves_quality_perc;
							$('#penalty_savesLabel').fadeIn(3000);
							$('#penalty_savesBlock').css('background',
									obtainColor(penalty_savesPercentage));
							$('#penalty_savesText').html(penalty_savesQuality);
							$('#penalty_savesText').fadeIn(3000);
							fade('#penalty_savesBlock', 3200);

							var six_m_savesQuality = parsedDataJSON.six_m_saves;
							var six_m_savesPercentage = parsedDataJSON.six_m_saves_quality_perc;
							$('#six_m_savesLabel').fadeIn(3200);
							$('#six_m_savesBlock').css('background',
									obtainColor(six_m_savesPercentage));
							$('#six_m_savesText').html(six_m_savesQuality);
							$('#six_m_savesText').fadeIn(3200);
							fade('#six_m_savesBlock', 3400);

							var nine_m_savesQuality = parsedDataJSON.nine_m_saves;
							var nine_m_savesPercentage = parsedDataJSON.nine_m_saves_quality_perc;
							$('#nine_m_savesLabel').fadeIn(3400);
							$('#nine_m_savesBlock').css('background',
									obtainColor(nine_m_savesPercentage));
							$('#nine_m_savesText').html(nine_m_savesQuality);
							$('#nine_m_savesText').fadeIn(3400);
							fade('#nine_m_savesBlock', 3600);

							
							
							
						}// END else (playerPosition == GK)
					 
					} else if (parsedDataJSON.status == "sessionExpired")
						alert('Session Expired');
					else if (parsedDataJSON.status == "error")
						alert('error.');
					
				},
				error : function() {
					alert('Error occured when fetching player skills');
					
				}
			}); // END AJAX post for player's skills
}; // END showPlayerDetailsOnModal function

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
