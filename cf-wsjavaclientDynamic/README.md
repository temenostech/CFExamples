## cf-wsjavaclientDynamic [For TAFC and TAFJ]
 
This project downloads and use Java web service client released with the component build. To run this example please make sure you have valid Web Service deoloyed in axis2 and then simply run the java main line program to verify. You can deploy a web service in axis2 by copying the file './dist/release/OFSConnector/lib/ws/java/t24-OFSConnectorService-jws.aar' into 'JBOSS_HOME/server/R12GA/deploy/axis2.war/WEB-INF/services' directory, once the deployment is finished you can verify your new web service should be available on following link by accessing its WSDL;
<pre>
	For TAFJ MB: http://localhost:9090/axis2/services/OFSConnectorServiceWS?wsdl
	For TAFC MB: http://localhost:8080/axis2/services/OFSConnectorServiceWS?wsdl
</pre>

### Build and run the cf-wsjavaclientDynamic
<pre>
	$ cd PROJECT_HOME/cf-wsjavaclientDynamic
	$ mvn clean install
</pre>

The project contains a Java main line program which can either executed from within Eclipse oor from command line using 'java -jar <JAR_NAME> -cp <CLASSPATH_WITH_CLIENT_JAR> 
