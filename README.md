sandbox-ofsconnectorpoc
=======================
This project is to provide example usage for invoking T24 Enterprise comoponents.

## ofs-connector

The ofs-connector example uses the component framework (currently apigen) to create a Java & jBC interface from a UML model.

### Installing dependencies for ofs-connector

We want to use maven to compile our ofs-connector jar into a project. Therefore we need to install our dependencies in the local maven repository as we don't publish these dependencies currently.

NOTE: Please make sure 1) TAFJ_HOME is set 2) The path for artifact "t24-precompiled" is pointing to the T24 pre-compiled jar

	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-common -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/TAFJCommon.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-core -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/TAFJCore.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-locking -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/TAFJLocking.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafj-collectorclient -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/T24CollectorClient.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=tafc-jremote -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/lib/jremote.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=t24-precompiled -Dversion=dev -Dpackaging=jar -Dfile=%TAFJ_HOME%/../T24/DEV/lib/201206.jar -DgeneratePom=true
	mvn install:install-file -DgroupId=com.temenos -DartifactId=component-framework -Dversion=dev -Dpackaging=jar -Dfile=./ofs-connector/lib/tComponentFramework.jar -DgeneratePom=true


### Building ofs-connector
This example uses an ant build task to call apigen and the TAFJ compiler (as this was already available), but we then use maven to compile & package the generated jBC, Java, and TAFJ generated Java into a simple jar.

	$ cd ofs-connector
	$ mvn clean install

## ofs-connector-test

The ofs-connector-test example has junit test cases to show the uage of invoking the ofs-connector component APIs.

### Running ofs-connector-test
This example has some junit test cases which run during the test phase. You'll need to be running H2 with a real T24 environment as these test cases send OFS requests for processing.

	$ cd ofs-connector-test
	$ mvn clean install

## cf-wsjavaclient (NOT YET USED)
 
This project creates the Java clients for a web service by invoking the wsdl2java.

### Running cf-wsjavaclient

	$ cd cf-wsjavaclient
	$ mvn clean install

