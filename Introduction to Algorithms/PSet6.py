""" Problem 4 """

## Subproblems:
##  val_max(i,j) = max value of a sequence of num op and parens from i to j
##  val_min(i,j) = min value " " "
##
## Recursion:
##  val_max(i,j) = max over k and op ( val_max[i,k] + val_max[k,j] , val_max[i,k] - val_min[k,j] , val_max[i,k] x val_max[k,j] , val_min[i,k] x val_min[k,j] )
##  val_min(i,j) = min over k and op ( val_min[i,k] + val_min[k,j] , val_min[i,k] - val_max[k,j] , val_max[i,k] x val_max[k,j] , val_min[i,k] x val_min[k,j] )
##
## Base Case:
##  val_max(i,i) and val_min(i,i) = numbers[i]
##
## Want:
##  val_max(0,len(numbers) - 1)

## For k = 1 to len(numbers) - 1:
##    for i = 0 to len(numbers) - 1 - k:
##      j = i + k
##      find val_max(i,j)
##      find val_min(i,j)
##
## return val_max[0, len(numbers) - 1]

def find_largest_value(numbers):

  val_max = {}
  val_min = {}

  for k in range(1, len(numbers)):
    for i in range(0, len(numbers) - k):
      j = i + k
      val_max[(i, j)] = find_max(i, j, val_max, val_min, numbers)
      val_min[(i, j)] = find_min(i, j, val_max, val_min, numbers)

  return val_max[(0, len(numbers) - 1)]



def find_max(i, j, val_max, val_min, numbers):

  maxVal = -99999

  if j - i == 1:

    if numbers[i] + numbers[j] >= maxVal:
      maxVal = numbers[i] + numbers[j]

    if numbers[i] - numbers[j] >= maxVal:
      maxVal = numbers[i] - numbers[j]

    if numbers[i] * numbers[j] >= maxVal:
      maxVal = numbers[i] * numbers[j]
    
  else:
    
    for x in xrange(j - i):

      if i + x == i:
        
        if numbers[i] + val_max[(i + x + 1, j)] >= maxVal:
          maxVal = numbers[i] + val_max[(i + x + 1, j)]

        if numbers[i] - val_min[(i + x + 1, j)] >= maxVal:
          maxVal = numbers[i] - val_min[(i + x + 1, j)]

        if numbers[i] * val_max[(i + x + 1, j)] >= maxVal:
          maxVal = numbers[i] * val_max[(i + x + 1, j)]

        if numbers[i] * val_min[(i + x + 1, j)] >= maxVal:
          maxVal = numbers[i] * val_min[(i + x + 1, j)]

      elif i + x + 1 == j:

        if numbers[j] + val_max[(i, i + x)] >= maxVal:
          maxVal = numbers[j] + val_max[(i, i + x)]

        if val_max[(i, i + x)] - numbers[j] >= maxVal:
          maxVal = val_max[(i, i + x)] - numbers[j]

        if numbers[j] * val_max[(i, i + x)] >= maxVal:
          maxVal = numbers[j] * val_max[(i, i + x)]

        if numbers[j] * val_min[(i, i + x)] >= maxVal:
          maxVal = numbers[j] * val_min[(i, i + x)]

      else:

        if val_max[(i, i + x)] + val_max[(i + x + 1, j)] >= maxVal:
          maxVal = val_max[(i, i + x)] + val_max[(i + x + 1, j)]

        if val_max[(i, i + x)] - val_min[(i + x + 1, j)] >= maxVal:
          maxVal = val_max[(i, i + x)] - val_min[(i + x + 1, j)]

        if val_max[(i, i + x)] * val_max[(i + x + 1, j)] >= maxVal:
          maxVal = val_max[(i, i + x)] * val_max[(i + x + 1, j)]

        if val_min[(i, i + x)] * val_min[(i + x + 1, j)] >= maxVal:
          maxVal = val_min[(i, i + x)] * val_min[(i + x + 1, j)]

        if val_max[(i, i + x)] * val_min[(i + x + 1, j)] >= maxVal:
          maxVal = val_max[(i, i + x)] * val_min[(i + x + 1, j)]

        if val_min[(i, i + x)] * val_max[(i + x + 1, j)] >= maxVal:
          maxVal = val_min[(i, i + x)] * val_max[(i + x + 1, j)]

  return maxVal

def find_min(i, j, val_max, val_min, numbers):

  minVal = 99999

  if j - i == 1:

    if numbers[i] + numbers[j] <= minVal:
      minVal = numbers[i] + numbers[j]

    if numbers[i] - numbers[j] <= minVal:
      minVal = numbers[i] - numbers[j]

    if numbers[i] * numbers[j] <= minVal:
      minVal = numbers[i] * numbers[j]
    
  else:
    
    for x in xrange(j - i):

      if i + x == i:
        
        if numbers[i] + val_min[(i + x + 1, j)] <= minVal:
          minVal = numbers[i] + val_min[(i + x + 1, j)]

        if numbers[i] - val_max[(i + x + 1, j)] <= minVal:
          minVal = numbers[i] - val_max[(i + x + 1, j)]

        if numbers[i] * val_max[(i + x + 1, j)] <= minVal:
          minVal = numbers[i] * val_max[(i + x + 1, j)]

        if numbers[i] * val_min[(i + x + 1, j)] <= minVal:
          minVal = numbers[i] * val_min[(i + x + 1, j)]

      elif i + x + 1 == j:
        if numbers[j] + val_min[(i, i + x)] <= minVal:
          minVal = numbers[j] + val_min[(i, i + x)]

        if val_min[(i, i + x)] - numbers[j] <= minVal:
          minVal = val_min[(i, i + x)] - numbers[j]

        if numbers[j] * val_max[(i, i + x)] <= minVal:
          minVal = numbers[j] * val_max[(i, i + x)]

        if numbers[j] * val_min[(i, i + x)] <= minVal:
          minVal = numbers[j] * val_min[(i, i + x)]

      else:

        if val_min[(i, i + x)] + val_min[(i + x + 1, j)] <= minVal:
          minVal = val_min[(i, i + x)] + val_min[(i + x + 1, j)]

        if val_min[(i, i + x)] - val_min[(i + x + 1, j)] <= minVal:
          minVal = val_min[(i, i + x)] - val_max[(i + x + 1, j)]

        if val_max[(i, i + x)] * val_max[(i + x + 1, j)] <= minVal:
          minVal = val_max[(i, i + x)] * val_max[(i + x + 1, j)]

        if val_min[(i, i + x)] * val_min[(i + x + 1, j)] <= minVal:
          minVal = val_min[(i, i + x)] * val_min[(i + x + 1, j)]

        if val_max[(i, i + x)] * val_min[(i + x + 1, j)] <= minVal:
          minVal = val_max[(i, i + x)] * val_min[(i + x + 1, j)]

        if val_min[(i, i + x)] * val_max[(i + x + 1, j)] <= minVal:
          minVal = val_min[(i, i + x)] * val_max[(i + x + 1, j)]
          

  return minVal

        

      
    
  
