<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="af.handball.entity.Match,af.handball.entity.MatchOutcome, java.util.Map, java.util.List,java.util.ArrayList"%>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
    
    
    
<%! private String teamName; 
	private String email;
	private List<Match> matchList;
	private List<MatchOutcome> matchOutcomeList;
	private Map<String, Object> nextMatchMap;
	private String nextMatchHomeTeamName;
	private String nextMatchAwayTeamName;
	private long timeLeftInMs;%>
    
<% if (session.getAttribute("matchList") != null && session.getAttribute("nextMatchMap") != null) {
	
		matchList = (List<Match>) session.getAttribute("matchList");
		nextMatchMap = (Map<String, Object>) session.getAttribute("nextMatchMap");
		timeLeftInMs = (Long) nextMatchMap.get("timeLeftInMs");	
} %>
<div id="matchPanel" class="ui ignored error message"
	style="position: absolute; margin-left: 20%; width: 35%; height: 84%; opacity: 0.9; filter: alpha(opacity = 90);">
	
	<div style="height: 33%; width: 100%;">
     <div style="text-align: center; font-size: 180%; margin-top: -3%;">Next Game:</div>
    </div>
	
	<div class="ui grid">
    
    
    <div class="row" style="height: 33%;">
     
      <div class="seven wide column"><div style="font-size: 170%; margin-top: 10%; text-align: right; font: bold; text-overflow:ellipsis; white-space: nowrap;
	overflow: hidden;"><%=nextMatchMap.get("homeTeamName") %></div></div>
      <div class="two wide column"><div style="font-size: 170%; margin-top: 66%; text-align: center; font: bold; color: black;">V</div></div>
      <div class="seven wide column"><div style="font-size: 170%; margin-top: 10%; text-align: left; font: bold; text-overflow:ellipsis; white-space: nowrap;
	overflow: hidden;"><%=nextMatchMap.get("awayTeamName") %></div></div>
      
    </div>
    
    <div class="row" style="height: 33%;">
    
      <div class="two wide column"></div>
      <div id="matchCountdown" class="twelve wide column" style="text-align: center;"></div>
      <div class="two wide column"></div>
      
    </div>
  </div><!-- END ui grid -->
  

</div>

<script type="text/javascript" src="/hb/scripts/jquery.countdown.js"></script>
<script>
	//get the current date & time
	var dateObj = Date.now();

	// Add 3 days to the current date & time
	//   I'd suggest using the calculated static value instead of doing inline math
	//   I did it this way to simply show where the number came from
	var timeLeftInMs = <%= nextMatchMap.get("timeLeftInMs") %>;
	dateObj += timeLeftInMs;

	// create a new Date object, using the adjusted time
	dateObj = new Date(dateObj);
	$("#matchCountdown").countdown(dateObj, function(event) {
		/* $(this).text(event.strftime('%D days %H:%M:%S')); */
		
		
		
		
		
		
		 var format = '%H:%M:%S';
		 if(event.offset.days > 0) {
		      format = '%-d day%!d ' + format;
		    }
		    if(event.offset.weeks > 0) {
		      format = '%-w week%!w ' + format;
		    }
		   $(this).html(event.strftime(format));
		 })
		 .on('finish.countdown', function(event) {
		   	$(this).html("<a href='#' onclick='startMatch()' >Match Started!</a>");
			/* Obtain the initial html data */
		     $.ajax({
				url: 'modalLiveMatch.html',
				data: {matchId: "<%=nextMatchMap.get("matchId")%>"},
				
			}).done(function (data) {
				$('#modalLiveMatchSpace').html(data);
		
			}); /* END ajax post */
		}); /* END on('finish.countdown') */
	
 	function startMatch() {
		alert('startMatch function');
		
		
			$('#modalLiveMatch').modal('show'); 
		
	} /* END function startMatch() */
	
</script>
