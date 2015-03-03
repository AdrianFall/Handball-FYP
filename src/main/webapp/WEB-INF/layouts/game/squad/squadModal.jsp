<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<link rel="stylesheet" href="/hb/css/metro-bootstrap.css">
<link rel="stylesheet" href="/hb/css/gradients.css">
<link rel="stylesheet" href="/hb/css/progress-bar.css">
<script type="text/javascript"
	src="/hb/scripts/ajax.getPlayerDetails.js"></script>
<script src="/hb/scripts/metro.min.js"></script>
<script type="text/javascript" src="/hb/scripts/progress-bar.js"></script>
<script type="text/javascript" src="/hb/scripts/playerDetailsNavigation.js">
</script>
<div class="modal fade" id="modalPlayerDetails" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
	style="">

	<div class="modal-dialog"
		style="width: 800px; height: 400px; position: relative;">
		<div class="modal-content"
			style="width: 800px; height: 400px; padding: 0;">

			<div
				style="overflow-x: auto; overflow-y: auto; background-image: url('/hb/img/player_details_container_v5.png'); background-repeat: no-repeat; background-size: 800px 400px; padding-left: 0%; padding-right: 0%; position: relative; height: 100%; width: 100%;">
				<!--   <div id="what" class = "sandia">Bringing the <span id="spanItalic">cloud</span>, <span id="spanBold">visualization</span> &amp; <span id="spanBold"><span id="spanItalic">cutting edge technology</span></span> to the energy industry</div> -->
				<!-- Close x button on top-right -->
				<div style="margin-right: 1.5%;">
					<button type="button"
						style="position: absolute; margin-left: 97.5%; margin-top: 9px; background: red;"
						class="close" data-dismiss="modal"
						data-target="#modalPlayerDetails">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
				</div>
				<!-- END Close x button on top-right -->

				<!-- START the player details -->

				<!-- Club name display -->

				<!-- START Positioning the Player Report  -->

				<div id="clubName"
					style="position: absolute; margin-left: 12%; margin-top: 50px; font-size: 100%"></div>
				<div id="playerName"
					style="position: absolute; margin-left: 10%; margin-top: 73px; font-size: 100%"></div>
				<div id="qualityLabel"
					style="position: absolute; margin-left: 9%; margin-top: 93px; font-size: 80%">Quality</div>
				<div id="positionLabel"
					style="position: absolute; margin-left: 17%; margin-top: 93px; font-size: 80%">Position</div>
				<div id="ageLabel"
					style="position: absolute; margin-left: 26%; margin-top: 93px; font-size: 80%">Age</div>
				<div id="playerQuality"
					style="position: absolute; margin-left: 9%; margin-top: 113px; font-size: 80% font-family:'Comic Sans MS', cursive, sans-serif; color: green;"></div>
				<div id="playerPosition"
					style="position: absolute; margin-left: 18.5%; margin-top: 113px; font-size: 80%;"></div>
				<div id="playerAge"
					style="position: absolute; margin-left: 26.5%; margin-top: 113px; font-size: 80%;"></div>
				<div id="marketValueLabel"
					style="position: absolute; margin-left: 8.5%; margin-top: 136px; font-size: 75%; font-family: 'Comic Sans MS', cursive, sans-serif; color: white;">Market
					Val</div>
				<div id="playerMarketValue"
					style="position: absolute; margin-left: 18.5%; margin-top: 136px; font-size: 75%; font-family: 'Comic Sans MS', cursive, sans-serif; color: #D1FF75;"></div>
				<div id="formLabel"
					style="position: absolute; margin-left: 9.5%; margin-top: 154px; font-size: 75%; font-family: 'Comic Sans MS', cursive, sans-serif; color: white;">Form</div>
				<div id="playerForm"
					style="position: absolute; margin-left: 20.5%; margin-top: 154px; font-size: 75%; font-family: 'Comic Sans MS', cursive, sans-serif; color: #D1FF75;"></div>
				<!-- END Positioning the Player Report -->


				<!-- Spinners for the duration of ajax post  -->
				<div id="physicalSectionSpinner"
					style="position: absolute; z-index: 2; margin-left: 75%; margin-top: 160px;"></div>
				<script>
					var opts = {
						lines : 11, // The number of lines to draw
						length : 18, // The length of each line
						width : 10, // The line thickness
						radius : 30, // The radius of the inner circle
						corners : 1, // Corner roundness (0..1)
						rotate : 0, // The rotation offset
						direction : 1, // 1: clockwise, -1: counterclockwise
						color : '#000', // #rgb or #rrggbb or array of colors
						speed : 0.8, // Rounds per second
						trail : 60, // Afterglow percentage
						shadow : true, // Whether to render a shadow
						hwaccel : false, // Whether to use hardware acceleration
						className : 'spinner', // The CSS class to assign to the spinner
						zIndex : 1000, // The z-index (defaults to 2000000000)
						top : '50%', // Top position relative to parent
						left : '50%' // Left position relative to parent
					};
					var target = document
							.getElementById('physicalSectionSpinner');
					var spinner = new Spinner(opts).spin(target);
				</script>

				<!-- END Spinners -->

				<!-- START Positioning the Player Skills -->

				<!-- Physical Skills -->
				<div id="physicalSectionLabel"
					style="display: none; position: absolute; margin-left: 70%; margin-top: 7px; font-size: 90%; font-style: bold;">Physical</div>
				<hr id="physicalLine"
					style="display: none; position: absolute; background-color: green; margin-left: 52%; margin-top: 21px; height: 1px; width: 330px;">

				<!--  <div id="accelerationLabel" style="position: absolute; display:none; margin-left: 53%; margin-top: 26px; font-size: 90%; display: none;">Acceleration</div>
				<div id="accelerationBlock" class="fadeable-block" style="margin-left: 69%; margin-top: 26px;"><div id="accelerationText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div> -->

				<div id="strengthLabel"
					style="position: absolute; margin-left: 52.9%; margin-top: 26px; font-size: 90%; display: none;">Strength</div>
				<div id="strengthBlock" class="fadeable-block"
					style="margin-left: 69%; margin-top: 26px;">
					<div id="strengthText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<div id="sprintSpeedLabel"
					style="position: absolute; margin-left: 74%; margin-top: 26px; font-size: 90%; display: none;">Sprint
					Speed</div>
				<div id="sprintSpeedBlock" class="fadeable-block"
					style="margin-left: 88.2%; margin-top: 26px;">
					<div id="sprintSpeedText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<div id="jumpingLabel"
					style="position: absolute; margin-left: 52.9%; margin-top: 47px; font-size: 90%; display: none;">Jumping</div>
				<div id="jumpingBlock" class="fadeable-block"
					style="margin-left: 69%; margin-top: 47px;">
					<div id="jumpingText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<div id="balanceLabel"
					style="position: absolute; margin-left: 74%; margin-top: 47px; font-size: 90%; display: none;">Balance</div>
				<div id="balanceBlock" class="fadeable-block"
					style="margin-left: 88.2%; margin-top: 47px;">
					<div id="balanceText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>


				<div id="blockingLabel"
					style="position: absolute; margin-left: 52.9%; margin-top: 68px; font-size: 90%; display: none;">Blocking</div>
				<div id="blockingBlock" class="fadeable-block"
					style="margin-left: 69%; margin-top: 68px;">
					<div id="blockingText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>
				<!-- <div id="agilityLabel" style="position: absolute; margin-left: 53%; margin-top: 68px; font-size: 90%; display: none;">Agility</div>
				<div id="agilityBlock" class="fadeable-block" style="margin-left: 69%; margin-top: 68px;"><div id="agilityText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div> -->

				<div id="staminaLabel"
					style="position: absolute; margin-left: 74%; margin-top: 68px; font-size: 90%; display: none;">Stamina</div>
				<div id="staminaBlock" class="fadeable-block"
					style="margin-left: 88.2%; margin-top: 68px;">
					<div id="staminaText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>



				<!-- <div id="reactionsLabel" style="position: absolute; margin-left: 74%; margin-top: 89px; font-size: 90%; display: none;">Reactions</div>
				<div id="reactionsBlock" class="fadeable-block" style="margin-left: 88.2%; margin-top: 89px;"><div id="reactionsText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div> -->



				<!-- <div id="fitnessLabel" style="position: absolute; margin-left: 74%; margin-top: 110px; font-size: 90%; display: none;">Fitness</div>
				<div id="fitnessBlock" class="fadeable-block" style="margin-left: 88.2%; margin-top: 110px;"><div id="fitnessText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div> -->

				<!-- END Physical Skills -->

				<!-- Mental Skills -->

				<div id="mentalSectionLabel"
					style="display: none; position: absolute; margin-left: 70%; margin-top: 88px; font-size: 90%; font-style: bold;">Mental</div>
				<hr id="mentalLine"
					style="display: none; position: absolute; background-color: blue; margin-left: 52%; margin-top: 102px; height: 1px; width: 330px;">

				<div id="aggressionLabel"
					style="position: absolute; margin-left: 52.9%; margin-top: 107px; font-size: 90%; display: none;">Aggression</div>
				<div id="aggressionBlock" class="fadeable-block"
					style="margin-left: 69%; margin-top: 107px;">
					<div id="aggressionText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>


				<div id="creativityLabel"
					style="position: absolute; margin-left: 74%; margin-top: 107px; font-size: 90%; display: none;">Creativity</div>
				<div id="creativityBlock" class="fadeable-block"
					style="margin-left: 88.2%; margin-top: 107px;">
					<div id="creativityText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>
				<!-- 
				<div id="interceptionsLabel" style="position: absolute; margin-left: 74%; margin-top: 148px; font-size: 90%; display: none;">Interceptions</div>
				<div id="interceptionsBlock" class="fadeable-block" style="margin-left: 88.2%; margin-top: 148px;"><div id="interceptionsText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div> -->

				<div id="attack_positionLabel"
					style="position: absolute; margin-left: 52.9%; margin-top: 128px; font-size: 90%; display: none;">Attack
					Positioning</div>
				<div id="attack_positionBlock" class="fadeable-block"
					style="margin-left: 69%; margin-top: 128px;">
					<div id="attack_positionText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<div id="visionLabel"
					style="position: absolute; margin-left: 74%; margin-top: 128px; font-size: 90%; display: none;">Vision</div>
				<div id="visionBlock" class="fadeable-block"
					style="margin-left: 88.2%; margin-top: 128px;">
					<div id="visionText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<!-- END Mental Skills -->

				<!-- Technical Skills -->

				<div id="technicalSectionLabel"
					style="display: none; position: absolute; margin-left: 70%; margin-top: 148px; font-size: 90%; font-style: bold;">Technical</div>
				<hr id="technicalLine"
					style="display: none; position: absolute; background-color: red; margin-left: 52%; margin-top: 162px; height: 1px; width: 330px;">

				<div id="catchingLabel"
					style="position: absolute; margin-left: 52.9%; margin-top: 167px; font-size: 90%; display: none;">Catching</div>
				<div id="catchingBlock" class="fadeable-block"
					style="margin-left: 69%; margin-top: 167px;">
					<div id="catchingText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<div id="shot_powerLabel"
					style="position: absolute; margin-left: 74%; margin-top: 167px; font-size: 90%; display: none;">Shot
					Power</div>
				<div id="shot_powerBlock" class="fadeable-block"
					style="margin-left: 88.2%; margin-top: 167px;">
					<div id="shot_powerText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<div id="dribblingLabel"
					style="position: absolute; margin-left: 52.9%; margin-top: 188px; font-size: 90%; display: none;">Dribbling</div>
				<div id="dribblingBlock" class="fadeable-block"
					style="margin-left: 69%; margin-top: 188px;">
					<div id="dribblingText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<div id="passingLabel"
					style="position: absolute; margin-left: 74%; margin-top: 188px; font-size: 90%; display: none;">Passing</div>
				<div id="passingBlock" class="fadeable-block"
					style="margin-left: 88.2%; margin-top: 188px;">
					<div id="passingText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>



				<div id="curveLabel"
					style="position: absolute; margin-left: 52.9%; margin-top: 209px; font-size: 90%; display: none;">Curve</div>
				<div id="curveBlock" class="fadeable-block"
					style="margin-left: 69%; margin-top: 209px;">
					<div id="curveText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<div id="penaltiesLabel"
					style="position: absolute; margin-left: 74%; margin-top: 209px; font-size: 90%; display: none;">Penalty
					Shots</div>
				<div id="penaltiesBlock" class="fadeable-block"
					style="margin-left: 88.2%; margin-top: 209px;">
					<div id="penaltiesText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>



				<div id="six_m_shotsLabel"
					style="position: absolute; margin-left: 52.9%; margin-top: 230px; font-size: 90%; display: none;">Six
					Meter Shots</div>
				<div id="six_m_shotsBlock" class="fadeable-block"
					style="margin-left: 69%; margin-top: 230px;">
					<div id="six_m_shotsText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<div id="nine_m_shotsLabel"
					style="position: absolute; margin-left: 74%; margin-top: 230px; font-size: 90%; display: none;">Nine
					Meter Shots</div>
				<div id="nine_m_shotsBlock" class="fadeable-block"
					style="margin-left: 88.2%; margin-top: 230px;">
					<div id="nine_m_shotsText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<div id="finishingLabel"
					style="position: absolute; margin-left: 52.9%; margin-top: 251px; font-size: 90%; display: none;">Finishing</div>
				<div id="finishingBlock" class="fadeable-block"
					style="margin-left: 69%; margin-top: 251px;">
					<div id="finishingText"
						style="display: none; margin-top: -1px; margin-left: 1px;">
						<font color="white" size="2" style="margin-bottom: 11px;"></font>
					</div>
				</div>

				<!-- END Technical Skills -->








				<!-- START Positioning the Goal Keeping Skills -->
				<div style="margin-top: 25px;">
					<div id="goalKeepingSectionLabel"
						style="display: none; position: absolute; margin-left: 70%; margin-top: 2px; font-size: 90%; font-style: bold;">Goal
						Keeping</div>
					<hr id="goalKeepingLine"
						style="display: none; position: absolute; background-color: green; margin-left: 52%; margin-top: 21px; height: 1px; width: 330px;">

					<div id="reflexesLabel"
						style="position: absolute; display: none; margin-left: 53%; margin-top: 26px; font-size: 90%; display: none;">Reflexes</div>
					<div id="reflexesBlock" class="fadeable-block"
						style="margin-left: 69%; margin-top: 26px;">
						<div id="reflexesText"
							style="display: none; margin-top: -1px; margin-left: 1px;">
							<font color="white" size="2" style="margin-bottom: 11px;"></font>
						</div>
					</div>


					<div id="nine_m_savesLabel"
						style="position: absolute; margin-left: 74%; margin-top: 26px; font-size: 90%; display: none;">Nine
						Meter Saves</div>
					<div id="nine_m_savesBlock" class="fadeable-block"
						style="margin-left: 88.2%; margin-top: 26px;">
						<div id="nine_m_savesText"
							style="display: none; margin-top: -1px; margin-left: 1px;">
							<font color="white" size="2" style="margin-bottom: 11px;"></font>
						</div>
					</div>
					<!-- <div id="handlingLabel" style="position: absolute; margin-left: 74%; margin-top: 26px; font-size: 90%; display: none;">Handling</div>
				<div id="handlingBlock" class="fadeable-block" style="margin-left: 88.2%; margin-top: 26px;"><div id="handlingText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div> -->

					<div id="positioningLabel"
						style="position: absolute; margin-left: 52.9%; margin-top: 47px; font-size: 90%; display: none;">Positioning</div>
					<div id="positioningBlock" class="fadeable-block"
						style="margin-left: 69%; margin-top: 47px;">
						<div id="positioningText"
							style="display: none; margin-top: -1px; margin-left: 1px;">
							<font color="white" size="2" style="margin-bottom: 11px;"></font>
						</div>
					</div>

					<div id="leg_savesLabel"
						style="position: absolute; margin-left: 74%; margin-top: 47px; font-size: 90%; display: none;">Leg
						Saves</div>
					<div id="leg_savesBlock" class="fadeable-block"
						style="margin-left: 88.2%; margin-top: 47px;">
						<div id="leg_savesText"
							style="display: none; margin-top: -1px; margin-left: 1px;">
							<font color="white" size="2" style="margin-bottom: 11px;"></font>
						</div>
					</div>

					<div id="penalty_savesLabel"
						style="position: absolute; margin-left: 53%; margin-top: 68px; font-size: 90%; display: none;">Penalty
						Saves</div>
					<div id="penalty_savesBlock" class="fadeable-block"
						style="margin-left: 69%; margin-top: 68px;">
						<div id="penalty_savesText"
							style="display: none; margin-top: -1px; margin-left: 1px;">
							<font color="white" size="2" style="margin-bottom: 11px;"></font>
						</div>
					</div>

					<div id="six_m_savesLabel"
						style="position: absolute; margin-left: 74%; margin-top: 68px; font-size: 90%; display: none;">Six
						Meter Saves</div>
					<div id="six_m_savesBlock" class="fadeable-block"
						style="margin-left: 88.2%; margin-top: 68px;">
						<div id="six_m_savesText"
							style="display: none; margin-top: -1px; margin-left: 1px;">
							<font color="white" size="2" style="margin-bottom: 11px;"></font>
						</div>
					</div>


				</div>
				<!-- END Goal Keeping Skills -->
				<div class="metro"
					style="width: 48%; height: 10px; margin-left: 15px; margin-top: 175px;">
					<nav class="navigation-bar dark"> <nav
						class="navigation-bar-content"> <span
						style="margin-left: 64px;" class="element-divider"></span> <a href="#" onclick="showDetails()"
						class="element">Details</a> <span class="element-divider"></span>
					<a onclick="showContract()" href="#" class="element">Contract</a> <span class="element-divider"></span>
					<a href="#" onclick="showHealth()" class="element">Health</a> <span class="element-divider"></span>
					</nav> </nav>
				</div>

				<!-- START the details  -->
				<div id="details" class="grad-blue"
					style="height: 165px; width: 382px; margin-left: 2%; margin-top: 46px; display: none;">
					
					<div style="margin-left: 28%; margin-top: -10px; font-size: 105%;">In
						club for:</div>
					<div id="inClubFor" style="margin-left: 59%; margin-top: -20px;">TBD</div>
					<div style="margin-left: 28%; margin-top: 1px; font-size: 105%;">Nationality:</div>
					<div id="nationality" style="margin-left: 59%; margin-top: -20px;">TBD</div>
					<div style="margin-left: 28%; margin-top: 1px; font-size: 105%;">Height:</div>
					<div id="height" style="margin-left: 59%; margin-top: -20px;">TBD</div>
					<div style="margin-left: 28%; margin-top: 1px; font-size: 105%;">Weight:</div>
					<div id="weight" style="margin-left: 59%; margin-top: -20px;">TBD</div>
					<div style="margin-left: 28%; margin-top: 1px; font-size: 105%;">Handed:</div>
					<div id="handed" style="margin-left: 59%; margin-top: -20px;">TBD</div>
					<div style="margin-left: 28%; margin-top: 1px; font-size: 105%;">Special
						Ability:</div>
					<div id="specialAbility"
						style="margin-left: 59%; margin-top: -20px;">TBD</div>
				</div>
				<!-- END the details -->


				<!-- Start the contract -->
				<div id="contract" class="grad-green"
					style="display: none; height: 165px; width: 382px; margin-left: 2%; margin-top: 36px;">
					

					<div id="seasonWageBox"
						style="border: 1px solid #B3CC9B; border-top: none; border-left: none; border-right: none; width: 100px; height: 20px; margin-left: 4%;">
						&nbsp;Season Wage
						<div id="seasonWage" style="margin-top: 4%; margin-left: 4%;">$40,000,000</div>
						<div style="margin-top: 4%; margin-left: 10%; font-size: 80%;">
							Sell or sack </br> player to free </br> up some space.
						</div>
						<div style="margin-top: 4%; margin-left: 20%;">
							<button type="button"
								style="height: 25px; font-size: 12px; padding: 0px 8px; font-weight: 0; margin: 0"
								class="btn btn-danger">Sack</button>
						</div>
					</div>

					<div id="marketValueBox"
						style="border: 1px solid #a5b596; border-top: none; border-left: none; border-right: none; width: 100px; height: 20px; margin-left: 34%; margin-top: -5.20%;">
						&nbsp;Market Value</div>
					<div id="marketValue" style="height: 20px; margin-top: 1%; margin-left: 34%;">$40,000,000</div>
					<div
						style="height: 50px; margin-left: 34%; margin-top: 1%; width: 100px; font-size: 80%;">'player
						name' is not transfer listed.</div>
					<div style="height: 25px; margin-left: 40%;">
						<button type="button"
							style="height: 25px; font-size: 12px; padding: 0px 8px; font-weight: 0; margin: 0"
							class="btn btn-success">Sell</button>
					</div>

					<div id="yearsLeftBox"
						style="border: 1px solid #a5b596; border-top: none; border-left: none; border-right: none; width: 100px; height: 20px; margin-left: 68%; margin-top: -32.20%;">
						&nbsp;Years left</div>
					<div id="yearsLeft"
						style="height: 74px; width: 110px; margin-top: 1%; margin-left: 68%;">3
						years left at Arsenal FC Club</div>

					<div style="height: 25px; margin-left: 71%;">
						<button type="button"
							style="height: 25px; font-size: 12px; padding: 0px 8px; font-weight: 0; margin: 0"
							class="btn btn-info">Renew</button>
					</div>


				</div>
				<!-- END the contract -->

				<!-- START the health -->

				<div id="health" class="grad-red"
					style="display: none; height: 165px; width: 382px; margin-left: 2%; margin-top: 36px;">


					<div id="injuryBox"
						style="border: 1px solid #B3CC9B; border-top: none; border-left: none; border-right: none; width: 100px; height: 20px; margin-left: 4%; text-align: center; margin-top: 0px;">
						Injury
						<div style="height: 60px; margin-top: 8%; margin-left: 4%;">
							<div id="injuryCause" style="height: 40px; margin-top: -8%; margin-left: 4%;">Ankle
								injury 12 days off</div>

							<div style="height: 20px; margin-top: 4%; margin-left: 20%;">
								<button type="button" id="hireDoctorButton"
									style="height: 25px; font-size: 12px; padding: 0px 8px; font-weight: 0; margin-left: -20px;"
									class="btn btn-danger">Hire Doctor</button>
							</div>

							<div id="hireDoctorText"
								style="height: 40px; width: 100px; margin-top: 12%; margin-left: -4%; font-size: 85%;">
								Hire a specialist doctor to reduce the player's injury length</div>
						</div>
					</div>

					<div id="conditionBox"
						style="border: 1px solid #B3CC9B; border-top: none; border-left: none; border-right: none; width: 100px; height: 20px; margin-left: 34%; text-align: center; margin-top: -20px;">
						Condition
						<div style="height: 60px; margin-top: -40%; margin-left: 4%;">
							<div style="height: 40px; margin-top: -8%; margin-left: 4%;">
								<!-- PROGRESS BAR -->
								<div id="conditionLoader" class="loader" style="margin-left: -5%;">
									<div id="conditionProgressBar" class="progress-bar" data-perc="100">
										<div id="conditionProgressStripes" class="progress-stripes"></div>
										<div id="conditionPercentage" class="percentage">0%</div>
									</div>
								</div>
								
								
								<script>
								
								</script>
								<!-- END PROGRESS BAR -->
							</div>

							<div style="height: 20px; margin-top: -8%; margin-left: 20%;">
								<button type="button"
									style="height: 25px; font-size: 12px; padding: 0px 8px; font-weight: 0; margin-left: -20px;"
									class="btn btn-success">Hire Therapist</button>
							</div>

							<div
								style="height: 40px; width: 100px; margin-top: 12%; margin-left: -4%; font-size: 85%;">
								Hire a physical therapist to improve the player's condition</div>
						</div>
					</div>
					
					<div id="moraleBox"
						style="border: 1px solid #B3CC9B; border-top: none; border-left: none; border-right: none; width: 100px; height: 20px; margin-left: 68%; text-align: center; margin-top: -20px;">
						Morale
						<div style="height: 60px; margin-top: -40%; margin-left: 4%;">
							<div style="height: 40px; margin-top: -8%; margin-left: 4%;">
								<!-- PROGRESS BAR -->
								<div id="moraleLoader" class="loader" style="margin-left: -5%;">
									<div id="moraleProgressBar" class="progress-bar" data-perc="70">
										<div id="moraleProgressStripes" class="progress-stripes"></div>
										<div id="moralePercentage" class="percentage">0%</div>
									</div>
								</div>
								
								
								<script>
								
								</script>
								<!-- END PROGRESS BAR -->
							</div>

							<div style="height: 20px; margin-top: -8%; margin-left: 20%;">
								<button type="button"
									style="height: 25px; font-size: 12px; padding: 0px 8px; font-weight: 0; margin-left: -20px;"
									class="btn btn-success">Hire Psych</button>
							</div>

							<div
								style="height: 40px; width: 100px; margin-top: 12%; margin-left: -4%; font-size: 85%;">
								Hire a psychologist to improve the player's morale</div>
						</div>
					</div>
					
					
					
					
					
					
					

				</div>

				<!-- END the health -->
			</div>

		</div>
	</div>
</div>