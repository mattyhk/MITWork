# This solution template should be turned in through our submission site, at
# https://alg.csail.mit.edu

# Remember
# - You can submit as many times as you want
# - Only your last submission counts.  MAKE SURE it's what you want to submit.
# - You will get feedback after each submission.
# - If your submission has crashed, it is worth ZERO points.

### PROBLEM 4 ###
# Fill in the body of this function, which should take a description of the abyss,
# and return a minimal length sequence of moves to help the Zoombinis escape.
import collections

def escape(abyss):
  size = len(abyss[0])
  board = BoardState(abyss,size)
  done = board.done(board.zoombiniLocations[0])
  statesToVisit = collections.deque([])
  statesToVisit.append(board.zoombiniLocations)
  while True:
    currentState = statesToVisit.popleft()
    board.update(currentState)
    ##print 'the board is ' + str(board)
    ##print 'the current state is ' + str(currentState)
    ##print 'the current state locations is ' + str(currentState[0])
    ##raw_input()
    if board.done(currentState[0]):
      ##print 'with this state should be done'
      return currentState[1]
    nextStates = board.getnextstates()
    for state in nextStates:
      statesToVisit.append(state)

class BoardState:

  def __init__(self, abyss, size):
    self.seenLocations = {}
    self.size = size
    self.abyss = abyss
    self.zoombiniLocations = [[],[]]
    self.zoombinis = set()
    numZoombinis = self.findNumberZoombinis()
    for i in range(numZoombinis):
      self.zoombiniLocations[0].append((0,0))
    self.findZoombinis()
    (exits, boulders) = self.findExitsBoulders()
    self.exits = frozenset(exits)
    self.boulders = frozenset(boulders)
    self.directions = frozenset(['N', 'S', 'W', 'E'])
    self.seenLocations = set()

  def findZoombinis(self):
    for i in range(self.size):
      for j in range(self.size):
        y = self.abyss[i][j]
        if y == '0' or y == '1' or y == '2' or y == '3' or y == '4' or y == '5':
          self.zoombiniLocations[0][int(y)] = (i,j)   
    

  def findNumberZoombinis(self):
    x = 0
    for i in range(self.size):
      for j in range(self.size):
        y = self.abyss[i][j]
        if y == '0' or y == '1' or y == '2' or y == '3' or y == '4' or y == '5':
          self.zoombinis.add(int(y))
          x += 1   
    return x

  def findExitsBoulders(self):
    exits = []
    boulders = []
    for i in range(self.size):
      for j in range(self.size):
        cell = self.abyss[i][j]
        if cell == '*':
          exits.append((i,j))
        elif cell == 'X':
          boulders.append((i,j))
    return (exits, boulders)


  def move(self, zoombini, direction):
    location = self.zoombiniLocations[0][zoombini]
    i = location[0]
    j = location[1]
    changed = False
    exited = False
    stoppedLocation = (i,j)
    
    if i < 0 or i >= self.size or j < 0 or j >= self.size:
      return (True, stoppedLocation)

    if direction == 'N':
      while i > 0:
        nextLoc = (i - 1, j)
        if nextLoc in self.zoombiniLocations[0] or nextLoc in self.boulders:
          stoppedLocation = (i, j)
          changed = True
          break
        elif nextLoc in self.exits:
          stoppedLocation = (self.size, self.size)
          changed = True
          exited = True
          break
        i = i - 1
      if not changed and not exited:
        stoppedLocation = (0, j)
        
    elif direction == 'S':
      while i < self.size - 1:
        nextLoc = (i + 1, j)
        if nextLoc in self.zoombiniLocations[0] or nextLoc in self.boulders:
          stoppedLocation = (i, j)
          changed = True
          break
        elif nextLoc in self.exits:
          stoppedLocation = (self.size, self.size)
          changed = True
          exited = True
          break
        i = i + 1
      if not changed and not exited:
        stoppedLocation = (self.size - 1, j)
                  
    elif direction == 'W':
      while j > 0:
        nextLoc = (i, j - 1)
        if nextLoc in self.zoombiniLocations[0] or nextLoc in self.boulders:
          stoppedLocation = (i, j)
          changed = True
          break
        elif nextLoc in self.exits:
          stoppedLocation = (self.size, self.size)
          changed = True
          exited = True
          break
        j = j - 1
      if not changed and not exited:
        stoppedLocation = (i, 0)
                  
    elif direction == 'E':
      while j < self.size - 1:
        nextLoc = (i, j + 1)
        if nextLoc in self.zoombiniLocations[0] or nextLoc in self.boulders:
          stoppedLocation = (i, j)
          changed = True
          break
                  
        elif nextLoc in self.exits:
          stoppedLocation = (self.size, self.size)
          changed = True
          exited = True
          break
        j = j + 1   
      if not changed and not exited:
        stoppedLocation = (i, self.size - 1)

    if stoppedLocation[0] == self.zoombiniLocations[0][zoombini][0] and stoppedLocation[1] == self.zoombiniLocations[0][zoombini][1]:
      locationChanged = False
    else:
      locationChanged = True

    return (locationChanged, stoppedLocation)  

  def getnextstates(self):
    nextStates = []
    for zoombini in self.zoombinis:
      for direction in ['N', 'S', 'W', 'E']:
        (haschanged, newLocation) = self.move(zoombini, direction)
        if haschanged:
          newLocations = []
          newMoves = []
          for location in self.zoombiniLocations[0]:
            newLocations.append(location)
          newLocations[zoombini] = newLocation
          for moves in self.zoombiniLocations[1]:
              newMoves.append(moves)
          newMoves.append((zoombini, direction))
          if frozenset(newLocations) not in self.seenLocations:
            nextStates.append((newLocations, newMoves))
            self.seenLocations.add(frozenset(newLocations))
    return nextStates

  def update(self, state):
    for zoombini in self.zoombinis:
      self.zoombiniLocations[0][zoombini] = state[0][zoombini]
    self.zoombiniLocations[1] = state[1]
      
  def done(self, locations):
    for zoombini in self.zoombinis:
      ##print str(locations)
      i = locations[zoombini][0]
      j = locations[zoombini][1]
      if i < self.size and i >= 0 and j < self.size and j >= 0:
        return False
    return True

  def __hash__(self):
    x = 0
    for (i,j) in self.zoombiniLocations[0]:
      if i < self.size and i >= 0 and j < self.size and j >= 0:
        x += 1
    return x
  
  def __str__(self):
    string = ''
    for i in range(self.size):
      string = string + str(self.abyss[i]) + '\n'
    return string

class State:

  def __init__(self, locations, moves):
    self.locations = locations
    self.moves = moves

  def lengthOfMoves(self):
    return len(self.moves)

  def locations(self):
    return self.locations


