function processHighlight(highlights) {
	var splitHighlights = highlights.split(";");
	var arrayLength = splitHighlights.length;
	var delayInDisplaying = 30 / (arrayLength-1) * 1000;
	var countAddedElements = 1;
	for (key in splitHighlights) {
			
			
		var splitHighlightEntry = splitHighlights[key].split(":");
		
		var splitMinuteTime = splitHighlightEntry[0].split("=");
		var minute = splitMinuteTime[1];
		
		if(typeof minute != "undefined") {
			if (splitHighlights[key].indexOf("goal=home") > -1) {
				var splitScorer = splitHighlightEntry[2].split("=");
				var scorer = splitScorer[1];
				/* alert("delay = " + (countAddedElements * delayInDisplaying)); */
				setTimeout(addGoalLeftWithAnimation, (countAddedElements * delayInDisplaying));
				setTimeout(addLeft, (countAddedElements * delayInDisplaying), minute, 'Goal', 'incredible goal by ' + scorer + ' ', 'goal');
				countAddedElements++;
			} else if (splitHighlights[key].indexOf("goal=away") > -1) {
				var splitScorer = splitHighlightEntry[2].split("=");
				var scorer = splitScorer[1];
				setTimeout(addGoalRightWithAnimation, (countAddedElements * delayInDisplaying));
				setTimeout(addRight, countAddedElements * delayInDisplaying, minute, 'Goal', 'incredible goal by ' + scorer + ' ', 'goal');
				countAddedElements++;
			} else if ((splitHighlights[key].indexOf("penalty=home") > -1) || (splitHighlights[key].indexOf("penalty=away") > -1)) { /* Penalty for home OR away */
				var splitPenaltyTaker = splitHighlightEntry[4].split("=");
				var penaltyTaker = splitPenaltyTaker[1];
				var splitIsGoal = splitHighlightEntry[6].split("=");
				var isGoal = splitIsGoal[1];
				var highlight = "";
				if (isGoal == "true") {
					highlight = penaltyTaker + " has converted the penalty into a GOAL!";
				} else if (isGoal == "false") {
					/* TODO obtain the goal keeper name from the other side */
					highlight = "Ohh.. what a SAVE!";
				} else {
					highlight = "ERROR.";
				}
				var timeRemainingInMs = delayInDisplaying;
				if (splitHighlights[key].indexOf("penalty=home") > -1) {
					// For decrementing the time left for displaying the penalty animation
					
					setTimeout(displayPenaltyOnRightSide, (countAddedElements * delayInDisplaying), timeRemainingInMs, isGoal, minute);	
				} else if (splitHighlights[key].indexOf("penalty=away") > -1) {
					
					setTimeout(displayPenaltyOnLeftSide, (countAddedElements * delayInDisplaying), timeRemainingInMs, isGoal, minute);
				}
				countAddedElements += 2;
			} else if ((splitHighlights[key].indexOf("free-kick=home") > -1) || (splitHighlights[key].indexOf("free-kick=away") > -1)) { /* Free-Kick for home OR away */
				
				var splitFreeKickTaker = splitHighlightEntry[4].split("=");
				var freeKickTaker = splitFreeKickTaker[1];
				var splitIsGoal = splitHighlightEntry[6].split("=");
				var isGoal = splitIsGoal[1];
				
				var highlight = "";
				
				if (isGoal == "true") {
					highlight = freeKickTaker + " was clinical and SCORED!";
				} else if (isGoal == "false") {
					/* TODO obtain the goal keeper name from the other side */
					highlight = "What a reflex from the keeper, it is a SAVE!";
				} else {
					highlight = "ERROR";
				}
				
				if (splitHighlights[key].indexOf("free-kick=home") > -1) {
					if (isGoal == "true") setTimeout(addGoalLeftWithAnimation, (countAddedElements * delayInDisplaying));
					setTimeout(addLeft, (countAddedElements * delayInDisplaying), minute, 'Free-Kick', highlight, 'free-kick');
				} else if (splitHighlights[key].indexOf("free-kick=away") > -1) {
					if (isGoal == "true") setTimeout(addGoalRightWithAnimation, (countAddedElements * delayInDisplaying));
					setTimeout(addRight, (countAddedElements * delayInDisplaying), minute, 'Free-Kick', highlight, 'free-kick');
				}
				/* 
				 addLeft('" + minute + "', 'Free-Kick', '" + highlight + "', 'free-kick');
				*/
			} else if ((splitHighlights[key].indexOf("possession=home") > -1) || (splitHighlights[key].indexOf("possession=away") > -1)) { /* Free-Kick for home OR away */
				
				if (splitHighlights[key].indexOf("possession=home") > -1) {
					setTimeout(function(){ 
						$('#overlay-highlight-right').fadeOut(50);
						$('#overlay-team-name-right').fadeOut(50);
						$('#overlay-from-right').fadeOut(200, function() {
							// Animation complete
							$('#overlay-from-left').fadeIn(delayInDisplaying-200);
							$('#overlay-team-name-left').fadeIn(delayInDisplaying-250);
							// Change the highlight
							$('#overlay-highlight-left').text('POSSESSION');
							$('#overlay-highlight-left').fadeIn(delayInDisplaying-180);
						}); 
					}, (countAddedElements * delayInDisplaying));
					
				} else if (splitHighlights[key].indexOf("possession=away") > -1) {
					setTimeout(function(){ 
						$('#overlay-highlight-left').fadeOut(50);
						$('#overlay-team-name-left').fadeOut(50);
						$('#overlay-from-left').fadeOut(200, function() {
							// Animation complete
							$('#overlay-from-right').fadeIn(delayInDisplaying-200);
							$('#overlay-team-name-right').fadeIn(delayInDisplaying-250);
							// Change the highlight
							$('#overlay-highlight-right').text('POSSESSION');
							$('#overlay-highlight-right').fadeIn(delayInDisplaying-180);
						}); 
					}, (countAddedElements * delayInDisplaying));
				
					
				}
				countAddedElements++;
			}
			
		} /* END if(typeof minute != "undefined") { */
	}
}

