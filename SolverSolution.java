package OnAireServlets;

public class SolverSolution {
	double cost;
	double[] idlingTimeProb;
	public SolverSolution(double cost, double[] idlingTimeProb) {
		super();
		this.cost = cost;
		this.idlingTimeProb = idlingTimeProb;
	}
	public double getCost() {
		return cost;
	}
	public double[] getIdlingTimeProb() {
		return idlingTimeProb;
	}
}
