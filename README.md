# Connect :four:
## implemented in Java :coffee:

#### Usage
```
javac CFGMain.java
  # which should automatically compile (in that order?):
  # - CFGMain
  # - ConnectFourFrame
  # - ConnectFourPanel

# run the program
java CFGMain
```

My goals for this project
- refactor for clarity and structure
  - as it is, a "ConnectFourFrame" controls the state of the game
  - I should extract the game feature into it's own class (or find a version online...)

- create a Connect4 "AI" using minimax algorithm
  - and not random like I am atm
