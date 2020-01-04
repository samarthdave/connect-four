# Connect :four:
## implemented in Java :coffee:
*unarchived/restored* code from a project I made a couple of years back

### Usage
```
javac CFGMain.java
  # which should automatically compile (in that order?):
  # - CFGMain
  # - ConnectFourFrame
  # - ConnectFourPanel

# run the program
java CFGMain
```

#### Work in Progress
- ~~Integrating a ConnectFourGame I [found online](https://medium.com/@ssaurel/creating-a-connect-four-game-in-java-f45356f1d6ba)~~
- or I can extract the logic from my existing Frame code

My goals for this project
- refactor for clarity and structure
  - as it is, a "ConnectFourFrame" controls the state of the game
  - I should extract the game feature into it's own class (or find a version online...)
  - change the colors from red & black to red/yellow

- create a Connect4 "AI" using minimax algorithm
  - and not random like I am atm