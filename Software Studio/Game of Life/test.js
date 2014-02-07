// This file contains some simple tests for functions defined in game.js

// Helper array checking function
// http://stackoverflow.com/questions/4025893/how-to-check-identical-array-in-most-efficient-way

function arraysEqual(arr1, arr2) {
    if(arr1.length !== arr2.length){
        return false;
    }
    for(var i = arr1.length; i--;) {
    	for(var j = arr1[i].length; j--;) {
        	if(arr1[i][j] != arr2[i][j]) 
               return false;
        }
    }
    return true;
}

// Testing count function

emptyGrid = [[0,0,0], [0,0,0], [0,0,0]];
edgeGrid = [[1,1,1], [1,0,0], [1,0,0]];
fullGrid = [[1,1,1], [1,1,1], [1,1,1]];

gameA = Game(emptyGrid);
gameB = Game(edgeGrid);
gameC = Game(fullGrid);

resultA = [[0,0,0], [0,0,0], [0,0,0]];
resultB = [[2,3,1], [3,5,2], [1,2,0]];
resultC = [[3,5,3], [5,8,5], [3,5,3]];

if (arraysEqual(gameA.neighbour, resultA)) {console.log("emptyGrid test passed");}
else {console.log("emptyGrid test failed");}

if (arraysEqual(gameB.neighbour, resultB)) {console.log("edgeGrid test passed");}
else {console.log("edgeGrid test failed");}

if (arraysEqual(gameC.neighbour, resultC)) {console.log("fullGrid test passed");}
else {console.log("fullGrid test failed");}

// Testing update function

gameA.update();
gameB.update();
gameC.update();

gridResultA = [[0,0,0], [0,0,0], [0,0,0]];
gridResultB = [[1,1,0], [1,0,0], [0,0,0]];
gridResultC = [[1,0,1], [0,0,0], [1,0,1]];

neighbourResultA = [[0,0,0], [0,0,0], [0,0,0]];
neighbourResultB = [[2,2,1], [2,3,1], [1,1,0]];
neighbourResultC = [[0,2,0], [2,4,2], [0,2,0]];

if (arraysEqual(gameA.board, gridResultA) && arraysEqual(gameA.neighbour, neighbourResultA)) {console.log("emptyGrid test passed");}
else {console.log("emptyGrid test failed");}

if (arraysEqual(gameB.board, gridResultB) && arraysEqual(gameB.neighbour, neighbourResultB)) {console.log("edgeGrid test passed");}
else {console.log("edgeGrid test failed");}

if (arraysEqual(gameC.board, gridResultC) && arraysEqual(gameC.neighbour, neighbourResultC)) {console.log("fullGrid test passed");}
else {console.log("fullGrid test failed");}