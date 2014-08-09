/* SET RANDOM LOADER COLORS FOR DEMO PURPOSES */
var demoColorArray = [ 'red', 'blue', 'green', 'yellow', 'purple', 'gray' ];
var colorIndex = Math.floor(Math.random() * demoColorArray.length);
setSkin(demoColorArray[colorIndex]);

// Stripes interval
var stripesAnim;

$conditionProgress = $('#conditionProgressBar');
$conditionPercent = $('#conditionPercentage');
$conditionStripes = $('#conditionProgressStripes');
$conditionStripes.text('////////////////////////');

$moraleProgress = $('#moraleProgressBar');
$moralePercent = $('#moralePercentage');
$moraleStripes = $('#moraleProgressStripes');
$moraleStripes.text('\\\\\\\\\\\\\\\\\\\\\\\\');


/* WHEN LOADED */
$(window).load(function() {

});

/** * FUNCTIONS ** */

/* LOADING */
function preload() {

	var conditionPerc = $('#conditionProgressBar').data("perc");
	var moralePerc = $('#moraleProgressBar').data("perc");

		if (conditionPerc >= 0)
			setSkin("red", "condition");
		if (conditionPerc >= 40 && conditionPerc < 70)
			setSkin("yellow", "condition");
		if (conditionPerc > 69)
			setSkin("green", "condition");
			
		if (moralePerc >= 0)
			setSkin("red", "morale");
		if (moralePerc >= 40 && moralePerc < 70)
			setSkin("yellow", "morale");
		if (moralePerc > 69)
			setSkin("green", "morale");
			

		$conditionProgress.animate({
			width : conditionPerc + "%"
		}, 800);
		
		$moraleProgress.animate({
			width : moralePerc + "%"
		}, 800);
		
		

		$conditionPercent.text(Math
				.floor(($conditionProgress.width() / $('#conditionLoader').width()) * 100) + '%');

		$moralePercent.text(Math
				.floor(($moraleProgress.width() / $('#moraleLoader').width()) * 100) + '%');

	
	
	loaded = true;
		$conditionProgress.animate({
			width: conditionPerc + '%'
		}, conditionPerc, function() {
			$conditionPercent.text( conditionPerc + '%');
		});	
		
		$moraleProgress.animate({
			width: moralePerc + '%'
		}, moralePerc, function() {
			$moralePercent.text( moralePerc + '%');
		});	
} // END preLoad()

/* STRIPES ANIMATION */
function stripesAnimate() {
	animating();
	stripesAnim = setInterval(animating, 1000);
}

function animating() {
	$conditionStripes.animate({
		marginLeft : "-=90px"
	}, 2000, "linear").append('/');
}

function setSkin(skin, loaderType) {
	if (new String(loaderType).valueOf() == new String("condition").valueOf()) 
		$('#conditionLoader').attr('class', 'loader ' + skin);
	
	else if (new String(loaderType).valueOf() == new String("morale").valueOf())
		$('#moraleLoader').attr('class', 'loader ' + skin);
	
}