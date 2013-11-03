import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class ArrayMaxSubSum2 {
	static boolean invalidInput = false;
	
	public static void main(String[] args){
		String filename = "e:/input.txt";
		String[] type = {};
		int argslength = args.length;
		//no file name entered
		if (argslength==0){
			invalidInput = true;
			System.out.println("A file name is required.");
		}
		//last parameter is file name, other parameters stored in String[] type
		else{
			filename = args[argslength-1];
			type = new String[argslength-1];
			for(int i=0;i<argslength-1;i++){
				type[i]=args[i];
			}
		}
		
		int[][] array = getArrayInFile(filename);
		checkArray(array);
		
		//check input
		if(invalidInput)
			System.out.println("Invalid input.");
		else{
			int max=0;
			
			//no type, rectangle
			if (type==null||type.length==0){
				System.out.println("Sub array should be rectangle.");
				max=maxSubRec(array);
			}else if(type.length==1){
				//parameter: /h
				if(type[0].equals("/h")){
					System.out.println("Sub array should be rectangle. /h means it can be connected horizontally.");
					max=maxSubRecHor(array);
				// parameter: /v
				}else if(type[0].equals("/v")){
					System.out.println("Sub array should be rectangle. /v means it can be connected vertically.");
					max=maxSubRecVer(array);
				// parameter: /a
				}else if(type[0].equals("/a")){
					System.out.println("Elements in sub array are connected. /a.");
					System.out.println("Such type parameters haven't been solved.");
					max=maxSubConnected(array);
				}else{
					System.out.println("Such type parameters haven't been solved.");
				}
			}else if(type.length==2){
				// parameter: /v /h or /h /v
				if((type[0].equals("/v")&&type[1].equals("/h"))||(type[0].equals("/h")&&type[1].equals("/v"))){
					System.out.println("Sub array should be rectangle. /h & /v means it can be connected horizontally and vertically.");
					max=maxSubRecHorAndVer(array);
				}else{
					System.out.println("Such type parameters haven't been solved.");
				}
			}
			else{
				System.out.println("Such type parameters haven't been solved.");
			}
			
			System.out.println("Max sum of sub array is "+max);
		}

	}
	

	/**
	 * check whether array is null or its length is 0
	 * 
	 * @param array
	 */
	private static void checkArray(int[][] array) {
		if(array==null||array.length==0){
			invalidInput = true;
		}
	}
	
	/**
	 * get sub array max sum with elements connected
	 * 
	 * @param array
	 * @return
	 */
	private static int maxSubConnected(int[][] array) {
		
		return 0;
	}
	
	/**
	 * get sub array max sum in rectangle horizontally and vertically connected
	 * 
	 * @param array
	 * @return
	 */
	private static int maxSubRecHorAndVer(int[][] array) {
		int rowNum=array.length;
		int colNum=array[0].length;
		int[][] doubleArray=new int[rowNum*2][colNum*2];
		for(int i=0;i<rowNum;i++){
			System.arraycopy(array[i], 0, doubleArray[i], 0, colNum);
			System.arraycopy(array[i], 0, doubleArray[i], colNum, colNum);
			System.arraycopy(array[i], 0, doubleArray[i+rowNum], 0, colNum);
			System.arraycopy(array[i], 0, doubleArray[i+rowNum], colNum, colNum);
		}
		int max=0;
		for(int i=0;i<rowNum;i++){
			for(int j=i;j<i+rowNum;j++){
				int[] oneDimension=getOneDimensionArray(doubleArray,i,j);
				int maxOneDimension=maxSubSumWithWidthRestrict(oneDimension);
				if(maxOneDimension>max)
					max=maxOneDimension;
			}
		}
		return max;
	}


	/**
	 * get sub array max sum in rectangle vertically connected
	 * 
	 * @param array
	 * @return
	 */
	private static int maxSubRecVer(int[][] array) {
		int rowNum = array.length;
		int colNum = array[0].length;
		int[][] doubleArrayInRow=new int[rowNum*2][colNum];
		for(int i=0;i<rowNum;i++){
			System.arraycopy(array[i], 0, doubleArrayInRow[i], 0, colNum);
			System.arraycopy(array[i], 0, doubleArrayInRow[i+rowNum], 0, colNum);
		}
		int max=0;
		for(int i=0;i<rowNum;i++){
			for(int j=i;j<i+rowNum;j++){
				int[] oneDimension=getOneDimensionArray(doubleArrayInRow,i,j);
				int maxOneDimension=maxSubSum(oneDimension);
				if(maxOneDimension>max)
					max=maxOneDimension;
			}
		}
		return max;
	}

	/**
	 * get sub array max sum in rectangle horizontally connected
	 * 
	 * @param array
	 * @return
	 */
	private static int maxSubRecHor(int[][] array) {
		int rowNum = array.length;
		int colNum = array[0].length;
		int[][] transArray = new int[colNum][rowNum];
		for(int i=0;i<colNum;i++){
			for(int j=0;j<rowNum;j++){
				transArray[i][j]=array[rowNum-1-j][i];
			}
		}
		
		return maxSubRecVer(transArray);
	}

	/**
	 * get sub array max sum in rectangle
	 * 
	 * @param array
	 * @return
	 */
	private static int maxSubRec(int[][] array) {
		int row = array.length;
		
		int max=0;
		for(int i=0;i<row;i++){
			for(int j=i;j<row;j++){
				int[] oneDimension=getOneDimensionArray(array,i,j);
				int maxOnedimension=maxSubSum(oneDimension);
				if(maxOnedimension>max)
					max=maxOnedimension;
			}
		}
		return max;
	}
	
	/**
	 * transform 2 dimensional array to 1 dimensional array
	 * with elements in one column are seen in a whole
	 * 
	 * @param array
	 * @param start
	 * 		start row
	 * @param end
	 * 		end row
	 * @return
	 */
	private static int[] getOneDimensionArray(int[][] array, int start, int end) {
		int columnNum=array[0].length;
		int[] oneDimension = new int[columnNum];
		for(int i=0;i<columnNum;i++){
			for(int j=start;j<=end;j++){
				oneDimension[i]+=array[j][i];
			}
		}
		return oneDimension;
	}
	
	/**
	 * get max sum of sub array of 1 dimensional array
	 * with length of sub array is smaller than half length of array, which is also the length of original array
	 * 
	 * @param array
	 * @return
	 */
	private static int maxSubSumWithWidthRestrict(int[] array) {
		//store max sum
		int maxSum=array[0];
		//store current sum in each group
		int currentSum=0;
		int start=0;
		for(int i=0; i<array.length; i++){
			// current sum is negative, discard it, or it will decrease sum of following elements
			if(currentSum<0){
				currentSum=array[i];
				start=i;
			}
			// start to end is larger than column num of original array
			// restart to add from start+1
			else if(i-start==array.length/2){
				i=start+1;
				currentSum=array[i];
				start=i;
			}
			// just add new element
			else
				currentSum+=array[i];
			// current>max, replace
			if(currentSum>maxSum)
				maxSum=currentSum;
		}
		return maxSum;
	}
	
	/**
	 * get max sum of sub array of 1 dimensional array
	 * 
	 * @param array
	 * @return
	 */
	private static int maxSubSum(int[] array){
		//store max sum
		int maxSum=array[0];
		//store current sum in each group
		int currentSum=0;
		for(int i=0; i<array.length; i++){
			// current sum is negative, discard it, or it will decrease sum of following elements
			if(currentSum<0)
				currentSum=array[i];
			// just add new element
			else
				currentSum+=array[i];
			// current>max, replace
			if(currentSum>maxSum)
				maxSum=currentSum;
		}
		return maxSum;
	}
	
	/**
	 * get two dimensional array by parsing file
	 * 
	 * @param filename
	 * @return
	 */
	private static int[][] getArrayInFile(String filename) {
		int[][] array = null;
		File file = new File(filename);
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
			String oneline = null;
			// get row
			int row = Integer.parseInt(reader.readLine().split(",")[0]);
			// get column
			int column = Integer.parseInt(reader.readLine().split(",")[0]);
			array = new int[row][column];
			int rowindex=0;
			while((oneline=reader.readLine())!=null){
				String[] rowStrs=oneline.split(",");
				//columnNum is not equal to column 
				if(rowStrs.length!=column){
					invalidInput=true;
					return null;
				}
				for(int j=0;j<rowStrs.length;j++){
					array[rowindex][j]=Integer.parseInt(rowStrs[j]);
				}
				rowindex++;
			}
			//rowNum is not equal to row
			if(rowindex!=row){
				invalidInput=true;
				return null;
			}
		}catch(Exception e){
			invalidInput = true;
			e.printStackTrace();
		}finally{
			if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
		}
		return array;
	}
	
	
}
