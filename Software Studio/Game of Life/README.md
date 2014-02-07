Project 1

This project starts a game of John Conway's Game Of Life.

Code Layout:

P1.1 There are three key javascript files: one that implements the game's logic (game.js), one that implements the drawing of a game (draw.js), and finally a third which starts a game (startGameX.js). The third file is interchangeable and only needs to contain the parameters of the game's intial state.

The HTML file that ties everything together is gameX.html.

There is a test file test.js that tests functions defined in game.js.

P1.2 There are three key javascript files: one that implements the game's logic and provides access to the state of the game (game.js), one that intialises the DOM elements and starts the game (startNewGame.js), and one that handles changing the DOM elements representing cells (modify.js).

The HTML file that ties everything together is gamePage.html.

Testing was done by visually inspecting the progress of the game, as well as console log outputs. As no complex functions were added to change the game's logic, and all more complicated functions dealt with graphic output, visual inspection was deemed to be enough.

Design Challenges:

Game Representation:

The inital design consideration was how to represent the game and what data object reflects the game. I chose to represent the game as an array of arrays, with either 1 or 0 at each index to represent the cells. A cell is alive if its value is 1, and dead if its value is 0. This binary nature could have been represented by booleans, but by using integers, the counting of living neighbours became trivial.

Although this design does reveal the state of the game to the world, extra care was taken to ensure that the board was being manipulated correctly. However, this does not excuse the rep exposure - it was a decision chosen due to time and complexity constraints. Nevertheless, the board

Game State:

The state of the game is represented by both the array describing the game and another array of arrays that keeps a live count of the number of living neighbours a cell has. Each index corresponds to its respective cell in the game array. This array is updated each time the game's state changes. At each game update, this array is iterated through, and if the number of neighbours is suitable, the cell changes in the game array, and all of its neighbours get their counts updated. This 'dynamic' updating reduces the number of times an array has to be iterated through.

The game also has three states: preparation, resumed, and paused. Preparation is the state of the game upon intialisation, while resumed and paused refer to games in progress.

An alternative to this representation would have been to explicitly create an abstract data type that represents cells, and carries fields that correspond to its state and its neighbours state. In this case, only one array needs to be created to hold these cell objects. However, the implementation of the chosen representation proved to be simple enough such that this alternative was not pursued.

Game Features:

Depending on what state the game is in, clicking on the cells have different effects. If the game is in the "preparation" or "paused" state, clicking on the cell toggles its aliveness. Therefore, cells can be made dead or alive upon user choice.

Citations

test.js makes use of a function defined at: http://stackoverflow.com/questions/4025893/how-to-check-identical-array-in-most-efficient-way

several functions presented in class (especially iterators) are used.