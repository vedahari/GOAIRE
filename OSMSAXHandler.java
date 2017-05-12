package OnAireServlets;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Date;
import java.util.HashMap;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OSMSAXHandler extends DefaultHandler {
	//maps uid and prev attr of that uid.. This is required as there are many users recording the trace.
	private Map<String,OSMNode> prevAttrMap; 
	private int stopCount;
	private int totalStopCount;
	private KdTreeBuilder builder;
	private static final int MAX_TIMESTAMP_INTERVAL = 300;
	private static final double MAX_CONGESTION_SPEED_MPS = 1.0;
	private String city = null;
	public OSMSAXHandler(String c) {
		city = new String(c);
		System.out.println("Building the KdTree for "+city);
		builder = new KdTreeBuilder(city);		
		//readProcessedOutputFile();
	}
	
	public void readProcessedOutputFile() {		
		File f1 = null;f1 = new File("D:\\Courses\\Spring2017\\Project\\Data_Collection\\mapzen\\sfmta\\Output.txt");
	    Scanner scanner = null; 
	    try {
			scanner = new Scanner(f1);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}	    
	    while(scanner.hasNextLine()){
	        String data[]=scanner.nextLine().split("\t");
	        builder.addPoint(new Point(Double.parseDouble(data[0]),Double.parseDouble(data[1]),Integer.parseInt(data[2])));
	                
	    }    
	    scanner.close();	
	}
	
	@Override
	public void startDocument() throws SAXException {		
		//System.out.println("Started parsing the document");
		this.prevAttrMap = new HashMap<String, OSMNode>();
		this.stopCount = 0;
		this.totalStopCount = 0;
	}

	@Override
	public void endDocument() throws SAXException {				
		System.out.println("Done parsing the document. Total stops found is "+this.stopCount+"  out of "+this.totalStopCount);		
		builder.writePointsToFile(Constants.outputStopPointFilePrefix+city+".txt");
		builder.build();
		System.out.println("Built the kdTree for the city "+city);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {		
		//System.out.println("URI::  "+uri+"LocalName:: "+localName+" qName:: "+qName);
		if (qName.equals("node")){
			OSMNode node = new OSMNode(attributes);
			handleNodeAttributes(node);		
		}
	}

	private void handleNodeAttributes(OSMNode n) {
		//System.out.println("AttrQName "+attributes.getQName(i)+" AttrValue "+attributes.getValue(i));
		detectStopTime(n);
	}
	
	private static long getDateDiff(Date date1, Date date2) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return TimeUnit.SECONDS.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	
	private Double computeSpeed(OSMNode n1, OSMNode n2){
		Double speed = 0.0;
		if (n1 ==null || n2==null){
			return speed;			
		}
		else{
			Double distance = DistanceCalculator.computeDistance(n1.getLatitude(), n1.getLongitude(), n2.getLatitude(), n2.getLongitude());
			long time = Math.abs(getDateDiff(n1.getTimestamp(),n2.getTimestamp()));
			if (time!=0 && time<MAX_TIMESTAMP_INTERVAL){
				speed = distance/time;
			}
			else{
				//System.out.println("Computing speed for distance  "+distance+" and time   "+time);
				speed = Double.MAX_VALUE;
			}				
		}
		return speed;
	}
	
	private boolean isCongested(OSMNode n1, OSMNode n2){
		Double speed =computeSpeed(n1,n2);		
		if (speed > MAX_CONGESTION_SPEED_MPS){			
			return false;
		}
		else{
			//System.out.println("Comparing "+n1.getNodeId()+" and " + n2.getNodeId()+"  Speed is "+speed+". Computed between "+n1.getTimestamp().toString()+" and "+n2.getTimestamp().toString());
			return true;		
		}
	}

	private void detectStopTime(OSMNode newNode) {
		String currUid = newNode.getUid();
		if (this.prevAttrMap.get(currUid)==null){
			this.prevAttrMap.put(currUid,newNode);
			//System.out.println("Adding a new user "+newNode.getUid());
		}
		else{
			OSMNode prevNode = this.prevAttrMap.get(currUid);
			//if the timestamp is same then we can't measure speed. So don't store the latest node into the map.
			if (!prevNode.getTimestamp().equals(newNode.getTimestamp())){
				if (isCongested(prevNode,newNode)){
					long time = Math.abs(getDateDiff(prevNode.getTimestamp(),newNode.getTimestamp()));
					if (time<MAX_TIMESTAMP_INTERVAL){
						builder.addPoint(new Point(prevNode.getLatitude(),prevNode.getLongitude(),(int)time));
						this.stopCount++;
					}
				}			
				this.prevAttrMap.put(currUid,newNode);				
			}			
		}
		this.totalStopCount++;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {		
		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {		
		super.characters(ch, start, length);
	}
}
