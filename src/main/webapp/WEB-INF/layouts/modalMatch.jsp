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
		<div class="modal fade" id="modalMatch" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog" style="width: 95%; height: 100%;">
		    <div class="modal-content" style="background-color: #DDD0D0;">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title">Match <%=match.getHome_team_name()%> VS <%=match.getAway_team_name()%></h4>
		      </div>
		      
		      <div style="width: 95%; height:10%;">
    		  	<img style="height: 120px; width: 80%; margin-left: 10%; display: block; z-index:1; position:absolute;" src="/hb/img/handball-pitchv4.png">
     		 	<img id="overlay-from-left" style="height: 120px; width: 80%; margin-left: 10%; display: none; z-index:2; position:absolute;" src="/hb/img/handball-pitchv4-overlay-from-left.png">
     		 	<img id="overlay-from-right" style="height: 120px; width: 80%; margin-left: 10%; display: none; z-index:2; position:absolute;" src="/hb/img/handball-pitchv4-overlay-from-right.png">
     		 </div>
     		 
     		 <div id="highlights" style="width: 100%;">
				<section id="cd-timeline" class="cd-container">
					<div class="cd-timeline-block">
						<div class="cd-timeline-img cd-picture">
							<i class="big wait icon" style="margin-left: 20%; margin-top: 25%;"></i>
						</div>
						<!-- cd-timeline-img -->

						<div class="cd-timeline-content">
							<h2>Match due to start..</h2>
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
$('#modalMatch').on('hide.bs.modal', function () {
	/* Clear all timeouts for highlights  */
	while (typeof timeoutId !== 'undefined' && timeoutId--) {
	    window.clearTimeout(timeoutId); // will do nothing if no timeout with id is present
	}
});
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
						out.println("var timeoutId = setTimeout(function() { addLeft('" + minute + "', 'Goal', 'incredible goal by " + scorer + "', 'goal'); }," + (countAddedElements * 2000) + ");");
						countAddedElements++;
					 } else if (s.contains("goal=away")) {
						 String[] splitScorer = splitHighlight[2].split("=");
						 String scorer = splitScorer[1];
					  	 out.println("var timeoutId = setTimeout(function() { addRight('" + minute + "', 'Goal', 'incredible goal by " + scorer + "', 'goal'); }," + (countAddedElements * 2000) + ");");
					  	 countAddedElements++;
					 } else if (s.contains("penalty=home")) {
						 String[] splitPenaltyTaker = splitHighlight[4].split("=");
						 String penaltyTaker = splitPenaltyTaker[1];
						 String[] splitIsGoal = splitHighlight[6].split("=");
						 String isGoal = splitIsGoal[1];
						 
						 String highlight = "";
						 if (isGoal.equals("true")) {
							 highlight = penaltyTaker + " has converted the penalty into a GOAL!";
						 } else {
							 // TODO obtain the goal keeper from other side
							 
							 highlight = "Ohh.. what a SAVE!";
						 }
						 out.println("var timeoutId = setTimeout(function() { addLeft('" + minute + "', 'Penalty', '" + highlight + "', 'penalty'); }," + (countAddedElements * 2000) + ");");
						 countAddedElements++;
					 } else if (s.contains("penalty=away")) {
						 String[] splitPenaltyTaker = splitHighlight[4].split("=");
						 String penaltyTaker = splitPenaltyTaker[1];
						 String[] splitIsGoal = splitHighlight[6].split("=");
						 String isGoal = splitIsGoal[1];
						 
						 String highlight = "";
						 if (isGoal.equals("true")) {
							 highlight = penaltyTaker + " has converted the penalty into a GOAL!";
						 } else {
							 // TODO obtain the goal keeper from other side
							 
							 highlight = "Ohh.. what a SAVE!";
						 }
						 out.println("var timeoutId = setTimeout(function() { addRight('" + minute + "', 'Penalty', '" + highlight + "', 'penalty'); }," + (countAddedElements * 2000) + ");");
						 countAddedElements++;
					 } else if (s.contains("free-kick=home")) {
						 String[] splitFreeKickTaker = splitHighlight[4].split("=");
						 String freeKickTaker = splitFreeKickTaker[1];
						 String[] splitIsGoal = splitHighlight[6].split("=");
						 String isGoal = splitIsGoal[1];
						 
						 String highlight = "";
						 if (isGoal.equals("true")) {
							 highlight = freeKickTaker + " was clinical and SCORED!";
						 } else {
							 // TODO obtain the goal keeper from other side
							 
							 highlight = "What a reflex from the keeper, it is a SAVE!";
						 }
						 out.println("var timeoutId = setTimeout(function() { addLeft('" + minute + "', 'Free-Kick', '" + highlight + "', 'free-kick'); }," + (countAddedElements * 2000) + ");");
						 countAddedElements++;
						 
					 } else if (s.contains("free-kick=away")) {
						 String[] splitFreeKickTaker = splitHighlight[4].split("=");
						 String freeKickTaker = splitFreeKickTaker[1];
						 String[] splitIsGoal = splitHighlight[6].split("=");
						 String isGoal = splitIsGoal[1];
						 
						 String highlight = "";
						 if (isGoal.equals("true")) {
							 highlight = freeKickTaker + " was clinical and SCORED!";
						 } else {
							 // TODO obtain the goal keeper from other side
							 
							 highlight = "What a reflex from the keeper, it is a SAVE!";
						 }
						 out.println("var timeoutId = setTimeout(function() { addRight('" + minute + "', 'Free-Kick', '" + highlight + "', 'free-kick'); }," + (countAddedElements * 2000) + ");");
						 countAddedElements++;
					 } else if (s.contains("save=home")) {
						 
					 } else if (s.contains("save=away")) {
						 
					 }
					
				
					// Example of highlight text
					// "possession=away:minute=1;
					//  goal=away:minute=2:scorer=J.Lozano:scorerId=104283;
					//  possession=home:minute=2;
					//  penalty=home:minute=4:causedBy=R.Cavalehro:causedById=102345:taker=C.Rodrigo:takerId=10321:isGoal=false;
					//  suspension=away:minute=4:suspensionFor=R.Cavalehro:suspensionForId=102345:causedFoulOn=M.Miyaichi:causedFoulOnId=15435;
					//  possession=away:minute=5;
					//  free-kick=away:minute=6:causedBy=R.Cavalehro:causedById=102345:taker=A.Fall:takerId=1337:isGoal=true;
					//  possession=home:minute=7;"
				 
				/* s += "<br>"; */
				/* out.println("$('#highlights').append('" + s + "')"); */
				
			} // END for(each string in matchHighlightsTextSplit)
			
			
		} // END for (each string in matchHighlightsList) %>
		
		/* alert('game over'); */
		// pass all the highlights to an animation queue or smthg like that
<%	} 
} // END if match is started %>





</script>