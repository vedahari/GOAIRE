package OnAireServlets;

/*
 * IdlingTimeResponse is the 
 * 
 * */
public class IdlingTimeResponse {
	private int idlingTime;
	private double cost;
	private double uB;
	private double qB;
	
	public IdlingTimeResponse(){
		idlingTime = 0;
		uB = 0.0;
		qB = 0.0;
		cost = OnAire.getB();
	}
	
	public double getuB() {
		return uB;
	}

	public void setuB(double uB) {
		this.uB = uB;
	}

	public double getqB() {
		return qB;
	}

	public void setqB(double qB) {
		this.qB = qB;
	}
	
	public IdlingTimeResponse(int idlingTime, double cost2, double uB2, double qB2) {		
		super();
		this.idlingTime = idlingTime;
		this.cost = cost2;
		this.uB = uB2;
		this.qB = qB2;
	}

	public int getIdlingTime() {
		return idlingTime;
	}
	public double getCost() {
		return cost;
	}

	public void setIdlingTime(int idlingTime) {
		this.idlingTime = idlingTime;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "IdlingTimeResponse [idlingTime=" + idlingTime + ", cost=" + cost + "]";
	}	
}
