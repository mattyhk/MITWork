// Sets up a new Game of Life page
// Creates the html elements and adds listeners
// Handles user interaction with the webpage

// invoke f(i) with i ranging over from..to
var fromTo = function (from, to, f) {
	for (var i = from; i <= to; i = i+1) f(i);
	};

// The Game of Life has three states: a preparation state, 
// a resumed state and a paused state
// Depending on the different states, clicking on cells have different effects
// If the game is in preparation state, clicking on cells toggle their alive status
// If the game is in resumed state, clicking has no effect
// If the game is in paused state, clicking on cells toggle their alive status
// The default state of cells upon initialisation is dead

var BOARD_WIDTH = 4;
var BOARD_HEIGHT = 4;

// This is a structure to store all of the div elements representing cells
// Stored in a 2x2 array to reflect the nature of the board
var divCells = [];

var playGame = function (game) {
	console.log("game state is: " + game.getState() + " and cells are: " + game.getBoard());
	game.update();
	modifyAllElem(game, divCells);
}

var changeCellState = function(game, x, y) {
	if (game.getState() === "preparation" || game.getState() === "paused"){
		console.log("Cell will be changed");
		game.toggleCell(x, y);
		modifyCell(game, x, y, divCells);
		return;
	}
	else {
		return;
	}
}

var createPage = function (game) {	
	fromTo(0, BOARD_HEIGHT, function(i) {
		divCells.push([]);
		var newRow = document.createElement("div");
		$(newRow).attr("class", "row");
		$(newRow).attr("id", "row" + i);
		$("#container").append(newRow);

		fromTo(0, BOARD_HEIGHT, function(j) {
			var newCell = document.createElement("div");
			$(newCell).attr("class", "cell dead");
			$(newCell).attr("id", i + "," + j);
			$("#row" + i).append(newCell);
			divCells[i].push(newCell);

			$(newCell).click(function() {
				var currentCell = divCells[i][j];
				changeCellState(game, i, j);
			});
		});
	});
}


$(document).ready (function () {
	var game = Game([[0,0,0,0,0], [0,0,0,0,0], [0,0,0,0,0], [0,0,0,0,0], [0,0,0,0,0]]);
	createPage(game);
	
	// Attach listeners to the game flow buttons
	$("#buttonStart").click(function() {
		console.log("Game Started");
		game.start();
		interval = setInterval(function(){playGame(game);}, 1000);
	});

	$("#buttonPause").click(function() {
		console.log("Game Paused");
		clearInterval(interval);
		game.pause();
	});

	$("#buttonResume").click(function() {
		console.log("Game Resumed");
		game.start();
		interval = setInterval(function(){playGame(game);}, 1000);
	});

	$("#buttonReset").click(function() {
		console.log("Game Reset");
		clearInterval(interval);
		game.reset();
		modifyAllElem(game, divCells);
	});

});