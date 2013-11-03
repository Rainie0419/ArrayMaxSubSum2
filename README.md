## Problem Description ##
1. Find the largest sum of elements in the sub array for a **2-dimensional** array.
2. With the command:  
	`java -jar ArrayMaxSubSum2 <filename>`  
   Assign the file with array's information in it. File content like this:  
   ![](http://images.cnitblog.com/blog/202788/201309/12233328-28c976daed0648078944cf27e35b8ba4.png)  
   row number,  
   column number,  
   each row of the array  
  
	Sub array should be in the form of a **rectangle**, as follows:
	![](http://images.cnitblog.com/blog/202788/201309/12233331-bc38dd85b312434f995a59436d8832a7.png)  
   Return 28 in this example.
3. The array can be **connected vertically**:  
	`java -jar ArrayMaxSubSum2 /v <filename>`  
   or **horizontally**:  
	`java -jar ArrayMaxSubSum2 /h <filename>`  
   or **both**:  
	`java -jar ArrayMaxSubSum2 /h /v <filename>`
	`java -jar ArrayMaxSubSum2 /v /h <filename>`
4. Elements don't need to be in the form of a rectangle, as follows:
	![](http://images.cnitblog.com/blog/202788/201309/12233333-ef35109aa7e1474d904cf907e49035ca.png)  
   Return 50 in this example.***(Still Unsolved)***
## Solution ##
### For 2-demisional array ###
Suggested that we've know which row to start(row i) and which row to end(row j), we treat the elements between these two rows as a 1-dimensional array, in which each element is the sum of elements in the same column.  
For example, the original array is **original[rowNum][columnNum]**. New array with elements between row i and row j is **new[columnNum]**.  
`new[index]=original[i][index]+original[i+1][index]+……+original[j][index]`
Then we turn this question to a 1-dimensional one. Check [ArrayMaxSubSum](https://github.com/Rainie0419/ArrayMaxSubSum) for details.
### Connected vertically ###
Add the same array below the original one. Solve the question like the former one but notice that the **height of the sub array should not be larger than the height of the original array**.
### Connected horizontally ###
Transform the problem into the vertical one, which means we need to transform the array and then double it vertically.
### Connected in both directions ###
Method is quite like those before. Double the array in both directions, so there are **2x2 copies of the original on**e. Restrict height and width at the same time. So on basis of Connected vertically, do some modification to the method of dealing with 1-dimensional array. Check source code for more details.