# Compilation:
	# this command compiles .java files into .class files, put them into target directory
	mkdir target; javac -d ./target/ src/java/edu/school21/printer/*/*.java

# Example use:
	If image file put to root project folder
	java -cp ./target edu.school21.printer.app.Program . 0 "$PWD/it.bmp"