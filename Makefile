
run:
	mvn compile exec:java -Dexec.mainClass=com.elias_gill.App

test:
	mvn test

.PHONY: run
