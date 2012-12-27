@echo off
SET CURR_WORK_DIR=%CD%

REM Please update this path according to your machine	<<<<<<<<<<<<<<
SET TAFJ_MB_DIR=C:\TAFJ\Temenos\ModelBank_R12_JAVA		

SET TAFJ_HOME=%TAFJ_MB_DIR%\TAFJ
SET OFSCONNECTOR_LIB=%TAFJ_MB_DIR%\T24\Component\OFSConnector\lib
SET JAVA_HOME=%TAFJ_MB_DIR%\3rdParty\jdk\jdk1.6.0_29-64

REM Point to the valid Maven Location on your machine 	<<<<<<<<<<<<<<
SET MAVEN_HOME=c:\Utils\apache-maven-3.0.4

REM Amend the path
SET PATH=%TAFJ_HOME%\bin;%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

REM =============
REM For User Info
REM =============
echo "Current Working Directory : %CURR_WORK_DIR%"
echo "TAFJ Model Bank Directory : %TAFJ_MB_DIR%"
echo "TAFJ Home 		: %TAFJ_HOME%"
echo "OFSCONNECTOR LIB	: %OFSCONNECTOR_LIB%"
echo "Java Home 		: %JAVA_HOME%"
echo "Maven Home 		: %MAVEN_HOME%"