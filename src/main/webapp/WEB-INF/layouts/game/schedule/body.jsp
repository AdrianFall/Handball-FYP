<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="af.handball.entity.Match,af.handball.entity.MatchOutcome, java.util.List,java.util.ArrayList"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%!private String teamName;
   private int teamId;
	List<Match> matchList = new ArrayList<Match>();
	List<MatchOutcome> matchOutcomeList = new ArrayList<MatchOutcome>();%>
	
<% matchList = (List<Match>) session.getAttribute("matchList");
	matchOutcomeList = (List<MatchOutcome>) session.getAttribute("matchOutcomeList");
   teamId = (Integer) session.getAttribute("teamId");
   teamName = (String) session.getAttribute("teamName");%>
<script src="/hb/scripts/conflict.js"></script>

<% for (int i = 0; i < matchList.size(); i++) { 
	if (matchList.get(i).getHome_team() == teamId) { %>
		<div class="ui top attached tabular menu">
    		<div background-color: green;" class="active item"><%out.print(matchList.get(i).getMatch_date());%></div>
  		</div>
		<div class="ui bottom attached active tab segment">	
			<div class="ui grid">
			    <div class="four wide column"></div>
			    <div class="three wide column"><%=matchList.get(i).getHome_team_name()%></div>
			    <div class="two wide column">

				<%
					boolean matchOutcomeFound = false;
							String matchScore = "";
							for (int j = 0; j < matchOutcomeList.size(); j++) {
	
								if (matchOutcomeList.get(j).getMatch_id() == matchList
										.get(i).getMatch_id()) {
									matchOutcomeFound = true;
									matchScore = matchOutcomeList.get(j)
											.getMatch_score();
									break;
								} // END if matchOutCome.matchId == match.matchId
							} // END for (matchOutcomeList)
	
							if (matchOutcomeFound) {
								out.print("<div id='matchButton"  + i
										+ "' onClick='showMatch(" +matchList.get(i).getMatch_id() + ")' class ='ui button'>" + matchScore
										+ "</div>");
							} else {
								out.print("<div id='matchButton" + i
										+ "' class ='ui button'>VS</div>");
							}
				%>
	
			</div>
			    <div class="three wide column"><%=matchList.get(i).getAway_team_name()%></div>
			    <div class="four wide column"></div>
	  		</div>
		
<%	} else if (matchList.get(i).getAway_team() == teamId) { %>
		<div class="ui top attached tabular menu" style="">
    		<div style="background-color: green;" class="active item right"><%out.print(matchList.get(i).getMatch_date());%></div>
  		</div>
		<div class="ui bottom attached active tab segment" style="">	
		
	<div class="ui grid">
	    <div class="four wide column"></div>
	    <div class="three wide column"><%=matchList.get(i).getHome_team_name()%></div>
	    <div class="two wide column">
	    <%  boolean matchOutcomeFound = false;
	    	String matchScore = "";
	    	for (int j = 0; j < matchOutcomeList.size(); j++) {
	    		
	    		if (matchOutcomeList.get(j).getMatch_id() == matchList.get(i).getMatch_id()) {
	    			matchOutcomeFound = true;
	    			matchScore = matchOutcomeList.get(j).getMatch_score();
	    			break;
	    		} // END if matchOutCome.matchId == match.matchId
	    	} // END for (matchOutcomeList)
	    	
	    	if (matchOutcomeFound) {
	    		out.print("<div id='matchButton"  + i
						+ "' onClick='showMatch(" +matchList.get(i).getMatch_id() + ")' class ='ui button'>" + matchScore
						+ "</div>");
	    	} else {
	    		out.print("<div id='matchButton" + i + "' class ='ui button'>VS</div>");
	    	}	%>
	    	
	   
	  
	    
	    </div>
	    <div class="three wide column"><%=matchList.get(i).getAway_team_name()%></div>
	    <div class="four wide column"></div>
  	</div>
		  
		
<%	} else {
		out.print("ERROR.");
	}
	%>
	</div>
	<%out.print("<br>");	
   } // END for (matchList)	%>

    <p></p>
    <p></p>
  
<script> 	function showMatch(matchId) {
	alert('showMatch function');
	
	$.ajax({
		url: 'modalMatch.html', 
		data: {matchId: matchId},
		
	}).done(function (data) {
		alert('done');
		$('#modalMatchSpace').html(data);
		$('#modalMatch').bsModal('show');  
	});
} /* END function startMatch() */
</script>

