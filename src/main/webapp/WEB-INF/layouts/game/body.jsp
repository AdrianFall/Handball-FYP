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
   
<!-- Main jumbotron for a primary marketing message or call to action -->

	
	<div style="padding-left: 10%; padding-right: 10%" id="container">
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
		/* Deprecated */
		/* $('#dynamicContainer').load("squad.html"); */
		
		/* function load_home() {
			alert('loadhome');
    var mygetrequest = new ajaxRequest()
    if (mygetrequest.overrideMimeType) mygetrequest.overrideMimeType('text/html')
    mygetrequest.onreadystatechange = function () {
        if (mygetrequest.readyState == 4) {
            if (mygetrequest.status == 200 || window.location.href.indexOf("http") == -1) {
                var data = mygetrequest.responseText;
                document.getElementById("dynamicContainer").innerHTML = data;
            } else {
                alert("An error has occured making the request");
            }
        }
    }

    mygetrequest.open("GET", "squad.html", true);
    mygetrequest.send(null);
    return false;
}
		
		function ajaxRequest() {
		    var activexmodes = ["Msxml2.XMLHTTP", "Microsoft.XMLHTTP"]
		    if (window.ActiveXObject) {
		        for (var i = 0; i < activexmodes.length; i++) {
		            try {
		                return new ActiveXObject(activexmodes[i])
		            } catch (e) {

		            }
		        }
		    } else if (window.XMLHttpRequest) return new XMLHttpRequest()
		    else return false;
		} */
		
		/* function test() {
			alert('test');
			$(document).ready(function() {
			    $.get('squad.html')
			             .success(function(data) {
			                 $('#dynamicContainer').html(data);
			             });
			    });
		} */
		
		  $('#squadHref').click( function() {
			
			 $(document).ready(function() {
				    $.get('squad.html')
				             .success(function(data) {
				                 $('#dynamicContainer').html(data);
				             });
				    });
		 });  
		
		</script>
		<!-- End of container -->
	</div>

<% } %>