import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

//Complete this...

// @author Chris Bharucha
public class HammingDist {
	
	//Initial size of the stationData array
	private static int length = 10;
	
	/*
	 * variables that will hold data from the given parameters in the Driver class
	 */
	private String stationA;
	private String stationB;
	
	private int distanceA = 0;
	private int distanceB = 0;
	
	private int sameDistanceA;
	private int sameDistanceB;

	
	
	/**
	 * Array of HammingDist objects to store the data in
	 * 
	 * Capacity of the array is initially set to 'length', and will call the expandArray method if full
	 */
	private String[] stationData = new String[length];
	
	
	
	/**
	 * The number of stations present in the stationData Array. Initially set to '0'
	 * 
	 */
	private int numStations = 0;
	
	
	
	/**
	 * Default constructor for a HammingDist object
	 * 
	 * @param station1 
	 * @param station2
	 */
	public HammingDist(String station1,String station2) throws IOException{
		
		
		stationA = station1; //setting the parameters equal to the local variables of the class
		stationB = station2;
		
		/*
		 * This part of the constructor reads through the data in the Mesonet.txt file
		 * and stores the station ID's into the stationData array
		 * 
		 */
		BufferedReader br = new BufferedReader(new FileReader("Mesonet.txt")); //Opening the file using the filename to read the data
		
		//Reading through the first 6 lines of the text file
		for (int i = 0; i < 6; ++i)
		{
			br.readLine();
		}
		
		String dataLine = br.readLine(); //Begin to actually read the data from Mesonet.txt
	
		//Using a loop to verify that data is still being read
		while (br.ready())
		{
				if (numStations == stationData.length) { //Adding the new station to the ArrayList and expanding array if needed
				expandArray();
				}
		
				String info = dataLine.split("\\s+")[1]; //Creating a HammingDist object using a line of data
				stationData[numStations++] = info;//adding the data at info[0] to the stationData, which should be the station ID	
				
				dataLine = br.readLine(); //Reading the next line of the data to continue the loop
		}
		
		//Closing the reader after finished
		br.close();
		
		/**
		 * Adding the two given stations to the arrayList
		 * 
		 * Also ensuring that if the array is full, the array gets expanded in the expandArray method
		 */
		if (numStations == stationData.length) {
			expandArray();
		}
		
		stationData[numStations++] = station1;
		stationData[numStations++] = station2;
		
		distanceA = calculateHammingDist(stationA, "NRMN");
		distanceB = calculateHammingDist(stationB, "NRMN");
		
		
		/**
		 * Figuring the number of stations with the same HammingDistance as station1 and station2 parameters
		 * now that the array has station ID's to compare to
		 */
		sameDistanceA = sameDistance(stationA, distanceA);
		sameDistanceB = sameDistance(stationB, distanceB);
	}
	
	
	
	/**
	 * This method is only called if the array stationData is full. It doubles the length of 
	 * stationData by creating a new array and copying all of the data over to the new array
	 */
	public void expandArray() {
		
		int newLength = length * 2;	
		String[] newStationData = new String[newLength];	//Creating a new array with double the size of the stationData array
		
		for (int i = 0; i < stationData.length; ++i) {
			
			newStationData[i] = stationData[i];
			
		}
		this.stationData = newStationData;
		length = newLength;
	}
	
	
	
	/**
	 * This method is for calculating the Hamming Distance between two stations
	 * 
	 * @param station
	 * @return distance
	 */
	public int calculateHammingDist(String station1, String station2){
		
		//Initializing a distance counter to keep track of HammingDistance
		int distance = 0;
		
		/**
		 * This for loop compares each char of a given station to the String "NRMN" and increments 
		 * the distance variable if the two chars are not equal
		 */
		for (int i = 0; i < 4; ++i) {
			
			char testChar = station2.charAt(i);
			char station1Char = station1.charAt(i);
			
			if (testChar != station1Char) {
				distance++;
			}
		}
		
		return distance;
	}
	
	
	
	/**
	 * This method is for finding the number of stations that have the same Hamming
	 * Distance as the given station ID
	 * 
	 * @param station
	 * @param distance
	 * @return sameDistance
	 */
	public int sameDistance(String station , int distance) {
		
		int sameDistance = 0;
		int i = -1;
		
		while (stationData[++i] != null) {
			
			int testDistance = calculateHammingDist(station, stationData[i]);
			
			if (testDistance == distance) {
				++sameDistance;
			}
		}
		return sameDistance;
	}
	
	
	
	/*
	 * This method provides the correct String format to print when a HammingDist object is printed
	 * in the Driver class
	 * 
	 */
	public String toString() {
		
		return "The Hamming Distance between Norman and " + stationA + " is " + distanceA
				+ " and for " + stationB + ": " + distanceB + ".\nFor " + stationA + ", number" +
				" of stations with Hamming Distance " + distanceA + " is " + sameDistanceA + ", and\n"
				+ "for " + stationB + ", number of stations with Hamming Distance " + distanceB + " is "
				+ sameDistanceB + ".";
	}
}
