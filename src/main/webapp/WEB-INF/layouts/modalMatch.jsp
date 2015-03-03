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
    		  <img style="height: 120px; width: 80%; margin-left: auto; margin-right: auto; display: block; " src="/hb/img/handball_v3.png">
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

<script>
$('#modalMatch').on('hide.bs.modal', function () {
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
				 if (s.contains("goal=home")) {
					String[] splitMinuteTime = splitHighlight[0].split("=");
					String minute = splitMinuteTime[1];
					//setTimeout(function(){ alert("Hello"); }, 3000);
					out.println("var timeoutId = setTimeout(function() { addLeft('" + minute + "', 'goal', 'incredible goal by...'); }," + (countAddedElements * 2000) + ");");
					countAddedElements++;
				 }  else if (s.contains("goal=away")) {
					String[] splitMinuteTime = splitHighlight[0].split("=");
					String minute = splitMinuteTime[1];
					out.println("var timeoutId = setTimeout(function() { addRight('" + minute + "', 'goal', 'incredible goal by...'); }," + (countAddedElements * 2000) + ");");
					countAddedElements++;
				 }  
				/* s += "<br>"; */
				/* out.println("$('#highlights').append('" + s + "')"); */
				
			}
			
			
		} %>
		
		/* alert('game over'); */
		// pass all the highlights to an animation queue or smthg like that
<%	} else {  
		if (matchHighlightsList.isEmpty()) {%>
			var updateNumber = 1;
		<%} else {%>
			var updateNumber = <%=matchHighlightsList.size()%>;
		<%} // END else (matchHighlightList is not empty)%>
	
	/* alert('match started'); */
	<%-- alert('<%=matchHighlightsList.size()%>'); --%>
	$.ajax({
		type : "POST",
		url : "getUpdate.html",
		data : JSON.stringify({
			"matchId" : <%=match.getMatch_id()%>,
			"updateNumber" : updateNumber
		}),
		contentType : "application/json; charset=UTF-8",
		success : function(data) {
			
			var parsedDataJSON = $.parseJSON(data);
			if (parsedDataJSON.status == "OK") {
				alert("OK");
			} else if (parsedDataJSON.status == "sessionExpired")
				alert('Session Expired');
		},
		error : function() {
			alert('Error occured when calling POST!');
		}
		
	
	});

			

<% 	} // END else (matchHighlightList.size() != numberOfTotalUpdates)
} // END if match is started %>

var isLastLeft = true;
function addLeft(minute, title, highlight) {
	
	var html = "<div class='cd-timeline-block'>" +
			"<div class='cd-timeline-img cd-picture'>" +
			"<img src='img/handball-ball.png' style='width: auto; height:auto; margin-left: -50%; margin-top: -50%;' alt='Picture'>"+
			"</div> <!-- cd-timeline-img -->" +

			"<div class='cd-timeline-content bounce-in'>"+
				"<h2>" + title + "</h2>"+
				"<p>" + highlight + "</p>" +
				"<span class='cd-date'>" + minute + " minute</span>"+
			"</div> <!-- cd-timeline-content -->"+
		"</div> <!-- cd-timeline-block -->";
		if (isLastLeft) {
			newHiddenContent();
		}
		$('#cd-timeline div:first').before( $(html) );
		
		/*$(".cd-timeline-block").before( html );*/
		/*$("#cd-timeline-text").before(html);*/
	/*document.getElementById('cd-timeline').innerHTML = html + document.getElementById('cd-timeline').innerHTML;*/
}

function addRight(minute, title, highlight) {
	var html = "<div class='cd-timeline-block'>" +
	"<div class='cd-timeline-img cd-picture'>" +
	"<img src='img/handball-ball.png' style='width: auto; height:auto; margin-left: -50%; margin-top: -50%;' alt='Picture'>"+
	"</div> <!-- cd-timeline-img -->" +

	"<div class='cd-timeline-content bounce-in'>"+
		"<h2>" + title + "</h2>"+
		"<p>" + highlight + "</p>" +
		"<span class='cd-date'>" + minute + " minute</span>"+
	"</div> <!-- cd-timeline-content -->"+
"</div> <!-- cd-timeline-block -->";
		
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



</script>