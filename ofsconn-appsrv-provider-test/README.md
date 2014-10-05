## ofsconn-appsrv-provider-test [TAFJ Only]
This sample demonstrate how a web servlet can instatiate and invoke Component Java Provider API. This requires deploying Component API into TAFJ environment running in Application Server mode. 
Note: Make sure component service is deployed within TAFJ environment

### To build the project by apply following command;
<pre>
	cd PROJECT_HOME/ofsconn-appsrv-provider-test
	mvn clean install
</pre>

Once build finsihed successfully, navigate to ofsconn-appsrv-provider-test/target and deploy the ofsconn.war into JBOSS_HOME/server/&lt;profile&gt;/deploy directory and access the following URL on your browser;
<pre>
	http://localhost:9090/ofsconn/foo?ofsRequest=ENQUIRY.SELECT,,INPUTT/123456,CURRENCY-LIST
</pre>