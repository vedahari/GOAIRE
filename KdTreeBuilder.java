package OnAireServlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/*
 * Builds the KdTree initially from the populated 
 * StopPointList. Clients should populate the StopPointList "pts" 
 * first and then build the KdTree.
 */
public class KdTreeBuilder {
	
	private ArrayList<Point> pts;
	private KdTree kdRoot;
	
	public KdTreeBuilder(String city){
		pts = new ArrayList<Point>();
		kdRoot = KdTreeFactory.getKdTree(city);
	}
	
	public void addPoint(Point p){
		pts.add(p);
	}
	
	private void build_helper(int start, int end, int direction){
		if (start<=end){			
			direction=(direction==0)?1:0;
			order(start,end,direction);			
			int mid = start+(end-start)/2;
			kdRoot.insert(pts.get(mid));			
			build_helper(start,mid-1,direction);
			build_helper(mid+1,end,direction);			
		}
	}
	
	public void build(){
		build_helper(0,pts.size()-1,1);
	}
	
	//end parameter is inclusive
	public void order (int start,int end,int direction){
		if (direction==0){
		Collections.sort(pts.subList(start,end+1), new Comparator<Object>(){
			public int compare(Object ob1, Object ob2){
				Point o1 = (Point)ob1;
				Point o2 =  (Point)ob2;
				if (o1.x().equals(o2.x())){					
					return o1.y().compareTo(o2.y());					
				}
				else {					
					return (o1.x().compareTo(o2.x()));
				}
			}
		});		
		}
		else {
			Collections.sort(pts.subList(start,end+1), new Comparator<Object>(){
				public int compare(Object ob1, Object ob2){
					Point o1 = (Point)ob1;
					Point o2 =  (Point)ob2;
					if (o1.y().equals(o2.y())){					
						return o1.x().compareTo(o2.x());						
					}
					else {					
						return (o1.y().compareTo(o2.y()));
					}
				}
			});	
		}
	}
	
	public void clear(){
		pts.clear();		
	}

	public void print_all_stoplength() {		
		System.out.println("The total "+pts.size()+" stops are as follows!");
		String output =" ";
		for (int i=0;i<pts.size();i++){
			output+=" "+Integer.toString(pts.get(i).getStopTime());
		}
		System.out.println(output);		
	}
	
	/************************************************************************/
	
	/*
	 * The following methods are for debugging only. 
	 * The methods output various details about the stop points in 
	 * different format.
	 * 
	 * */
	/************************************************************************/
	
	/*
	 * This debugging method is to output the stop lengths of various stops.
	 * Also, the frequency distribution of various stop lengths is also 
	 * produced as output. This can be useful in cases like 
	 * comparing the results with OnAire.
	 * 
	 * */
	
	public void writeSLToFile(String filename){
		System.out.println("Writing the kdTree to the output file "+filename.toString());
		File file = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try{	
			file = new File(filename);
			if (!file.exists()){
				file.createNewFile();
			}
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			
			
			HashMap<Integer,Integer> freqCount = new HashMap<Integer,Integer>();
			freqCount.put(15, 0);
			freqCount.put(28, 0);
			freqCount.put(50,0);
			freqCount.put(100, 0);
			freqCount.put(200, 0);
			freqCount.put(300, 0);
			freqCount.put(500, 0);
			freqCount.put(2000, 0);
			
			
			for (int i=0;i<pts.size();i++){
				//bw.write(String.format("%f\t%f\n", pts.get(i).x(),pts.get(i).y()));
				bw.write(String.format("%d ",pts.get(i).getStopTime()));				
				
				/* Freq count section starts **/
				if (pts.get(i).getStopTime()<=15){
					freqCount.put(15,freqCount.get(15)+1);					
				}
				else if (pts.get(i).getStopTime()<=28){
					freqCount.put(28,freqCount.get(28)+1);					
				}
				else if (pts.get(i).getStopTime()<=50){
					freqCount.put(50,freqCount.get(50)+1);					
				}
				else if (pts.get(i).getStopTime()<=100){
					freqCount.put(100,freqCount.get(100)+1);					
				}
				else if (pts.get(i).getStopTime()<=200){
					freqCount.put(200,freqCount.get(200)+1);					
				}
				else if (pts.get(i).getStopTime()<=300){
					freqCount.put(300,freqCount.get(300)+1);					
				}
				else if (pts.get(i).getStopTime()<=500){
					freqCount.put(500,freqCount.get(500)+1);					
				}
				else if (pts.get(i).getStopTime()<=2000){
					freqCount.put(2000,freqCount.get(2000)+1);					
				}
				
				/*Freq count section ends*/
			}
			
			
			for(Map.Entry<Integer, Integer> entry: freqCount.entrySet())
			{
				System.out.println("Key :: "+entry.getKey()+"   Value "+entry.getValue());
			}			
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				if (bw!=null){
					bw.close();					
				}
				if (fw!=null){
					fw.close();
				}
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		
	}
	
	/*
	 * Writes stop point locations to external file.
	 */
	public void writeGPSToFile(String filename){
		System.out.println("Writing the kdTree to the output file "+filename.toString());
		File file = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try{	
			file = new File(filename);
			if (!file.exists()){
				file.createNewFile();
			}			
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			for (int i=0;i<pts.size();i++){
				bw.write(String.format("%f\t%f\n", pts.get(i).x(),pts.get(i).y()));
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				if (bw!=null){
					bw.close();					
				}
				if (fw!=null){
					fw.close();
				}
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		
	}
	
	/*
	 * Writes stop point location and stop length to an external file.
 	 * 
	 * */
	
	public void writePointsToFile(String filename){
		System.out.println("Writing the kdTree to the output file "+filename.toString());
		File file = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try{	
			file = new File(filename);
			if (!file.exists()){
				file.createNewFile();
			}			
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			for (int i=0;i<pts.size();i++){
				bw.write(String.format("%f\t%f\t%d\n", pts.get(i).x(),pts.get(i).y(),pts.get(i).getStopTime()));
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				if (bw!=null){
					bw.close();					
				}
				if (fw!=null){
					fw.close();
				}
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}		
	}	
}
/************************************************************************/