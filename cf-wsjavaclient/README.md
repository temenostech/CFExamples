## cf-wsjavaclient [For TAFC and TAFJ]
 
This project creates the Java clients for a web service by invoking the 'JAVA_HOME/bin/wsimport' utility on a deployed webservice. To generate first we need to deploy the Web Service in axis2. This can be done by copying the file './dist/release/OFSConnector/lib/ws/java/t24-OFSConnectorService-jws.aar' into 'JBOSS_HOME/server/R12GA/deploy/axis2.war/WEB-INF/services' directory, one the deployment is finished you can verify your new web service on folllowing link by accessing its WSDL;
<pre>
	For TAFJ MB: http://localhost:9090/axis2/services/OFSConnectorServiceWS?wsdl
	For TAFC MB: http://localhost:8080/axis2/services/OFSConnectorServiceWS?wsdl
</pre>

### Build and run the cf-wsjavaclient
<pre>
	$ cd PROJECT_HOME/cf-wsjavaclient
	$ mvn clean install
</pre>