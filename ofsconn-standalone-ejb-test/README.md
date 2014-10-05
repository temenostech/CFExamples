## ofsconn-standalone-ejb-test [For TAFC and TAFJ]

This sample will demonstrate how we can invoke jBC Impl using Component secure EJB API deployed in Application Server from a standalone client. The same example can be used for both TAFC and TAFJ system by updating the JNDI-name lookup according to the system you want to connect, switch between TADC and TAFJ by updating the Confiurations.java. Example will lookup the default resource in jBoss Applocation Server as follows (update the JNDI if you have deployed with different name);
<pre>
	- For TAFJ 	-> OFSConnectorServiceBeanTAFJ/remote
		- Calls to jBC Impl will reach as follows;
			TestClass --> Component EJB API --> Component Java API --> Component jBC Impl
	- For TAFC  -> t24-OFSConnectorService/OFSConnectorServiceBeanTAFC/remote	
		- Calls to jBC Impl will reach as follows;
			TestClass --> Component EJB API --> Component Provider API --> Component jBC Impl
</pre>

To deploy the component ejb service in jBoss, simply drop the 't24-OFSConnectorService-ejb.jar' for TAFJ and  't24-OFSConnectorService.ear' for TAFC present in '%OFSCONNECTOR_LIB%/ejb/[tafj/tafc]' directory into 'JBOSS_HOME/server/&lt;profile&gt;/deploy' directory.
Note: By default service EJB are security enabled and expect 'T24App' security domain to be present at deployment. If its not created please create one beofore deploying the EJB service and define a user 'SSOUSER' with password '123456' and role as 't24user' within jboss for authentication. SSOUSER will be passed a SessionContext to and EJB which will be then used to set context within T24 before invoking the jBC Impl.

### To compile and run the test class 'OfsConnectorComponentTest' apply folliwng command;
<pre>
	$ cd PROJECT_HOME/ofsconn-standalone-ejb-test
	$ mvn clean install
</pre>
