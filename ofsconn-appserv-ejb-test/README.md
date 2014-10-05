## ofsconn-appserv-ejb-test [For TAFC and TAFJ]

This sample demonstrate how we can invoke component jBC Impl from a web application deployed in an JavaEE Application Server via Component EJB API. The instructions here are to test with TAFC system, but same logic can be applied to TAFJ by updating the JNDI name to lookup. This example will also demostrate how we can pass the Web Secutiry Context from Web Container to an EJB container as all our Component EJB by default secutiry enabled.
the web archive is also configured to be secured and using a 'T24App' as a security domain. Please make sure you have this secutiry doamin available in your jBoss area with user profile as 'SSOUSER=123456' and user role
'SSOUSER=t24user'.
Note: Make sure you have started the tafc_agent on T24 Application server

### To build the example war apply following command;
<pre>
	$ cd PROJECT_HOME/ofsconn-appserv-ejb-test
	$ mvn clean install
</pre>

Once the build is finished, yoiu will get XXXX.war under ofsconn-appserv-ejb-test/target directory, simply copy this file under 'JBOSS_HOME/server/&lt;profile&gt;/deploy' directory and access the following URL;
<pre>
	http://localhost:9090/ofsconn-ejb/foo?ofsRequest=ENQUIRY.SELECT,,INPUTT/123456,CURRENCY-LIST
</pre>

As soon as you will try to access the above URL, application server will challenge you to provide a user name and password. Provide a jboss user you have defined earlier as 'SSOUSER=123456', once authenticated 'SSOUSER' will be
the user context for that session and will be received by the web application, which then internally pass that context to component EJB as Signle Sign On, which internally call T24 with the same context.
