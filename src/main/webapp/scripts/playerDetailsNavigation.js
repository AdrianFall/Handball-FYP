function showDetails() {
	$('#contract').fadeOut(500);
	$('#health').fadeOut(500);
	$('#details').fadeIn(2000);
	

}

function showContract() {
	$('#details').fadeOut(500);
	$('#health').fadeOut(500);
	$('#contract').fadeIn(2000);
	
}

function showHealth() {
	$('#details').fadeOut(500);
	$('#contract').fadeOut(500);
	$('#health').fadeIn(2000);
}