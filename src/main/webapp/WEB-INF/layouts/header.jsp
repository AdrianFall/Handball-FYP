<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Handball Manager</a>
		</div>
		<div class="navbar-collapse collapse">

			<form class="navbar-form navbar-left">
				<button type="submit" data-toggle="modal" data-target="#myModal"
					class="btn btn-primary">Register</button>
			</form>



			<form action="try.jsp" class="navbar-form navbar-right">
				<button type="submit" class="btn btn-success">Sign in</button>
				<div class="form-group">
					<input type="text" placeholder="Email" class="form-control">
				</div>
				
				<div class="form-group">
					<input type="password" placeholder="Password" class="form-control">
					
				</div>
				<p class="text-right"><font color="#000EEE">Forgot pw?</font></p>
				
			</form>






		</div>
		<!--/.navbar-collapse -->
	</div>
</div>

