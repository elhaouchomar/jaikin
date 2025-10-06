all: run

compile:
	javac *.java -d build


# tell 'make' to always compile first before running.
run: compile
	java -cp build Main


clean:
	rm -rf build

# Declares that these are command names, not files
.PHONY: all compile run clean