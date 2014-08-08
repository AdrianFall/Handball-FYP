/* SET RANDOM LOADER COLORS FOR DEMO PURPOSES */
var demoColorArray = [ 'red', 'blue', 'green', 'yellow', 'purple', 'gray' ];
var colorIndex = Math.floor(Math.random() * demoColorArray.length);
setSkin(demoColorArray[colorIndex]);

// Stripes interval
var stripesAnim;
var calcPercent;

$progress = $('.progress-bar');
$percent = $('.percentage');
$stripes = $('.progress-stripes');
$stripes.text('////////////////////////');

/* WHEN LOADED */
$(window).load(function() {

});

/** * FUNCTIONS ** */

/* LOADING */
function preload() {

	var perc = $('.progress-bar').data("perc");
	var increment = 1;



		if (perc >= 0)
			setSkin("red");
		if (perc >= 40 && perc < 70)
			setSkin("yellow");
		if (perc > 69)
			setSkin("green");

		$progress.animate({
			width : perc + "%"
		}, 800);
		
		calcPercent = setInterval(function() {

			// loop through the items
			$percent.text(Math
					.floor(($progress.width() / $('.loader').width()) * 100)
					+ '%');

		});

	
	
	loaded = true;
		$progress.animate({
			width: perc + '%'
		}, perc, function() {
			$percent.text( perc + '%');
			clearInterval(calcPercent);
			clearInterval(stripesAnim);
		});	
} // END preLoad()

/* STRIPES ANIMATION */
function stripesAnimate() {
	animating();
	stripesAnim = setInterval(animating, 1000);
}

function animating() {
	$stripes.animate({
		marginLeft : "-=90px"
	}, 2000, "linear").append('/');
}

function setSkin(skin) {
	$('.loader').attr('class', 'loader ' + skin);

}