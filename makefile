JFLAGS = -g
JC = javac
.SUFFIXES: .java .class

.java.class:
	javac -cp ".:src" -d ./bin ./CFGMain.java

CLASSES = \
	ConnectFourGame.java \
	ConnectFourPanel.java \
	ConnectFourFrame.java \
	CFGMain.java

default: connect_four run

connect_four: $(CLASSES:.java=.class)

run:
	java -cp ".:bin" CFGMain

clean:
	$(RM) *.class
	$(RM) ./bin/*.class