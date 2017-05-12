package OnAireServlets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.Attributes;

public class OSMNode {
	private Double longitude;
	private Double latitude;
	private String uid;
	private Date timestamp;
	private String node_id;
	@SuppressWarnings("deprecation")
	public OSMNode(Attributes attr) {
		for (int i=0;i<attr.getLength();i++){
			switch(attr.getQName(i)){
			case "lat":
				this.latitude = Double.parseDouble(attr.getValue(i));
				break;
			case "lon":
				this.longitude = Double.parseDouble(attr.getValue(i));
				break;
			case "timestamp":				
				SimpleDateFormat dtformatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				try {
					//System.out.println("Received timestamp "+attr.getValue(i));
					this.timestamp = dtformatter.parse(attr.getValue(i));		
					//System.out.println("Recorded timestamp "+this.timestamp.toString());
				} catch (ParseException e) {				
					e.printStackTrace();
				}				
				break;
			case "uid":
				this.uid = attr.getValue(i)+'_'+this.timestamp.getYear()+this.timestamp.getMonth()+this.timestamp.getDate();				
				break;
				
			case "id":
				this.node_id = attr.getValue(i);
				break;
			}			
		}
	}
	public Date getTimestamp() {
		return this.timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Double getLongitude() {
		return this.longitude;
	}
	public Double getLatitude() {
		return this.latitude;
	}
	public String getUid() {
		return this.uid;
	}
	public String getNodeId() {
		return this.node_id;
	}
}
