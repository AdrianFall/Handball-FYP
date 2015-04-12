<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="af.handball.entity.Match,af.handball.entity.MatchOutcome,af.handball.entity.Team,java.util.Map, java.util.List,java.util.ArrayList"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%! private String teamName; 
	private String email;
	private List<Match> matchList;
	private List<MatchOutcome> matchOutcomeList;
	private Map<String, Object> nextMatchMap;
	private String nextMatchHomeTeamName;
	private String nextMatchAwayTeamName;
	private Team team;%>

<% email = (String) session.getAttribute("email"); 
	team = (Team) session.getAttribute("team");
   teamName = (String) session.getAttribute("teamName"); 
   if (email == null) { %>  
   	<script src="/hb/scripts/conflict.js"></script>
	<script>$("#modalSessionExpired").bsModal('show');</script> 
 	<%} else {%>
   
<!-- Load the page  -->
<script src="/hb/scripts/game/loadPage.js"></script>

	

<div id="loadingAnimation" align="center" class="fond">
	<div class="contener_general">
		<div class="contener_mixte">
			<div class="ballcolor ball_1">&nbsp;</div>
		</div>
		<div class="contener_mixte">
			<div class="ballcolor ball_2">&nbsp;</div>
		</div>
		<div class="contener_mixte">
			<div class="ballcolor ball_3">&nbsp;</div>
		</div>
		<div class="contener_mixte">
			<div class="ballcolor ball_4">&nbsp;</div>
		</div>
	</div>

	<div style="padding-top: 35px;">
	
		
	</div>
</div>

<div id="sidebar" class="ui left vertical inverted labeled icon uncover visible sidebar menu">
   
	<a class="item"> <i class="home icon"></i> Home</a>
	<a id="squadHref" class="item"><i id="squadIcon" class="edit icon"></i> Formation</a> 
	<!-- <a class="item"> <i class="smile icon"></i> Friends</a>  -->
	<a id="scheduleHref" class="item"> <i id="scheduleIcon" class="calendar icon"></i> Schedule</a> 
	<a id="leaderboardHref" class="item"> <i id="leaderboardIcon" class="trophy icon"></i> Leaderboards</a>
	<a class="item"> <i class="mail icon"></i> Inbox TBD</a> 
	<!-- <a class="item"> <i class="chat icon"></i> Discussions</a>  -->
	<a class="item"> <i class="shop icon"></i> Auction TBD</a>
	<a href="logout.html" class="item"> <i class="sign out icon"></i> Logout</a>
</div>


	
<div style="padding-left: 10%; padding-right: 10%; position: relative;" id="container">
	
<div id="match">
</div>

	<!-- TABLE ROW -->
	<div class="row" id="infoTable">
	
			<table class="table" style="background-color: #D95C5C;" >
				<thead>
					<tr style="background-color: #B74D4D;">
						<th>Team: <% if (teamName != null) out.print(teamName); %></th>
							<th width="30%;">&nbsp;</th>
							<th>League</th>
						</tr> 
					</thead>
					<tbody>
						<tr>
							<td><div class="popup" data-content="Transfer Funds"><i class="large dollar icon"></i><%=team.getMoney()%></div></td>
							<td>&nbsp;</td>
							<td id="leaguePosition">Position: ...</td>
						</tr>
						
						<tr>
							<td><div class="popup" data-content="Fans"><i class="large child icon"></i><%=team.getFans()%></div></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						
					</tbody>
				</table>
	
	<!-- END OF TABLE ROW  -->
	</div>
</div>
	
<div class="container-fluid" id="squadSettings" style="position: absolute; height:70%; padding-left: 10%; padding-right: 10%; width: 100%;"></div>
<div class="container-fluid" id="schedule" style="position: absolute; height:70%; padding-left: 10%; padding-right: 10%; width: 100%;"></div>
<div class="container-fluid" id="leaderboard" style="position: absolute; height:70%; padding-left: 10%; padding-right: 10%; width: 100%;"></div>



<!-- Register the navigation click event handlers  -->
<script src="/hb/scripts/game/gameNavigation.js"></script>
<% } %>