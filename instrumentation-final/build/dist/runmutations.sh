#!/usr/bin/env bash
FILE=`which $0`
DIST=`dirname $FILE`
MUTATIONS_JAR=$DIST/mutations.jar
RUNNER_JAR=$DIST/mutationrunner.jar
HW2_OUTPUT_JAR="/Users/manaswikarra/Projects/HW2_Output/out/artifacts/HW2_Output_jar/HW2_Output.jar"

echo MUTATIONS_JAR=$MUTATIONS_JAR
echo RUNNER_JAR=$RUNNER_JAR

java -jar $RUNNER_JAR mutations.config $RUNNER_JAR -cp $HW2_OUTPUT_JAR sudoku.TestRunner