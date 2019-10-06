# Root Finder
The purpose of this assignment is to implement some of these methods and 
demonstrate their use in a Java application. The Java application should 
provide the following functionality through a GUI:

 1. Selection of the function f(x) being solved based from those listed 
in Table 1 below
 2. Plotting the function in a suitable range to show the root(s) of the 
equation
 3. Selection of one or more of the root-finding methods from those 
listed in Table 2 below
 4. User selection of the starting point(s) for the numerical algorithm 
to find the root
 5. Display of the root found to a user-selected number of decimal 
places for the selected numerical method
 6. Tabular and graphical representation of results to show iterative 
history of calculation breakdown and root approximations

## Table 1: The functions to be tested
|Function|What happens when the starting point includes|
|--|--|
|x - x<sup>2</sup>|x=-1, x=0.5, x=0.50001,x=0.49999|
|ln(x+1) + 1|x=0, x=-1, x=-1.0e-10|
|e<sup>x</sup> - 3x|x=1.09, x=1.1, x=1.11|

## Table 2: Numerical Methods to be implemented
|Numerical Method|Data structure to store *x*<sub>i</sub>, 
*f*(*x*<sub>i</sub>), *f*’(*x*<sub>i</sub>)|
|--|--|
|Newton-Raphson|Linked list|
|Secant|Array|
|Bisection|Array|
|One that you research|Linked list|
