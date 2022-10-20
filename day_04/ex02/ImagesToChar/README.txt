# Downloading file
	wget -P lib https://repo1.maven.org/maven2/com/beust/jcommander/1.78/jcommander-1.78.jar
	wget -P lib https://repo1.maven.org/maven2/com/diogonunes/JColor/5.5.1/JColor-5.5.1.jar

# Compilation:
	# this command compiles .java files into .class files, put them into target directory
	mkdir target; javac -d target -cp lib/\*  src/java/edu/school21/printer/*/*.java

# Packing in jar:
	cp -r src/resources target/; cd target/; jar xf ../lib/jcommander-1.78.jar; jar xf ../lib/JColor-5.5.1.jar; cd ..
	jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .

# Example use:
	If image file put to root project folder
	java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN