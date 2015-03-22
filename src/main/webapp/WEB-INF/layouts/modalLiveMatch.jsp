<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="af.handball.entity.Match,af.handball.entity.MatchOutcome, af.handball.entity.MatchHighlight, java.util.Map, java.util.List,java.util.ArrayList"%>
	

<%! private Match match; 
	private long timeLeftInMs;
	List<MatchHighlight> matchHighlightsList;%>


<% 	match = (Match) session.getAttribute("currentMatch");
	timeLeftInMs = (Long) session.getAttribute("timeLeftInMs");
	matchHighlightsList = (List<MatchHighlight>) session.getAttribute("matchHighlightList");
	
	if (match == null) { %>
		<script>alert("error when loading match.");</script>
<%	} else { %>
		<!-- if (!match.isMatch_started()) -->
		<script src="/hb/scripts/modernizr.js"></script> <!-- Modernizr -->
		<script src="/hb/scripts/vertical_timeline.js"></script> <!-- Resource jQuery -->

		<!-- Player Details Modal -->
		<div class="modal fade" id="modalLiveMatch" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog" style="width: 95%; height: 100%;">
		    <div class="modal-content" style="background-color: #EDAF61;">
		     <%--  <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title">Match <%=match.getHome_team_name()%> VS <%=match.getAway_team_name()%></h4>
		      </div> --%>
		      <button type="button" class="close" style="margin-right: 1%;" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		      <div style="width: 95%; height:10%;">
    		  	<img style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: block; z-index:1; position:absolute;" src="/hb/img/handball-pitchv4.png">
     		 	<img id="overlay-from-left" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/handball-pitchv4-overlay-from-left.png">
     		 	<img id="overlay-from-right" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/handball-pitchv4-overlay-from-right.png">
     		 	
     		 	<p id="overlay-highlight-left" style="height: 15px; width: 28%; text-align:left; margin-left: 25%; margin-top: 70px; display: none; z-index:3; position:absolute; font-size:14px; color: white; font-family: Impact, Charcoal, sans-serif;">IN POSSESSION</p>
     		 	<p id="overlay-highlight-right" style="height: 15px; width: 28%; text-align:right; margin-left: 46%; margin-top: 70px; display: none; z-index:3; position:absolute; font-size:14px; color: white; font-family: Impact, Charcoal, sans-serif;">IN POSSESSION</p>
     		 	
     		 	<p id="overlay-team-name-left" style="height: 15px; width: 28%; text-align:left; margin-left: 25%; margin-top: 50px; display: none; z-index:3; position:absolute; font-size:16px; color: #6EA8E2; font-family: Impact, Charcoal, sans-serif;"><%=match.getHome_team_name()%></p>
     		 	<p id="overlay-team-name-right" style="height: 15px; width: 28%; text-align:right; margin-left: 46%; margin-top: 50px; display: none; z-index:3; position:absolute; font-size:16px; color: #6EA8E2; font-family: Impact, Charcoal, sans-serif;"><%=match.getAway_team_name()%></p>
     		 	
     		 	
     		 	
     		 	<img id="overlay-white-line-left" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-white-line-left.png">
     		 	<img id="overlay-white-line-right" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-white-line-right.png">
     		 	<img id="overlay-zero-line-left" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-red-zero-line-left.png">
     		 	<img id="overlay-zero-line-right" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-red-zero-line-right.png">
     		 	
     		 	<img id="overlay-first-line-left" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-red-first-line-left.png">
     		 	<img id="overlay-first-line-right" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-red-first-line-right.png">
     		 	
     		 	<img id="overlay-second-line-left" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-red-second-line-left.png">
     		 	<img id="overlay-second-line-right" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-red-second-line-right.png">
     		 	
     		 	<img id="overlay-third-line-left" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-red-third-line-left.png">
     		 	<img id="overlay-third-line-right" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-red-third-line-right.png">
     		 	
     		 	<img id="overlay-fourth-line-left" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-red-fourth-line-left.png">
     		 	<img id="overlay-fourth-line-right" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-red-fourth-line-right.png">
     		 
     		 	<img id="overlay-arrow-line-left" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-arrow-line-left.png">
     		 	<img id="overlay-arrow-line-right" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-penalty-arrow-line-right.png">
     		 	
     		 	<img id="overlay-box" style="height: 180px; width: 90%; margin-left: 5%; margin-top:-1%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-box.png">
     		 	<img id="overlay-ball-center" style="height: 120px; width: 90%; margin-left: 5%; margin-top:2%; display: none; z-index:2; position:absolute;" src="/hb/img/live_match/handball-pitchv4-ball-center.png">
     			<p id="overlay-box-text" style="height: 15px; width: 28%; text-align:right; margin-left: 29%; margin-top: 50px; display: none; z-index:3; position:absolute; font-size:16px; color: #6EA8E2; font-family: Impact, Charcoal, sans-serif;">Game Finished.</p>
     			
     		 </div>
     		 <div id="scorebar" style="width:95%; height: 8%;">
     		 	<img style="height: 60px; width: 90%; margin-left: 5%; margin-top:137px; display: block; z-index:3; position:absolute;" src="/hb/img/live_match/scorebar.png">
     		 	<p id="scoreHome" style="height: 15px; width: 5%; margin-left: 45%; margin-top: 138px; display: block; z-index:3; position:absolute; font-size:20px; color: white; font-family: Impact, Charcoal, sans-serif;">0</p>
     		 	<p id="scoreAway" style="height: 15px; width: 5%; margin-left: 54%; margin-top: 138px; display: block; z-index:3; position:absolute; font-size:20px; color: white; font-family: Impact, Charcoal, sans-serif;">0</p>
     		 	<p style="height: 15px; width: 5%; margin-left: 6%; margin-top: 152px; display: block; z-index:3; position:absolute; font-size:16px; color: white; font-family: Impact, Charcoal, sans-serif;">Home</p>
     		 	<p style="height: 15px; width: 5%; margin-left: 89%; text-align: right; margin-top: 152px; display: block; z-index:3; position:absolute; font-size:16px; color: white; font-family: Impact, Charcoal, sans-serif;">Away</p>
     		 	<p id="homeTeamName" style="height: 15px; width: 40%; margin-left: 6%; margin-top: 172px; display: block; z-index:3; position:absolute; font-size:16px; color: white; font-family: Impact, Charcoal, sans-serif;"><%=match.getHome_team_name()%></p>
     		 	<p id="awayTeamName" style="height: 15px; width: 40%; text-align:right; margin-left: 54%; margin-top: 172px; display: block; z-index:3; position:absolute; font-size:16px; color: white; font-family: Impact, Charcoal, sans-serif;"><%=match.getAway_team_name()%></p>
     		 </div>
     		 
     		 <div id="highlights" style="width: 100%; margin-top: 180px;">
				<section id="cd-timeline" class="cd-container">
					<div class="cd-timeline-block">
						<div class="cd-timeline-img cd-picture">
							<i class="big wait icon" style="margin-left: 20%; margin-top: 25%;"></i>
						</div>
						<!-- cd-timeline-img -->

						<div class="cd-timeline-content">
							<h2>Match due to start..</h2>
							<!-- <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
								Iusto, optio, dolorum provident rerum aut hic quasi placeat iure
								tempora laudantium ipsa ad debitis unde? Iste voluptatibus minus
								veritatis qui ut.</p> -->
							<!-- <a href="#0" class="cd-read-more">Read more</a> --> 
							<!-- <span
								class="cd-date">Jan 14</span> -->
						</div>
						<!-- cd-timeline-content -->
					</div>
					<!-- cd-timeline-block -->
				</section>
				<!-- cd-timeline -->
			</div>
		   
			 
		      
		      
		     
		    </div> <!-- end modal content -->
		  </div>
		</div>
<!-- END Player Details Modal -->
<%	} // END else (match != null) %>
<script src="/hb/scripts/match-highlight.js"></script>
<script>

