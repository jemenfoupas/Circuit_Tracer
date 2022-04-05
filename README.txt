****************
* Circuit Tracer
* CS 221-2
* 4/27/2021
* Rich Boundji
**************** 


OVERVIEW:

 This program finds the shortest paths to connect two components. The program can use a 
 stack or a queue of the storage. Once it finds the shortest paths it prints it out in the console or in the gui if selected.


INCLUDED FILES:

 * README - this file
 * Storage.java - source file
 * TraceState.java - source file
 * CircuitBoard.java - source file
 * CircuitTracer.java - source file
 * InvalidFileFormatException.java - source file
 * OccupiedPositionException.java - source file


COMPILING AND RUNNING:

 From the directory containing all source files, compile the
 driver class (and all dependencies) with the command:
 $ javac CircuitTracer.java

 Run the compiled class file with the command:
 $java CircuitTracer [-s -- use a stack for storage | -q -- use a queue for storage] [-c --run program in console mode | -g --run program in GUI mode] fileName 

 Console output will give the results after the program finishes.


ANALYSIS:

 The choice of storage does affect the sequence o f which the paths are explored.
 Using the stack storage I was able to find a solution in less moves then using queue storage,
 I do think that it could have had the exact opposite outcome. Both of the methods have the same amount 
 of states, the only difference is the order of the states. In this case the stack storage was able 
 to find the solution faster but i do not think that it would always be the case. I think that doing so could instead take more time. 
 I think that using a queue storage has a better chance of encountering the shortest path first. 
 Because the queue goes through each path at the same time. I think that the maximum number of states is the same for both of the storage, 
 the difference between stack and queue storage is the order of the steps. The runtime of the search algorithm is big-O ‘n’. N reflects the maximum path length. 
 The main input factor that increases the difficulty of that is the size of the array.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 The  CircuitBoard class takes the filename and finds out if the file is formatted correctly or not,
 if the file is not formatted correctly the program throws an InvalidFileFormatException
 that will have a message to explain why the file was found to be incorrectly formatted.
 CircuitBoard class catches any incorrectly formatted file and clearly states why they are not correct.
 
 The CircuitTracer class is the class that finds the best paths and once it is done it prints them in the console or the gui.
 The CircuitTracer class uses the impute values to select what type of storage to use and how to display the outcome of the program,
 it also takes the filename. If the correct commands are entered in the right order and the file is valid the program will
 output the best paths in the console, the program does not have a gui but if implemented correctly and the commend for it were
 entered instead of the one for the console the output would be in the gui.

 CircuitTracer works by creating a storage object and a list, once that is done a trace state object is created for every
 adjacent position from the starting point and is then added to the storage. The program then goes on to look for every
 possible paths and if they are as short as the first best path it is stored else the list is clear and the new shortest
 path is add to it until there is no other path available.
 

TESTING:

 The main way of testing was by using the TraceStateTester. The tester was made before the
 CircuitBoard and CircuitTrace classes were completed. The tester was used throughout the development
 of the classes to ensure that they function as planed. The tester tested for different scenarios like 
 invalid files or invalid commands. The tester has combinations of potential error and correctly predict the output.

 Some of the test were
     java CircuitTracer -q -c valid9.dat
     java CircuitTracer -s -c valid9.dat
     java CircuitTracer -q -c valid7.dat

 CircuitBoard and CircuitTrace are both currently passing all of their tests.


DISCUSSION:
 
 An issues that I encounter when programming was that eclipse would not compile the code
 because it could not find the CircuitTracer class, I was able overcome that problem by
 compiling my code using the terminal and entering javac CircuitTracerTester.java and then 
 java CircuitTracerTester. Doing so I was able to finish the project, I do not know why eclipse had that issue.

 This project was challenging because I had a hard time wrapping my head around what it was
 that each class needed to do, it took me some time to understand what it is that I needed to do to 
 complete the programs but with some help I was able to understand the project.

 
EXTRA CREDIT:

 N/A
