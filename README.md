sandbox-ofsconnectorpoc
=======================
These projects are to provide example usage for invoking T24 Enterprise comoponents with TAFJ R12_201212_1 and TAFC PB201213

Navigate to the PROJECT_HOME (directory where you have loaded the Git repository) and setup build and run environment by executing the bat file (Please update the TAFJ_MB_DIR in the file according to your machine path)
<pre>
	$ dev
</pre>

## EB_OFSConnector

This actual T24 component to wrap OFS.BULK.MANAGER calls into component framework. To build the component, apply following commands;
<pre>
	$ cd T24BuildDependencies
	$ build release
</pre>

Once the build is finished successfully, following artefacts with Java API's will be available for us under PROJECT_HOME/dist/release/OFSConnector/lib directory to be used according to our requirements and needs;

<pre>
		1. Under data/java/t24-OFSConnectorService-Data.jar	--> Component Data Objects, Converter Objects and Response Objects, applicable for TAFC and TAFJ
		2. Under ejb
			2.1 tafc/t24-OFSConnectorService.ear	--> Component EJB interface for TAFC
			2.2 tafj/t24-OFSConnectorService-ejb.jar--> Component EJB interface for TAFJ
		3. Under proxyAdaptor/java/t24-OFSConnectorService-ProxyAdaptor.jar	--> Component Proxy Adaptor classes for external call out to 3rd party, applicable for TAFJ
		4. Under t24service/java/t24-OFSConnectorService.jar	--> Component Java API and jBC Implementation of Component service, applicable for TAFJ
		5. Under ws/java
			5.1 t24-OFSConnectorService-provider.jar	--> Component Provder API, applicable for TAFC and TAFJ (configurations needs to be changed to activate the related TAF impl)
			5.2 t24-OFSConnectorService-jws.aar	--> Component Axis2 deployable web service archive, applicable for TAFC and TAFJ (internally uses Component Provider API to call jBC Impl)
</pre>

# TAFJ Examples

## Installing TAFJ Dependencies and Starting TAFJ DB
	
To compile and run the different samples we need to install few libraries from TAFJ into local maven repositories. This can be done by applying the following command;

NOTE: Please make sure TAFJ_HOME is set
<pre>
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-common -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/TAFJCommon.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-core -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/TAFJCore.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-locking -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/TAFJLocking.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-collectorclient -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/T24CollectorClient.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafc-jremote -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/jremote.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=component-framework -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/ext/tComponentFramework.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=t24-precompiled -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/../T24/lib/R12GA.jar -DgeneratePom=true
</pre>

Make sure Databse configured with TAFJ MB is up and running

## Sample - ofsconn-standalone-test - [TAFJ Only]

This sample demonstrate how to directly invoke the Component Java API in a standalone TAFJ environment from a Java test class.
Refer to the test classes under com.temenos.services.ofsconnector.tafj.standalone package for an examples. Call to jBC Impl will reach as follows;
<pre>
	TestClass --> Component Java API --> Component jBC Impl
</pre>
Note: Before running this project, please install following component artefacts into maven repositorty;
<pre>
	$ cd PROJECT_HOME
	mvn install:install-file -DgroupId=com.temenos -DartifactId=eb_ofsconnector-data -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/data/java/t24-OFSConnectorService-Data.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=eb_ofsconnector -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/t24service/java/t24-OFSConnectorService.jar -DgeneratePom=true
</pre>	

### To run the project apply following command;
<pre>
	$ cd PROJECT_HOME/ofsconn-standalone-test
	mvn clean install
</pre>	
	
## Sample - ofsconn-standalone-provider-test [TAFJ Only]

This sample demonstrate how to invoke the Component jBC Implementation via Provider API in standalone TAFJ environment from a Java test class
Refer to the test classes under com.temenos.services.ofsconnector.tafj.standalone package for an examples. Calls to jBC Impl will reach as follows;
<pre>
	TestClass --> Component Provider API --> Component Java API --> Component jBC Impl
</pre>

