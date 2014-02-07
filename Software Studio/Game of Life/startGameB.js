// Initialises a game
// Has a timer
// Every time the timer fires, the board is updated, and a new game is drawn
(function () {

	var boardGrid = [[1, 1, 0, 0], [1, 0, 0, 0], [0, 0, 0, 1], [0, 0, 1, 1]];

	game = Game(boardGrid);

	// create the drawing pad object and associate with the canvas
	pad = Pad(document.getElementById('canvas'));
	pad.clear(); 

	// drawGrid(pad, boardGrid);
	drawGrid(pad, game.board);

	updateGame = function () {game.update();
								drawGrid(pad, game.board)};

	setInterval(updateGame, 100);

}) ();