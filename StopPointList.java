package OnAireServlets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StopPointList {	
	private List<StopPoint> pts;
	public StopPointList(){
		pts = new ArrayList<StopPoint>();		
	}
	public void add (StopPoint mo){
		pts.add(mo);
	}
	public void add (Double lat, Double lon, int stopTime){		
		Point p = new Point(lat, lon,stopTime);
		StopPoint sp = new StopPoint(p, stopTime);
		pts.add(sp);
	}
	
	public void order (){
		Collections.sort(pts, new Comparator<Object>(){
			public int compare(Object ob1, Object ob2){
				Point o1 = (Point)ob1;
				Point o2 =  (Point)ob2;
				return o1.compareTo(o2);				
			}
		});		
	}	
	public int getSize(){
		return pts.size();
	}
	public void print(){
		for (int i=0;i<pts.size();i++){
			System.out.println(pts.get(i));
		}
	}
	public void clear(){
		pts.clear();
	}
}
