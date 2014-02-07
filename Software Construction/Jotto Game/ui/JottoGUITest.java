// The GUI was tested by running through complete games to completion (i.e. to the point of a win)
// During runs through a game, different scenarios were tested and different components of the GUI were tested in order to ensure correctness
// These scenarios were:
// The newPuzzleButton was depressed with either no number in the newPuzzleNumber text field, 
//  with text that did not represent an integer in newPuzzleNumber,
//  with text that did represent a new Puzzle number,
//  and with text that represented the same Puzzle number
//  In each case it was checked against the expected outcome as given by the specs.
// The enter key was depressed when the cursor was in the newPuzzleNumber text field with either no number in the newPuzzleNumber text field, 
//  with text that did not represent an integer in newPuzzleNumber,
//  with text that did represent a new Puzzle number,
//  and with text that represented the same Puzzle number
//  In each case it was checked against the expected outcome as given by the specs.
// The guess table was tested by:
//  Entering a sequence of non delayed correct guesses
//  Entering a sequence of non delayed correct and incorrect guesses
//  Entering a sequence of one delayed correct guess followed by a sequence of correct and incorrect non delayed guesses
//  Entering a sequence of delayed and non delayed correct and incorrect guesses
// In each case it was checked that the GUI remained responsive, the output was returned to the table in the order that the guesses were submitted
// and the correct output was displayed for their respective guess (i.e. whether or not the guess was valid and if the guess was a win)