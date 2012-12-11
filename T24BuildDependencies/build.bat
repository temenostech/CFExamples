@echo off
setlocal
set ANT_CMD=apache-ant-1.8.2\bin\ant.bat
if "%2"=="verbose" set T24BUILD_VERBOSE=-Dt24build.verbose=true
if "%1"=="clean" 		goto Clean
if "%1"=="debug" 		goto Debug
if "%1"=="release" 		goto Release
if "%1"=="test" 		goto Test
if "%1"=="scheduled" 	goto Scheduled
if "%1"=="dist" 		goto Dist
goto BuildHelp

:BuildHelp
echo *************************************************************
echo *               T24 Enterprise Build System                 *
echo *************************************************************
echo Syntax:
echo		build ^<target^> {options}
echo ^Targets:
echo		clean			Clean all projects
echo		debug			Create a development build and publish artifacts
echo		release			Create a release build, publish artifacts and build reports
echo		test			Run integration tests
echo		scheduled		Show a list of projects currently scheduled to build
echo		dist			Copy all build and released artifacts to centralised distribution location
echo ^Options:
echo		verbose			Show build output
echo ^Example:
echo 	build.bat clean
echo 	build.bat release
echo		build.bat debug verbose
goto BuildEnd

:Clean
CALL %ANT_CMD% clean %T24BUILD_VERBOSE%
del /S /Q lib
rmdir lib
goto BuildEnd

:Debug
CALL %ANT_CMD% debug %T24BUILD_VERBOSE%
goto BuildEnd

:Release
CALL %ANT_CMD% release %T24BUILD_VERBOSE%
goto BuildEnd

:Test
CALL %ANT_CMD% test %T24BUILD_VERBOSE%
goto BuildEnd

:Scheduled
CALL %ANT_CMD% show.scheduled %T24BUILD_VERBOSE%
goto BuildEnd

:Dist
CALL %ANT_CMD% dist %T24BUILD_VERBOSE%
goto BuildEnd

:BuildEnd
