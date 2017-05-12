package OnAireServlets;

/*
 * Class to denote the various GPS points
 * along with their stop points.
 */

public class StopPoint{
	private Point pt;
	private int stopTime;
	StopPoint(Point p, int stoptime){
		pt = p;
		stopTime = stoptime;
	}
	public Point getPt() {
		return pt;
	}
	public int getStopTime() {
		return stopTime;
	}
}