function finishGame() {
	// cancel possession
	$('#overlay-from-left').fadeOut(400);
	$('#overlay-highlight-left').fadeOut(200);
	$('#overlay-team-name-left').fadeOut(200);
	
	$('#overlay-from-right').fadeOut(400);
	$('#overlay-highlight-right').fadeOut(200);
	$('#overlay-team-name-right').fadeOut(200);
	
	// show box
	$('#overlay-box').fadeIn(2300, function() {
		// show text in box
		$('#overlay-box-text').fadeIn(1100);
	}); 
	
	var loadingAnimationClass = 'icon-spin refresh icon';
	
	var scheduleIconClass = $('#scheduleIcon').attr('class');
	var leaderboardIconClass = $('#leaderboardIcon').attr('class');
	
	// Swap the current icon classes with loading animation
	$('#scheduleIcon').removeClass(scheduleIconClass).addClass(loadingAnimationClass);
	$('#leaderboardIcon').removeClass(leaderboardIconClass).addClass(loadingAnimationClass);
	
	// Reload the pages
	$.get('schedule.html').success(function(data) {
			// Swap the loading animation of scheduleIcon with its previous class
			$('#scheduleIcon').removeClass(loadingAnimationClass).addClass(scheduleIconClass);
	
			$('#schedule').html('').html(data);
			
			// Reload the leaderboards site
			$.get('leaderboard.html').success(function(data) {
				// Swap the loading animation of leaderboardIcon with its previous class
				$('#leaderboardIcon').removeClass(loadingAnimationClass).addClass(leaderboardIconClass);
			
				$('#leaderboard').html('').html(data);
			});
	});
	

}



