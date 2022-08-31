# Bowling Game - Java Challenge

## Running

#### Running the project

1. [JDK][]: Download and install Java JDK v1.8+.
2. [Maven][]: Download and install Maven or use integrated Maven Wrapper(mvnw).

After installing JDK and maven we can package and run the project using the following commands:

Packaging:

```

./mvnw clean package

or

mvn clean package
```

After we create a jar package file, we can run it using the following command:

```
java -jar target/JavaChallenge-1.0-SNAPSHOT.jar input_file.txt
```

Example:

````
java -jar target/JavaChallenge-1.0-SNAPSHOT.jar src/test/resources/positive/perfect.txt

````

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

[JDK]: https://www.oracle.com/java/technologies/javase-downloads.html

[Maven]: https://maven.apache.org/download.cgi

