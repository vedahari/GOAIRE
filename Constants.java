package OnAireServlets;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * This file consists of all the constants that are used in the program
 * Make sure that the file paths are modified according to the file.
*/

public final class Constants {
	public static final String SF = "SanFrancisco";
	public static final String SF_PATH = "D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\san-francisco_california.osm";
	
	public static final String CHE = "Chennai";
	public static final String CHE_PATH = "D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\chennai_india.osm";
	
	public static final String LA = "LosAngeles";
	public static final String LA_PATH = "D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\los-angeles_california.osm";
	
	public static final String BB = "Blacksburg";
	public static final String BB_PATH = "D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\blacksburg.osm";
	
	public static final String SEA = "Seattle";
	public static final String SEA_PATH = "D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\seattle_washington.osm";
	
	public static final String TEST = "Test";
	public static final String TEST_PATH = "D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\aalborg_denmark.osm";
	
	public static final String SA = "SanAntonio";
	public static final String SA_PATH = "D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\san-antonio_texas.osm";
	
	public static final String AT = "Atlanta";
	public static final String AT_PATH = "D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\atlanta_georgia.osm";
	
	public static final String NY = "NewYork";
	public static final String NY_PATH = "D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\new-york.osm";
	
	public static final String outputStopPointFilePrefix = "D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\sfmta\\outputProcessing\\StopPoints_";
	
	public static final Map<String,String> CityFileMap;
	static{
		Map<String,String> fileMap = new HashMap<String,String>();
		fileMap.put(Constants.SF,Constants.SF_PATH);
		fileMap.put(Constants.CHE,Constants.CHE_PATH);
		fileMap.put(Constants.LA,Constants.LA_PATH);
		fileMap.put(Constants.BB,Constants.BB_PATH);
		fileMap.put(Constants.SEA,Constants.SEA_PATH);
		fileMap.put(Constants.TEST,Constants.TEST_PATH);
		fileMap.put(Constants.SA,Constants.SA_PATH);
		fileMap.put(Constants.AT,Constants.AT_PATH);
		fileMap.put(Constants.NY,Constants.NY_PATH);
		CityFileMap = Collections.unmodifiableMap(fileMap);
	}
	
	
	public static final Map<String,BoundBox> CityBoundaries;
	static{
		Map<String,BoundBox> cityBndMap = new HashMap<String,BoundBox>();
		cityBndMap.put(SF,new BoundBox(37.449,-122.737,37.955,-122.011));
		cityBndMap.put(CHE, new BoundBox(12.7,79.9,13.3,80.4));
		cityBndMap.put(LA, new BoundBox(33.298,-119.437,34.583,-116.724));
		cityBndMap.put(BB, new BoundBox(37.1751293,-80.4912875,37.2855176,-80.3675103));		
		cityBndMap.put(SEA, new BoundBox(46.608,-123.931,48.528,-121.335));
		cityBndMap.put(SA, new BoundBox(29.109,-98.9509999,29.951,-97.88));
		cityBndMap.put(AT, new BoundBox(32.844,-85.386,34.618,-83.269));
		cityBndMap.put(NY, new BoundBox(40.345,-74.501,41.097,-73.226));
		CityBoundaries = Collections.unmodifiableMap(cityBndMap); 
	}
	
	
	private Constants(){
		//static const class
		throw new AssertionError();
	}
}