function addGoalLeft() {
	var currentNumberOfGoalsText = $('#scoreHome').text();
	var currentNumberOfGoals = Number(currentNumberOfGoalsText);
	currentNumberOfGoals++;
	$('#scoreHome').text(currentNumberOfGoals);
	if (currentNumberOfGoals > 9) {
		$('#scoreHome').css("margin-left", "44.5%");
	}
}

function addGoalRight() {
	var currentNumberOfGoalsText = $('#scoreAway').text();
	var currentNumberOfGoals = Number(currentNumberOfGoalsText);
	currentNumberOfGoals++;
	$('#scoreAway').text(currentNumberOfGoals);
	if (currentNumberOfGoals > 9) {
		$('#scoreAway').css("margin-left", "53.65%");
	}
}


function addGoalLeftWithAnimation() {
	// OBTAIN CURRENT GOAL 
	var currentNumberOfGoalsText = $('#scoreHome').text();
	var currentNumberOfGoals = Number(currentNumberOfGoalsText);
	//alert('Current number of goals = ' + currentNumberOfGoals);
	// INCREMENT 
	currentNumberOfGoals++;
	// IF > 9 THEN MANAGE THE MARGIN-LEFT
	if (currentNumberOfGoals > 9) {
		$('#scoreHome').css("margin-left", "44.5%");
	} 
	
	
	// FADE IN THE GOAL
	$('#scoreHome').fadeOut(200, function() {
		$('#scoreHome').text(currentNumberOfGoals);
		$('#scoreHome').fadeIn(1000);
	});
}

function addGoalRightWithAnimation() {
	// OBTAIN CURRENT GOAL 
	var currentNumberOfGoalsText = $('#scoreAway').text();
	var currentNumberOfGoals = Number(currentNumberOfGoalsText);
	//alert('Current number of goals = ' + currentNumberOfGoals);
	// INCREMENT 
	currentNumberOfGoals++;
	// IF > 9 THEN MANAGE THE MARGIN-LEFT
	if (currentNumberOfGoals > 9) {
		$('#scoreAway').css("margin-left", "53.5%");
	}
	// FADE IN THE GOAL
	$('#scoreAway').fadeOut(200, function() {
		$('#scoreAway').text(currentNumberOfGoals);
		$('#scoreAway').fadeIn(1000);
	});
}

function displayPenaltyOnLeftSide(timeLeftInMs, isGoal, minute) {
	// Change the highlight
	$('#overlay-highlight-right').fadeOut(50, function() {
		$('#overlay-highlight-right').text('PENALTY');
		$('#overlay-highlight-right').fadeIn(200);
	});
	$('#overlay-white-line-left').fadeIn(20, function() {
		// Animation complete
		$('#overlay-zero-line-left').fadeIn(200, function() {
			$('#overlay-first-line-left').fadeIn(200, function() {
				$('#overlay-second-line-left').fadeIn(200, function() {
					$('#overlay-zero-line-left').fadeOut(200);
					$('#overlay-third-line-left').fadeIn(200, function() {
						$('#overlay-first-line-left').fadeOut(200);
						$('#overlay-fourth-line-left').fadeIn(200, function() {
							$('#overlay-second-line-left').fadeOut(200);
							$('#overlay-arrow-line-left').fadeIn(200, function() {
								$('#overlay-third-line-left').fadeOut(200);
								$('#overlay-fourth-line-left').fadeOut(200);
								$('#overlay-arrow-line-left').fadeOut(200, function () {
									// total =  6 elements * 200ms 
									var timeTakenInMs = 1200;
									
									if ((timeLeftInMs - timeTakenInMs) < 0) {
										
										if (isGoal == "true") {
											addGoalRightWithAnimation();
											highlight = "OH DAMN IT'S A GOAL (test)";
										
										} else {
											highlight = "THAT SCUMBAG HAS SAVED IT (test)";
										}
										addRight(minute, 'Penalty', highlight, 'penalty');
									} else {
										
										//Recursively call the method
										displayPenaltyOnLeftSide(timeLeftInMs-timeTakenInMs, isGoal, minute);
									}
								});
								
								
							});
						});
					});
				});
			});
		});
	}); 
}

