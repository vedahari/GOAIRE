package OnAireServlets;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xml.sax.SAXException;

public class RequestManager {

	private KdTreeFactory factory=null;
	
	public RequestManager() {
		factory = new KdTreeFactory();
	}
	
	public void initialize(){		
		List<String> cities = new ArrayList<>(Arrays.asList(				
				Constants.AT,			
				Constants.BB
				));
		for (int i=0;i<cities.size();i++){
			factory.addCity(cities.get(i));
			OSMWrapperAPI osmWrapper = new OSMWrapperAPI();
			try {
				osmWrapper.parse(cities.get(i));
			} catch (SAXException | IOException e) {			
				e.printStackTrace();
			}
		}	
	}
		
	public IdlingTimeResponse getAdvisedIdlingTime(double la, double lo,int break_even ){
		//find the city in which the point exists
		String city = getCity( la, lo);
		if (city.isEmpty()){
			return new IdlingTimeResponse();
		}
		/*if (!validRequest(city,la,lo)){
			System.err.println("\n The request is out of range!");
			return null;
		}*/		
		ArrayList<Integer> stopTimeList = getStopTimeList(city,la,lo);				
		OnAire oOnAire = new OnAire(break_even);
		oOnAire.vReadStopLengthArrayFromArrayList(stopTimeList);
		return oOnAire.vExecute();
	}
	
	private ArrayList<Integer> getStopTimeList(String city, double la, double lo) {
		BoundBox box;
		//BoundBox box = new BoundBox (37.449,-122.737,37.955,-122.011);		
		double factor = 0.0001;
		ArrayList<Integer> stopTimeList = null;
		while(stopTimeList == null || stopTimeList.size()<=2){
			box = new BoundBox (la-factor,lo-factor,la+factor,lo+factor);
			stopTimeList = (ArrayList<Integer>) KdTreeFactory.getKdTree(city).range(box);
			factor = factor+factor;
		}
		print(stopTimeList);
		return stopTimeList;
	}
	
	public ArrayList<Point> getNearestStops(double la, double lo) {
		String city = getCity(la,lo);
		BoundBox box;				
		double factor = 0.0001;
		ArrayList<Point> nearestStops = null;
		while(nearestStops == null || nearestStops.size()<=2){
			box = new BoundBox (la-factor,lo-factor,la+factor,lo+factor);
			nearestStops = (ArrayList<Point>) KdTreeFactory.getKdTree(city).range_points(box);
			factor = factor+factor;
		}		
		return nearestStops;
	}
	
	private void print(ArrayList<Integer> stopTimeList) {
		System.out.println("The following are the "+stopTimeList.size()+" stops!");
		String output =" ";
		for (int i=0;i<stopTimeList.size();i++){
			output+=" "+Integer.toString(stopTimeList.get(i));
		}
		System.out.println(output);
	}

	public boolean validRequest(String city, double la, double lo) {
		if (city==null || city.isEmpty()){
			return false;
		}
		//if city is found check whether it is initialized
		if (factory.containsCity(city)){
			return true;
		}
		//if yes, make the request otherwise make		
		return false;	
	}

	public String getCity(double la, double lo) {
		Iterator<Entry<String,BoundBox> > it = Constants.CityBoundaries.entrySet().iterator();
		String city = new String();
	    while (it.hasNext()) {
	        Map.Entry<String,BoundBox> pair = (Entry<String, BoundBox>)it.next();
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        BoundBox bb = (BoundBox) pair.getValue();
	        if (la>=bb.xmin() && la<=bb.xmax() && lo>=bb.ymin() && lo<=bb.ymax()){
	        	city = (String)pair.getKey();
	        	break;
	        }
	    }	    
	    //System.out.println("The given location is in "+city);
		return city;		
	}
	
	/*
	public static void main(String[] args) {
		//initialize the server
		initialize();
		//wait for the client/user request
		//BoundBox box = new BoundBox (33.298,-119.437,34.583,-116.724);
		//BB BoundBox box = new BoundBox (37.1751293,-80.4912875,37.2855176,-80.3675103);
		test1();
		test2();
		System.out.println("\n All tests are done");		
	}

	private static void test1() {
		BoundBox box = new BoundBox (37.449,-122.737,37.955,-122.011);		
		ArrayList<Integer> stopTimeList = (ArrayList<Integer>) KdTreeFactory.getKdTree(Constants.SF).range(box);
		System.out.println("The Stop time list size is "+stopTimeList.size());	
		OnAire oOnAire = new OnAire();
		oOnAire.vReadStopLengthArrayFromArrayList(stopTimeList);
		oOnAire.vExecute();
	}
	
	private static void test2() {	
		BoundBox box = new BoundBox (37.749,-122.237,37.855,-122.111);		
		ArrayList<Integer> stopTimeList = (ArrayList<Integer>) KdTreeFactory.getKdTree(Constants.SF).range(box);
		System.out.println("The Stop time list size is "+stopTimeList.size());	
		OnAire oOnAire = new OnAire();
		oOnAire.vReadStopLengthArrayFromArrayList(stopTimeList);
		oOnAire.vExecute();
	}	*/
}
