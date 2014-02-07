// code for the game of life logic


// The grid representing the game is a 2x2 array with each slot containing {0, 1}
// 1 denotes the cell is alive, 0 denotes the cell is dead
// locations outside the grid are always assumed dead

// StartGame object
// Intialises a game by taking in a board and create a neighbour count 
// Provides functions to get the board state and update it

Game = function (grid) {
	var that = Object.create(Game.prototype);
	var board = grid;
	var neighbour = [];
	var state = "preparation";
	for (var x = 0; x < board.length; x += 1) {
		neighbour.push([]);
		for (var y = 0; y < board[x].length; y += 1) {
			neighbour[x].push(0);
		}
	}

	// Count function
	// After initialisation, this function steps through the grid, and for each cell counts the number
	// of neighbours that are alive

	that.count = function () {
		for (var x = 0; x < board.length; x += 1)	{
			for (var y = 0; y < board[x].length; y += 1)	{
				var liveCount = 0;
				liveCount += ((x - 1) >= 0 && (y - 1) >= 0) ?board[x - 1][y - 1]:0;
				liveCount += ((x - 1) >= 0) ?board[x - 1][y]:0;
				liveCount += ((x - 1) >= 0 && (y + 1) < board[x].length) ?board[x-1][y+1]:0;
				liveCount += ((y - 1) >= 0) ?board[x][y-1]:0;
				liveCount += ((y + 1) < board[x].length) ?board[x][y+1]:0;
				liveCount += ((x + 1) < board.length && (y - 1) >= 0) ?board[x+1][y-1]:0;
				liveCount += ((x + 1) < board.length) ?board[x+1][y]:0;
				liveCount += ((x + 1) < board.length && (y + 1) < board[x].length) ?board[x+1][y+1]:0;
				neighbour[x][y] = liveCount;
				}
			}
		}

	that.count();

	// Update function
	// Steps through each cell and its respective neighbour count
	// Uses the standard rules to check if the cell lives or dies, and updates the count of its neighbours
	
	that.update = function () {
		var neighbourChange = [];
		for (var x = 0; x < board.length; x += 1) {
			neighbourChange.push([]);
			for (var y = 0; y < board[x].length; y += 1) {
				neighbourChange[x].push(0);
			}
		}
		for (var x = 0; x < board.length; x += 1) {
			for (var y = 0; y < board[x].length; y+= 1)	{
				if (board[x][y] === 1)	{
					if (neighbour[x][y] < 2 || neighbour[x][y] > 3)	{
						board[x][y] = 0;
						if ((x - 1) >= 0 && (y - 1) >= 0) neighbourChange[x-1][y-1] = neighbourChange[x-1][y-1] - 1;
						if ((x - 1) >= 0) neighbourChange[x-1][y] = neighbourChange[x-1][y] - 1;
						if ((x - 1) >= 0 && (y + 1) < board[x].length) neighbourChange[x-1][y+1] = neighbourChange[x-1][y+1] - 1;
						if ((y - 1) >= 0) neighbourChange[x][y-1] = neighbourChange[x][y-1] - 1;
						if ((y + 1) < board[x].length) neighbourChange[x][y+1] = neighbourChange[x][y+1] - 1;
						if ((x + 1) < board.length && (y - 1) >= 0) neighbourChange[x+1][y-1] = neighbourChange[x+1][y-1] - 1;
						if ((x + 1) < board.length) neighbourChange[x+1][y] = neighbourChange[x+1][y] - 1;
						if ((x + 1) < board.length && (y + 1) < board[x].length) neighbourChange[x+1][y+1] = neighbourChange[x+1][y+1] - 1; 
						}
					}
				else {
					if (neighbour[x][y] === 3)	{
						board[x][y] = 1;
						if ((x - 1) >= 0 && (y - 1) >= 0) neighbourChange[x-1][y-1] = neighbourChange[x-1][y-1] + 1;
						if ((x - 1) >= 0) neighbourChange[x-1][y] = neighbourChange[x-1][y] + 1;
						if ((x - 1) >= 0 && (y + 1) < board[x].length) neighbourChange[x-1][y+1] = neighbourChange[x-1][y+1] + 1;
						if ((y - 1) >= 0) neighbourChange[x][y-1] = neighbourChange[x][y-1] + 1;
						if ((y + 1) < board[x].length) neighbourChange[x][y+1] = neighbourChange[x][y+1] + 1;
						if ((x + 1) < board.length && (y - 1) >= 0) neighbourChange[x+1][y-1] = neighbourChange[x+1][y-1] + 1;
						if ((x + 1) < board.length) neighbourChange[x+1][y] = neighbourChange[x+1][y] + 1;
						if ((x + 1) < board.length && (y + 1) < board[x].length) neighbourChange[x+1][y+1] = neighbourChange[x+1][y+1] + 1;
					}
				}
			}
		}
		for (var x = 0; x < board.length; x += 1) {
			for (var y = 0; y < board[x].length; y += 1) {
				neighbour[x][y] = neighbour[x][y] + neighbourChange[x][y];
			}
		}
	}

	// Reset function
	// Resets the board to all dead cells
	
	that.reset = function () {
		for (var x = 0; x < board.length; x += 1) {
			for (var y = 0; y < board.length; y += 1) {
				board[x][y] = 0;
				neighbour[x][y] = 0;
			}
		}
		state = "preparation";
	}

	// Start function
	// Checks the state of the game
	// If it was already in progress, continues
	// Else, assumes the game is starting from the beginning

	that.start = function () {

		if (state === "preparation") {
			that.count();
			state = "resumed";
		}
		else if (state === "resumed") {
			return;
		}
		else if (state === "paused") {
			that.count();
			state = "resumed";
		}

		// Should not enter this conditional
		else {
			throw "non-valid state of game";
		}

	}

	// Pause function
	// Checks the state of the game
	// If the game is in progress, pauses
	// Else, does nothing

	that.pause = function () {

		if (state === "preparation") {
			return;
		}
		else if (state === "resumed") {
			state = "paused";
		}
		else if (state === "paused") {
			return;
		}

		// Should not enter this conditional
		else {
			throw "non-valid state of game";
		}
	}

	// Resume function
	// If the game is paused, continues game
	// Else, does nothing

	that.resume = function () {

		if (state === "preparation") {
			that.count();
			return;
		}
		else if (state === "paused") {
			that.count();
			state = "resumed";
			return;
		}
		else if (state === "resumed") {
			return;
		}

		// Should not enter this conditional
		else {
			throw "non-valid state of game";
		}
	}

	// getState function
	// Returns state of the game

	that.getState = function () {
		return state;
	}

	// getBoard function
	// Returns the grid representing the game

	that.getBoard = function () {
		return board;
	}

	// getCell function
	// Returns the value held at spot (x,y) of the board

	that.getCell = function (x, y) {
		return board[x][y];
	}

	// toggleCell function
	// If the cell referred to is dead, makes it alive
	// If the cell referred to is alive, makes it dead
	
	that.toggleCell = function (x, y) {
		if (board[x][y] === 1) {
			board[x][y] = 0;
		}

		else {
			board[x][y] = 1;
		}
	}

	return that;

}

Game.prototype = {};
