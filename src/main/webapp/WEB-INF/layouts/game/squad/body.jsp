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

			<!-- LOOP for first squad players -->
			<% 
			for (int i = 0; i < countFirstSquadPlayers; i++) { %>
			<tr class="success" id="fsqp_tr<%=i%>">
				
				<td id="fsqp_td<%=i%>" class="td"
					style="padding-top: 4px; padding-bottom: 4px;">
					<div id="fsqp_number<%=i%>" style='display: none;' class="center"><div id="fsqp_pn<%=firstSquadPlayersList.get(i).getNumber()%>"><%=firstSquadPlayersList.get(i).getNumber()%></div></div>
					
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
					<div id="bp_number<%=i%>" style='display: none;' class="center"><div id="bp_pn<%=benchPlayersList.get(i).getNumber()%>"><%=benchPlayersList.get(i).getNumber()%></div></div>
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
				<td class="droppable" id="LW">
					<div style="position: relative;">
						<img style="position: absolute; left: 5px;"
							src="/hb/img/field_player_back_shirt.png" height="42" width="28">

						<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
						<% if(firstSquadPlayersList.get(n).getPlay_position().equals("LW")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 13px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 16px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

					</div>

				</td>
				<td></td>
				<td class="droppable" id="PV" width="50">
					<div style="position: relative;">
						<img style="position: absolute; left: 7px;"
							src="/hb/img/field_player_back_shirt.png" height="42" width="28">

						<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
						<% if(firstSquadPlayersList.get(n).getPlay_position().equals("PV")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 15px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -24px; left: 18px;'>" + playerNumber + "</h3>"); 
									 }
									
								 }
								 
							} %>

					</div>
				</td>
				<td></td>
				<td class="droppable" id="RW">
					<div style="position: relative; left: 20px;">
						<img style="position: absolute;"
							src="/hb/img/field_player_back_shirt.png" height="42" width="28">

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
				<td class="droppable" id="LB"><div style="position: relative;">
						<img style="position: absolute; top: -10px; left: 5px;" src="/hb/img/field_player_back_shirt.png"
							height="42" width="28">
						
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
						<img style="position: absolute; top: -10px; left: 7px;" src="/hb/img/field_player_back_shirt.png"
							height="42" width="28">
						
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
				<td class="droppable" id="RB">	<div style="position: relative;">
						<img style="position: absolute; top: -10px; left: 20px;" src="/hb/img/field_player_back_shirt.png"
							height="42" width="28">
						
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
				<td class="droppable" id="GK"><div style="position: relative;">
						<img style="position: absolute; left: 7px; top: -4px;" src="/hb/img/gk_back_shirt.png"
							height="42" width="28">

						<% for (int n = 0; n < firstSquadPlayersList.size(); n++) { %>
						<% if(firstSquadPlayersList.get(n).getPlay_position().equals("GK")) {
									 int playerNumber = firstSquadPlayersList.get(n).getNumber();
									 if (String.valueOf(playerNumber).length() > 1) {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -20px; left: 15px;'>" + playerNumber + "</h3>"); 
									 } else {
										 out.println("<h3 id='h3" + playerNumber + "' style='position: absolute; color: white; font-size: 72%; bottom: -20px; left: 18px;'>" + playerNumber + "</h3>"); 
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
  	  var playerNumber = $("#bp_tr<%=i%> td bp_number<%=i%>");
  	  
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
		
		var fieldPlayerNumber = document.getElementById(this.id).innerText;
		
		// Obtain the player number and figure out which table tr/td holds the player number record
		var trNode = $('#fsqp_pn' + fieldPlayerNumber).parent().parent().parent();
		
		/* var fsqp = document.getElementById('fsqp_pn' + fieldPlayerNumber); */
		
		
		 var splitTrNode = trNode.attr('id').split("tr"); 
		 var trType = splitTrNode[0];
		var trIndex = splitTrNode[1];
		alert(splitTrNode);

		
		// Animate the slide up of the interchanged rows
		 $('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideUp('slow');
		 $('#' + trType + 'td' + trIndex + ' div').slideUp('slow');
	

 		// Obtain the current values of first squad/dragged players
 		var firstSquadPlayerNumber = document.getElementById(trType + 'number' + trIndex).innerText;
		var draggedPlayerNumber = document.getElementById(draggableAttributeType + 'number' + draggableAttributeIndex).innerText;
		// END obtaining the current values of first squad/dragged players
		
		// Swap the values of each record on the table, as well as the number on the shirt (pitch representation of team)
		
		// Obtain the players to be swapped
		var swapFirstSquadPlayer = $('#fsqp_pn' + fieldPlayerNumber);
		var swapDraggedPlayer = $('#' + draggableAttributeType + 'pn' + draggedPlayerNumber);
		swapFirstSquadPlayer.parent().html("<div id='fsqp_pn" + $.trim(draggedPlayerNumber)+"'>" + $.trim(draggedPlayerNumber)+"</div>");
		
		
		 swapDraggedPlayer.parent().html("<div id='" + draggableAttributeType + "pn" + $.trim(firstSquadPlayerNumber) + "'>" + firstSquadPlayerNumber + "</div>");  
		
			
		
	 	
		//determine whether the dragged player is in first squad, if so do double swap of numbers
		if (new String(draggableAttributeType).valueOf() == new String("fsqp_").valueOf()) {
			var tempFirstSquadPlayerNumber = firstSquadPlayerNumber;
			var tempDraggedPlayerNumber = draggedPlayerNumber;
			
			$('#h3' + $.trim(tempFirstSquadPlayerNumber)).text($.trim(tempDraggedPlayerNumber));
			
			$('#h3' + $.trim(tempDraggedPlayerNumber)).text($.trim(tempFirstSquadPlayerNumber)); 
			 
			
			alert($('#h3' + $.trim(tempFirstSquadPlayerNumber)).attr("id"));
			var h3DraggedPlayer = $('#h3' + $.trim(tempDraggedPlayerNumber));
			var h3FirstSquadPlayer = $('#h3' + $.trim(tempFirstSquadPlayerNumber));
			h3DraggedPlayer.attr( "id",  "h3" + $.trim(tempFirstSquadPlayerNumber));
			h3FirstSquadPlayer.attr( "id",  "h3" + $.trim(tempDraggedPlayerNumber));
		
		} else {
		
		
		$('#h3' + $.trim(firstSquadPlayerNumber)).text($.trim(draggedPlayerNumber));
		
		
		/* $('#h3' + $.trim(draggedPlayerNumber)).text($.trim(firstSquadPlayerNumber)); */
		$('#h3' + $.trim(firstSquadPlayerNumber)).attr( "id",  "h3" + $.trim(draggedPlayerNumber));
		alert($("#h3" + $.trim(firstSquadPlayerNumber)).attr("id"));
		/* $('#h3' + $.trim(draggedPlayerNumber)).attr( "id",  "h3" + $.trim(firstSquadPlayerNumber));
		alert($('#h3' + $.trim(draggedPlayerNumber)).attr("id")); */
		}
	
		// END of swaping values for each record and shirt values
		
		$('#' + draggableAttributeType + 'td' + draggableAttributeIndex + ' div').slideDown('slow');
		$('#' + trType + 'td' + trIndex + ' div').slideDown('slow');
		/* document.getElementById('fsqp_number' + index).value = 'Test'; */
		/* alert('The square with ID "' + draggable.attr('id')
				+ '" was dropped onto me!'); */
	}
	/* END droppable */
	
	
</script>



<!-- End of container -->
<!-- END OF CONTAINER -->


<%
	 } // END Else (user is logged in)
%>