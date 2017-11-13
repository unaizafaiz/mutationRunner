# CS 474: Object Oriented Languages and Environtment HW3

By Lakshmi Manaswi Karra, Pavan Bharadwaj , Unaiza Faiz



## Steps To Run The Program
    1. Open instrumentation-final intelliJ project
    
    2. Set arguments for to be passed to main method
       - Click 'Run' on the File menu -> Edit Configuration ->  Program Arguments - Paste the below line 
        mutations.config -cp ../Projects/HW2_Output/out/artifacts/HW2_Output_jar/HW2_Output.jar sudoku.TestRunner
        - In runMutation() method set env variable JAVA_TOOL_OPTIONS to <Full_path_of>/instrumentation-final/build/libs/myAgent.jar
        
    3. From the command line run gradle build to generate myAgent.jar -
        > ./gradlew build 
        
    4. Run mutationrunner.Main through intelliJ GUI


## Instumentation-final 

This project is divided into two modules:

### src/main/java
    
This folder contains javassist instrumentation implementation packages:
- instrumentation 
  - instrumentation.SimpleMain class
              implements Premain function for javassist launcher

  - instrumentation.SimpleTransformer class
		    implements ClassFileTransformer 
- mutations
     Contains classes that implement different mutation operators
	    
     - Access Modifier Change - AMC 
		
		* ModifyPublicAccessMutation Class:
	       		 mutates modifier of method from public to private
	   	* ModifyProtectedAccessMutation Class:
	       		 mutates fields of superclass from protected to private
	        
	 - Java Specific Feature 
	   
	   * ModifyStaticMutation Class:
	        JSD - static modifier deletion 
	           - mutates superclass static field to non-static
	        
	   * ModifyNonStaticMutation Class:
	        JSI - static modifier insertion 
	            - mutates superclass non-static field to static
		    
	- Inheritance
		
		* InsertHidingVarMutation Class:
		    ISI - Hiding variable insertion
		           mutates subclass inserts variable declaration of fields that are declare in superclass field
		

### mutationrunner

* public static void main(String arguments) 
		
* runMutate() 
		Calls the corresponding mutation using Javassist
		
* compareFiles()
		Compares the output files of different mutations with the output file for no mutation type.

## HW2_Output
This project contains ASTParser instrumented project output from HW2
    
   - src folder contains the entire instrumented source code of HW1 sudoku project
		
   - test folder contains jUnit test classes for boxChecker, columnChecker, rowChecker, checker class for sudoku


