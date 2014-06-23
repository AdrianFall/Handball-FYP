<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="false">&times;</button>
				<h3 class="modal-title" id="myModalLabel">Register</h3>
			</div>
			<div class="modal-body">
				<form name="modal-form" class="form-horizontal">
					<!-- form stuff goes here -->
					<input type="text" name="email" value="" placeholder="Email"/> <br> 
					<input
						type="text" name="password" value="" placeholder="Password"/>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					Close</button>
				<button type="button" type="submit" class="btn btn-primary"
					id="save" data-dismiss="modal">Proceed</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->