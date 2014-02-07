# This solution template should be turned in through our submission site, at
# https://alg.csail.mit.edu

# Remember
# - You can submit as many times as you want
# - Only your last submission counts.  MAKE SURE it's what you want to submit.
# - You will get feedback after each submission.
# - If your submission has crashed, it is worth ZERO points.

### PROBLEM 3 ###
# Fill in the body of this function, which should take a description of the abyss,
# and return a sequence of moves to help the Zoombinis escape.
import heapq
import collections

(EXIT, BLOCK, EMPTY) = ('*', 'X', ' ')

class State:
  def __init__(self, board, zoombinis, unobstructed_moves, costTable, cost = 0, heuristiccost = 0, last_move = None, parent = None):
    self.board = board # array representing board with no people on it
    (self.n, self.m) = (len(self.board), len(self.board[0]))
    self.zoombinis = zoombinis # list of tuples (i, j)
    self.zoombinihash = frozenset([z for z in zoombinis if z is not None])
    self.last_move = last_move
    self.cost = cost
    self.parent = parent
    self.unobstructed_moves = unobstructed_moves
    self.costTable = costTable
    self.heuristic = heuristiccost

  def get(self, i, j):
    if i < 0 or i >= self.n or j < 0 or j >= self.m or ((i, j) in self.zoombinihash):
      return BLOCK
    else:
      return self.board[i][j]

  def move(self, zoombini, direction):
    (cur_i, cur_j) = self.zoombinis[zoombini]
    (new_i, new_j) = self.unobstructed_moves[(cur_i, cur_j, direction)]
    shift_i = (direction == 'S') - (direction == 'N')
    shift_j = (direction == 'E') - (direction == 'W')
    for (block_i, block_j) in self.zoombinihash:
      if (cur_j == block_j) and (cur_i * shift_i < block_i * shift_i <= new_i * shift_i):
        new_i = block_i - shift_i
      if (cur_i == block_i) and (cur_j * shift_j < block_j * shift_j <= new_j * shift_j):
        new_j = block_j - shift_j

    newzoombinis = [z for z in self.zoombinis]
    if self.get(new_i, new_j) == EXIT:
      newzoombinis[zoombini] = None
    else:
      newzoombinis[zoombini] = (new_i, new_j)

    newcost = self.cost
    newcost += abs(new_i - cur_i) + abs(new_j - cur_j)

    heuristic = 0
    for x in newzoombinis:
      if x == None: 
        pass
      else:
        heuristic += self.costTable[x]  
      return State(self.board, newzoombinis, self.unobstructed_moves, self.costTable, newcost,  heuristic, (zoombini, direction), self)
  
  def get_path(self):
    if self.parent is None:
      return []
    else:
      return self.parent.get_path() + [self.last_move]
    
  def getmoves(self):
    moves = []
    for z in range(len(self.zoombinis)):
      if self.zoombinis[z] is not None:
        moves.extend([(z, 'N'), (z, 'W'), (z, 'S'), (z, 'E')])
    return moves

  def getnextstates(self):
    return [self.move(z, d) for (z, d) in self.getmoves()]

  def done(self):
    return len(self.zoombinihash) == 0

  # Draws a sequence of moves, starting from this state.  Useful for debugging
  def drawmoves(self, moves = [], i = 0):
    print self.__str__()
    if i < len(moves):
      print 'ZOOMBINI %d MOVING %s' % (moves[i][0], moves[i][1])
    self.move(moves[i][0], moves[i][1]).drawmoves(moves, i+1)

  # Nicely formatted version of the current board
  def __str__(self):
    st = [' '] + ['-'] * self.m + ['\n']
    for i in range(self.n):
      st.append('|')
      for j in range(self.m):
        if (i, j) in self.zoombinihash:
          tup = (i, j)
          st.append(str(self.zoombinis.index(tup)))
        else:
          st.append(self.board[i][j])
      st.append('|\n')
    st.extend([' '] + ['-'] * self.m)
    return ''.join(st)

  def __eq__(self, other):
    return self.cost == other.cost
  def __lt__(self, other):
    return self.cost + self.heuristic < other.cost + other.heuristic
  def __gt__(self, other):
    return self.cost > other.cost

def move(board, cur_i, cur_j, direction):
  n, m = len(board), len(board[0])

  def get(i, j):
    if i < 0 or i >= n or j < 0 or j >= m:
      return BLOCK
    else:
      return board[i][j]

  shift_i = (direction == 'S') - (direction == 'N')
  shift_j = (direction == 'E') - (direction == 'W')
  while get(cur_i + shift_i, cur_j + shift_j) == EMPTY:
    cur_i += shift_i
    cur_j += shift_j
  if get(cur_i + shift_i, cur_j + shift_j) == EXIT:
    cur_i += shift_i
    cur_j += shift_j    
  return (cur_i, cur_j)

def parse(board):
  n, m = len(board), len(board[0])
  board = [row[:] for row in board]
  exits = []
  costTable = {}
  
  numzoombinis = 0
  for i in range(n):
    for j in range(m):
      costTable[(i,j)] = 0
      if board[i][j] not in [EMPTY, BLOCK, EXIT]:
        numzoombinis += 1
      elif board[i][j] == EXIT:
        exits.append((i, j))
        
  zoombinis = [(None, None)] * numzoombinis
  for i in range(n):
    for j in range(m):
      if board[i][j] not in [EMPTY, BLOCK, EXIT]:
        zoombinis[int(board[i][j])] = (i, j)
        board[i][j] = ' '
        
  unobstructed_moves = {}
  for i in range(n):
    for j in range(m):
      if board[i][j] == ' ':
        for d in ['N', 'E', 'W', 'S']:
          unobstructed_moves[(i, j, d)] = move(board, i, j, d)
  
  for loc in costTable:
    totalCost = 0
    for exitLoc in exits:
      totalCost += abs(loc[0] - exitLoc[0]) + abs(loc[1] - exitLoc[1])
    costTable[loc] = totalCost
  
  return State(board, zoombinis, unobstructed_moves, costTable)
  

def escape(abyss):
  startState = parse(abyss)
  statesToVisit = []
  heapq.heappush(statesToVisit, startState)
  finishedLocations = set()
  seenLocations = []
  locationCost = {}
  lowestCostState = None
  lowestCost = 0
  while not(len(statesToVisit) == 0):
    currentState = heapq.heappop(statesToVisit)
    if currentState.done():
      lowestCostState = currentState
      lowestCost = currentState.cost
      break
    finishedLocations.add(currentState.zoombinihash)
    for state in currentState.getnextstates():
      if state.zoombinihash not in locationCost:
        locationCost[state.zoombinihash] = state.cost
        heapq.heappush(statesToVisit, state)
      else:
        if state.zoombinihash not in finishedLocations:
          if state.cost < locationCost[state.zoombinihash]:
            locationCost[state.zoombinihash] = state.cost
            heapq.heappush(statesToVisit, state)
  return lowestCostState.get_path()
    
