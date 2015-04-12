<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="af.handball.entity.LeaderboardTeam, java.util.List,java.util.ArrayList"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%!private String email;
   private String teamName;
   private List<LeaderboardTeam> leaderboardTeamList;
   private List<String> teamNameList;
   %>
	

<%
	// Protect the website.
	email = (String) session.getAttribute("email");
	teamName = (String) session.getAttribute("teamName");
	if (email == null) {
%>
<script src="/hb/scripts/conflict.js"></script>

<script>$("#modalSessionExpired").hbModal('show');</script>

<% } else { %>
	<% leaderboardTeamList = (List<LeaderboardTeam>) request.getAttribute("leaderboardTeamList");
	   teamNameList = (List<String>) request.getAttribute("teamNameList");
	   out.println("<table class='leagueTable'>");
	   out.println("<thead><tr>");
	   
	   out.println("<th class='th pos' data-content='League Position'>pos</th>");
	   out.println("<th class='th team' data-content='Team Name'>team</th>");
	   out.println("<th class='th played' data-content='Matches Played'>p</th>");
	   out.println("<th class='th won' data-content='Matches Won'>w</th>");
	   out.println("<th class='th drew' data-content='Matches Drew'>d</th>");
	   out.println("<th class='th lost' data-content='Matches Lost'>l</th>");
	   out.println("<th class='th goalsFor' data-content='Goals For'>gf</th>");
	   out.println("<th class='th goalsAgainst' data-content='Goals Against'>ga</th>");
	   out.println("<th class='th goalsDiff' data-content='Goals Difference'>gd</th>");
	   out.println("<th class='th points' data-content='Points'>pts</th>");
	   
	   out.println("</tr></thead>");
	   out.println("<tbody>");%>
	<% 
	for (int i = 0; i < leaderboardTeamList.size(); i++) {
		if (teamNameList.get(i).equals(teamName)) { // Currently iterated team name is user's team
			out.println("<script>$('#leaguePosition').html('Position: " + (i+1) +  " ')</script>");
		}
		int trClass = 0; // 0 is the default
		if (i < 3)
			trClass = 1;
		else if (i > 9 && i < 12)
			trClass = 2;
		out.println("<tr class='accent" + trClass + "'>");
		out.println("<td class='col-pos'>" + (i+1) + "</td>");
		out.println("<td class='col-team'>"+  teamNameList.get(i) +"</td>");
		out.println("<td class='col-played'>"+ leaderboardTeamList.get(i).getMatches_played() +"</td>");
		out.println("<td class='col-won'>"+ leaderboardTeamList.get(i).getWins() +"</td>");
		out.println("<td class='col-drew'>"+ leaderboardTeamList.get(i).getDraws() +"</td>");
		out.println("<td class='col-lost'>"+ leaderboardTeamList.get(i).getLoses() +"</td>");
		out.println("<td class='col-goalsFor'>"+ leaderboardTeamList.get(i).getGoals_scored() +"</td>");
		out.println("<td class='col-goalsAgainst'>"+ leaderboardTeamList.get(i).getGoals_conceded() +"</td>");
		out.println("<td class='col-goalsDiff'>"+ leaderboardTeamList.get(i).getGoals_difference() +"</td>");
		out.println("<td class='col-points'>"+ leaderboardTeamList.get(i).getPoints() +"</td>");
	
		
		out.println("</tr>");
	}
	out.println("</tbody>");
	out.println("</table>");%>
	<script src="/hb/scripts/leaderboards/table.js"></script>
<% } // END else (email != null) %>