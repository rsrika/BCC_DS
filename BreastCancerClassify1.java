
import java.text.*;
import java.math.*;


/**
 * Project One: Breast Cancer Classification
 * Identify any classmates that you collaborated with.
 * 	1)
 * 	2)
 * 	3) 
 * 
 * BreastCancerClassify contains the core implementation of the 
 * kNearestNeighbors algorithm to classify cell clumps as malignant
 * or benign. 
 * 
 * Work on the functions in the following order:
 *  0) author
 * 	1) calculateDistance - once you finish this, you should see a
 * 	   graph of distances appear!
 * 	2) getAllDistances
 * 	3) findKClosestEntries
 * 	4) classify
 *  5) kNearestNeighbors (use your helpers correctly!)
 *  6) getAccuracy
 */
public class BreastCancerClassify {
	
	static int K = 5;
	static int BENIGN = 2;
	static int MALIGNANT = 4;

	/**
	 * Return a String in the format: "Last_Name, First_Name"
	 * This will be used for the autograder. If you'd like to earn a grade in the gradebook, please do this correctly.
	 * Your name should match the name in PowerSchool.
	 */
	public static String author()
	{
		return "Srikanth, Roshni";
	}
	
	/**
	 * calculateDistance computes the distance between the two data
	 * parameters. The distance is found by taking the difference in each 
	 * "coordinate", squaring it, adding all of those, and then taking the 
	 * square root of the result. 
	 * 
	 * Remember to exclude the patient ID and the tumor classification
	 * 
	 * For example: 
	 * [12345, 6, 4, 4, MALIGNANT]
	 * [22344, 2, 8, 3, BENIGN]
	 * 
	 * distance = sqrt((6-2)^2 + (4-8)^2 + (4-3)^2)
	 */
	public static double calculateDistance(int[] first, int[] second)
	{
		double totalSum = 0;
		for(int i = 1; i< first.length-1;i++)
		{
			totalSum+=(Math.pow((first[i]-second[i]),2));
		}
		return Math.sqrt(totalSum);
	}
	
	/**
	 * getAllDistances creates an array of doubles with the distances
	 * to each training instance. The double[] returned should have the 
	 * same number of instances as trainData. 
	 */
	public static double[] getAllDistances(int[][] trainData, int[] testInstance)
	{
		double[] allDistances = new double[trainData.length];
		for(int i = 0; i<trainData.length; i++)
		{
			allDistances[i] = calculateDistance(trainData[i],testInstance);
		}
		
		return allDistances;
	}
	
	/**
	 * findKClosestEntries finds and returns the indexes of the 
	 * K closest distances in allDistances. Return an array of size K, 
	 * that is filled with the indexes of the closest distances (not
	 * the distances themselves). 
	 * 
	 * Be careful! This function can be tricky.
	 * in this case, k = 5
	 */
	public static double findMin(double[] distances)
	{
		double min = Integer.MAX_VALUE;
		for(int i = 0; i< distances.length; i++)
		{
			if(distances[i]<min)
			{
				min = distances[i];
			}
		}
		return min;
	}
	public static int[] findKClosestEntries(double[] allDistances)
	{
		double[] allDistancesCopy = new double[allDistances.length];
		for(int i = 0; i< allDistances.length;i++)
		{
			allDistancesCopy[i] = allDistances[i];
		}
		
		
		int[] kClosestIndexes = new int[K];
		for(int i =0; i<K; i++)
		{
			double min = findMin(allDistancesCopy);
			int index = findIndex(allDistancesCopy,min);
			kClosestIndexes[i] = index;
			allDistancesCopy[index] = Integer.MAX_VALUE;
			
		}
		return kClosestIndexes;
	}
	
	public static int findIndex (double[] distances, double number)
	{
		for(int i = 0; i< distances.length; i++)
		{
			if(distances[i]== number)
			{
				return i;
			}
			
		}
		return -1;
	}
	
	/**
	 * classify makes a decision as to whether an instance of testing 
	 * data is BENIGN or MALIGNANT. The function makes this decision based
	 * on the K closest train data instances (whose indexes are stored in 
	 * kClosestIndexes). If more than half of the closest instances are 
	 * malignant, classify the growth as malignant. Otherwise classify
	 * as benign.
	 * 
	 * Return one of the global integer constants defined in this function. 
	 */
	public static int classify(int[][] trainData, int[] kClosestIndexes)
	{
		int benign = 0;
		int malignant = 0;
		//only works for rectangular arrays
		int trainDataLength = trainData[0].length;
		for(int i = 0; i<K; i++)
		{
			if(trainData[kClosestIndexes[i]][trainDataLength-1]== MALIGNANT)
			{
				malignant++;
			}
			else
			{
				benign++;
			}
		}
		if(malignant>benign)
		{
			return MALIGNANT;
		}
		else
		{
			return BENIGN;
		}
		
	}
	
	/**
	 * kNearestNeighbors classifies all the data instances in testData as 
	 * BENIGN or MALIGNANT using the helper functions you wrote and the kNN 
	 * algorithm.
	 * 
	 * For each instance of your test data, use your helper methods to find the
	 * K closest points, and classify your result based on that!
	 * @param trainData: all training instances
	 * @param testData: all testing instances
	 * @return: int array of classifications (BENIGN or MALIGNANT)
	 */
	public static int[] kNearestNeighbors(int[][] trainData, int[][] testData){
		int[] myResults = new int[testData.length];
		int[] indexList = new int[K];
		for(int i = 0; i< testData.length;i++)
		{
			double[] data = getAllDistances(trainData,testData[i]);
			indexList = findKClosestEntries(data);
		}
		for(int i = 0; i< indexList.length; i++)
		{
			myResults[i]= indexList[i];
		}
		return myResults;
	}

	/**
	 * printAccuracy returns a String representing the classification accuracy.
	 *
	 * The output String should be rounded to two decimal places followed by the % symbol.
	 * For example, if 4 out of 5 outcomes were correctly predicted, the returned String should be: "80.00%"
	 * For example, if 3 out of 9 outcomes were correctly predicted, the returned String should be: "33.33%"
	 * For example, if 6 out of 9 outcomes were correctly predicted, the returned String should be: "66.67%"
	 * Look up Java's String Formatter to learn how to round a double to two-decimal places.
	 *
	 * This method should work for any data set, given that the classification outcome is always listed
	 * in the last column of the data set.
	 * @param: myResults: The predicted classifications produced by your KNN model
	 * @param: testData: The original data that contains the true classifications for the test data.
	 */
	public static String getAccuracy(int[] myResults, int[][] testData)
	{	
		int numCorrect = 0;
		for(int i = 0; i<myResults.length;i++)
		{
			if(myResults[i]==testData[i][testData[0].length-1])
			{
				numCorrect++;
			}
		}
		int myResultLength = myResults.length;
		double resultPct = (double) numCorrect / myResultLength * 100.00;
		
		String s = String.format("%.2f", resultPct);

		return s+"%";
		
		}
	
	
	//DO NOT MODIFY THE MAIN METHOD
	public static void main(String[] args) {

		int[][] trainData = InputHandler.populateData("./datasets/train_data.csv");
		int[][] testData = InputHandler.populateData("./datasets/test_data.csv");
		
		//Display the distances between instances of the train data. 
		//Points in the upper left corner (both benign) or in the bottom
		//right (both malignant) should be darker. 
		Grapher.createGraph(trainData);

		int[] myResults = kNearestNeighbors(trainData, testData);

		System.out.println("Model Accuracy: " + getAccuracy(myResults, testData));
	}

}