Note: Before running this project, please install following component artefacts into maven repositorty;
<pre>
	$ cd PROJECT_HOME
	// Only if NOT already deployed}
	mvn install:install-file -DgroupId=com.temenos -DartifactId=eb_ofsconnector-data -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/data/java/t24-OFSConnectorService-Data.jar -DgeneratePom=true
	// Only if NOT already deployed}
	mvn install:install-file -DgroupId=com.temenos -DartifactId=eb_ofsconnector -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/t24service/java/t24-OFSConnectorService.jar -DgeneratePom=true
	
	//Please upadte the spring configurations to load TAFJ provder. This can be done by updating the configurations in oFSConnectorServiceContext.xml under t24-OFSConnectorService-provider.jar, comment the TAFC part and install into local maven repository;
	mvn install:install-file -DgroupId=com.temenos -DartifactId=eb_ofsconnector-provider-tafj -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/ws/java/t24-OFSConnectorService-provider.jar -DgeneratePom=true
</pre>	

### To run the project apply following command;
<pre>
	$ cd PROJECT_HOME/ofsconn-standalone-provider-test
	mvn clean install
</pre>	


## Sample - ofsconn-appsrv-provider-test [TAFJ Only]

This sample demonstrate how a web servlet can instatiate and invoke Component Java Provider API. This requires deploying Component API into TAFJ environment running in Application Server mode. 
Copy the following JAR's under TAFJ_MB_DIR/T24/Components/OFSConnector (create a directory if its does not exist) directory;

<pre>
	PROJECT_HOME/dist/release/OFSConnector/lib/data/java/t24-OFSConnectorService-Data.jar
	PROJECT_HOME/dist/release/OFSConnector/lib/t24service/java/t24-OFSConnectorService.jar
</pre>

And add classpath xml entry with following details in your tafj-service.xml under JBOSS_HOME/server/R12GA/deploy directory;
<pre>
	codebase="${jboss.home.url}/../../../T24/Component/OFSConnector" 
	archives="*"
</pre>

### To build the project by apply following command;
<pre>
	cd PROJECT_HOME/ofsconn-appsrv-provider-test
	mvn clean install
</pre>

Once build finsihed successfully, navigate to ofsconn-appsrv-provider-test/target and deploy the ofsconn.war into JBOSS_HOME/server/R12GA/deploy directory and access the following URL on your browser;
<pre>
	http://localhost:9090/ofsconn/foo?ofsRequest=ENQUIRY.SELECT,,INPUTT/123456,CURRENCY-LIST
</pre>

## Sample - ofsconn-standalone-ejb-test [For TAFC and TAFJ]

This sample will demonstrate how we can invoke jBC Impl using Component secure EJB API deployed in Application Server. The same example can be used for both TAFC and TAFJ system by updating the JNDI-name
lookup according to the system you want to connect. Example will lookup the default resource in jBoss Applocation Server as follows (update the JNDI if you have deployed with different name);
<pre>
	- For TAFJ 	-> OFSConnectorServiceBeanTAFJ/remote
		- Calls to jBC Impl will reach as follows;
			TestClass --> Component EJB API --> Component Java API --> Component jBC Impl
	- For TAFC  -> t24-OFSConnectorService/OFSConnectorServiceBeanTAFC/remote	
		- Calls to jBC Impl will reach as follows;
			TestClass --> Component EJB API --> Component Provider API --> Component jBC Impl
</pre>

To deploy the component ejb service in jBoss, simply drop the 't24-OFSConnectorService-ejb.jar' for TAFJ and  't24-OFSConnectorService.ear' for TAFC present in 'PROJECT_HOME/dist/release/OFSConnector/lib/ejb/[tafj/tafc]' directory into 'JBOSS_HOME/server/<profile>/deploy' directory.
Note: By default service EJB are security enabled and expect 'T24App' security domain to be present at deployment. If its not created please create one beofore deploying the EJB service and define a user 'SSOUSER=123456' 
with role 't24user' within jboss for authentication. SSOUSER will be passed a SessionContext to and EJB which will be then used to set context within T24 before invoking the jBC Impl.

The project depends on jBoss Client JAR whihc is referenced from 'ofsconn-standalone-ejb-test/lib' directory and few component jars i.e. Data and EJB jar, apply following command to publish jars in maven;
<pre>
	// Component Data JAR - Only if NOT already deployed}
	mvn install:install-file -DgroupId=com.temenos -DartifactId=eb_ofsconnector-data -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/data/java/t24-OFSConnectorService-Data.jar -DgeneratePom=true
	
	// Component EJB JAR
	mvn install:install-file -DgroupId=com.temenos -DartifactId=eb_ofsconnector-ejb-tafj -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/ejb/tafj/t24-OFSConnectorService-ejb.jar -DgeneratePom=true
