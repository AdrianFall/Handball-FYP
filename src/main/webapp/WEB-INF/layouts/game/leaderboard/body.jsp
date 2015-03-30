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
	   teamNameList = (List<String>) request.getAttribute("teamNameList");%>
	<% for (int i = 0; i < leaderboardTeamList.size(); i++) {
		out.println("Position " + (i+1) + " team name: " + teamNameList.get(i) + " <br>");
	}%>
	
<% } // END else (email != null) %>