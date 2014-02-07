# This solution template should be turned in through our submission site, at
# https://alg.csail.mit.edu

# Remember
# - You can submit as many times as you want
# - Only your last submission counts.  MAKE SURE it's what you want to submit.
# - You will get feedback after each submission.
# - If your submission has crashed, it is worth ZERO points.

### PROBLEM 1a ###
# Distribute the numbers 1-10 within these lists of lists.  
# You should create more sublists if necessary.
answer_problem_1a = [[6], [2,10,4], [3], [7], [8], [1,5,9]]
# Create a set of integers corresponding to the specified set of trees
answer_problem_1b1 = set([1,2,3,4,5,6,8,10,11,12,13,14,15,16,17,19])
answer_problem_1b2 = set([1,2,3,4,5,6,10,13,14,15,16,19])
# Enter the integer corresponding to the specified tree
answer_problem_1b3 = 5
answer_problem_1b4 = 8
answer_problem_1b5 = 10
answer_problem_1b6 = 13
answer_problem_1b7 = 15
answer_problem_1b8 = 19

### PROBLEM 4 ###
# Fill in the body of this function, which should take a list of valid
# credit card numbers, and a list of invalid credit card numbers,
# and return a list of indices of corrections.
def recover_credit_cards(valid, invalid):

  (n, m, k) = (len(valid), len(invalid), len(valid[0]))
  validDict = {}
  s = []
  prime = 99999999977
  listOfTenModP = []
  for j in range(k):
    listOfTenModP.append(pow(10, k - (j + 1), prime))

  for i in range(n):
    validDict[int(valid[i]) % prime] = i

  for i in range(m):
    number = invalid[i]
    hashedNumber = int(number) % prime
    if hashedNumber in validDict:
      s.append(validDict[hashedNumber])
    else:
      for j in range(k-1):
        hashNumber = (hashedNumber - int(number[j]) * listOfTenModP[j] - int(number[j + 1]) * listOfTenModP[j + 1] + int(number[j + 1]) * listOfTenModP[j] + int(number[j]) * listOfTenModP[j + 1]) % prime
        if hashNumber in validDict:
          s.append(validDict[hashNumber])
          break
          
  return s
     
    
 