function displayPenaltyOnRightSide(timeLeftInMs, isGoal, minute) {
	// Change the highlight
	$('#overlay-highlight-left').fadeOut(50, function() {
		$('#overlay-highlight-left').text('PENALTY');
		$('#overlay-highlight-left').fadeIn(200);
	});
	$('#overlay-white-line-right').fadeIn(20, function() {
		// Animation complete
		$('#overlay-zero-line-right').fadeIn(200, function() {
			$('#overlay-first-line-right').fadeIn(200, function() {
				$('#overlay-second-line-right').fadeIn(200, function() {
					$('#overlay-zero-line-right').fadeOut(200);
					$('#overlay-third-line-right').fadeIn(200, function() {
						$('#overlay-first-line-right').fadeOut(200);
						$('#overlay-fourth-line-right').fadeIn(200, function() {
							$('#overlay-second-line-right').fadeOut(200);
							$('#overlay-arrow-line-right').fadeIn(200, function() {
								$('#overlay-third-line-right').fadeOut(200);
								$('#overlay-fourth-line-right').fadeOut(200);
								$('#overlay-arrow-line-right').fadeOut(200, function () {
									// total =  6 elements * 200ms 
									var timeTakenInMs = 1200;
									
									if ((timeLeftInMs - timeTakenInMs) < 0) {
										
										if (isGoal == "true") {
											addGoalLeftWithAnimation();
											highlight = "OH DAMN IT'S A GOAL (test)";
										
										} else {
											highlight = "THAT SCUMBAG HAS SAVED IT (test)";
										}
										addLeft(minute, 'Penalty', highlight, 'penalty');
									} else {
										
										//Recursively call the method
										displayPenaltyOnRightSide(timeLeftInMs-timeTakenInMs, isGoal, minute);
									}
								});
								
								
							});
						});
					});
				});
			});
		});
	}); 
}


var isLastLeft = true;
function addLeft(minute, title, highlight, type) {
	if (type == "goal") {
		
		var html = "<div class='cd-timeline-block'>" +
			"<div class='cd-timeline-img cd-picture'>" +
			"<img src='img/handball-ball.png' style='width: auto; height:auto; margin-left: -50%; margin-top: -50%;' alt='Goal'>"+
			"</div> <!-- cd-timeline-img -->" +

			"<div class='cd-timeline-content bounce-in'>"+
				"<h2>" + title + "</h2>"+
				"<p>" + highlight + "</p>" +
				"<span class='cd-date'>" + minute + " minute</span>"+
			"</div> <!-- cd-timeline-content -->"+
		"</div> <!-- cd-timeline-block -->";
	} else if (type == "penalty") {
		var html = "<div class='cd-timeline-block'>" +
		"<div class='cd-timeline-img cd-picture' style='background: #DF1C3A'>" +
		"<img src='img/misc-whistle.svg' style='width: auto; height:auto; margin-left: -50%; margin-top: -50%;' alt='Penalty'>"+
		"</div> <!-- cd-timeline-img -->" +

		"<div class='cd-timeline-content bounce-in'>"+
			"<h2>" + title + "</h2>"+
			"<p>" + highlight + "</p>" +
			"<span class='cd-date'>" + minute + " minute</span>"+
		"</div> <!-- cd-timeline-content -->"+
		"</div> <!-- cd-timeline-block -->";
	} else if (type == "free-kick") {
		var html = "<div class='cd-timeline-block'>" +
		"<div class='cd-timeline-img cd-picture' style='background: #F5AB49'>" +
		"<i class='huge crosshairs icon' style='margin-left: -7%; margin-top: 5%;'></i>"+
		"</div> <!-- cd-timeline-img -->" +
	
		"<div class='cd-timeline-content bounce-in'>"+
			"<h2>" + title + "</h2>"+
			"<p>" + highlight + "</p>" +
			"<span class='cd-date'>" + minute + " minute</span>"+
		"</div> <!-- cd-timeline-content -->"+
	"</div> <!-- cd-timeline-block -->";
	}
		if (isLastLeft) {
			newHiddenContent();
		}
		$('#cd-timeline div:first').before( $(html) );
		
		/*$(".cd-timeline-block").before( html );*/
		/*$("#cd-timeline-text").before(html);*/
	/*document.getElementById('cd-timeline').innerHTML = html + document.getElementById('cd-timeline').innerHTML;*/
}

