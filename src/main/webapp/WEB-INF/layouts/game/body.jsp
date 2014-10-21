<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%! private String teamName; 
	private String email;%>

<% email = (String) session.getAttribute("email"); 
   teamName = (String) session.getAttribute("teamName"); 
   if (email == null) { %>  
	<script>$("#modalSessionExpired").modal('show');</script>
 	<%} else {%>
   
	<!-- Load the page  -->

<div id="loadingAnimation" align="center" class="fond">
	<div class="contener_general">
		<div class="contener_mixte">
			<div class="ballcolor ball_1">&nbsp;</div>
		</div>
		<div class="contener_mixte">
			<div class="ballcolor ball_2">&nbsp;</div>
		</div>
		<div class="contener_mixte">
			<div class="ballcolor ball_3">&nbsp;</div>
		</div>
		<div class="contener_mixte">
			<div class="ballcolor ball_4">&nbsp;</div>
		</div>
	</div>

	<div style="padding-top: 35px;">
	
		
	</div>
</div>

<script>
	
		$(document).ready(function loadPage() {
			$('#container').hide();
			$('#infoTable').hide();
			$.get('squad.html').success(function(data) {
				$('#dynamicContainer').html('');
				$('#dynamicContainer').html(data);
				$('#dynamicContainer').hide();
				$('#loadingAnimation').html('');
				$('#container').slideDown(600);
				$('#infoTable').slideDown(1200);
			});

		});

	</script>
	
	<div style="padding-left: 10%; padding-right: 10%; position: relative;" id="container">
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
		<div class="row" id="infoTable">

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
		</div>
	
		
		<div class="container-fluid" id="dynamicContainer" style="position: absolute; height:70%; padding-left: 10%; padding-right: 10%; width: 100%;">
			
	
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
			
				          
			 $('#dynamicContainer').show();
				
		 });  
		
		</script>
		<!-- End of container -->
	

<% } %>