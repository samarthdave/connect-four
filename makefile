JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	ConnectFourGame.java \
	ConnectFourPanel.java \
	ConnectFourFrame.java \
	CFGMain.java

default: connect_four run

connect_four: $(CLASSES:.java=.class)

run:
	java CFGMain

clean:
	$(RM) *.class