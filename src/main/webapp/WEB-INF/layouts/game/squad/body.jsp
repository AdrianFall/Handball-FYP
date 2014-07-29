<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="af.handball.entity.Player,java.util.List,java.util.ArrayList,java.util.HashMap,af.handball.entity.Skill,java.util.Map"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head><script type="text/javascript" src="/hb/scripts/jquery.knob.js"></script>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.1.1/angular.min.js"></script>
<script type="text/javascript" src="/hb/scripts/jquery.easy-pie-chart.js"></script>
</head>
<body>
<%!private String teamName;
	private String email;
	private List<Player> playerList;
	private int countFirstSquadPlayers = 0;
	private int countBenchPlayers = 0;
	private int countReservesPlayers = 0;
	ArrayList<Player> firstSquadPlayersList = new ArrayList<Player>();
	ArrayList<Player> benchPlayersList = new ArrayList<Player>();
	ArrayList<Player> reservesList = new ArrayList<Player>();
	private Map<String, Skill> allPlayersSkills = new HashMap<String, Skill>();%>

<%
	// Protect the website.
	email = (String) session.getAttribute("email");
	teamName = (String) session.getAttribute("teamName");
	if (email == null) {
%>

<script>
	$("#modalSessionExpired").modal('show');
</script>

<%
	} else {
		firstSquadPlayersList = new ArrayList<Player>();
		benchPlayersList = new ArrayList<Player>();
		reservesList = new ArrayList<Player>();
%>

<br>
<div>
	<div
		style="width: 60%; max-width: 60%; min-width: 1%; left: 10%; float: left;">
		<!-- <button class="btn-success has-spinner">
    <span class="spinner"><i class="icon-spin icon-refresh"></i></span>
    Foo
  </button> -->
		<!-- TABLE ROW -->

		<table id="squadTable" class="table table-bordered table-hover"
			style="-webkit-user-select: none; -khtml-user-select: none; -moz-user-select: none; -o-user-select: none; user-select: none; cursor: default;">
			<thead>
				<tr class="danger">
					<th class="th" style="text-align: center" title="Player Number">No</th>
					<th class="th" title="Current Position of player"
						style="text-align: center">Position</th>
					<th class="th" title="Player Name" style="text-align: center">Name</th>
					<th class="th" title="Player status (injured, red card, etc.)"
						style="text-align: center">Status</th>
					<th class="th" title="Special Ability of a player"
						style="text-align: center">Spec.</th>
					<th class="th" title="Player Age" style="text-align: center">Age</th>
					<th class="th" title="Overall quality of a player"
						style="text-align: center">Qlty</th>
				</tr>
			</thead>

			<% 
			playerList = (List<Player>) request.getAttribute("playerList");
			allPlayersSkills = (Map<String, Skill>) request.getAttribute("allPlayersSkills");
			
			countFirstSquadPlayers = 0;
			countBenchPlayers = 0;
			countReservesPlayers = 0;
			firstSquadPlayersList = new ArrayList<Player>();
			benchPlayersList = new ArrayList<Player>();
			reservesList = new ArrayList<Player>();
			for (int i = 0; i < playerList.size(); i++) {
				if (playerList.get(i).getFormation().equals(Player.FORMATION_FIRST_SQUAD)) {
					countFirstSquadPlayers++;%>
			<%firstSquadPlayersList.add(playerList.get(i));
				}
				else if (playerList.get(i).getFormation().equals(Player.FORMATION_BENCH)) {
					++countBenchPlayers;
					benchPlayersList.add(playerList.get(i));
				}
				else if (playerList.get(i).getFormation().equals(Player.FORMATION_RESERVES)) {
					++countReservesPlayers;
					reservesList.add(playerList.get(i));
				}
			}%>

			<!-- LOOP for first squad players -->
			<% 
			for (int i = 0; i < countFirstSquadPlayers; i++) { %>

			<tr class="success" id="fsqp_tr<%=i%>">

				<td id="fsqp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="fsqp_number<%=i%>" style='display: none;' class="center">
						<div id="fsqp_pn<%=firstSquadPlayersList.get(i).getNumber()%>"><%=firstSquadPlayersList.get(i).getNumber()%></div>
					</div>

				</td>

				<td id="fsqp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="fsqp_position<%=i%>" style='display: none;' class="center"><%=firstSquadPlayersList.get(i).getPlay_position()%></div>
				</td>

				<td id="fsqp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="fsqp_name<%=i%>" style='display: none;' class="center"><%=firstSquadPlayersList.get(i).getName()%></div>
				</td>

				<td id="fsqp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="status<%=i%>" style='display: none;' class="center">TBD</div>
				</td>

				<td id="fsqp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<!--  TBD - PICTURE -->
					<div id="speciality<%=i%>" style='display: none;' class="center">TBD</div>
				</td>

				<td id="fsqp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="fsqp_age<%=i%>" style='display: none;' class="center"><%=firstSquadPlayersList.get(i).getAge()%></div>
				</td>

				<td id="fsqp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="fsqp_quality<%=i%>" style='display: none;' class="center"><%=firstSquadPlayersList.get(i).getPlayer_quality() %></div>
				</td>

				<td id="fsqp_td<%=i%>" class="td" style="display: none;">
					<div id="fsqp_marketValue<%=i%>" style="display: none;"><%= firstSquadPlayersList.get(i).getMarket_value()%></div>
				</td>

				<td id="fsqp_td<%=i%>" class="td" style="display: none;">
					<div id="fsqp_form<%=i%>" style="display: none;">
						<% if(firstSquadPlayersList.get(i).getForm() == 0) out.print("N/A"); else out.print(firstSquadPlayersList.get(i).getForm());%>
					</div>
				</td>

				<td id="fsqp_td<%=i%>" class="td" style="display: none;">
					<div id="fsqp_id<%=i%>" style="display: none;"><%= firstSquadPlayersList.get(i).getPlayer_id()%></div>
				</td>
			</tr>
			<!-- END loop for first squad players -->
			<% } %>

			<% 
			for (int i = 0; i < countBenchPlayers; i++) { %>
			<tr class="warning" id="bp_tr<%=i%>">

				<td class="td" id="bp_td<%=i%>"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="bp_number<%=i%>" style='display: none;' class="center">
						<div id="bp_pn<%=benchPlayersList.get(i).getNumber()%>"><%=benchPlayersList.get(i).getNumber()%></div>
					</div>
				</td>

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="bp_position<%=i%>" style='display: none;' class="center"><%=benchPlayersList.get(i).getPlay_position()%></div>
				</td>

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="bp_name<%=i%>" style='display: none;' class="center"><%=benchPlayersList.get(i).getName()%></div>
				</td>

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="status<%=i%>" style='display: none;' class="center">TBD</div>
				</td>

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<!--  TBD - PICTURE -->
					<div id="bp_speciality<%=i%>" style='display: none;' class="center">TBD</div>
				</td>

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="bp_age<%=i%>" style='display: none;' class="center"><%=benchPlayersList.get(i).getAge()%></div>
				</td>

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="bp_quality<%=i%>" style='display: none;' class="center"><%=benchPlayersList.get(i).getPlayer_quality() %></div>
				</td>

				<td id="bp_td<%=i%>" class="td" style="display: none;">
					<div id="bp_marketValue<%=i%>" style="display: none;"><%= benchPlayersList.get(i).getMarket_value()%></div>
				</td>

				<td id="bp_td<%=i%>" class="td" style="display: none;">
					<div id="bp_form<%=i%>" style="display: none;"><%= benchPlayersList.get(i).getForm()%></div>
				</td>

				<td id="bp_td<%=i%>" class="td" style="display: none;">
					<div id="bp_id<%=i%>" style="display: none;"><%= benchPlayersList.get(i).getPlayer_id()%></div>
				</td>
			</tr>
			<!-- END loop for bench players -->
			<% } %>


			<% 
			for (int i = 0; i < countReservesPlayers; i++) { %>
			<tr class="danger" id="rp_tr<%=i%>">

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="number<%=i%>" style='display: none;' class="center"><%=reservesList.get(i).getNumber()%></div>
				</td>

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="position<%=i%>" style='display: none;' class="center"><%=reservesList.get(i).getPlay_position()%></div>
				</td>

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="name<%=i%>" style='display: none;' class="center"><%=reservesList.get(i).getName()%></div>
				</td>

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="status<%=i%>" style='display: none;' class="center">TBD</div>
				</td>

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<!--  TBD - PICTURE -->
					<div id="speciality<%=i%>" style='display: none;' class="center">TBD</div>
				</td>

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="quality<%=i%>" style='display: none;' class="center"><%=reservesList.get(i).getPlayer_quality() %></div>
				</td>

			</tr>
			<!-- END loop for reserves players -->
			<% } %>




		</table>
	</div>

	<!-- START Table with the pitch -->

	<table id="pitchTable"
		style="background-image: url('/hb/img/handball_playfield.png'); height: 10%; width: 30%; background-size: 100% 100%; background-repeat: no-repeat; border-bottom: none; -webkit-user-select: none; -khtml-user-select: none; -moz-user-select: none; -o-user-select: none; user-select: none; cursor: default; position: fixed; right: 10%;">

		<tr height="45">
			<td class="droppable" id="LW" width="20%">
				<div style="position: relative;">
					<img style="position: absolute; top: -11px; left: 5px;"
						src="/hb/img/field_player_back_shirt.png" height="30" width="28">

					<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
					<% if(firstSquadPlayersList.get(n).getPlay_position().equals("LW")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 13px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 16px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

				</div>

			</td>
			<td></td>
			<td class="droppable" id="PV" width="15%">
				<div style="position: relative;">
					<img style="position: absolute; top: -11px; left: 7px;"
						src="/hb/img/field_player_back_shirt.png" height="30" width="28">

					<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
					<% if(firstSquadPlayersList.get(n).getPlay_position().equals("PV")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 15px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 18px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

				</div>
			</td>
			<td></td>
			<td class="droppable" id="RW" width="10%">
				<div style="position: relative; top: -11px;">
					<img style="position: absolute;"
						src="/hb/img/field_player_back_shirt.png" height="30" width="28">

					<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
					<% if(firstSquadPlayersList.get(n).getPlay_position().equals("RW")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 8px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 11px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

				</div>
			</td>
		</tr>



		<tr height="35">
			<td></td>
			<td class="droppable" width="15%" id="LB"><div
					style="position: relative;">
					<img style="position: absolute; top: -10px; left: 5px;"
						src="/hb/img/field_player_back_shirt.png" height="30" width="28">

					<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
					<% if(firstSquadPlayersList.get(n).getPlay_position().equals("LB")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 13px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 16px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

				</div></td>
			<td class="droppable" id="CB"><div style="position: relative;">
					<img style="position: absolute; top: -10px; left: 7px;"
						src="/hb/img/field_player_back_shirt.png" height="30" width="28">

					<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
					<% if(firstSquadPlayersList.get(n).getPlay_position().equals("CB")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 15px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 18px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

				</div></td>
			<td class="droppable" width="15%" id="RB">
				<div style="position: relative;">
					<img style="position: absolute; top: -10px; left: 20px;"
						src="/hb/img/field_player_back_shirt.png" height="30" width="28">

					<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
					<% if(firstSquadPlayersList.get(n).getPlay_position().equals("RB")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 28px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 31px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

				</div>
			</td>
			<td></td>
		</tr>


		<tr height="30">
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>

		<tr height="42">
			<td></td>
			<td></td>
			<td class="droppable" id="GK"><div style="position: relative;">
					<img style="position: absolute; left: 7px; top: -8px;"
						src="/hb/img/gk_back_shirt.png" height="30" width="28">

					<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
					<% if(firstSquadPlayersList.get(n).getPlay_position().equals("GK")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -18px; left: 15px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -18px; left: 18px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>
					<%-- <h3
							style="position: absolute; color: white; font-size: 72%; bottom: -24px; left: 11px;">
							<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
								 <% if(firstSquadPlayersList.get(n).getPlay_position().equals("GK")) 
									out.println(firstSquadPlayersList.get(n).getNumber()); 
								 
							} %>
						</h3> --%>
				</div></td>
			<td></td>
			<td></td>
		</tr>

	</table>

</div>




<!-- Scripts animating the slide down of the table with player list -->
<script>

/* Animate the first squad players */
<% for (int i = 0; i < firstSquadPlayersList.size(); i++)  { %>
$('#fsqp_td<%=i%> div').slideUp(
		<%=(100 * (i+(1-i*0.2)))%>
		);
		
		$('#fsqp_td<%=i%> div').slideDown(
		<%=(580 * (i+(1-i*0.2)))%>
		);
	
	<%-- 	alert("firstSquadPlayersList size = " + <%=firstSquadPlayersList.size()%>);
		if ($('#fsqp_td<%=i%>').data('draggable')) {
	        alert("yes");
		}
		else {
	        alert("no");
		} --%>
		<%-- $("#fsqp_tr<%=i%>").draggable( "destroy" ); --%>
		/* var refresh = $('#refresh');
		refresh.html(xmlhttp.responseText); */
		$('#fsqp_tr<%=i%>').draggable({
			  containment: 'holder',
	         helper: function(event) {
	        	 var d = document.getElementById("pitchTable");
				d.className = "table-bordered";
	        	 var fsqp_position = document.getElementById ( "fsqp_position<%=i%>" ).innerText;
	        	 var dragged_number = $('#fsqp_td<%=i%>').text();
	             var shirtStyle = $('<div style="position: absolute; margin:0;">' + '<img src="/hb/img/field_player_back_shirt.png" height="38" width="42" style="position:relative; margin:0; z-index:1;">' + '<h3 style="position: relative; margin-top: -30px; margin-left:8px; font-size: 16px; color:white; z-index:2; ">' + dragged_number + '</h3></div>');
	             if (new String("GK").valueOf() == new String(fsqp_position).valueOf()) shirtStyle = $('<div style="position: absolute; margin: 0;">' + '<img src="/hb/img/gk_back_shirt.png" height="38" width="42" style="position:relative; margin: 0px; z-index:1;">' + '<h3 style="position:relative; margin-top: -30px; margin-left: 8px; font-size:16px; color:white; z-index:2;">' + 'y' + dragged_number + '</h3></img></div>');
	             
	            	 return shirtStyle
	             /* return $('<div class="drag-row"><table></table></div>') */
	             /* return $('<img src="/hb/img/field_player_back_shirt.png" height="35" width="35" style="position:fixed; top: 0px; left:0; right:0; z-index:100;width:50px;">') */
	
	          },
		 
		 cursorAt: {
			    top: 20,
			    left: 20
		 },
		 stop: function( event, ui ) {
			 var d = document.getElementById("pitchTable");
			d.className = "";
		 }
	     });
		// Register the click listener on each row of first squad players
		  $("#fsqp_tr<%=i%>").click(function() {
			  
			  var player = {
  		            teamName: "<%=teamName%>",
  		            playerName: $("#fsqp_name<%=i%>").text(),
  		            playerQuality: $("#fsqp_quality<%=i%>").text(),
  		            playerPosition: $("#fsqp_position<%=i%>").text(),
  		            playerAge: $("#fsqp_age<%=i%>").text(),
  		            playerMarketValue: $('#fsqp_marketValue<%=i%>').text(),
  		            playerForm: $('#fsqp_form<%=i%>').text(),
  		            playerId: $('#fsqp_id<%=i%>').text()
  		        };
			  $("#modalPlayerDetails")
				.modal(
						'show');
			  
		    	$('#clubName').text(player.teamName);
			  	$('#playerName').text(player.playerName);
			  	$('#playerQuality').text(player.playerQuality);
			  	$('#playerPosition').text(player.playerPosition);
			  	$('#playerAge').text(player.playerAge);
			  	$('#playerMarketValue').text('$' + player.playerMarketValue);
			  	$('#playerForm').text(player.playerForm);
			  
			  	
				showPlayerDetailsModal(player.playerId, player.playerPosition);
			  		
			  	
			});
		
		
		 
<% } %>
/* END Animate the first squad players */

/* Animate the bench squad players */
  
  <% for (int i = 0; i < benchPlayersList.size(); i++) { %>
  $('#bp_td<%=i%> div').slideUp(
			<%=(100 * (i+(1-i*0.2)))%>
			);
  	  // Perform a slide down animation on page load
	  $('#bp_td<%=i%> div').slideDown(
			<%=(400 * (i+firstSquadPlayersList.size()+(1-(i+firstSquadPlayersList.size())*0.2)))%>
			);
  	  var playerNumber = $("#bp_tr<%=i%> td bp_number<%=i%>");
  	  // Register draggable to each row of bench squad players
	  $("#bp_tr<%=i%>").draggable({
	         helper: function(event) {
	        	 var d = document.getElementById("pitchTable");
					d.className = "table-bordered";
	             /* return $('<div class="drag-row"><table></table></div>') */
	             var position = $("#bp_position<%=i%>").text();
	             var dragged_number = $('#bp_td<%=i%>').text();
	             var shirtStyle = $('<div style="position: absolute; margin:0;">' + '<img src="/hb/img/field_player_back_shirt.png" height="38" width="42" style="position:relative; margin:0; z-index:1;">' + '<h3 style="position: relative; margin-top: -30px; margin-left:12px; font-size: 16px; color:white; z-index:2; ">' + dragged_number + '</h3></div>');
	             if (new String("GK").valueOf() == new String(position).valueOf()) shirtStyle = $('<div style="position: absolute; margin: 0;">' + '<img src="/hb/img/gk_back_shirt.png" height="38" width="42" style="position:relative; margin: 0px; z-index:1;">' + '<h3 style="position:relative; margin-top: -30px; margin-left: 12px; font-size:16px; color:white; z-index:2;">' + dragged_number + '</h3></img></div>');
	             
	            	 return shirtStyle

	          },
		 
		 cursorAt: {
			    top: 20,
			    left: 20
		 },
		 stop: function( event, ui ) {
			 var d = document.getElementById("pitchTable");
			d.className = "";
		 }
	     });
	  
	  // Register the click listener on each row of bench squad players
	  $("#bp_tr<%=i%>").click(function() {
		  alert( "Implementing." );
		});
  <% } %>
/* END Animate the bench squad players */

</script>
<!-- END Scripts animating the slide down of the player list -->



<script>
	/* START droppable */
	$('.droppable').droppable({
		drop : handleUIDropEvent
	});

	function handleUIDropEvent(event, ui) {
		
		alert(this.id);
		var draggable = ui.draggable;
		var draggableAttribute = draggable.attr('id');
		var splitDraggableAttribute = draggableAttribute.split("tr");
		var draggableAttributeType = splitDraggableAttribute[0];
		var draggableAttributeIndex = splitDraggableAttribute[1];
		
		var fieldPlayerNumber = $('#' + this.id).text();
		alert (fieldPlayerNumber);
		
		// Obtain the player number and figure out which table tr/td holds the player number record
		var trNode = $('#fsqp_pn' + $.trim(fieldPlayerNumber)).parent().parent().parent();
		
		/* var fsqp = document.getElementById('fsqp_pn' + fieldPlayerNumber); */
		
		
		 var splitTrNode = trNode.attr('id').split("tr"); 
		 var trType = splitTrNode[0];
		var trIndex = splitTrNode[1];
		

		
		// Animate the slide up of the interchanged rows
		 $('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideUp('slow');
		 $('#' + trType + 'td' + trIndex + ' div').slideUp('slow');
	
		 <% // TODO Major issue !! When reserves are implemented change the assumption that a change always leads to getting the dragged player to first squad !!!!! %>

 		// Obtain the current values of first squad/dragged players
 		var firstSquadPlayerNumber = $('#' + $.trim(trType) + 'number' + $.trim(trIndex)).text(); /* document.getElementById(trType + 'number' + trIndex).innerText; */
		var draggedPlayerNumber = $('#' + $.trim(draggableAttributeType) + 'number' + $.trim(draggableAttributeIndex)).text(); /* document.getElementById(draggableAttributeType + 'number' + draggableAttributeIndex).innerText; */
		// END obtaining the current values of first squad/dragged players
		
		// Swap the values of each record on the table, as well as the number on the shirt (pitch representation of team)
		
		// Obtain the elements corresponding the player numbers
		var swapFirstSquadPlayerNumber = $('#fsqp_pn' + $.trim(fieldPlayerNumber));
		var swapDraggedPlayerNumber = $('#' + $.trim(draggableAttributeType) + 'pn' + $.trim(draggedPlayerNumber));
		// Swap the numbers
		swapFirstSquadPlayerNumber.parent().html("<div id='fsqp_pn" + $.trim(draggedPlayerNumber)+"'>" + $.trim(draggedPlayerNumber)+"</div>");
		swapDraggedPlayerNumber.parent().html("<div id='" + $.trim(draggableAttributeType) + "pn" + $.trim(firstSquadPlayerNumber) + "'>" + $.trim(firstSquadPlayerNumber) + "</div>");  
		 
		// Obtain the elements corresponding to the player position
		var swapFirstSquadPlayerPosition = $('#fsqp_position' + $.trim(trIndex));
		var tempFirstSquadPlayerPosition = swapFirstSquadPlayerPosition.html();
		var swapDraggedPlayerPosition = $('#' + $.trim(draggableAttributeType) + 'position' + $.trim(draggableAttributeIndex));
		var tempDraggedPlayerPosition = swapDraggedPlayerPosition.html();
		// Swap the positions
		swapFirstSquadPlayerPosition.html($.trim(tempDraggedPlayerPosition));
		swapDraggedPlayerPosition.html($.trim(tempFirstSquadPlayerPosition));
		
		
		// Obtain the elements corresponding to player names
		  var swapFirstSquadPlayerName = $('#fsqp_name' + $.trim(trIndex)); 
		 var tempFirstSquadPlayerName = swapFirstSquadPlayerName.html();
		 var swapDraggedPlayerName = $('#' + draggableAttributeType + 'name' + draggableAttributeIndex);
		 var tempDraggedPlayerName = swapDraggedPlayerName.html();
		 // Swap the names
		 swapFirstSquadPlayerName.html(tempDraggedPlayerName);
		 swapDraggedPlayerName.html(tempFirstSquadPlayerName);
		 
		 // Obtain the elements corresponding to player market values
		 var swapFirstSquadPlayerMarketValue = $('#fsqp_marketValue' + $.trim(trIndex));
		 var tempFirstSquadPlayerMarketValue = swapFirstSquadPlayerMarketValue.html();
		 var swapDraggedPlayerMarketValue = $('#' + draggableAttributeType + 'marketValue' + draggableAttributeIndex);
		 var tempDraggedPlayerMarketValue = swapDraggedPlayerMarketValue.html();
		 // Swap the market values
		 swapFirstSquadPlayerMarketValue.html(tempDraggedPlayerMarketValue);
		 swapDraggedPlayerMarketValue.html(tempFirstSquadPlayerMarketValue);
		 
		 // Obtain the elements corresponding to the player age
		 var swapFirstSquadPlayerAge = $('#fsqp_age' + $.trim(trIndex));
		 var tempFirstSquadPlayerAge = swapFirstSquadPlayerAge.html();
		 var swapDraggedPlayerAge = $('#' + $.trim(draggableAttributeType) + 'age' + draggableAttributeIndex);
		 var tempDraggedPlayerAge = swapDraggedPlayerAge.html();
		 // Swap the ages
		 swapFirstSquadPlayerAge.html(tempDraggedPlayerAge);
		 swapDraggedPlayerAge.html(tempFirstSquadPlayerAge);
		 
		 // Obtain the elements corresponding to the player form
		 var swapFirstSquadPlayerForm = $('#fsqp_form' + $.trim(trIndex));
		 var tempFirstSquadPlayerForm = swapFirstSquadPlayerForm.html();
		 var swapDraggedPlayerForm = $('#' + $.trim(draggableAttributeType) + 'form' + draggableAttributeIndex);
		 var tempDraggedPlayerForm = swapDraggedPlayerForm.html();
		 // Swap the forms
		 swapFirstSquadPlayerForm.html(swapDraggedPlayerForm);
		 swapDraggedPlayerForm.html(tempFirstSquadPlayerForm);
		 
		 // Obtain the elements corresponding to the player id
		 var swapFirstSquadPlayerId = $('#fsqp_id' + $.trim(trIndex));
		 var tempFirstSquadPlayerId = swapFirstSquadPlayerId.html();
		 var swapDraggedPlayerId = $('#' + $.trim(draggableAttributeType) + 'id' + draggableAttributeIndex);
		 var tempdraggedPlayerId = swapDraggedPlayerId.html();
		 // Swap the ids
		 swapFirstSquadPlayerId.html(tempdraggedPlayerId);
		 swapDraggedPlayerId.html(tempFirstSquadPlayerId);
		 
		<%//TODO%> //Obtain the elements corresponding to status & speciality
		
		// Obtain the elements corresponding to player quality
		var swapFirstSquadPlayerQuality = $('#fsqp_quality' + trIndex); 
		var tempFirstSquadPlayerQuality = swapFirstSquadPlayerQuality.html();
		var swapDraggedPlayerQuality = $('#' + draggableAttributeType + 'quality' + draggableAttributeIndex);
		var tempDraggedPlayerQuality = swapDraggedPlayerQuality.html();
		// Swap the qualities
		swapFirstSquadPlayerQuality.html(tempDraggedPlayerQuality);
		swapDraggedPlayerQuality.html(tempFirstSquadPlayerQuality);
			
		
	 	
		//determine whether the dragged player is in first squad, if so do double swap of numbers
		if (new String(draggableAttributeType).valueOf() == new String("fsqp_").valueOf()) {
			var tempFirstSquadPlayerNumber = firstSquadPlayerNumber;
			var tempDraggedPlayerNumber = draggedPlayerNumber;
			
			
			$('#h3' + $.trim(tempFirstSquadPlayerNumber)).fadeOut(10);
			
				$('#h3' + $.trim(tempFirstSquadPlayerNumber)).text($.trim(tempDraggedPlayerNumber));
		
			$('#h3' + $.trim(tempFirstSquadPlayerNumber)).fadeIn(980);
			
			$('#h3' + $.trim(tempDraggedPlayerNumber)).fadeOut(10);
			$('#h3' + $.trim(tempDraggedPlayerNumber)).text($.trim(tempFirstSquadPlayerNumber)); 
			$('#h3' + $.trim(tempDraggedPlayerNumber)).fadeIn(680);
			
		
			var h3DraggedPlayer = $('#h3' + $.trim(tempDraggedPlayerNumber));
			var h3FirstSquadPlayer = $('#h3' + $.trim(tempFirstSquadPlayerNumber));
			h3DraggedPlayer.attr( "id",  "h3" + $.trim(tempFirstSquadPlayerNumber));
			h3FirstSquadPlayer.attr( "id",  "h3" + $.trim(tempDraggedPlayerNumber));
		
		} else {
		
			$('#h3' + $.trim(firstSquadPlayerNumber)).fadeOut(10);
		$('#h3' + $.trim(firstSquadPlayerNumber)).text($.trim(draggedPlayerNumber));
		$('#h3' + $.trim(firstSquadPlayerNumber)).fadeIn(980);
		
		/* $('#h3' + $.trim(draggedPlayerNumber)).text($.trim(firstSquadPlayerNumber)); */
		$('#h3' + $.trim(firstSquadPlayerNumber)).attr( "id",  "h3" + $.trim(draggedPlayerNumber));
		
		/* $('#h3' + $.trim(draggedPlayerNumber)).attr( "id",  "h3" + $.trim(firstSquadPlayerNumber));
		alert($('#h3' + $.trim(draggedPlayerNumber)).attr("id")); */
		}
	
		// END of swaping values for each record and shirt values
		
		$('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideDown('slow');
		$('#' + trType + 'td' + trIndex + ' div').slideDown('fast');
		
		/* document.getElementById('fsqp_number' + index).value = 'Test'; */
		/* alert('The square with ID "' + draggable.attr('id')
				+ '" was dropped onto me!'); */
		 
	}
	/* END droppable */
	/* setTimeout(function() { test(); }, 2000); */
	 
	
</script>

<!-- Player Details Modal -->
 
<div class="modal fade" id="modalPlayerDetails" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
	style="padding-left: 10px; padding-right: 10px;">
	
	<div class="modal-dialog"
		style="width: 100%; height: 100%; position: relative; padding-top: 0%;">
		<div class="modal-content"
			style="width: 830px; height: 400px; padding: 0;">

			<div
				style="overflow-x: auto; overflow-y: auto; background-image: url('/hb/img/player_details_container_v5.png'); background-repeat: no-repeat; background-size: 830px 400px; padding-left: 0%; padding-right: 0%; position: relative; height: 100%; width: 100%;">
				<!--   <div id="what" class = "sandia">Bringing the <span id="spanItalic">cloud</span>, <span id="spanBold">visualization</span> &amp; <span id="spanBold"><span id="spanItalic">cutting edge technology</span></span> to the energy industry</div> -->
				<!-- Close x button on top-right -->
				<div style="margin-right: 1.5%;">
					<button type="button"
						style="position: absolute; margin-left: 97.5%; margin-top: 9px; background: red;"
						class="close" data-dismiss="modal">
						<span "aria-hidden="true">&times;</span><span class="sr-only">Close</span>
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
				<div id="physicalSectionSpinner" style="position: absolute; z-index: 2; margin-left: 75%; margin-top: 160px;"></div>
				<script>var opts = {
						  lines: 11, // The number of lines to draw
						  length: 18, // The length of each line
						  width: 10, // The line thickness
						  radius: 30, // The radius of the inner circle
						  corners: 1, // Corner roundness (0..1)
						  rotate: 0, // The rotation offset
						  direction: 1, // 1: clockwise, -1: counterclockwise
						  color: '#000', // #rgb or #rrggbb or array of colors
						  speed: 0.8, // Rounds per second
						  trail: 60, // Afterglow percentage
						  shadow: true, // Whether to render a shadow
						  hwaccel: false, // Whether to use hardware acceleration
						  className: 'spinner', // The CSS class to assign to the spinner
						  zIndex: 1000, // The z-index (defaults to 2000000000)
						  top: '50%', // Top position relative to parent
						  left: '50%' // Left position relative to parent
						};
						var target = document.getElementById('physicalSectionSpinner');
						var spinner = new Spinner(opts).spin(target);</script>
				
				<!-- END Spinners -->
				
				<!-- START Positioning the Player Skills -->
				
				<div id="physicalSectionLabel" style="display: none; position: absolute; margin-left: 52%; margin-top: 15px; font-size: 90%; font-style: bold;">Physical</div>
				<hr id="physicalLine" style="display:none; position: absolute; background-color: green; margin-left: 52%; margin-top: 32px; height:1px; width: 330px;">
				
				 <div id="accelerationLabel" style="position: absolute; display:none; margin-left: 53%; margin-top: 34px; font-size: 90%; display: none;">Acceleration</div>
				
				<!-- <div id="accelerationBlock" style="position: absolute; border: 1px; margin-left: 69%; margin-top: 35px; width:22px; height: 17px; background: red;"><div style="margin-top: -1px; margin-left: 3px;"><font color="white" size="2" style="margin-bottom: 11px;">14</font></div></div></div> -->
				<div id="accelerationBlock" class="fadeable-block" style="margin-left: 69%; margin-top: 35px;"><div id="accelerationText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div>

				<div id="sprintSpeedLabel" style="position: absolute; margin-left: 74%; margin-top: 34px; font-size: 90%; display: none;">Sprint Speed</div>
				<div id="sprintSpeedBlock" class="fadeable-block" style="margin-left: 88.2%; margin-top: 34px;"><div id="sprintSpeedText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div>
				
				<div id="jumpingLabel" style="position: absolute; margin-left: 52.9%; margin-top: 55px; font-size: 90%; display: none;">Jumping</div>
				<div id="jumpingBlock" class="fadeable-block" style="margin-left: 69%; margin-top: 55px;"><div id="jumpingText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div>
				
				<div id="balanceLabel" style="position: absolute; margin-left: 74%; margin-top: 55px; font-size: 90%; display: none;">Balance</div>
				<div id="balanceBlock" class="fadeable-block" style="margin-left: 88.2%; margin-top: 55px;"><div id="balanceText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div>
				
				<div id="agilityLabel" style="position: absolute; margin-left: 53%; margin-top: 76px; font-size: 90%; display: none;">Agility</div>
				<div id="agilityBlock" class="fadeable-block" style="margin-left: 69%; margin-top: 76px;"><div id="agilityText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div>
				
				<div id="staminaLabel" style="position: absolute; margin-left: 74%; margin-top: 76px; font-size: 90%; display: none;">Stamina</div>
				<div id="staminaBlock" class="fadeable-block" style="margin-left: 88.2%; margin-top: 76px;"><div id="staminaText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div>
				
				<div id="strengthLabel" style="position: absolute; margin-left: 52.9%; margin-top: 97px; font-size: 90%; display: none;">Strength</div>
				<div id="strengthBlock" class="fadeable-block" style="margin-left: 69%; margin-top: 97px;"><div id="strengthText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div>
				
				<div id="reactionsLabel" style="position: absolute; margin-left: 74%; margin-top: 97px; font-size: 90%; display: none;">Reactions</div>
				<div id="reactionsBlock" class="fadeable-block" style="margin-left: 88.2%; margin-top: 97px;"><div id="reactionsText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div>
				
				<div id="blockingLabel" style="position: absolute; margin-left: 52.9%; margin-top: 118px; font-size: 90%; display: none;">Blocking</div>
				<div id="blockingBlock" class="fadeable-block" style="margin-left: 69%; margin-top: 118px;"><div id="blockingText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div>
				
				<div id="fitnessLabel" style="position: absolute; margin-left: 74%; margin-top: 118px; font-size: 90%; display: none;">Fitness</div>
				<div id="fitnessBlock" class="fadeable-block" style="margin-left: 88.2%; margin-top: 118px;"><div id="fitnessText" style="display: none; margin-top: -1px; margin-left: 1px;"><font color="white" size="2" style="margin-bottom: 11px;"></font></div></div>
				<% //TODO %>
		
			

			</div>

		</div>
	</div>
</div>


<script type="text/javascript" src="/hb/scripts/ajax.getPlayerSkills.js">

</script>

<!-- END Player Details Modal -->

<%
	} // END Else (user is logged in)
%>
</body>
</html>
