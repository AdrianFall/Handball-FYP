<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="af.handball.entity.Player,java.util.List"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%!private String teamName;
	private String email;
	private List<Player> playerList;%>

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
<div>
	<div style="width: 55%; left: 10%; float: left;">
		  <!-- <button class="btn-success has-spinner">
    <span class="spinner"><i class="icon-spin icon-refresh"></i></span>
    Foo
  </button> -->
		<!-- TABLE ROW -->

		<table class="table table-bordered table-hover">
			<thead>
				<tr>
					<th class="th" title="Player Number">No</th>
					<th class="th" title="Current Position of player">Position</th>
					<th class="th" title="Player Name">Name</th>
					<th class="th" title="Player status (injured, red card, etc.)">Status</th>
					<th class="th" title="Special Ability of a player">Spec.</th>
					<th class="th" title="Overall quality of a player">Qlty</th>
				</tr>
			</thead>
			
			<% 
			playerList = (List<Player>) request.getAttribute("playerList");
			for (int i = 0; i < playerList.size(); i++) { %>
				
				<tr class="active" id="tr<%=i%>">
				<td id="td1" class="td">
					<div id="div1" style='display: none'><%=playerList.get(i).getNumber()%></div>
				</td>
				
				<td id="td1" class="td">
					<div id="div1" style='display: none'><%=playerList.get(i).getPlay_position()%></div>
				</td>
				
				<td id="td1" class="td">
					<div id="div1" style='display: none'><%=playerList.get(i).getName()%></div>
				</td>
				
				<td id="td1" class="td">
					<div id="div1" style='display: none'>TBD</div>
				</td>
				
				<td id="td1" class="td">
					<!--  TBD - PICTURE -->
					<div id="div1" style='display: none'>TBD</div>
				</td>
				
				<td id="td1" class="td">
					<div id="div1" style='display: none'><%=playerList.get(i).getPlayer_quality() %></div>
				</td>
				
			</tr>
			
			<% } %>
			<!-- <tr class="active" id="div1" style="display:none;">
				<td id="td1" style='height: 0px'>
					1
				</td>
				
				<td id="td1" style='height: 0px'>
					GK
				</td>
				
				<td id="td1" style='height: 0px'>
					Hardcoded Quite Long Name
				</td>
				
				<td id="td1" style='height: 0px'>
					
				</td>
				
				<td id="td1" style='height: 0px'>
					Pict.
				</td>
				
				<td id="td1" style='height: 0px'>
					14.7
				</td>
				
			</tr> -->
			
			<!-- <tr class="active" id="tr1">
				<td id="td1" style='height: 0px'>
					<div id="div1" style='display: none'>1</div>
				</td>
				
				<td id="td1" style='height: 0px'>
					<div id="div1" style='display: none'>GK</div>
				</td>
				
				<td id="td1" style='height: 0px'>
					<div id="div1" style='display: none'>Hardcoded Quite Long Name</div>
				</td>
				
				<td id="td1" style='height: 0px'>
					<div id="div1" style='display: none'></div>
				</td>
				
				<td id="td1" style='height: 0px'>
					<div id="div1" style='display: none'>Pict.</div>
				</td>
				
				<td id="td1" style='height: 0px'>
					<div id="div1" style='display: none'>14.7</div>
				</td>
				
			</tr> -->

		</table>
	</div>

	<div style='width: 35%; right: 0%; float: right;'>gggg g g g g</div>
</div>
<%-- <table class="table">
				<thead>
					<tr class="warning">
						<th>Team: <% if (teamName != null) out.print(teamName); %></th>
						<th>Next Match</th>
						<th>League</th>
					</tr>
				</thead>
				<tbody>
					<tr class="active">
						<td>$ 1.9M</td>
						<td>Arsenal FC vs PagadiniMaestro220</td>
						<td>Level: 5</td>
					</tr>
					
					<tr class="danger">
					<td>Fans: 200K</td>
					<td>Tommorow 20:10</td>
					<td>Position: 10</td>
					</tr>
					
				</tbody>
			</table>
 --%>
<!-- END OF TABLE ROW  -->



<!--  -->
<script>
<% for (int i = 0; i < playerList.size(); i++) { %>
	$('#tr<%=i%> div').slideDown('slow');
	
<% } %>
	/* var td = document.getElementById("td1");

	if (td.style.height == "0px") {
		$('#div1').slideDown('fast');
		td.style.height = "1px";
	} else {
		$('#div1').slideUp('fast');
		td.style.height = "0px";
	} */
</script>
<!-- End of container -->
<!-- END OF CONTAINER -->


<%
	}
%>