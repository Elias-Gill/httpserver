
run:
	mvn compile exec:java -Dexec.mainClass=com.elias_gill.App

# run-bundle:
# 	java -jar target/*jar-with-dependencies.jar

# build:
# 	mvn clean compile assembly:single

test:
	mvn test

.PHONY: run