</pre>

### To compile and run the test class 'OfsConnectorComponentTest' apply folliwng command;
<pre>
	$ cd PROJECT_HOME/ofsconn-standalone-ejb-test
	$ mvn clean install
</pre>

## Sample - ofsconn-standalone-provider-tafc-test [For TAFC Only]

This sample demonstrate how we can invoke jBC Impl from standalone Java Program using Component default Provider API by loading TOCF T24 Resource Adaptor in a open source JENCKS framework which provides a JCA conatiner
for Provider API to lookup a jca resource and connecto to T24 tafc_agent

This project requires following component aretfacts to be published on maven;
<pre>
	// Component Data JAR - Only if NOT already deployed}
	mvn install:install-file -DgroupId=com.temenos -DartifactId=eb_ofsconnector-data -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/data/java/t24-OFSConnectorService-Data.jar -DgeneratePom=true
	
	// Component Provider API with TAFC Configurations enabled
	mvn install:install-file -DgroupId=com.temenos -DartifactId=eb_ofsconnector-provider-tafc -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/ws/java/t24-OFSConnectorService-providertafc.jar -DgeneratePom=true
</pre>

### To build and run the test apply following command;
<pre>
	$ cd PROJECT_HOME/ofsconn-standalone-provider-tafc-test
	$ mvn clean install
</pre>

## Sample - ofsconn-standalone-provider-tafc-customCXManager-test [For TAFC Only]

This sample demonstrate how we can invoke jBC Impl from standalone Java Program using Component Provider API by providing custom T24 connection manager to connect to tafc_agent

This project requires following component aretfacts to be published on maven;
<pre>
	// Component Data JAR - Only if NOT already deployed}
	mvn install:install-file -DgroupId=com.temenos -DartifactId=eb_ofsconnector-data -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/data/java/t24-OFSConnectorService-Data.jar -DgeneratePom=true
	
	// Component Provider API jar - Only if NOT already deployed}
	mvn install:install-file -DgroupId=com.temenos -DartifactId=eb_ofsconnector-provider-tafc -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/ws/java/t24-OFSConnectorService-providertafc.jar -DgeneratePom=true
</pre>

### To build and run the test apply following command;
<pre>
	$ cd PROJECT_HOME/ofsconn-standalone-provider-tafc-customCXManager-test
	$ mvn clean install
</pre>

## Sample - cf-wsjavaclient [For TAFC and TAFJ - NOT READY YET]
 
This project creates the Java clients for a web service by invoking the 'JAVA_HOME/bin/wsimport' utility on a deployed webservice. To generate first we need to deploy the Web Service in axis2. This can be done by copying the file './dist/release/OFSConnector/lib/ws/java/t24-OFSConnectorService-jws.aar' into 'JBOSS_HOME/server/R12GA/deploy/axis2.war/WEB-INF/services' directory, one the deployment is finished you can verify your new web service on folllowing link by accessing its WSDL;
<pre>
	For TAFJ MB: http://localhost:9090/axis2/services/OFSConnectorServiceWS?wsdl
	For TAFC MB: http://localhost:8080/axis2/services/OFSConnectorServiceWS?wsdl
</pre>

### Build and run the cf-wsjavaclient
<pre>
	$ cd PROJECT_HOME/cf-wsjavaclient
	$ mvn clean install [-DwsdlLoc=<URL_Of_WebService_WSDL>]
</pre>

Above will generate the client classes and compile it for us to be used

### To run the test against the service simply apply following commands;
<pre>
	$ cd PROJECT_HOME/cf-wsjavaclient
	$ mvn -DskipTests=false clean verify
</pre>

### Obselete Contents 
This project creates the Java clients for a web service by invoking the 'java2wsdl' utility using the WSDL file generated by component build present under PROJECT_HOME/cf-wsjavaclient/src/main/resources directory.
To generate first we need to deploy the Web Service in axis2. This can be done by copying the file './dist/release/OFSConnector/lib/ws/java/t24-OFSConnectorService-jws.aar' into 'JBOSS_HOME/server/R12GA/deploy/axis2.war/WEB-INF/services' directory, one the deployment is finished you can verify your new web service on folllowing link by accessing its WSDL;
Note: Please make sure you update the oFSConnectorServiceContext.xml for appropiate TAF platform you are deploying you web sevrice in.
