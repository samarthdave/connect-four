# Connect :four:
## implemented in Java :coffee:
*unarchived/restored* code from a project I made a couple of years back

### Usage
```
##############
### step 1 ### - build from source
##############
# run "make" - the inbuilt (usually preinstalled) dependency checker
make

# or build with "javac"
javac CFGMain.java
  # which should automatically compile (in that order?):
  # - CFGMain
  # - ConnectFourFrame
  # - ConnectFourPanel

##############
### step 2 ### - run the program
##############

java CFGMain
```

#### Work in Progress
- ~~Integrating a ConnectFourGame I [found online](https://medium.com/@ssaurel/creating-a-connect-four-game-in-java-f45356f1d6ba)~~
- or I can extract the logic from my existing Frame code

#### [How to use Makefiles with Java](https://www.cs.swarthmore.edu/~newhall/unixhelp/javamakefiles.html)
- Recursive Dependency checking
- Missing separator error?
  - *Note* use tabs not spaces or else make [will throw an error](https://stackoverflow.com/questions/16931770/makefile4-missing-separator-stop)
```
JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        Foo.java \
        Blah.java \
        Library.java \
        Main.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class
```

My goals for this project
- refactor for clarity and structure
  - as it is, a "ConnectFourFrame" controls the state of the game
  - I should extract the game feature into it's own class (or find a version online...)
  - change the colors from red & black to red/yellow

- create a Connect4 "AI" using minimax algorithm
  - and not random like I am atm
  - Update: alpha-beta pruning seems too complicated to implement at the moment...
