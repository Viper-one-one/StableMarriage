# Stable Marriage Instability Calculator
Built for Comp 482 Project 1, current Performance is O(N^2).

## Inputs
Takes an input file named input.txt which contains a first line with the size of the matricies, two ranked preference matricies, and a single matching matrix of the same size.
An example input file is included for illustrative purposes.

## Ouputs
Prints the number of instabilities with the provided matching and ranked preference matricies.

## Runtime Analysis
Each of the preference matricies is an N^2 operation because it needs to loop through the initialized matricies and set each value to the one retrieved from the input file. The instability calculation has 4 loops, nested at a maximum of 2 loops deep, this means that we have a worst case performance of N^2 on this portion as well. Therefore, we are looking at a total run time of O(N^2).
