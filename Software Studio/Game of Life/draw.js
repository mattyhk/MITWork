// a function that takes in a board and draws it

// define some colours

var black = Color(0,0,0);
var red = Color(255,0,0);
var green = Color(0,255,0);
var blue = Color(0,0,255);



// Define a draw grid function
// Takes in a pad, a grid and draws a representation of the grid
// Green circles indicates a cell is dead, and a green circle indicates the cell is alive
// Parameters:
// Pad pad = the pad to be drawn on
// Array grid = the game to be drawn

var drawGrid = function (pad, grid) {
	
	// split pad into x and y grid
	var MAX_X = grid.length;
	var MAX_Y = grid.length;
	var x_factor = pad.get_width() / MAX_X;
	var y_factor = pad.get_height() / MAX_Y;

	// draw a box to contain the grid / game
	pad.draw_rectangle(Coord(0, 0), pad.get_width(), pad.get_height(), 10, black);

	// define variables for circles and squares
	var RADIUS = x_factor / 3;
	var LINE_WIDTH = 1;

	var halfX = x_factor / 2;
	var halfY = y_factor / 2;

	for (var i = 0; i < MAX_X; i += 1) {
		for (var j = 0; j < MAX_Y; j += 1) {
			if (grid[i][j] === 1) {
				pad.draw_circle(Coord(i * x_factor + halfX, j * y_factor + halfY), RADIUS,
					LINE_WIDTH, green, green);
			}
			else {
				pad.draw_circle(Coord(i * x_factor + halfX, j * y_factor + halfY), RADIUS,
					LINE_WIDTH, red, red);
			}
		}
	}

}