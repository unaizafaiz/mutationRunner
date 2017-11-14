# CS 474: Object Oriented Languages and Environtment HW3

By Lakshmi Manaswi Karra, Pavan Bharadwaj Holenarasipura , Unaiza Faiz



## Steps To Run The Program
1. Open hw2_output
- run ./gradlew build
- Go to HW2_Output/build/libs/HW2_Output.jar  -> Right click and ‘copy path’ (to copy the fully qualified path of HW2_Output.jar)
2. Open instrumentation-final
3. Open the file runmutations.sh in the location - ./src/main/dist/runmutations.sh —> paste the fully qualified path of HW2_Output.jar to  HW2_OUTPUT_JAR variable  
4. ./gradlew clean dist 
5. Run >> Main OR execute the command build/dist/runmutations.sh  
6. OUTPUT:
View the log files of the form Comparing-<classname>-*.log in instrumentation-final folder to check the difference in the trace after mutation of code.

NOTE:  If the trace after mutation of code is same as the trace before mutation then it implies that the test has passed and the mutation has not effected the code. And hence the comparing-file.log contain an empty output.


## Configuration File - mutations.config

File location: instrumentation-final/mutations.config

Configuration file that is passed as input to the main function.

Each line represents the class on which mutation must be performed, the type of mutation operator and the corresponding test class that must be run to get the mutated code trace

## Instumentation-final

This project is divided into two modules:

### src/main/java

This folder contains javassist instrumentation implementation packages:

- instrumentation package:
	- instrumentation.SimpleMain class
		implements Premain function for javassist launcher

	- instrumentation.SimpleTransformer class
		implements ClassFileTransformer

- mutations package:
Contains classes that implement different mutation operators

- Access Modifier Change - AMC

	* ModifyPublicAccessMutation Class:
	mutates modifier of method from public to private

	* ModifyProtectedAccessMutation Class:
	mutates fields of superclass from protected to private

- Java Specific Feature

	* ModifyStaticMutation Class:
	JSD - static modifier deletion -
    mutates superclass static field to non-static

	* ModifyNonStaticMutation Class:
	JSI - static modifier insertion -
	 mutates superclass non-static field to static

- Inheritance

	* InsertHidingVarMutation Class:
	IHI - Hiding variable insertion -
	 mutates subclass inserts variable declaration of fields that are declare in superclass field

	* DeletingOverridingMethodMutation Class:
	IOD - Overridding Method Deleting -
	 Implements Mutation Operator to delete a method that is overriding a superclass method

- Polymorphism

	* DeletingOverloadingMethodMutation Class:
	OMD - Overloading Method Deleting -
	 Implements Mutation Operator to delete a method that is overloaded
	 
	 * TypeCastChangeMutation Class:
	PCC - type cast chage -
	 Implements Mutation Operator to cast type of a subclass field to its corresponding superclass type


### mutationrunner

* public static void main(String arguments)

* runMutation(ArrayList<String> command, String classToMutate, String mutation, String testToRun, int finalIndex, boolean runMutate)
	- Calls the mutation for the specified class and specific mutation if runMutate is true
	- Writes the output of the tests (the Trace) into files (with names corresponding to mutation-testToRun-classtoMutate)

* compareFiles(String noMutationOutput, String mutationOutput, int fileIndex, String classtoTest)
	- Compares the trace files of different mutations with the trace file without the mutation of code.
	- NOTE: Creates an empty file when both the trace files have the same output.

## HW2_Output
This project contains ASTParser instrumented project output from HW2

- src folder contains the entire instrumented source code of HW1 sudoku project

- test folder contains jUnit test classes for boxChecker, columnChecker, rowChecker, checker class for sudoku


