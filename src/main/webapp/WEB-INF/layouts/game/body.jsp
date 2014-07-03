<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%! private String teamName; %>
<% teamName = (String) session.getAttribute("teamName"); %>
<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
	<br> <br>
	<div class="container">
		<!-- NAVIGATION ROW -->
		<div class="row">

			<a href="#"> <img class="img-fix" alt="manager"
				src="/hb/img/manager.png">



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
						<th><% if (teamName != null) out.print(teamName); %></th>
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


		<!-- End of container -->
	</div>

</div>