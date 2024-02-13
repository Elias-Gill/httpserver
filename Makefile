.PHONY: run test

run:
	mvn compile exec:java -Dexec.mainClass=com.elias_gill.App

test:
	mvn test

format:
	mvn formatter:format
