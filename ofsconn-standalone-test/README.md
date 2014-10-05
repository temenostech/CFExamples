## ofsconn-standalone-test - [TAFJ Only]

This sample demonstrate how to directly invoke the Component jBC Impl via Component Java API in a standalone TAFJ environment from a Java test class.
Refer to the test classes under com.temenos.t24.cfservice.client.example.tafj.standalone package for an examples. Call to jBC Impl will reach as follows;
<pre>
	TestClass --> Component Java API --> Component jBC Impl
</pre>

### To build and run the test apply following command;
<pre>
	$ cd PROJECT_HOME/ofsconn-standalone-test
	mvn clean verify
</pre>	