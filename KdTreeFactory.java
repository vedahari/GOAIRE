package OnAireServlets;

import java.util.HashMap;
import java.util.Map;
/*
 * KdTreeFactory holds the root of various kdTrees built using the interface.
 * 
 * */
public class KdTreeFactory {
	private static Map<String,KdTree> KdTreeMap;
	
	public KdTreeFactory(){
		KdTreeMap = new HashMap<String,KdTree>();
	}
	
	public void addCity(String city){
		if (KdTreeMap.containsKey(city)){
			System.err.println("Already KdTree has been created for the city"+city);
			return;
		}
		System.out.println("Adding KdTreeMap for the city"+city);		
		KdTreeMap.put(city, new KdTree());
	}

	public static KdTree getKdTree(String city) {
		if (KdTreeMap.containsKey(city)){
			return KdTreeMap.get(city);
		}
		else{
			System.err.println("Factory doesn't have requested city!"+city);
			return null;			
		}	
	}
	
	public boolean containsCity(String city){
		if (city==null){
			return false;
		}
		else return KdTreeMap.containsKey(city);
	}

}
