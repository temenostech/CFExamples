#!/bin/ksh -e

function buildHelp {
	echo "*************************************************************"
	echo "*               T24 Enterprise Build System                 *"
	echo "*************************************************************"
	echo "Syntax:"
	echo "		build.sh <target> {options}"
	echo "Targets:"
	echo "		clean			Clean all projects"
	echo "		debug			Create a development build and publish artifacts"
	echo "		release			Create a release build, publish artifacts and build reports"
	echo "		test			Run integration tests"
	echo "		scheduled		Show a list of projects currently scheduled to build"
	echo "		dist			Copy all build and released artifacts to centralised distribution location"
	echo "Options:"
	echo "		verbose			Show build output"
	echo "Example:"
	echo "		./build.sh clean"
	echo "		./build.sh release"
	echo "		./build.sh debug verbose"
}

function clean {
	$ANT_CMD clean $T24BUILD_VERBOSE
	rm -fr ./lib
}

function debug {
        $ANT_CMD debug $T24BUILD_VERBOSE
}

function release {
        $ANT_CMD release $T24BUILD_VERBOSE
}

function runTest {
        $ANT_CMD test $T24BUILD_VERBOSE
}

function scheduled {
        $ANT_CMD show.scheduled $T24BUILD_VERBOSE
}

function dist {
        $ANT_CMD dist $T24BUILD_VERBOSE
}

arg1=$1
arg2=$2
typeset var T24BUILD_VERBOSE
typeset var ANT_CMD
ANT_CMD=apache-ant-1.8.2/bin/ant
if [[ $arg2 = "verbose" ]]; then T24BUILD_VERBOSE=-Dt24build.verbose=true; fi
if [[ $arg1 = "clean" ]]; then 
	clean
elif [[ $arg1 = "debug" ]]; then
	debug
elif [[ $arg1 = "release" ]]; then
	release
elif [[ $arg1 = "test" ]]; then
	runTest
elif [[ $arg1 = "scheduled" ]]; then
	scheduled
elif [[ $arg1 = "dist" ]]; then
	dist
else
	buildHelp
fi
exit 0
