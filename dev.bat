@echo off
REM Update the TAFJ_MB_DIR to valid location on you machine
SET CURR_WORK_DIR=%CD%

SET TAFJ_MB_DIR=C:\TAFJ\Temenos\ModelBank_R12_JAVA

SET T24_HOME=%TAFJ_MB_DIR%\T24\source\T24_BP
SET T24_INSERT_DIR=%T24_HOME%
SET JBCEMULATE=prime
SET TAFJ_HOME=%TAFJ_MB_DIR%\TAFJ
SET JAVA_HOME=%TAFJ_MB_DIR%\3rdParty\jdk\jdk1.6.0_29-64

REM Point to the valid Maven Location on your machine
SET MAVEN_HOME=%CURR_WORK_DIR%\T24BuildDependencies\apache-maven-3.0.4

REM Amend the path
SET PATH=%TAFJ_HOME%\bin;%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

REM =============
REM For User Info
REM =============
echo "Current Working Directory : %CURR_WORK_DIR%"
echo "TAFJ Model Bank Directory : %TAFJ_MB_DIR%"
echo "TAFJ Home 		: %TAFJ_HOME%"
echo "Java Home 		: %JAVA_HOME%"
echo "Maven Home 		: %MAVEN_HOME%"