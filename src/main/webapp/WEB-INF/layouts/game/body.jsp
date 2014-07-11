<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%! private String teamName; 
	private String email;%>

<% email = (String) session.getAttribute("email"); 
   teamName = (String) session.getAttribute("teamName"); 
   if (email == null) { %>
		   
	<script>
	$("#modalSessionExpired").modal('show');
	</script>
 	<%} else {%>
   }
<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron" style="margin-top: -5%">
	<br> <br>
	<div class="container" id="container">
		<!-- NAVIGATION ROW -->
		<div class="row">

			<a href="#"> <img class="img-fix" alt="manager"
				src="/hb/img/manager.png">
			
			<a href="#" id="squadHref"> <img class="img-fix" alt="squad"
				src="/hb/img/player.png">
			

			</a> <a href="#"><img class="img-fix" alt="transfer"
				src="/hb/img/transfer.png"> </a> <a href="#"> <img
				class="img-fix" alt="training" src="/hb/img/training.png">

			</a> <a href="#"> <img class="img-fix" src="/hb/img/inbox.png"
				alt="inbox">
			</a>

			<!-- END OF NAVIGATION ROW -->
		</div>

		<!-- TABLE ROW -->
		<div class="row">

			<table class="table">
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

		<!-- END OF TABLE ROW  -->
		</div>
		<div class="row">
		<div class="container-fluid" id="dynamicContainer" style="margin-left: 0px;">
			
		</div>
		</div>
		<!--  -->
		<script>
		
		
		 $('#squadHref').click( function() {
			
			alert('Looks like squad was clicked.');
			
			$('#dynamicContainer').load("squad.html");
			
			return false;
		 }); 
		
		</script>
		<!-- End of container -->
	</div>

</div>
<% } %>