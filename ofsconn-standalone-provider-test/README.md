## ofsconn-standalone-provider-test [TAFJ Only]

This sample demonstrate how to invoke the Component jBC Implementation via Provider API in standalone TAFJ environment from a Java test class
Refer to the test classes under com.temenos.t24.cfservice.client.example.tafj.standalone.provider package for an examples. Calls to jBC Impl will reach as follows;
<pre>
	TestClass --> Component Provider API --> Component Java API --> Component jBC Impl
</pre>

### To run the project apply following command;
<pre>
	$ cd PROJECT_HOME/ofsconn-standalone-provider-test
	mvn clean verify
</pre>	