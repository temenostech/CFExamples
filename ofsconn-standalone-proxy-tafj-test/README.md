---
layout: default
title: Component Framework Proxy Adapter Impl Example
---

This project is designed to provide an example of writing a proxy implementation for T24 Component Framework Proxy API

To run this example please make sure yous setup you environment as below;

1) Setup TAFJ_HOME environment (please update path according to your machine environment)

	
	SET TAFJ_HOME=D:\TAFJ\Temenos\TAFJ
	# Directory where you have all T24 JARS
	SET PRECOMPILE=D:\TAFJ\Temenos\jboss\modules\com\temenos\t24\main

2) Start your database
3) Make sure above TAFJ is configured to use the database correctly
4) Launch Eclipse - using following bat file;

	SET JAVA_HOME=D:\TAFJ\Temenos\java\jdk
	SET TAFJ_HOME=D:\TAFJ\Temenos\TAFJ
	SET PRECOMPILE=D:\TAFJ\Temenos\jboss\modules\com\temenos\t24\main

	SET PATH=%JAVA_HOME%\bin;%TAFJ_HOME%\bin;%PATH%

	CALL "D:\Utils\eclipse\eclipse.exe"

Note: Above script make sure that Eclipse has access to you environment variable

5) Run jUnit Test