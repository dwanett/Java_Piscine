# Compilation:
	# this command compiles .java files into .class files, put them into target directory
	mkdir target; javac -d ./target/ src/java/edu/school21/printer/*/*.java

# Packing in jar:
	cp -r src/resources target/
	jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target edu/ -C target/ resources

# Example use:
	If image file put to root project folder
	java -jar target/images-to-chars-printer.jar . o