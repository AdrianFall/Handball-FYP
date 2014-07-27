<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
	<div class="container">
		<h1>v0.04!</h1>
		<p id="test">League, Team generation & Formation fully working.</p>
		<p>
			<button onclick="test()" class="btn btn-primary btn-lg">Learn more
				&raquo;</a>
		</p>
	</div>
</div>

<script>

function test() {
	document.getElementById('test').innerHTML = 'HEY TUNDE!';
}


</script>