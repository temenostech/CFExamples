sandbox-ofsconnectorpoc
=======================
This project is to provide example usage for invoking T24 Enterprise comoponents with TAFJ R12_201212_1

## Setup build and run environment by executing the bat file (Please update the TAFJ_MB_DIR according to your machine path)

	$ dev

## ofsconnector-component

Build the T24 Component by applying following commands;

	$ cd T24BuildDependencies
	$ build release

## Installing Dependencies
	
To compile and run the samples we need to install few TAFJ libraries and compile component libraries into local maven repositories. This can be done by applying the following command;

NOTE: Please make sure TAFJ_HOME is set
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-common -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/TAFJCommon.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-core -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/TAFJCore.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-locking -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/TAFJLocking.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-collectorclient -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/T24CollectorClient.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafc-jremote -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/jremote.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=component-framework -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/ext/tComponentFramework.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=t24-precompiled -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/../T24/lib/R12GA.jar -DgeneratePom=true

Now install the component libs into local maven repository;
	$ cd ..
	mvn install:install-file -DgroupId=com.temenos -DartifactId=ofsconnector-data -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/data/java/t24-OFSConnectorService-Data.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=ofsconnector -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/t24service/java/t24-OFSConnectorService.jar -DgeneratePom=true
	
	Note: Before deploying the following JAR, please upadte the spring configurations to load TAFJ provder. This can be done by updating the configurations in oFSConnectorServiceContext.xml, comment the TAFC part
		and uncomment the TADJ configs
	mvn install:install-file -DgroupId=com.temenos -DartifactId=ofsconnector-provider -Dversion=dev -Dpackaging=jar -Dfile=./dist/release/OFSConnector/lib/ws/java/t24-OFSConnectorService-provider.jar -DgeneratePom=true
	
## Sample - ofsconn-standalone-test

This sample demonstrate how to directly invoke the Component Java API in a standalone TAFJ environment from a Java test class. 
Refer to the test class OfsConnectorComponentTest under com.temenos.services.ofsconnector.tafj.standalone package

## Sample - ofsconn-standalone-provider-test

This sample demonstrate how to invoke the Component Java Provider API in standalone TAFJ environment from a Java test class
Refer to the test class OfsConnectorComponentTest under com.temenos.services.ofsconnector.tafj.standalone package

## Sample - ofsconn-appsrv-provider-test

This sample demonstrate how a web servlet can instatiate and invoke Component Java Provider API. To build apply following command;

	cd ofsconn-appsrv-provider-test
	mvn clean install

Once build finsihed successfully, navigate to ofsconn-appsrv-provider-test/target and deploy the ofsconn-war.war into JBOSS_HOME/server/R12GA/deploy directory and access the following URL on your browser;

	http://localhost:8080/ofsconn/foo?firstname=Me&lastname=Too

## cf-wsjavaclient (NOT YET USED)
 
This project creates the Java clients for a web service by invoking the wsdl2java.

### Running cf-wsjavaclient

	$ cd cf-wsjavaclient
	$ mvn clean install