<% if (match.isMatch_started()) { 
	int countAddedElements = 1;
	int numberOfTotalUpdates = (match.getMatch_duration_in_seconds() / 60) * match.getUpdates_per_minute();
	
	if (matchHighlightsList.size() == numberOfTotalUpdates) {
		 for (MatchHighlight mh : matchHighlightsList) {
			String[] matchHighlightsTextSplit = mh.getHighlight_text().split(";");
			for (String s : matchHighlightsTextSplit) {
				String[] splitHighlight =  s.split(":");
				
					String[] splitMinuteTime = splitHighlight[0].split("=");
					String minute = splitMinuteTime[1];
					 if (s.contains("goal=home")) {
						String[] splitScorer = splitHighlight[2].split("=");
						String scorer = splitScorer[1];
						out.println("addGoalLeft();");
						out.println("addLeft('" + minute + "', 'Goal', 'incredible goal by " + scorer + "', 'goal');");
						countAddedElements++;
					 } else if (s.contains("goal=away")) {
						 String[] splitScorer = splitHighlight[2].split("=");
						 String scorer = splitScorer[1];
						 out.println("addGoalRight();");
					  	 out.println("addRight('" + minute + "', 'Goal', 'incredible goal by " + scorer + "', 'goal');");
					  	 countAddedElements++;
					 } else if (s.contains("penalty=home")) {
						 String[] splitPenaltyTaker = splitHighlight[4].split("=");
						 String penaltyTaker = splitPenaltyTaker[1];
						 String[] splitIsGoal = splitHighlight[6].split("=");
						 String isGoal = splitIsGoal[1];
						 
						 String highlight = "";
						 if (isGoal.equals("true")) {
							 out.println("addGoalLeft();");
							 highlight = penaltyTaker + " has converted the penalty into a GOAL!";
						 } else {
							 // TODO obtain the goal keeper from other side
							 
							 highlight = "Ohh.. what a SAVE!";
						 }
						 out.println("addLeft('" + minute + "', 'Penalty', '" + highlight + "', 'penalty');");
						 countAddedElements++;
					 } else if (s.contains("penalty=away")) {
						 String[] splitPenaltyTaker = splitHighlight[4].split("=");
						 String penaltyTaker = splitPenaltyTaker[1];
						 String[] splitIsGoal = splitHighlight[6].split("=");
						 String isGoal = splitIsGoal[1];
						 
						 String highlight = "";
						 if (isGoal.equals("true")) {
							 out.println("addGoalRight();");
							 highlight = penaltyTaker + " has converted the penalty into a GOAL!";
						 } else {
							 // TODO obtain the goal keeper from other side
							 
							 highlight = "Ohh.. what a SAVE!";
						 }
						 out.println("addRight('" + minute + "', 'Penalty', '" + highlight + "', 'penalty');");
						 countAddedElements++;
					 } else if (s.contains("free-kick=home")) {
						 String[] splitFreeKickTaker = splitHighlight[4].split("=");
						 String freeKickTaker = splitFreeKickTaker[1];
						 String[] splitIsGoal = splitHighlight[6].split("=");
						 String isGoal = splitIsGoal[1];
						 
						 String highlight = "";
						 if (isGoal.equals("true")) {
							 out.println("addGoalLeft();");
							 highlight = freeKickTaker + " was clinical and SCORED!";
						 } else {
							 // TODO obtain the goal keeper from other side
							 
							 highlight = "What a reflex from the keeper, it is a SAVE!";
						 }
						 out.println("addLeft('" + minute + "', 'Free-Kick', '" + highlight + "', 'free-kick');");
						 countAddedElements++;
						 
					 } else if (s.contains("free-kick=away")) {
						 String[] splitFreeKickTaker = splitHighlight[4].split("=");
						 String freeKickTaker = splitFreeKickTaker[1];
						 String[] splitIsGoal = splitHighlight[6].split("=");
						 String isGoal = splitIsGoal[1];
						 
						 String highlight = "";
						 if (isGoal.equals("true")) {
							 out.println("addGoalRight();");
							 highlight = freeKickTaker + " was clinical and SCORED!";
						 } else {
							 // TODO obtain the goal keeper from other side
							 
							 highlight = "What a reflex from the keeper, it is a SAVE!";
						 }
						 out.println(" addRight('" + minute + "', 'Free-Kick', '" + highlight + "', 'free-kick'); ");
						 countAddedElements++;
					 } else if (s.contains("save=home")) {
						 
					 } else if (s.contains("save=away")) {
						 
					 }
				
			} // END for(each string in matchHighlightsTextSplit)
			
			
		} // END for (each string in matchHighlightsList) %>
		
<%	} else {  
	
	if (matchHighlightsList.isEmpty()) {%>
		var updateNumber = 1;
	<%} else {%>
	
		var updateNumber = <%=matchHighlightsList.size()%>;
		
		/* Add all current highlights straight away, except the last one which will be fetched through update i.e. that's why updateNumber = matchHighlightList.size()*/
		 <%  for (int i = 0; i < matchHighlightsList.size() -1; i++) {
				String[] matchHighlightsTextSplit = matchHighlightsList.get(i).getHighlight_text().split(";");
				for (String s : matchHighlightsTextSplit) {
					String[] splitHighlight =  s.split(":");
					
						String[] splitMinuteTime = splitHighlight[0].split("=");
						String minute = splitMinuteTime[1];
						 if (s.contains("goal=home")) {
							String[] splitScorer = splitHighlight[2].split("=");
							String scorer = splitScorer[1];
							out.println("addGoalLeft();");
							out.println("addLeft('" + minute + "', 'Goal', 'incredible goal by " + scorer + "', 'goal');");
							countAddedElements++;
						 } else if (s.contains("goal=away")) {
							 String[] splitScorer = splitHighlight[2].split("=");
							 String scorer = splitScorer[1];
							 out.println("addGoalRight();");
						  	 out.println("addRight('" + minute + "', 'Goal', 'incredible goal by " + scorer + "', 'goal');");
						  	 countAddedElements++;
						 } else if (s.contains("penalty=home")) {
							 String[] splitPenaltyTaker = splitHighlight[4].split("=");
							 String penaltyTaker = splitPenaltyTaker[1];
							 String[] splitIsGoal = splitHighlight[6].split("=");
							 String isGoal = splitIsGoal[1];
							 
							 String highlight = "";
							 if (isGoal.equals("true")) {
								 out.println("addGoalLeft();");
								 highlight = penaltyTaker + " has converted the penalty into a GOAL!";
							 } else {
								 // TODO obtain the goal keeper from other side
								 
								 highlight = "Ohh.. what a SAVE!";
							 }
							 out.println("addLeft('" + minute + "', 'Penalty', '" + highlight + "', 'penalty');");
							 countAddedElements++;
						 } else if (s.contains("penalty=away")) {
							 String[] splitPenaltyTaker = splitHighlight[4].split("=");
							 String penaltyTaker = splitPenaltyTaker[1];
							 String[] splitIsGoal = splitHighlight[6].split("=");
							 String isGoal = splitIsGoal[1];
							 
							 String highlight = "";
							 if (isGoal.equals("true")) {
								 out.println("addGoalRight();");
								 highlight = penaltyTaker + " has converted the penalty into a GOAL!";
							 } else {
								 // TODO obtain the goal keeper from other side
								 
								 highlight = "Ohh.. what a SAVE!";
							 }
							 out.println("addRight('" + minute + "', 'Penalty', '" + highlight + "', 'penalty');");
							 countAddedElements++;
						 } else if (s.contains("free-kick=home")) {
							 String[] splitFreeKickTaker = splitHighlight[4].split("=");
							 String freeKickTaker = splitFreeKickTaker[1];
							 String[] splitIsGoal = splitHighlight[6].split("=");
							 String isGoal = splitIsGoal[1];
							 
							 String highlight = "";
							 if (isGoal.equals("true")) {
								 out.println("addGoalLeft();");
								 highlight = freeKickTaker + " was clinical and SCORED!";
							 } else {
								 // TODO obtain the goal keeper from other side
								 
								 highlight = "What a reflex from the keeper, it is a SAVE!";
							 }
							 out.println("addLeft('" + minute + "', 'Free-Kick', '" + highlight + "', 'free-kick');");
							 countAddedElements++;
							 
						 } else if (s.contains("free-kick=away")) {
							 String[] splitFreeKickTaker = splitHighlight[4].split("=");
							 String freeKickTaker = splitFreeKickTaker[1];
							 String[] splitIsGoal = splitHighlight[6].split("=");
							 String isGoal = splitIsGoal[1];
							 
							 String highlight = "";
							 if (isGoal.equals("true")) {
								 out.println("addGoalRight();");
								 highlight = freeKickTaker + " was clinical and SCORED!";
							 } else {
								 // TODO obtain the goal keeper from other side
								 
								 highlight = "What a reflex from the keeper, it is a SAVE!";
							 }
							 out.println(" addRight('" + minute + "', 'Free-Kick', '" + highlight + "', 'free-kick'); ");
							 countAddedElements++;
						 } else if (s.contains("save=home")) {
							 
						 } else if (s.contains("save=away")) {
							 
						 }
					
				} // END for(each string in matchHighlightsTextSplit)
				
				
			} // END for (matchHighlighList - 1) 
				
		%>
		
		
	<%} // END else (matchHighlightList is not empty)%>

	getAllUpdates(updateNumber, <%=numberOfTotalUpdates%>, 1);
	
	/* alert('match started'); */
	<%-- alert('<%=matchHighlightsList.size()%>'); --%>
	
	
	
	

		

<% 	} // END else (matchHighlightList.size() != numberOfTotalUpdates)
} // END if match is started %>

