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
	Map<String,String> captainsMap = new HashMap<String,String>();
	private Map<String, Skill> allPlayersSkills = new HashMap<String, Skill>();%>

<%
	// Protect the website.
	email = (String) session.getAttribute("email");
	teamName = (String) session.getAttribute("teamName");
	if (email == null) {
%>

<script>
	$("#modalSessionExpired").bsModal('show');
</script>

<% } else {
		firstSquadPlayersList = new ArrayList<Player>(); 
		benchPlayersList = new ArrayList<Player>();
		reservesList = new ArrayList<Player>();
		captainsMap = (Map<String,String>) request.getAttribute("captainsMap");%>


<!-- Fix loading spinner on the button -->
<button onclick="postClientSideFormationList()" id="saveSquadButton"
	class="btn btn-info has-spinner"
	style="position: fixed; display: none; right: 10%; float: right; margin-top: -15px">
	<span id="squadLoad" class="spinner"><i class="icon-spin refresh icon"></i></span> Save Squad
</button>

<script type="text/javascript" src="/hb/scripts/squadSettingsNavigation.js"></script>
<br>
<!-- Modal Semantic-UI -->
        <div id="modal_semantic" class="ui basic test modal transition hidden" style="margin-top: -20px;">
    <div class="header">
      Swap Player
    </div>
    <div class="content">
      <div class="image">
        <i class="retweet icon"></i>
      </div>
      <div class="description">
      	<p>You are about to swap the following players:</p>
      	
      	<b id="dragged_player"></b> with <b id="dropped_player"></b> 
      	
        <p>Are you sure you want to swap the player?</p>
      </div>
    </div>
    <div class="actions">
      <div class="two fluid ui inverted buttons">
        <div class="ui red basic cancel inverted button">
          <i class="remove icon"></i>
          No
        </div>
        <div class="ui green ok basic inverted button">
          <i class="checkmark icon"></i>
          Yes
        </div>
      </div>
    </div>
  </div>
<div>
	<div
		style="position: absolute; width: 40%; left: 8.8%; float: left;">
		<!-- TABLE ROW -->

		<table id="squadTable" class="table table-bordered table-hover"
			style="-webkit-user-select: none; -khtml-user-select: none; -moz-user-select: none; -o-user-select: none; user-select: none; cursor: default;">
			<thead>
				<tr class="info">
					<th class="th" style="text-align: center" title="Player Number">No</th>
					<th class="th" title="Main position of player"
						style="text-align: center">Main Position</th>
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
			<tr class="danger droppableSquadTable" id="rp_tr<%=i%>">

				<td class="td" id="rp_td<%=i%>"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="rp_number<%=i%>" style='display: none;' class="center">
						<div id="rp_pn<%=benchPlayersList.get(i).getNumber()%>"><%=reservesList.get(i).getNumber()%></div>
					</div>
				</td>

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="rp_position<%=i%>" style='display: none;' class="center"><%=reservesList.get(i).getPlay_position()%></div>
				</td>

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="rp_name<%=i%>" style='display: none;' class="center"><%=reservesList.get(i).getName()%></div>
				</td>

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="status<%=i%>" style='display: none;' class="center">TBD</div>
				</td>

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<!--  TBD - PICTURE -->
					<div id="rp_speciality<%=i%>" style='display: none;' class="center">TBD</div>
				</td>

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="rp_age<%=i%>" style='display: none;' class="center"><%=reservesList.get(i).getAge()%></div>
				</td>

				<td id="rp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="rp_quality<%=i%>" style='display: none;' class="center"><%=reservesList.get(i).getPlayer_quality() %></div>
				</td>

				<td id="rp_td<%=i%>" class="td" style="display: none;">
					<div id="rp_marketValue<%=i%>" style="display: none;"><%= reservesList.get(i).getMarket_value()%></div>
				</td>

				<td id="rp_td<%=i%>" class="td" style="display: none;">
					<div id="rp_form<%=i%>" style="display: none;"><%= reservesList.get(i).getForm()%></div>
				</td>

				<td id="rp_td<%=i%>" class="td" style="display: none;">
					<div id="rp_id<%=i%>" style="display: none;"><%= reservesList.get(i).getPlayer_id()%></div>
				</td>

			</tr>
			<!-- END loop for reserves players -->
			<% } %>




		</table>
	</div>

	<!-- START Table with the pitch -->



	<table id="pitchTable"
		style="background-image: url('/hb/img/handball-pitch-perspective-net.png'); height: 50%; width: 40%; background-size: 100% 100%; background-repeat: no-repeat; border-bottom: none; -webkit-user-select: none; -khtml-user-select: none; -moz-user-select: none; -o-user-select: none; user-select: none; cursor: default; position: fixed; right: 10%;">

		<tr height="45">

			<td width="15%"></td>
			<td class="droppablePitchTable" id="LW" width="10%">
				<div style="position: relative;">
					<img style="position: absolute; top: -11px; left: 35%;"
						src="/hb/img/field_player_back_shirt.png" height="30" width="28">

					<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
					<% if(firstSquadPlayersList.get(n).getFirst_squad_play_position().equals("LW")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 43%;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 45%;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

				</div>

			</td>
			<td class="droppablePitchTable" id="PV" width="10%">
				<div style="position: relative;">
					<img style="position: absolute; top: -11px; left: 7px;"
						src="/hb/img/field_player_back_shirt.png" height="30" width="28">

					<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
					<% if(firstSquadPlayersList.get(n).getFirst_squad_play_position().equals("PV")) {
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
			<td class="droppablePitchTable" id="RW" width="10%">
				<div style="position: relative; top: -11px;">
					<img style="position: absolute; left: 25%;"
						src="/hb/img/field_player_back_shirt.png" height="30" width="28">

					<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
					<% if(firstSquadPlayersList.get(n).getFirst_squad_play_position().equals("RW")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 33%;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 36%;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

				</div>
			</td>
			<td width="15%"></td>

		</tr>



		<tr height="35">
			<td></td>
			<td class="droppablePitchTable" width="15%" id="LB"><div
					style="position: relative;">
					<img style="position: absolute; top: -10px; left: 5px;"
						src="/hb/img/field_player_back_shirt.png" height="30" width="28">

					<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
					<% if(firstSquadPlayersList.get(n).getFirst_squad_play_position().equals("LB")) {
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
					<% if(firstSquadPlayersList.get(n).getFirst_squad_play_position().equals("CB")) {
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
					<% if(firstSquadPlayersList.get(n).getFirst_squad_play_position().equals("RB")) {
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
					<% if(firstSquadPlayersList.get(n).getFirst_squad_play_position().equals("GK")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -18px; left: 15px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -18px; left: 18px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>
					
				</div></td>
			<td></td>
			<td></td>
		</tr>

	</table>
	<!-- END the pitch table -->


</div>
<!-- END div holding the squad and pitch tables -->

<div id="captainRoles"
	style="display: none; right: 10%; width: 40%; float: right; position: fixed;">

	<img id="captainImage" src="/hb/img/temp/handball_captain_temp.png"
		style="position: fixed; float: right; height: 30%; width: 30%; margin-top: 8%; right: 25%; background-size: 100% 100%;">

	<input id="captain1" class="droppableCaptainRole"
		placeholder="   Primary Captain"
		style="text-align: center; border: 0; outline: 0; width: 15%; font-family: Fantasy; margin-top: 2%; color: white; float: right; position: fixed; right: 15%; height: 6%; box-shadow: 0 1px 0 rgba(255, 255, 255, .15), 0 2px 4px rgba(0, 0, 0, 0.2) inset, 0 0 12px rgba(211, 64, 64, 0.1); background: rgba(0, 0, 0, .1);"
		readonly> <input id="captain2" class="droppableCaptainRole"
		placeholder="   Secondary Captain"
		style="text-align: center; border: 0; outline: 0; width: 15%; font-family: Fantasy; margin-top: 6%; color: white; float: right; position: fixed; right: 15%; height: 6%; box-shadow: 0 1px 0 rgba(255, 255, 255, .15), 0 2px 4px rgba(0, 0, 0, 0.2) inset, 0 0 12px rgba(211, 64, 64, 0.1); background: rgba(0, 0, 0, .1);"
		readonly> <input id="captain3" class="droppableCaptainRole"
		placeholder="   Tertiary Captain"
		style="text-align: center; border: 0; outline: 0; width: 15%; font-family: Fantasy; margin-top: 10%; color: white; float: right; position: fixed; right: 15%; height: 6%; box-shadow: 0 1px 0 rgba(255, 255, 255, .15), 0 2px 4px rgba(0, 0, 0, 0.2) inset, 0 0 12px rgba(211, 64, 64, 0.1); background: rgba(0, 0, 0, .1);"
		readonly> <input id="captain4" class="droppableCaptainRole"
		placeholder="   Quaternary Captain"
		style="text-align: center; border: 0; outline: 0; width: 15%; font-family: Fantasy; margin-top: 14%; color: white; float: right; position: fixed; right: 15%; height: 6%; box-shadow: 0 1px 0 rgba(255, 255, 255, .15), 0 2px 4px rgba(0, 0, 0, 0.2) inset, 0 0 12px rgba(211, 64, 64, 0.1); background: rgba(0, 0, 0, .1);"
		readonly>

	<script src="/hb/scripts/droppableCaptainRoles.js"></script>

	<!-- START Script for displaying and holding the data about 
	 	  the current captains list -->
	<script>
	 
	 var captainsList = ["","","",""];
	
	  <% if(!captainsMap.get("captain_id_one").equals("-1")) { %>
	 		captainsList[0] = '<%=captainsMap.get("captain_id_one")%>';
	 		
	 		// Change the html to show the number and name of the captain
	 		$('#captain1').val('<%=captainsMap.get("captain_number_one")%>' + '  ' + '<%=captainsMap.get("captain_name_one")%>');
	 <% } %>
	 
	<% if(!captainsMap.get("captain_id_two").equals("-1")) { %>
		captainsList[1] = '<%=captainsMap.get("captain_id_two")%>';
		
		// Change the html to show the number and name of the captain
		$('#captain2').val('<%=captainsMap.get("captain_number_two")%>' + '  ' + '<%=captainsMap.get("captain_name_two")%>');
	<% } %>
	
	<% if(!captainsMap.get("captain_id_three").equals("-1")) { %>
	captainsList[2] = '<%=captainsMap.get("captain_id_three")%>';
	
	// Change the html to show the number and name of the captain
	$('#captain3').val('<%=captainsMap.get("captain_number_three")%>' + '  ' + '<%=captainsMap.get("captain_name_three")%>');
	<% } %>
	
	<% if(!captainsMap.get("captain_id_four").equals("-1")) { %>
	captainsList[3] = '<%=captainsMap.get("captain_id_four")%>';
	
	// Change the html to show the number and name of the captain
	$('#captain4').val('<%=captainsMap.get("captain_number_four")%>' + '  ' + '<%=captainsMap.get("captain_name_four")%>');
	<% } %>
	 
	 </script>
	<!-- END Script for displaying and holding the data about 
	 	  the current captains list -->


</div>
<!-- END div holding the captains roles -->


<div id="autoSubs" style="display:none;">
 <div id="scrollbox-autosubs">
   	<input id="pv_1" class="input-box" placeholder="Primary PV" style="margin-top: 10%;" readonly>
  	
  	<input id="pv_2" class="input-box" placeholder="Secondary PV" style="margin-top: 2%;" readonly>
  	
  	<input id="lw_1" class="input-box" placeholder="Primary LW" style="margin-top: 5%;" readonly>
  	
  	<input id="lw_2" class="input-box" placeholder="Secondary LW" style="margin-top: 2%;" readonly>
  	
  	<input id="rw_1" class="input-box" placeholder="Primary RW" style="margin-top: 5%;" readonly>
  	
  	<input id="rw_2" class="input-box" placeholder="Secondary RW" style="margin-top: 2%;" readonly>
  	
  	<input id="gk_1" class="input-box" placeholder="Primary GK" style="margin-top: 5%;" readonly>
  	
  	<input id="gk_2" class="input-box" placeholder="Secondary GK" style="margin-top: 2%;" readonly>
  	
  	<input id="lb_1" class="input-box" placeholder="Primary LB" style="margin-top: 5%;" readonly>
  	
  	<input id="lb_2" class="input-box" placeholder="Secondary LB" style="margin-top: 2%;" readonly>
  	
  	<input id="cb_1" class="input-box" placeholder="Primary CB" style="margin-top: 5%;" readonly>
  	
  	<input id="cb_2" class="input-box" placeholder="Secondary CB" style="margin-top: 2%;" readonly>
  	
  	<input id="rb_1" class="input-box" placeholder="Primary RB" style="margin-top: 5%;" readonly>
  	
  	<input id="rb_2" class="input-box" placeholder="Secondary RB" style="margin-top: 2%;" readonly>
  	
  
  </div> 
</div>

<img onclick="showPitchTable()"
	style="position: fixed; float: right; height: 8%; width: 6%; right: 3%; background-size: 100% 100%;"
	src="/hb/img/temp/temp_tactics_icon.png">

<img onclick="showCaptainTable()"
	style="position: fixed; float: right; height: 8%; width: 6%; margin-top: 4.5%; right: 3%; background-size: 100% 100%;"
	src="/hb/img/temp/temp_captain_roles_icon.png">

<img onclick="showAutoSubsTable()"
	style="position: fixed; float: right; height: 8%; width: 6%; margin-top: 9%; right: 3%; background-size: 100% 100%;"
	src="/hb/img/temp/temp_auto_subs_icon.png">

<img onclick="showPenaltyTakersTable()"
	style="position: fixed; float: right; height: 8%; width: 6%; margin-top: 13.5%; right: 3%; background-size: 100% 100%;"
	src="/hb/img/temp/temp_penalty_takers_icon.png">




<!-- Scripts animating the slide down of the table with player list -->
<script>
$(document).ready(function loadTable() {
/* Animate the first squad players and register draggable and mouse clicks events */
<% for (int i = 0; i < firstSquadPlayersList.size(); i++)  { %>

$('#fsqp_td<%=i%> div').slideUp(
		<%=(100 * (i+(1-i*0.2)))%>
		);
		
		$('#fsqp_td<%=i%> div').slideDown(
		<%=(580 * (i+(1-i*0.2)))%>
		);
		
		var draggableOptions = {
				
				 containment: 'holder',
		         helper: function(event) {
		        	 var d = document.getElementById("pitchTable");
		        	 
					d.className = "table-bordered";
		        	 var fsqp_position = document.getElementById ( "fsqp_position<%=i%>" ).innerText;
		        	 var dragged_number = $('#fsqp_td<%=i%>').text();
		             var shirtStyle = $('<div style="position: absolute; margin:0;">' + '<img src="/hb/img/field_player_back_shirt.png" height="38" width="42" style="position:relative; margin:0; z-index:1;">' + '<h3 style="position: relative; margin-top: -30px; margin-left:8px; font-size: 16px; color:white; z-index:2; ">' + dragged_number + '</h3></div>');
		             if (new String("GK").valueOf() == new String(fsqp_position).valueOf()) shirtStyle = $('<div style="position: absolute; margin: 0;">' + '<img src="/hb/img/gk_back_shirt.png" height="38" width="42" style="position:relative; margin: 0px; z-index:1;">' + '<h3 style="position:relative; margin-top: -30px; margin-left: 8px; font-size:16px; color:white; z-index:2;">' + dragged_number + '</h3></img></div>');
		             
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
		}
		
		$('#fsqp_tr<%=i%>').draggable(draggableOptions);
	
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
				.bsModal(
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

/* Animate the bench squad players and register draggable and mouse clicks events */
  
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
				.bsModal(
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


/* Animate the bench squad players and register draggable and mouse clicks events */
  
  <% for (int i = 0; i < reservesList.size(); i++) { %>
  $('#rp_td<%=i%> div').slideUp(
			<%=(100 * (i+(1-i*0.2)))%>
			);
  	  // Perform a slide down animation on page load
	  $('#rp_td<%=i%> div').slideDown(
			<%=(700 * (i+firstSquadPlayersList.size()+(1-(i+firstSquadPlayersList.size())*0.2)))%>
			);
  	  var playerNumber = $("#rp_tr<%=i%> td rp_number<%=i%>");
  	  // Register draggable to each row of bench squad players
	  $("#rp_tr<%=i%>").draggable({
	         helper: function(event) {
	        	 var d = document.getElementById("pitchTable");
					d.className = "table-bordered";
	             var position = $("#rp_position<%=i%>").text();
	             var dragged_number = $('#rp_td<%=i%>').text();
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
	  $("#rp_tr<%=i%>").click(function() {
		  
		  var rp_player = {
		            teamName: "<%=teamName%>",
		            playerName: $("#rp_name<%=i%>").text(),
		            playerQuality: $("#rp_quality<%=i%>").text(),
		            playerPosition: $("#rp_position<%=i%>").text(),
		            playerAge: $("#rp_age<%=i%>").text(),
		            playerMarketValue: $('#rp_marketValue<%=i%>').text(),
		            playerForm: $('#rp_form<%=i%>').text(),
		            playerId: $('#rp_id<%=i%>').text()
		};

		$("#modalPlayerDetails").bsModal('show');

		$('#clubName').text(rp_player.teamName);
		$('#playerName').text(rp_player.playerName);
		$('#playerQuality').text(rp_player.playerQuality);
		$('#playerPosition').text(rp_player.playerPosition);
		$('#playerAge').text(rp_player.playerAge);
		$('#playerMarketValue').text('$' + rp_player.playerMarketValue);
		$('#playerForm').text(rp_player.playerForm);

		showPlayerDetailsOnModal(rp_player.playerId, rp_player.playerPosition);

	});
<% } %>
	/* END Animate the bench squad players */
});
</script>
<!-- END Scripts animating the slide down of the player list -->



<script type="text/javascript" src="/hb/scripts/squadSwap.js"></script>

<!-- Player Details Modal -->

<jsp:include page="squadModal.jsp"></jsp:include>
<!-- END Player Details Modal -->


<script type="text/javascript"
	src="/hb/scripts/ajax.saveSquadchanges.js"></script>

<script type="text/javascript" src="/hb/scripts/ajax.getPlayerSkills.js"></script>



<% } // END Else (user is logged in) %>



