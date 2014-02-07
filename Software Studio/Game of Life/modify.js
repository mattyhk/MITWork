// This file describes the functions that are needed to modify the DOM elements 
// to reflect everytime the game is updated

// Function goes through the entire board
// Checks each cell for aliveness
// If it is alive, removes any dead class, and adds alive class
// If it is dead, removes any alive class, and adds dead class

var modifyAllElem = function (game, cells) {
	var board = game.getBoard();
	var MAX_X = board.length - 1;
	var MAX_Y = board.length - 1;

	fromTo(0, MAX_X, function(i) {
		fromTo(0, MAX_Y, function(j) {
			modifyCell(game, i, j, cells)
		});
	});
}

// Function modifys the class of an individual cell
// If it was dead, makes it alive
// If it was alive, makes it dead

var modifyCell = function (game, x, y, cells) {
	var currentCellDiv = cells[x][y];
	if (game.getCell(x,y) === 1) {
		$(currentCellDiv).attr("class", "cell alive");
	}

	else {
		$(currentCellDiv).attr("class", "cell dead");
	}
}