function getAllUpdates(currentUpdateNo, numberOfTotalUpdates, attemptNumber) {
	var updateNumber = currentUpdateNo;
	/* START FETCHING THE UPDATES till updateNumber+1 == numberOfTotalUpdates */
	if (updateNumber == (numberOfTotalUpdates+1)) {
		/* Finished */
		/* TODO - cancel the overlays and overlay something to indicate the match has finished  */
		/* alert('finished on update number: ' + updateNumber + '. Number of total updates: ' + numberOfTotalUpdates + '.'); */
		finishGame();
	} else {
		var updated = getUpdate(updateNumber);
		if (updated == "OK") {
			
			updateNumber++;
			setTimeout(function() { getAllUpdates(updateNumber, numberOfTotalUpdates, 1);}, 31000);
		} else if (updated == "ERROR") {
			/* Attempt to obtain */
			if (attemptNumber > 20) {
				/* TODO display error */
				alert('attemptNumber > 20. ERROR.');
			} else {
				setTimeout(function() { getAllUpdates(updateNumber, numberOfTotalUpdates, attemptNumber + 1);}, 2000);
			}
		} /* END else if (updated == "ERROR") */
	} /* END else ((updateNumber+1) != numberOfTotalUpdates) */
}

function getUpdate(updateNo) {
	var updated = "ERROR";
	$.ajax({
		type : "POST",
		url : "getUpdate.html",
		async: false,
		data : JSON.stringify({
			"matchId" : <%=match.getMatch_id()%>,
			"updateNumber" : updateNo
		}),
		contentType : "application/json; charset=UTF-8",
		success : function(data) {
			
			var parsedDataJSON = $.parseJSON(data);
			if (parsedDataJSON.status == "OK") {
				/* alert("UPDATE " + updateNo + " OK"); */
				updated = "OK";
				/* alert("highlight = " + parsedDataJSON.highlight); */
				processHighlight(parsedDataJSON.highlight);
			} else if (parsedDataJSON.status == "sessionExpired") {
				alert('Session Expired');
			}
		},
		error : function() {
			alert('Error occured when calling POST!');
			updated = "ERROR";
		}
		
	
	});
	return updated;
}

</script>