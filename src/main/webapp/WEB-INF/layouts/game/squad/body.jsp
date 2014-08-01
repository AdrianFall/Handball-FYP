<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="af.handball.entity.Player,java.util.List,java.util.ArrayList,java.util.HashMap,af.handball.entity.Skill,java.util.Map"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

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

			<tr class="success droppableSquadTable" id="fsqp_tr<%=i%>">

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

				<td id="fsqp_td<%=i%>" class="td"
					style="display: none;">
					<div id="fsqp_marketValue<%=i%>" style="display: none;"><%= firstSquadPlayersList.get(i).getMarket_value()%></div>
				</td>

				<td id="fsqp_td<%=i%>" class="td"
					style="display: none;">
					<div id="fsqp_form<%=i%>" style="display: none;">
						<% if(firstSquadPlayersList.get(i).getForm() == 0) out.print("N/A"); else out.print(firstSquadPlayersList.get(i).getForm());%>
					</div>
				</td>

				<td id="fsqp_td<%=i%>" class="td"
					style="display: none;">
					<div id="fsqp_id<%=i%>" style="display: none;"><%= firstSquadPlayersList.get(i).getPlayer_id()%></div>
				</td>
			</tr>
			<!-- END loop for first squad players -->
			<% } %>

			<% 
			for (int i = 0; i < countBenchPlayers; i++) { %>
			<tr class="warning droppableSquadTable" id="bp_tr<%=i%>">

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
			<td class="droppablePitchTable" id="LW" width="20%">
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
			<td class="droppablePitchTable" id="PV" width="15%">
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
			<td class="droppablePitchTable" id="RW" width="10%">
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
			<td class="droppablePitchTable" width="15%" id="LB"><div
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
			<td class="droppablePitchTable" id="CB"><div
					style="position: relative;">
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
			<td class="droppablePitchTable" width="15%" id="RB">
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
			<td class="droppablePitchTable" id="GK"><div
					style="position: relative;">
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
		
		<%-- if ($('#fsqp_tr<%=i%>').data('.uiDraggable')) {
			alert('destryoing draggable!');
			$('#fsqp_tr<%=i%>').draggable("destroy");
		}  --%>
		/* $('.draggable').draggable('destroy');  */
		
	
		$('#fsqp_tr<%=i%>').draggable({
			  containment: 'holder',
	         helper: function(event) {
	        	 var d = document.getElementById("pitchTable");
	        	 
				d.className = "table-bordered";
	        	 var fsqp_position = document.getElementById ( "fsqp_position<%=i%>" ).innerText;
	        	 var dragged_number = $('#fsqp_td<%=i%>').text();
	             var shirtStyle = $('<div style="position: absolute; margin:0;">' + '<img src="/hb/img/field_player_back_shirt.png" height="38" width="42" style="position:relative; margin:0; z-index:1;">' + '<h3 style="position: relative; margin-top: -30px; margin-left:8px; font-size: 16px; color:white; z-index:2; ">' + dragged_number + '</h3></div>');
	             if (new String("GK").valueOf() == new String(fsqp_position).valueOf()) shirtStyle = $('<div style="position: absolute; margin: 0;">' + '<img src="/hb/img/gk_back_shirt.png" height="38" width="42" style="position:relative; margin: 0px; z-index:1;">' + '<h3 style="position:relative; margin-top: -30px; margin-left: 8px; font-size:16px; color:white; z-index:2;">' + dragged_number + '</h3></img></div>');
	             
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
			  
			  	
				showPlayerDetailsOnModal(player.playerId, player.playerPosition);
			  		
			
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
		  
		  var bp_player = {
		            teamName: "<%=teamName%>",
		            playerName: $("#bp_name<%=i%>").text(),
		            playerQuality: $("#bp_quality<%=i%>").text(),
		            playerPosition: $("#bp_position<%=i%>").text(),
		            playerAge: $("#bp_age<%=i%>").text(),
		            playerMarketValue: $('#bp_marketValue<%=i%>').text(),
		            playerForm: $('#bp_form<%=i%>').text(),
		            playerId: $('#bp_id<%=i%>').text()
		        };
			  
			 
			  $("#modalPlayerDetails")
				.modal(
						'show');
			  
			  
		    	$('#clubName').text(bp_player.teamName);
			  	$('#playerName').text(bp_player.playerName);
			  	$('#playerQuality').text(bp_player.playerQuality);
			  	$('#playerPosition').text(bp_player.playerPosition);
			  	$('#playerAge').text(bp_player.playerAge);
			  	$('#playerMarketValue').text('$' + bp_player.playerMarketValue);
			  	$('#playerForm').text(bp_player.playerForm);
			  
			  	
				showPlayerDetailsOnModal(bp_player.playerId, bp_player.playerPosition);
			  		
			
			
		  
		});
  <% } %>

/* END Animate the bench squad players */

</script>
<!-- END Scripts animating the slide down of the player list -->



<script>
	/* START droppable */
	$('.droppablePitchTable').droppable({
		drop : handlePitchTableUIDropEvent
	});
	
	$('.droppableSquadTable').droppable({
		drop : handleSquadTableUIDropEvent
	});
	
	function handleSquadTableUIDropEvent(event, ui) {
		alert(this.id);
		var splitDroppableId = this.id.split("tr");
		var droppableAttributeType = splitDroppableId[0];
		var droppableAttributeIndex = splitDroppableId[1];
		
		var draggable = ui.draggable;
		var draggableAttribute = draggable.attr('id');
		var splitDraggableAttribute = draggableAttribute.split("tr");
		var draggableAttributeType = splitDraggableAttribute[0];
		var draggableAttributeIndex = splitDraggableAttribute[1];
		
		// Animate the slide up of the interchanged rows
	     $('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideUp('slow');
		 $('#' + droppableAttributeType + 'td' + droppableAttributeIndex + ' div').slideUp('slow'); 
		 
		swapPlayerDetailsOnSquadTableCells('number', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('position', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('name', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('marketValue', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('age', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('form', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('id', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		swapPlayerDetailsOnSquadTableCells('quality', droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex);
		
		// Call method to perform changes (if needed) to the pitch table 
		performPitchTableChangeOnShirtNumbers('h3', draggableAttributeType, draggableAttributeIndex, droppableAttributeType, droppableAttributeIndex);
		
		
		
		
		// Animate the slide down of the interchanged rows
		$('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideDown('slow');
		 $('#' + droppableAttributeType + 'td' + droppableAttributeIndex + ' div').slideDown('slow'); 
	}
	
	// Performs (if needed) a swap in the shirt numbers on the pitch table
	function performPitchTableChangeOnShirtNumbers(textElementIdentifier, draggableAttributeType, draggableAttributeIndex, droppableAttributeType, droppableAttributeIndex) {
		
		var droppablePlayerNumber = $('#' + droppableAttributeType + 'number' + droppableAttributeIndex).text();
		var draggablePlayerNumber = $('#' + draggableAttributeType + 'number' + draggableAttributeIndex).text();
		
		if (new String(draggableAttributeType).valueOf() == new String("fsqp_").valueOf() 
				&& new String(droppableAttributeType).valueOf() == new String("fsqp_").valueOf()) {
		
			// Add fade out animation, swap the text and fade in the droppable player number
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).fadeOut(10);
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).text($.trim(draggablePlayerNumber)); 
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).fadeIn(700);
			
			// Add fade out animation, swap the text and fade in the draggable player number
			$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).fadeOut(10);
			$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).text($.trim(droppablePlayerNumber)); 
			$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).fadeIn(700);
			
			
			// Swap the elements' identifiers. To perform a change, set the ids of the elements
			// to a temporary ids, to address them uniquely and make a successful swap.
			var id1 = 'changer1';
			var id2 = 'changer2';
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).attr('id', id1);
			$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).attr('id', id2);
			
			// swap.
			$('#' + id1).attr('id', textElementIdentifier + $.trim(draggablePlayerNumber));
			$('#' + id2).attr('id', textElementIdentifier + $.trim(droppablePlayerNumber));
			
			
		} else if (new String(draggableAttributeType).valueOf() == new String("fsqp_").valueOf()) { // Draggable is first squad player, but droppable not. Therefore droppable becomes the fsqp now
			// Fade out animation
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).fadeOut(10);
			// Swap the text
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).text($.trim(draggablePlayerNumber)); 
			// Fade in animation
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).fadeIn(700);
			// Swap the elements' identifiers
			$('#' + textElementIdentifier + $.trim(droppablePlayerNumber)).attr('id', textElementIdentifier + $.trim(draggablePlayerNumber));
			
			
		
			} else if (new String(droppableAttributeType).valueOf() == new String("fsqp_").valueOf()) { // Droppable is first squad player. Therefore draggable becomes the fsqp.
				// Fade out animation
				$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).fadeOut(10);
				// Swap the text
				$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).text($.trim(droppablePlayerNumber)); 
				// Fade in animation
				$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).fadeIn(700);
				// Swap the elements' identifiers
				$('#' + textElementIdentifier + $.trim(draggablePlayerNumber)).attr('id', textElementIdentifier + $.trim(droppablePlayerNumber));
			} 
		// Otherwise no change needed on the pitch table
	}
	
	function swapPlayerDetailsOnSquadTableCells(cellIdentifier, droppableAttributeType, droppableAttributeIndex, draggableAttributeType, draggableAttributeIndex) {

		var swapDroppablePlayer = $('#' + $.trim(droppableAttributeType)  + cellIdentifier + $.trim(droppableAttributeIndex));
		var tempDroppablePlayerHTML = swapDroppablePlayer.html();
		var swapDraggedPlayer = $('#' + $.trim(draggableAttributeType) + cellIdentifier + $.trim(draggableAttributeIndex));
		var tempDraggedPlayerHTML = swapDraggedPlayer.html();
		// Swap the _numbers
		swapDroppablePlayer.html($.trim(tempDraggedPlayerHTML));
		swapDraggedPlayer.html($.trim(tempDroppablePlayerHTML));
	}

	function handlePitchTableUIDropEvent(event, ui) {
		
		alert(this.id);
		var draggable = ui.draggable;
		var draggableAttribute = draggable.attr('id');
		var splitDraggableAttribute = draggableAttribute.split("tr");
		var draggableAttributeType = splitDraggableAttribute[0];
		var draggableAttributeIndex = splitDraggableAttribute[1];
		
		var fieldPlayerNumber = $('#' + this.id).text();
		
		// Obtain the player number and figure out which table tr/td holds the player number record
		
		var trNode;
		var splitTrNode;
		
		try {
			// Try obtaining the trNode from first squad player row
			trNode = $('#fsqp_pn' + $.trim(fieldPlayerNumber)).parent().parent().parent();
			splitTrNode = trNode.attr('id').split("tr");
		} catch (e) {
			
			try {
				trNode = $('#bp_pn' + $.trim(fieldPlayerNumber)).parent().parent().parent();
				splitTrNode = trNode.attr('id').split("tr");
			} catch (e) {
				
				trNode = $('#rp_pn' + $.trim(fieldPlayerNumber)).parent().parent().parent();
				splitTrNode = trNode.attr('id').split("tr");
			}
		}
		/* var trNode = $('#fsqp_pn' + $.trim(fieldPlayerNumber)).parent().parent().parent(); */
		
		/* var fsqp = document.getElementById('fsqp_pn' + fieldPlayerNumber); */
		
		
		 /* var splitTrNode = trNode.attr('id').split("tr");  */
		 var trType = splitTrNode[0];
		var trIndex = splitTrNode[1];
		

		
		// Animate the slide up of the interchanged rows
		 $('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideUp('slow');
		 $('#' + trType + 'td' + trIndex + ' div').slideUp('slow');
	
		 swapPlayerDetailsOnSquadTableCells('number', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
			swapPlayerDetailsOnSquadTableCells('position', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
			swapPlayerDetailsOnSquadTableCells('name', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
			swapPlayerDetailsOnSquadTableCells('marketValue', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
			swapPlayerDetailsOnSquadTableCells('age', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
			swapPlayerDetailsOnSquadTableCells('form', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
			swapPlayerDetailsOnSquadTableCells('id', trType, trIndex, draggableAttributeType, draggableAttributeIndex);
			swapPlayerDetailsOnSquadTableCells('quality', trType, trIndex, draggableAttributeType, draggableAttributeIndex);

			
		
	 	
			performPitchTableChangeOnShirtNumbers('h3', draggableAttributeType, draggableAttributeIndex, trType, trIndex);
		
		$('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideDown('slow');
		$('#' + trType + 'td' + trIndex + ' div').slideDown('fast');
		
		
		 
	} /* END droppablePitchTable */
	 
	
	
</script>

<!-- Player Details Modal -->

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