function addRight(minute, title, highlight, type) {
	if (type == "goal") {
		var html = "<div class='cd-timeline-block'>" +
		"<div class='cd-timeline-img cd-picture'>" +
		"<img src='img/handball-ball.png' style='width: auto; height:auto; margin-left: -50%; margin-top: -50%;' alt='Goal'>"+
		"</div> <!-- cd-timeline-img -->" +
	
		"<div class='cd-timeline-content bounce-in'>"+
			"<h2>" + title + "</h2>"+
			"<p>" + highlight + "</p>" +
			"<span class='cd-date'>" + minute + " minute</span>"+
		"</div> <!-- cd-timeline-content -->"+
	"</div> <!-- cd-timeline-block -->";
	} else if (type == "penalty") {
		var html = "<div class='cd-timeline-block'>" +
		"<div class='cd-timeline-img cd-picture' style='background: #DF1C3A'>" +
		"<img src='img/misc-whistle.svg' style='width: auto; height:auto; margin-left: -50%; margin-top: -50%;' alt='Penalty'>"+
		"</div> <!-- cd-timeline-img -->" +
	
		"<div class='cd-timeline-content bounce-in'>"+
			"<h2>" + title + "</h2>"+
			"<p>" + highlight + "</p>" +
			"<span class='cd-date'>" + minute + " minute</span>"+
		"</div> <!-- cd-timeline-content -->"+
	"</div> <!-- cd-timeline-block -->";
	} else if (type == "free-kick") {
		var html = "<div class='cd-timeline-block'>" +
		"<div class='cd-timeline-img cd-picture' style='background: #F5AB49'>" +
		"<i class='huge crosshairs icon' style='margin-left: -7%; margin-top: 5%;'></i>"+
		"</div> <!-- cd-timeline-img -->" +
	
		"<div class='cd-timeline-content bounce-in'>"+
			"<h2>" + title + "</h2>"+
			"<p>" + highlight + "</p>" +
			"<span class='cd-date'>" + minute + " minute</span>"+
		"</div> <!-- cd-timeline-content -->"+
	"</div> <!-- cd-timeline-block -->";
	}
		
		if (!isLastLeft) {
			newHiddenContent();	
			$('#cd-timeline div:first').before( $(html) );
			newHiddenContent();	
		} else {
			
			$('#cd-timeline div:first').before( $(html) );
			newHiddenContent();
		}
}

function newHiddenContent() {


		
			var html = "<div class='cd-timeline-block' style='display:none;'>" +
			"<div class='cd-timeline-img cd-picture is-hidden'>" +
				"<img src='img/cd-icon-picture.svg' alt='Picture'>"+
			"</div> <!-- cd-timeline-img -->" +

			"<div class='cd-timeline-content is-hidden'>"+
				"<h2>Title of section 1</h2>"+
				"<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Iusto, optio, dolorum provident rerum aut hic quasi placeat iure tempora laudantium ipsa ad debitis unde? Iste voluptatibus minus veritatis qui ut.</p>" +
				"<a href='#0' class='cd-read-more'>Read more</a>"+
				"<span class='cd-date'>Jan 14 </span>"+
			"</div> <!-- cd-timeline-content -->"+
		"</div> <!-- cd-timeline-block -->";
		$('#cd-timeline div:first').before( $(html) );


}