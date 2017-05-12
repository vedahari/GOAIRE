package OnAireServlets;

import java.util.ArrayList;

import lpsolve.*;

class Constraint{
	String sConstraintFunction;
	int iRelation;
	double rhs;
	public Constraint(String sConstraintFunction, int iRelation, double rhs) {
		super();
		this.sConstraintFunction = sConstraintFunction;
		this.iRelation = iRelation;
		this.rhs = rhs;
	}	
}

/*
 * This is the minimal interface to the lp_solve library. 
 * Clients can add constraints, set objective function and 
 * solve the formulated linear programs.
 * */

public class LPSolver {
	private String sObjFunction;
	private ArrayList<Constraint> sConstraintList = new ArrayList<Constraint>();
	final int LESSER_THAN = 1;
	final int GREATER_THAN = 2;
	final int EQUAL_TO = 3;
	
	public void vSetObjective(String sFunction)
	{
		this.sObjFunction = sFunction;
	}
	public void vAddConstraint(String sFunction, int relationOperator, double rhs_value)
	{
		Constraint oConstraint = new Constraint(sFunction,relationOperator,rhs_value);
		sConstraintList.add(oConstraint); 
	}
	public SolverSolution vSolveLinearProgram()
	{
		SolverSolution sol = null;
		double[] var = null;
		try {
			// Create a problem with 4 variables and 0 constraints
			LpSolve solver = LpSolve.makeLp(0, 3*OnAire.getB());
			// add constraints
			//System.out.println("Solving the given problem!");
			for(int i=0;i<sConstraintList.size();i++)
			{
				//System.out.println("Constraint "+i+" "+sConstraintList.get(i).sConstraintFunction);				
				solver.strAddConstraint(sConstraintList.get(i).sConstraintFunction,						
						sConstraintList.get(i).iRelation, sConstraintList.get(i).rhs);
			}			
			// set objective function
			//System.out.println("Objective Function is : "+sObjFunction);
			solver.strSetObjFn(sObjFunction);
			// solve the problem
			solver.solve();
			// print solution
			System.out.println("Value of objective function: " +
					solver.getObjective()+"\n");
			var = solver.getPtrVariables();
			for (int i = 0; i < OnAire.getB(); i++) { //total var length is var.length
				//System.out.println("Value of var[" + i + "] = " + var[i]);
				System.out.print("  " + var[i]);
			}
			System.out.println("\n The above are the probabilities!");
			sol = new SolverSolution(solver.getObjective(), var);
			//solver.printLp();
			// delete the problem and free memory
			solver.deleteLp();
		}
		catch (LpSolveException e) {
			e.printStackTrace(); 
		}
		return sol;
	}
}
