<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="af.handball.entity.Player,java.util.List,java.util.ArrayList"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%!private String teamName;
	private String email;
	private List<Player> playerList;
	private int countFirstSquadPlayers = 0;
	private int countBenchPlayers = 0;
	private int countReservesPlayers = 0;
	ArrayList<Player> firstSquadPlayersList = new ArrayList<Player>();
	ArrayList<Player> benchPlayersList = new ArrayList<Player>();
	ArrayList<Player> reservesList = new ArrayList<Player>();%>

<%
	email = (String) session.getAttribute("email");
	teamName = (String) session.getAttribute("teamName");
	if (email == null) {
%>

<script>
	$("#modalSessionExpired").modal('show');
</script>

<%
	} else {
%>

<br>
<div>
	<div style="width: 55%; left: 10%; float: left;">
		<!-- <button class="btn-success has-spinner">
    <span class="spinner"><i class="icon-spin icon-refresh"></i></span>
    Foo
  </button> -->
		<!-- TABLE ROW -->

		<table class="table table-bordered table-hover"
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
					<th class="th" title="Overall quality of a player"
						style="text-align: center">Qlty</th>
				</tr>
			</thead>

			<% 
			playerList = (List<Player>) request.getAttribute("playerList");
			countFirstSquadPlayers = 0;
			countBenchPlayers = 0;
			countReservesPlayers = 0;
			firstSquadPlayersList = new ArrayList<Player>();
			benchPlayersList = new ArrayList<Player>();
			reservesList = new ArrayList<Player>();
			for (int i = 0; i < playerList.size(); i++) {
				if (playerList.get(i).getFormation().equals(Player.FORMATION_FIRST_SQUAD)) {
					++countFirstSquadPlayers;	
					firstSquadPlayersList.add(playerList.get(i));
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

			<% 
			for (int i = 0; i < countFirstSquadPlayers; i++) { %>
			<tr class="success" id="fsqp_tr<%=i%>">

				<td id="fsqp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="number<%=i%>" style='display: none;' class="center"><%=firstSquadPlayersList.get(i).getNumber()%></div>
				</td>

				<td id="fsqp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="fsqp_position<%=i%>" style='display: none;' class="center"><%=firstSquadPlayersList.get(i).getPlay_position()%></div>
				</td>

				<td id="fsqp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="name<%=i%>" style='display: none;' class="center"><%=firstSquadPlayersList.get(i).getName()%></div>
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
					<div id="quality<%=i%>" style='display: none;' class="center"><%=firstSquadPlayersList.get(i).getPlayer_quality() %></div>
				</td>

			</tr>
			<!-- END loop for first squad players -->
			<% } %>

			<% 
			for (int i = 0; i < countBenchPlayers; i++) { %>
			<tr class="warning" id="bp_tr<%=i%>">

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="number<%=i%>" style='display: none;' class="center"><%=benchPlayersList.get(i).getNumber()%></div>
				</td>

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="bp_position<%=i%>" style='display: none;' class="center"><%=benchPlayersList.get(i).getPlay_position()%></div>
				</td>

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="name<%=i%>" style='display: none;' class="center"><%=benchPlayersList.get(i).getName()%></div>
				</td>

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="status<%=i%>" style='display: none;' class="center">TBD</div>
				</td>

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<!--  TBD - PICTURE -->
					<div id="speciality<%=i%>" style='display: none;' class="center">TBD</div>
				</td>

				<td id="bp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="quality<%=i%>" style='display: none;' class="center"><%=benchPlayersList.get(i).getPlayer_quality() %></div>
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
	<div style='width: 30%; right: 10%; float: right; position: fixed;'>
		<table id="pitchTable" height="150px" width="280px"
			style="background-image: url('/hb/img/handball_playfield.png'); background-size: 100% 100%; background-repeat: no-repeat; border-bottom: none;">
			
			<tr height="45">
				<td>
					<div style="position: relative;">
						<img style="position: absolute; left: 5px;"
							src="/hb/img/field_player_back_shirt.png" height="42" width="28">

						<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
						<% if(firstSquadPlayersList.get(n).getPlay_position().equals("LW")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 13px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 16px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

					</div>

				</td>
				<td></td>
				<td width="50">
					<div style="position: relative;">
						<img style="position: absolute; left: 7px;"
							src="/hb/img/field_player_back_shirt.png" height="42" width="28">

						<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
						<% if(firstSquadPlayersList.get(n).getPlay_position().equals("PV")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 15px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 18px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

					</div>
				</td>
				<td></td>
				<td>
					<div style="position: relative; left: 20px;">
						<img style="position: absolute;"
							src="/hb/img/field_player_back_shirt.png" height="42" width="28">

						<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
						<% if(firstSquadPlayersList.get(n).getPlay_position().equals("RW")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 8px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 11px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

					</div>
				</td>
			</tr>



			<tr height="35">
				<td></td>
				<td><div style="position: relative;">
						<img style="position: absolute; top: -10px; left: 5px;" src="/hb/img/field_player_back_shirt.png"
							height="42" width="28">
						
							<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
								 <% if(firstSquadPlayersList.get(n).getPlay_position().equals("LB")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 13px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 16px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>
						
					</div></td>
				<td>	<div style="position: relative;">
						<img style="position: absolute; top: -10px; left: 7px;" src="/hb/img/field_player_back_shirt.png"
							height="42" width="28">
						
							<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
								 <% if(firstSquadPlayersList.get(n).getPlay_position().equals("CB")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 15px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 18px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>
						
					</div></td>
				<td>	<div style="position: relative;">
						<img style="position: absolute; top: -10px; left: 20px;" src="/hb/img/field_player_back_shirt.png"
							height="42" width="28">
						
							<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
								 <% if(firstSquadPlayersList.get(n).getPlay_position().equals("RB")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 28px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -14px; left: 31px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>
						
					</div></td>
				<td></td>
			</tr>
			

			<tr height="30">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>

			<tr height="34">
				<td></td>
				<td></td>
				<td id="droppable"><div style="position: relative;">
						<img style="position: absolute; left: 7px; top: -4px;" src="/hb/img/gk_back_shirt.png"
							height="42" width="28">

						<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
						<% if(firstSquadPlayersList.get(n).getPlay_position().equals("GK")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -20px; left: 15px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 style='position: absolute; color: white; font-size: 72%; bottom: -20px; left: 18px;'>" + playerNumber + "</h3>"); 
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
</div>

<script>

var c = {};

$("#test1").draggable({
        helper: "clone",
        start: function(event, ui) {
            c.tr = this;
            c.helper = ui.helper;
        }
});

/* $(function() {
    $( "#div1" ).draggable();
}); */
</script>


<!-- Scripts animating the slide down of the table with player list -->
<script>

/* Animate the first squad players */
<% for (int i = 0; i < firstSquadPlayersList.size(); i++)  { %>
		$('#fsqp_td<%=i%> div').slideDown(
		<%=(400 * (i+(1-i*0.2)))%>
		);

		
		$("#fsqp_tr<%=i%>").draggable({
	         helper: function(event) {
	        	 var d = document.getElementById("pitchTable");
				d.className = "table-bordered";
	        	 var fsqp_position = document.getElementById ( "fsqp_position<%=i%>" ).innerText;
	             var shirtStyle = $('<div style="position: relative;">' + '<img src="/hb/img/field_player_back_shirt.png" height="35" width="35" style="position:absolute; top: 0px; left:0; right:0; z-index:100;width:50px;">' + '<h3 style="position:absolute; color:white; font-size: 75%; bottom:-35px; left:20px; right:0; z-index:290;width:50px; height:10px;"><span>' + document.getElementById ( "fsqp_td<%=i%>" ).innerText + '</span></h3></div>');
	             if (new String("GK").valueOf() == new String(fsqp_position).valueOf()) shirtStyle = $('<div style="position: relative;">' + '<img src="/hb/img/gk_back_shirt.png" height="35" width="35" style="position:absolute; top: 0px; left:0; right:0; z-index:100;width:50px;">' + '<h3 style="position:absolute; color:white; font-size: 75%; bottom:-35px; left:20px; right:0; z-index:290;width:50px; height:10px;"><span>' + document.getElementById ( "fsqp_td<%=i%>" ).innerText + '</span></h3></div>');
	             
	            	 return shirtStyle
	             /* return $('<div class="drag-row"><table></table></div>') */
	             /* return $('<img src="/hb/img/field_player_back_shirt.png" height="35" width="35" style="position:fixed; top: 0px; left:0; right:0; z-index:100;width:50px;">') */
	.find('table').append($(event.target).closest('tr').clone()).end();
	          },
		 
		 cursorAt: {
			    top: 1,
			    left: 19
		 },
		 stop: function( event, ui ) {
			 var d = document.getElementById("pitchTable");
			d.className = "";
		 }
	     });
<% } %>
/* END Animate the first squad players */

/* Animate the bench squad players */
  
  <% for (int i = 0; i < benchPlayersList.size(); i++) { %>
 
	  $('#bp_td<%=i%> div').slideDown(
			<%=(400 * (i+firstSquadPlayersList.size()+(1-(i+firstSquadPlayersList.size())*0.2)))%>
			);
  	  var playerNumber = $("#bp_tr<%=i%> td number<%=i%>");
  	  
	  $("#bp_tr<%=i%>").draggable({
	         helper: function(event) {
	        	 var d = document.getElementById("pitchTable");
					d.className = "table-bordered";
	             /* return $('<div class="drag-row"><table></table></div>') */
	             var position = document.getElementById ( "bp_position<%=i%>" ).innerText;
	             var shirtStyle = $('<div style="position: relative;">' + '<img src="/hb/img/field_player_back_shirt.png" height="35" width="35" style="position:absolute; top: 0px; left:0; right:0; z-index:100;width:50px;">' + '<h3 style="position:absolute; color:white; font-size: 75%; bottom:-35px; left:20px; right:0; z-index:290;width:50px; height:10px;"><span>' + document.getElementById ( "bp_td<%=i%>" ).innerText + '</span></h3></div>');
	             if (new String("GK").valueOf() == new String(position).valueOf()) shirtStyle = $('<div style="position: relative;">' + '<img src="/hb/img/gk_back_shirt.png" height="35" width="35" style="position:absolute; top: 0px; left:0; right:0; z-index:100;width:50px;">' + '<h3 style="position:absolute; color:white; font-size: 75%; bottom:-35px; left:20px; right:0; z-index:290;width:50px; height:10px;"><span>' + document.getElementById ( "bp_td<%=i%>" ).innerText + '</span></h3></div>');
	             
	            	 return shirtStyle
	.find('table').append($(event.target).closest('tr').clone()).end();
	          },
		 
		 cursorAt: {
			    top: 1,
			    left: 19
		 },
		 stop: function( event, ui ) {
			 var d = document.getElementById("pitchTable");
			d.className = "";
		 }
	     });
  <% } %>
/* END Animate the bench squad players */

</script>
<!-- END Scripts animating the slide down of the player list -->

<script>

<% for (int i = 0; i < playerList.size(); i++) { %>

	$('#td<%=i%> div').slideDown(
	<%=(400 * (i+(1-i*0.2)))%>
	);
	
	 $("#tr<%=i%>")
			.draggable(
					{
						
						helper : function(event) {
							
							/* document.getElementById('pitchTable').className = "table-bordered"; */
							/* return $('<div class="drag-row"><table></table></div>') */
							return $(
									'<img src="/hb/img/field_player_back_shirt.png" height="35" width="35" style="position:fixed; top: 0px; left:0; right:0; z-index:100;width:50px;">')
									.find('table').append(
											$(event.target).closest('tr')
													.clone()).end();
						},

						cursorAt : {
							top : 1,
							left : 19
						},
						
					});
<% } %>
	
</script>

<script>
	$('#droppable').droppable({
		drop : handleDropEvent
	});

	function handleDropEvent(event, ui) {
		var draggable = ui.draggable;
		alert('The square with ID "' + draggable.attr('id')
				+ '" was dropped onto me!');
	}
</script>



<!-- End of container -->
<!-- END OF CONTAINER -->


<%
	 } // END Else (user is logged in)
%>