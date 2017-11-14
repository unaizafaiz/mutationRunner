#!/usr/bin/env bash
FILE=`which $0`
DIST=`dirname $FILE`
MUTATIONS_JAR=$DIST/mutations.jar
RUNNER_JAR=$DIST/mutationrunner.jar
HW2_OUTPUT_JAR="/Users/manaswikarra/Documents/OOLE/cs474_2017_hw3/lakshmimanaswi_karra_unaiza_faiz_pavan_bharadwaj_hw3/HW2_Output/build/libs/HW2_Output.jar"

java -jar $RUNNER_JAR mutations.config $MUTATIONS_JAR -cp $HW2_OUTPUT_JAR sudoku.TestRunner