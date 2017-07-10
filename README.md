## Base64 String Comparator Web Service
### Maven and JAX-RS on Jersey and Grizzly2

This web service provides 2 HTTP endpoints that accepts JSON base64 encoded binary data on both, as it follows:

    <host>/v1/diff/<ID>/left
    <host>/v1/diff/<ID>/right

• The provided data is diff-ed and the results are available on a third end point

    <host>/v1/diff/<ID>

• The results are provided in JSON format as following:

    If equal return that
    If not of equal size just return that
    If of same size provide insight in where the diffs are, actual diffs are not needed.
        § So mainly offsets + length in the data

### Technologies

This project was developed with:

    Java Development Kit (JDK) 1.8.0_131
    JUnit 4.9
    Apache Maven 3.3.9
    NetBeans 8.2
    Jersey 2.25.1
    Grizzly 2   
    

### Compile and Package

It was developed to run with an Uber jar.
In order to generate this jar, you should run:

    mvn package

It will clean, compile and generate a uber jar at target dir, e.g. json-diff-1.0-jar-with-dependencies.jar

### Test

It provides some methods to test:

• Unit and Integration Tests

For both, you can run:

    mvn test

Just for Unit Tests:

    mvn -Dtest=UnitTest test

Just for Integration Tests, run:

    mvn -Dtest=IntegrationTest test

• Command Line Tests on Linux using curl (just with web service running - check session 'Run')

Sending data to LEFT endpoint

    curl -i -H "Content-Type: application/json" -X POST -d '{"value":"YWMvZGM="}' http://localhost:8080/json-diff/v1/diff/1/left

Sending data to RIGHT endpoint

    curl -i -H "Content-Type: application/json" -X POST -d '{"value":"YWMvZGM="}' http://localhost:8080/json-diff/v1/diff/1/right

Getting the diff result

    curl -X GET -i http://localhost:8080/json-diff/v1/diff/1

### Run

In order to run the web service, run the uber jar simply as following:

    java -jar json-diff-1.0-jar-with-dependencies.jar

By default, the service will be available at 

    http://localhost:8080/json-diff

However, you are able to choose the host name and port just setting these two environment variables before running:

    export JSONDIFF_HOSTNAME='myserver.com'
    export JSONDIFF_PORT='80'



### License

MIT © [Marcio Branquinho Dutra](https://github.com/dutramb)